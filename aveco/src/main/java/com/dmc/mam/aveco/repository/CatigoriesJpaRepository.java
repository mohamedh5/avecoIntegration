package com.dmc.mam.aveco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dmc.mam.aveco.model.Catigories;

public interface CatigoriesJpaRepository extends JpaRepository<Catigories, String> {

//	@Query("SELECT C.fullName FROM Catigories C "
//			+ "JOIN C.parent P "
//			+ "WHERE C.shortName = :shortName "
//			+ "AND P.shortName = :parentShortName")
//	public String getFullNameByShortName(String parentShortName,String shortName);
	
	@Query("SELECT DISTINCT C.fullName FROM Catigories C "
			+ "WHERE C.shortName = :shortName ")
	public String getFullNameByShortName(String shortName);
}
