package com.dmc.mam.model;

import java.util.List;

/**
 *This is a simple class that has no representation in the database as it has only one field , which is a list of all the materials in the XML.
 * <p>
 * Normally  it will be one object in the list , except in case of the full aveco database content.
 * 
 * @author Automation/Mohamed
 * @version 1.0
 * @since 7/9/2018
 */
public class MaterialList {
	private List<Material> materials;
	
	/**
     * @return the materials
     */
    public List<Material> getMaterials() {
        return materials;
    }

    /**
     * @param materials the materials to set
     */
    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
}
