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
@Table(name = "specialty")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Specialty.findAll", query = "SELECT s FROM Specialty s"),
    @NamedQuery(name = "Specialty.findById", query = "SELECT s FROM Specialty s WHERE s.id = :id"),
    @NamedQuery(name = "Specialty.findByName", query = "SELECT s FROM Specialty s WHERE s.name = :name"),
    @NamedQuery(name = "Specialty.findByCode", query = "SELECT s FROM Specialty s WHERE s.code = :code"),
    @NamedQuery(name = "Specialty.findByDefaultTitle", query = "SELECT s FROM Specialty s WHERE s.defaultTitle = :defaultTitle"),
    @NamedQuery(name = "Specialty.findByDefaultIsSurgeon", query = "SELECT s FROM Specialty s WHERE s.defaultIsSurgeon = :defaultIsSurgeon"),
    @NamedQuery(name = "Specialty.findByAdjective", query = "SELECT s FROM Specialty s WHERE s.adjective = :adjective"),
    @NamedQuery(name = "Specialty.findByAbbreviation", query = "SELECT s FROM Specialty s WHERE s.abbreviation = :abbreviation"),
    @NamedQuery(name = "Specialty.findByCreatedDate", query = "SELECT s FROM Specialty s WHERE s.createdDate = :createdDate"),
    @NamedQuery(name = "Specialty.findByLastModifiedDate", query = "SELECT s FROM Specialty s WHERE s.lastModifiedDate = :lastModifiedDate")})
public class Specialty implements Serializable {
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
    @Column(name = "code")
    private int code;
    @Basic(optional = false)
    @Column(name = "default_title")
    private String defaultTitle;
    @Basic(optional = false)
    @Column(name = "default_is_surgeon")
    private boolean defaultIsSurgeon;
    @Basic(optional = false)
    @Column(name = "adjective")
    private String adjective;
    @Basic(optional = false)
    @Column(name = "abbreviation", columnDefinition = "char")
    private String abbreviation;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.DATE)
    private Date lastModifiedDate;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @JoinColumn(name = "specialty_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SpecialtyType specialtyTypeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialtyId")
    private Collection<Subspecialty> subspecialtyCollection;
    @OneToMany(mappedBy = "specialtyId")
    private Collection<Disorder> disorderCollection;

    public Specialty() {
    }

    public Specialty(Integer id) {
        this.id = id;
    }

    public Specialty(Integer id, String name, int code, String defaultTitle, boolean defaultIsSurgeon, String adjective, String abbreviation, Date createdDate, Date lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.defaultTitle = defaultTitle;
        this.defaultIsSurgeon = defaultIsSurgeon;
        this.adjective = adjective;
        this.abbreviation = abbreviation;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public void setDefaultTitle(String defaultTitle) {
        this.defaultTitle = defaultTitle;
    }

    public boolean getDefaultIsSurgeon() {
        return defaultIsSurgeon;
    }

    public void setDefaultIsSurgeon(boolean defaultIsSurgeon) {
        this.defaultIsSurgeon = defaultIsSurgeon;
    }

    public String getAdjective() {
        return adjective;
    }

    public void setAdjective(String adjective) {
        this.adjective = adjective;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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

    public SpecialtyType getSpecialtyTypeId() {
        return specialtyTypeId;
    }

    public void setSpecialtyTypeId(SpecialtyType specialtyTypeId) {
        this.specialtyTypeId = specialtyTypeId;
    }

    @XmlTransient
    public Collection<Subspecialty> getSubspecialtyCollection() {
        return subspecialtyCollection;
    }

    public void setSubspecialtyCollection(Collection<Subspecialty> subspecialtyCollection) {
        this.subspecialtyCollection = subspecialtyCollection;
    }

    @XmlTransient
    public Collection<Disorder> getDisorderCollection() {
        return disorderCollection;
    }

    public void setDisorderCollection(Collection<Disorder> disorderCollection) {
        this.disorderCollection = disorderCollection;
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
        if (!(object instanceof Specialty)) {
            return false;
        }
        Specialty other = (Specialty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.Specialty[ id=" + id + " ]";
    }
    
}
