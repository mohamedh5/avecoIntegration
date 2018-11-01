/**
 * 
 */
package com.dmc.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dmc.mam.model.MetaData;

/**
 * @author Mohamed Hussein
 *
 */
@Repository
public interface MetaDataJpaRepository extends JpaRepository<MetaData, String> {

}
