package com.dmc.mam.aveco.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Catigories {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;
	private String fullName;
	private String shortName;
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.MERGE)
	private Catigories parent;
	@OneToMany(mappedBy="parent",fetch=FetchType.LAZY,cascade=CascadeType.MERGE,orphanRemoval=true)
	private List<Catigories> subCat;


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the subCat
	 */
	public List<Catigories> getSubCat() {
		return subCat;
	}

	/**
	 * @param subCat the subCat to set
	 */
	public void setSubCat(List<Catigories> subCat) {
		this.subCat = subCat;
	}

	/**
	 * @return the parent
	 */
	public Catigories getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Catigories parent) {
		this.parent = parent;
	}
	
}
