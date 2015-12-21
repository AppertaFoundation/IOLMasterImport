/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes.models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author VEDELEKT
 */
@Entity
@Table(name = "dicom_eye_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DicomEyeStatus.findAll", query = "SELECT d FROM DicomEyeStatus d"),
    @NamedQuery(name = "DicomEyeStatus.findById", query = "SELECT d FROM DicomEyeStatus d WHERE d.id = :id"),
    @NamedQuery(name = "DicomEyeStatus.findByName", query = "SELECT d FROM DicomEyeStatus d WHERE d.name = :name")})
public class DicomEyeStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", columnDefinition = "SIGNED INT(10)")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eyeStatusRight")
    private Collection<EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eyeStatusLeft")
    private Collection<EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection1;

    public DicomEyeStatus() {
    }

    public DicomEyeStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<EtOphinbiometryMeasurement> getEtOphinbiometryMeasurementCollection() {
        return etOphinbiometryMeasurementCollection;
    }

    public void setEtOphinbiometryMeasurementCollection(Collection<EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection) {
        this.etOphinbiometryMeasurementCollection = etOphinbiometryMeasurementCollection;
    }

    @XmlTransient
    public Collection<EtOphinbiometryMeasurement> getEtOphinbiometryMeasurementCollection1() {
        return etOphinbiometryMeasurementCollection1;
    }

    public void setEtOphinbiometryMeasurementCollection1(Collection<EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection1) {
        this.etOphinbiometryMeasurementCollection1 = etOphinbiometryMeasurementCollection1;
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
        if (!(object instanceof DicomEyeStatus)) {
            return false;
        }
        DicomEyeStatus other = (DicomEyeStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.DicomEyeStatus[ id=" + id + " ]";
    }
    
}
