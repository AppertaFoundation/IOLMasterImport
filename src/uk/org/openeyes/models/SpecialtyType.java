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
@Table(name = "specialty_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SpecialtyType.findAll", query = "SELECT s FROM SpecialtyType s"),
    @NamedQuery(name = "SpecialtyType.findById", query = "SELECT s FROM SpecialtyType s WHERE s.id = :id"),
    @NamedQuery(name = "SpecialtyType.findByName", query = "SELECT s FROM SpecialtyType s WHERE s.name = :name"),
    @NamedQuery(name = "SpecialtyType.findByDisplayOrder", query = "SELECT s FROM SpecialtyType s WHERE s.displayOrder = :displayOrder"),
    @NamedQuery(name = "SpecialtyType.findByLastModifiedDate", query = "SELECT s FROM SpecialtyType s WHERE s.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "SpecialtyType.findByCreatedDate", query = "SELECT s FROM SpecialtyType s WHERE s.createdDate = :createdDate"),
    @NamedQuery(name = "SpecialtyType.findByActive", query = "SELECT s FROM SpecialtyType s WHERE s.active = :active")})
public class SpecialtyType implements Serializable {
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
    @Column(name = "display_order")
    private int displayOrder;
    @Basic(optional = false)
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialtyTypeId")
    private Collection<Specialty> specialtyCollection;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;

    public SpecialtyType() {
    }

    public SpecialtyType(Integer id) {
        this.id = id;
    }

    public SpecialtyType(Integer id, String name, int displayOrder, Date lastModifiedDate, Date createdDate, boolean active) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.active = active;
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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @XmlTransient
    public Collection<Specialty> getSpecialtyCollection() {
        return specialtyCollection;
    }

    public void setSpecialtyCollection(Collection<Specialty> specialtyCollection) {
        this.specialtyCollection = specialtyCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SpecialtyType)) {
            return false;
        }
        SpecialtyType other = (SpecialtyType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.SpecialtyType[ id=" + id + " ]";
    }
    
}
