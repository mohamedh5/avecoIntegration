/**
 * 
 */
package com.dmc.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dmc.mam.model.Material;

/**
 * @author Mohamed Hussein
 *
 */
@Repository
public interface MaterialJpaRepository extends JpaRepository<Material, String> {

}
