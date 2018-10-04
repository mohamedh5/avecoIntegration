package com.dmc.mam.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.annotations.GenericGenerator;

/**
 * This is a simple class , which main task is to bound a key and a value
 * together in one object , So it can be mapped throw JAXB (ASTRA XML).
 *
 * @author Mohamed Hussein
 * @version 1.0
 * @since 7/9/2018
 */
@Entity
@Table(
		name = "metadata",
		indexes = {@Index(name= "metadata_Index",columnList= "md_key,md_value",unique= false)}
		)
public class MetaData {

	@Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid" , strategy = "uuid2")
	private String id;
	@Column(name="md_key")
	private String key;
	@Column(name="md_value",columnDefinition = "text")
	private String value;

	/**
     * Get the ID of the object
     * @return the id
     */
	public String getId() {
		return id;
	}

	/**
     * Set the ID of the object normally , it won't be set by user as ORM will generate the ID(as UUID of 40 char)
     * @param id the id to set
     */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * Get the Key
     * @return the key
     */
    @XmlElement(name = "ebepName")
    public String getKey() {
        return key;
    }

    /**
     * Set the Key 
     * 
     * the key will be something like :- epebDescription , ebepSeason , eAutoQC
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Get the value
     * @return the value
     */
    @XmlElement(name = "ebepValue")
    public String getValue() {
        return value;
    }

    /**
     * Set the value
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}