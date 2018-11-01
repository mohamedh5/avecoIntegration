package com.dmc.mam.aveco.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * this class hold all the data needed from ASTRA XML
 *
 * @author Automation/Mohamed
 * @version 1.0
 * @since 7/9/2018
 */
@XmlType(
        propOrder = {
            "id",
            "operation",
            "idec",
            "jobId",
            "device",
            "recId",
            "inaddr",
            "outaddr",
            "writeProtected",
            "name",
            "cat1",
            "cat2",
            "mainQC",
            "cat4",
            "metadata",
            "created",
            "modified",
            "origInAddr",
            "origOutAddr",
        })
@XmlRootElement(name = "MatDescr")
@Entity
public class Material {
	@Id
    private String id;
    private String name;
    @Column(name = "action" ,length = 1)
    private String operation;
    private String idec;
    private String jobId;
    private String recId;
    private String device;
    private String inaddr;
    private String outaddr;
    private String origInAddr;
    private String origOutAddr;
    private int writeProtected;
    private String cat1;
    private String cat2;
    private String cat4;
    private String mainQC;
    private LocalDateTime created;
    private LocalDateTime modified;
    @OneToMany(orphanRemoval = true ,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<MetaData> metadata;
    @OneToMany(orphanRemoval = true ,cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    private List<HistoryManager> history;
    
    
    

    /**
     * @return the id
     */
    @XmlElement(name = "Id")
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the operation
     */
    @XmlElement(name = "Operation")
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the idec
     */
    @XmlElement(name = "Idec")
    public String getIdec() {
        return idec;
    }

    /**
     * @param idec the idec to set
     */
    public void setIdec(String idec) {
        this.idec = idec;
    }

    /**
     * @return the jobId
     */
    @XmlElement(name = "JobId")
    public String getJobId() {
        return jobId;
    }

    /**
     * @param jobId the jobId to set
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * @return the recId
     */
    @XmlElement(name = "MatPath")
    public String getRecId() {
        return recId;
    }

    /**
     * @param recId the recId to set
     */
    public void setRecId(String recId) {
        this.recId = recId;
    }

    /**
     * @return the device
     */
    @XmlElement(name = "Device")
    public String getDevice() {
        return device;
    }

    /**
     * @param device the device to set
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * @return the inaddr
     */
    @XmlElement(name = "InAddr")
    public String getInaddr() {
        return inaddr;
    }

    /**
     * @param inaddr the inaddr to set
     */
    public void setInaddr(String inaddr) {
        this.inaddr = inaddr;
    }

    /**
     * @return the outaddr
     */
    @XmlElement(name = "OutAddr")
    public String getOutaddr() {
        return outaddr;
    }

    /**
     * @param outaddr the outaddr to set
     */
    public void setOutaddr(String outaddr) {
        this.outaddr = outaddr;
    }

    /**
     * @return the origInAddr
     */
    @XmlElement(name = "OrigInAddr")
    public String getOrigInAddr() {
        return origInAddr;
    }

    /**
     * @param origInAddr the origInAddr to set
     */
    public void setOrigInAddr(String origInAddr) {
        this.origInAddr = origInAddr;
    }

    /**
     * @return the origOutAddr
     */
    @XmlElement(name = "OrigOutAddr")
    public String getOrigOutAddr() {
        return origOutAddr;
    }

    /**
     * @param origOutAddr the origOutAddr to set
     */
    public void setOrigOutAddr(String origOutAddr) {
        this.origOutAddr = origOutAddr;
    }

    /**
     * @return the writeProtected
     */
    @XmlElement(name = "WriteProtected")
    public int getWriteProtected() {
        return writeProtected;
    }

    /**
     * @param writeProtected the writeProtected to set
     */
    public void setWriteProtected(int writeProtected) {
        this.writeProtected = writeProtected;
    }

    /**
     * @return the cat1
     */
    @XmlElement(name = "Crit1")
    public String getCat1() {
        return cat1;
    }

    /**
     * @param cat1 the cat1 to set
     */
    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    /**
     * @return the cat2
     */
    @XmlElement(name = "Crit2")
    public String getCat2() {
        return cat2;
    }

    /**
     * @param cat2 the cat2 to set
     */
    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    /**
     * @return the cat4
     */
    @XmlElement(name = "Crit4")
    public String getCat4() {
        return cat4;
    }

    /**
     * @param cat4 the cat4 to set
     */
    public void setCat4(String cat4) {
        this.cat4 = cat4;
    }

    /**
     * @return the mainQC
     */
    @XmlElement(name = "Crit3")
    public String getMainQC() {
        return mainQC;
    }

    /**
     * @param mainQC the mainQC to set
     */
    public void setMainQC(String mainQC) {
        this.mainQC = mainQC;
    }

    /**
     * @return the metadata
     */
    @XmlElementWrapper(name = "EbEPTokens")
    @XmlElement(name = "ebepDescr")
    public List<MetaData> getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(List<MetaData> metadata) {
        this.metadata = metadata;
    }

    /**
     * @return the created
     */
    @XmlElement(name = "Created")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * @return the modified
     */
    @XmlElement(name = "Modified")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }
    /**
	 * @return the History
	 */
    //@XmlTransient
	public List<HistoryManager> geHistory() {
		return history;
	}

	/**
	 * @param metadataHistory the History to add
	 */
	public void addHistory(HistoryManager history) {
		if(this.history == null) 
			this.history = new ArrayList<>();
		if(this.history.size() > 2) {
			this.history.remove(0);
		}
		this.history.add(history);
	}

	/**
	 * @param history the history to set
	 */
	public void setHistory(List<HistoryManager> history) {
		this.history = history;
	}
	
	
}
