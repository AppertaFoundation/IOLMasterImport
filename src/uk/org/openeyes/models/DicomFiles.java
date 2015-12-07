/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VEDELEKT
 */
@Entity
@Table(name = "dicom_files")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DicomFiles.findAll", query = "SELECT d FROM DicomFiles d"),
    @NamedQuery(name = "DicomFiles.findById", query = "SELECT d FROM DicomFiles d WHERE d.id = :id"),
    @NamedQuery(name = "DicomFiles.findByEntryDateTime", query = "SELECT d FROM DicomFiles d WHERE d.entryDateTime = :entryDateTime"),
    @NamedQuery(name = "DicomFiles.findByFilename", query = "SELECT d FROM DicomFiles d WHERE d.filename = :filename"),
    @NamedQuery(name = "DicomFiles.findByFilesize", query = "SELECT d FROM DicomFiles d WHERE d.filesize = :filesize"),
    @NamedQuery(name = "DicomFiles.findByFiledate", query = "SELECT d FROM DicomFiles d WHERE d.filedate = :filedate"),
    @NamedQuery(name = "DicomFiles.findByProcessorId", query = "SELECT d FROM DicomFiles d WHERE d.processorId = :processorId")})
public class DicomFiles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "entry_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDateTime;
    @Basic(optional = false)
    @Column(name = "filename")
    private String filename;
    @Column(name = "filesize", columnDefinition = "int")
    private Long filesize;
    @Basic(optional = false)
    @Column(name = "filedate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date filedate;
    @Column(name = "processor_id")
    private String processorId;

    public DicomFiles() {
    }

    public DicomFiles(Integer id) {
        this.id = id;
    }

    public DicomFiles(Integer id, Date entryDateTime, String filename, Date filedate) {
        this.id = id;
        this.entryDateTime = entryDateTime;
        this.filename = filename;
        this.filedate = filedate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(Date entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public Date getFiledate() {
        return filedate;
    }

    public void setFiledate(Date filedate) {
        this.filedate = filedate;
    }

    public String getProcessorId() {
        return processorId;
    }

    public void setProcessorId(String processorId) {
        this.processorId = processorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DicomFiles)) {
            return false;
        }
        DicomFiles other = (DicomFiles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.DicomFiles[ id=" + id + " ]";
    }
    
}
