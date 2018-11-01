package com.dmc.mam.aveco.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class HistoryManager {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;

	@OneToMany(orphanRemoval = true ,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<MetadataHistory> metadataHistory;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the metadataHistory
	 */
	public List<MetadataHistory> getMetadataHistory() {
		return metadataHistory;
	}

	/**
	 * @param metadataHistory the metadataHistory to set
	 */
	public void setMetadataHistory(List<MetadataHistory> metadataHistory) {
		this.metadataHistory = metadataHistory;
	}
	
	
}
