/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author VEDELEKT
 */
@Entity
@Table(name = "eye")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eye.findAll", query = "SELECT e FROM Eye e"),
    @NamedQuery(name = "Eye.findById", query = "SELECT e FROM Eye e WHERE e.id = :id"),
    @NamedQuery(name = "Eye.findByName", query = "SELECT e FROM Eye e WHERE e.name = :name"),
    @NamedQuery(name = "Eye.findByDisplayOrder", query = "SELECT e FROM Eye e WHERE e.displayOrder = :displayOrder"),
    @NamedQuery(name = "Eye.findByCreatedDate", query = "SELECT e FROM Eye e WHERE e.createdDate = :createdDate"),
    @NamedQuery(name = "Eye.findByLastModifiedDate", query = "SELECT e FROM Eye e WHERE e.lastModifiedDate = :lastModifiedDate")})
public class Eye implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "display_order")
    private int displayOrder;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.DATE)
    private Date lastModifiedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eyeId")
    private Collection<EtOphinbiometryBiometrydat> etOphinbiometryBiometrydatCollection;
    @OneToMany(mappedBy = "eyeId")
    private Collection<Episode> episodeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eyeId")
    private Collection<EtOphinbiometrySelection> etOphinbiometrySelectionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eyeId")
    private Collection<EtOphinbiometryCalculation> etOphinbiometryCalculationCollection;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eyeId")
    private Collection<EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection;

    public Eye() {
    }

    public Eye(Integer id) {
        this.id = id;
    }

    public Eye(Integer id, int displayOrder, Date createdDate, Date lastModifiedDate) {
        this.id = id;
        this.displayOrder = displayOrder;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
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

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @XmlTransient
    public Collection<EtOphinbiometryBiometrydat> getEtOphinbiometryBiometrydatCollection() {
        return etOphinbiometryBiometrydatCollection;
    }

    public void setEtOphinbiometryBiometrydatCollection(Collection<EtOphinbiometryBiometrydat> etOphinbiometryBiometrydatCollection) {
        this.etOphinbiometryBiometrydatCollection = etOphinbiometryBiometrydatCollection;
    }

    @XmlTransient
    public Collection<Episode> getEpisodeCollection() {
        return episodeCollection;
    }

    public void setEpisodeCollection(Collection<Episode> episodeCollection) {
        this.episodeCollection = episodeCollection;
    }

    @XmlTransient
    public Collection<EtOphinbiometrySelection> getEtOphinbiometrySelectionCollection() {
        return etOphinbiometrySelectionCollection;
    }

    public void setEtOphinbiometrySelectionCollection(Collection<EtOphinbiometrySelection> etOphinbiometrySelectionCollection) {
        this.etOphinbiometrySelectionCollection = etOphinbiometrySelectionCollection;
    }

    @XmlTransient
    public Collection<EtOphinbiometryCalculation> getEtOphinbiometryCalculationCollection() {
        return etOphinbiometryCalculationCollection;
    }

    public void setEtOphinbiometryCalculationCollection(Collection<EtOphinbiometryCalculation> etOphinbiometryCalculationCollection) {
        this.etOphinbiometryCalculationCollection = etOphinbiometryCalculationCollection;
    }

    public User getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(User createdUserId) {
        this.createdUserId = createdUserId;
    }

    public User getLastModifiedUserId() {
        return lastModifiedUserId;
    }

    public void setLastModifiedUserId(User lastModifiedUserId) {
        this.lastModifiedUserId = lastModifiedUserId;
    }

    @XmlTransient
    public Collection<EtOphinbiometryMeasurement> getEtOphinbiometryMeasurementCollection() {
        return etOphinbiometryMeasurementCollection;
    }

    public void setEtOphinbiometryMeasurementCollection(Collection<EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection) {
        this.etOphinbiometryMeasurementCollection = etOphinbiometryMeasurementCollection;
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
        if (!(object instanceof Eye)) {
            return false;
        }
        Eye other = (Eye) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.Eye[ id=" + id + " ]";
    }
    
}
