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
@Table(name = "firm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firm.findAll", query = "SELECT f FROM Firm f"),
    @NamedQuery(name = "Firm.findById", query = "SELECT f FROM Firm f WHERE f.id = :id"),
    @NamedQuery(name = "Firm.findByPasCode", query = "SELECT f FROM Firm f WHERE f.pasCode = :pasCode"),
    @NamedQuery(name = "Firm.findByName", query = "SELECT f FROM Firm f WHERE f.name = :name"),
    @NamedQuery(name = "Firm.findByLastModifiedDate", query = "SELECT f FROM Firm f WHERE f.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "Firm.findByCreatedDate", query = "SELECT f FROM Firm f WHERE f.createdDate = :createdDate"),
    @NamedQuery(name = "Firm.findByActive", query = "SELECT f FROM Firm f WHERE f.active = :active")})
public class Firm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "pas_code")
    private String pasCode;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
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
    @OneToMany(mappedBy = "firmId")
    private Collection<Episode> episodeCollection;
    @JoinColumn(name = "consultant_id", referencedColumnName = "id")
    @ManyToOne
    private User consultantId;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @JoinColumn(name = "service_subspecialty_assignment_id", referencedColumnName = "id")
    @ManyToOne
    private ServiceSubspecialtyAssignment serviceSubspecialtyAssignmentId;
    @OneToMany(mappedBy = "lastFirmId")
    private Collection<User> userCollection;

    public Firm() {
    }

    public Firm(Integer id) {
        this.id = id;
    }

    public Firm(Integer id, String name, Date lastModifiedDate, Date createdDate, boolean active) {
        this.id = id;
        this.name = name;
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

    public String getPasCode() {
        return pasCode;
    }

    public void setPasCode(String pasCode) {
        this.pasCode = pasCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public User getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(User consultantId) {
        this.consultantId = consultantId;
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

    public ServiceSubspecialtyAssignment getServiceSubspecialtyAssignmentId() {
        return serviceSubspecialtyAssignmentId;
    }

    public void setServiceSubspecialtyAssignmentId(ServiceSubspecialtyAssignment serviceSubspecialtyAssignmentId) {
        this.serviceSubspecialtyAssignmentId = serviceSubspecialtyAssignmentId;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
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
        if (!(object instanceof Firm)) {
            return false;
        }
        Firm other = (Firm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.Firm[ id=" + id + " ]";
    }
    
}
