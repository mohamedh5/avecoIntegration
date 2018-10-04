package com.dmc.mam.aveco.config;

import javax.persistence.EntityManagerFactory;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.dmc.mam.aveco.service.JobListener;
import com.dmc.mam.model.Material;
import com.dmc.mam.model.MetaData;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public EntityManagerFactory entityManagerFactory;

	@Bean
	public Job avecoJob(JobListener listener) {
		return this.jobBuilderFactory.get("test").incrementer(new RunIdIncrementer()).listener(listener)
				.start(avecoStep()).build();
	}

	@Bean
	@Retryable(value = {LockAcquisitionException.class,ConstraintViolationException.class},maxAttempts = 3 ,backoff = @Backoff(delay = 10000 , maxDelay = 60000) )
	public Step avecoStep() {
		return stepBuilderFactory.get("step1")
				.<Material, Material>chunk(20)
				.reader(reader(null)).
				processor(processor())
				.writer(writer())
				.build();
	}

	@Bean
	public MaterialProcessor processor() {
		return new MaterialProcessor();
	}

	@Bean
	public JpaItemWriter<Material> writer() {
		JpaItemWriter<Material> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}

	@Bean
	@StepScope
	StaxEventItemReader<Material> reader(@Value("#{jobParameters[fileLocation]}") String fileLocation) {
		StaxEventItemReader<Material> xmlFileReader = new StaxEventItemReader<>();
		xmlFileReader.setResource(new FileSystemResource(fileLocation));
		xmlFileReader.setFragmentRootElementName("MatDescr");

		Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
		studentMarshaller.setClassesToBeBound(Material.class, MetaData.class);
		xmlFileReader.setUnmarshaller(studentMarshaller);

		return xmlFileReader;
	}
}
