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
@Table(name = "subspecialty")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subspecialty.findAll", query = "SELECT s FROM Subspecialty s"),
    @NamedQuery(name = "Subspecialty.findById", query = "SELECT s FROM Subspecialty s WHERE s.id = :id"),
    @NamedQuery(name = "Subspecialty.findByName", query = "SELECT s FROM Subspecialty s WHERE s.name = :name"),
    @NamedQuery(name = "Subspecialty.findByRefSpec", query = "SELECT s FROM Subspecialty s WHERE s.refSpec = :refSpec"),
    @NamedQuery(name = "Subspecialty.findByLastModifiedDate", query = "SELECT s FROM Subspecialty s WHERE s.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "Subspecialty.findByCreatedDate", query = "SELECT s FROM Subspecialty s WHERE s.createdDate = :createdDate")})
public class Subspecialty implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "ref_spec")
    private String refSpec;
    @Basic(optional = false)
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subspecialtyId")
    private Collection<ServiceSubspecialtyAssignment> serviceSubspecialtyAssignmentCollection;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @JoinColumn(name = "specialty_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Specialty specialtyId;

    public Subspecialty() {
    }

    public Subspecialty(Integer id) {
        this.id = id;
    }

    public Subspecialty(Integer id, String name, String refSpec, Date lastModifiedDate, Date createdDate) {
        this.id = id;
        this.name = name;
        this.refSpec = refSpec;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
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

    public String getRefSpec() {
        return refSpec;
    }

    public void setRefSpec(String refSpec) {
        this.refSpec = refSpec;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @XmlTransient
    public Collection<ServiceSubspecialtyAssignment> getServiceSubspecialtyAssignmentCollection() {
        return serviceSubspecialtyAssignmentCollection;
    }

    public void setServiceSubspecialtyAssignmentCollection(Collection<ServiceSubspecialtyAssignment> serviceSubspecialtyAssignmentCollection) {
        this.serviceSubspecialtyAssignmentCollection = serviceSubspecialtyAssignmentCollection;
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

    public Specialty getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Specialty specialtyId) {
        this.specialtyId = specialtyId;
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
        if (!(object instanceof Subspecialty)) {
            return false;
        }
        Subspecialty other = (Subspecialty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.Subspecialty[ id=" + id + " ]";
    }
    
}
