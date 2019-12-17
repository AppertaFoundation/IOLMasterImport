/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.math.BigInteger;
import javax.persistence.Basic;
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
@Table(name = "disorder")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disorder.findAll", query = "SELECT d FROM Disorder d"),
    @NamedQuery(name = "Disorder.findById", query = "SELECT d FROM Disorder d WHERE d.id = :id"),
    @NamedQuery(name = "Disorder.findByFullySpecifiedName", query = "SELECT d FROM Disorder d WHERE d.fullySpecifiedName = :fullySpecifiedName"),
    @NamedQuery(name = "Disorder.findByTerm", query = "SELECT d FROM Disorder d WHERE d.term = :term"),
    @NamedQuery(name = "Disorder.findByLastModifiedDate", query = "SELECT d FROM Disorder d WHERE d.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "Disorder.findByCreatedDate", query = "SELECT d FROM Disorder d WHERE d.createdDate = :createdDate"),
    @NamedQuery(name = "Disorder.findByActive", query = "SELECT d FROM Disorder d WHERE d.active = :active")})
public class Disorder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "fully_specified_name")
    private String fullySpecifiedName;
    @Basic(optional = false)
    @Column(name = "term")
    private String term;
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
    @OneToMany(mappedBy = "disorderId")
    private Collection<Episode> episodeCollection;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @JoinColumn(name = "specialty_id", referencedColumnName = "id")
    @ManyToOne
    private Specialty specialtyId;

    public Disorder() {
    }

    public Disorder(BigInteger id) {
        this.id = id;
    }

    public Disorder(BigInteger id, String fullySpecifiedName, String term, Date lastModifiedDate, Date createdDate, boolean active) {
        this.id = id;
        this.fullySpecifiedName = fullySpecifiedName;
        this.term = term;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.active = active;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getFullySpecifiedName() {
        return fullySpecifiedName;
    }

    public void setFullySpecifiedName(String fullySpecifiedName) {
        this.fullySpecifiedName = fullySpecifiedName;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
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
    public Collection<Episode> getEpisodeCollection() {
        return episodeCollection;
    }

    public void setEpisodeCollection(Collection<Episode> episodeCollection) {
        this.episodeCollection = episodeCollection;
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
        if (!(object instanceof Disorder)) {
            return false;
        }
        Disorder other = (Disorder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.Disorder[ id=" + id + " ]";
    }
    
}
