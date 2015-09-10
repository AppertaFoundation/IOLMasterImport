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
@Table(name = "episode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Episode.findAll", query = "SELECT e FROM Episode e"),
    @NamedQuery(name = "Episode.findById", query = "SELECT e FROM Episode e WHERE e.id = :id"),
    @NamedQuery(name = "Episode.findByStartDate", query = "SELECT e FROM Episode e WHERE e.startDate = :startDate"),
    @NamedQuery(name = "Episode.findByEndDate", query = "SELECT e FROM Episode e WHERE e.endDate = :endDate"),
    @NamedQuery(name = "Episode.findByLastModifiedDate", query = "SELECT e FROM Episode e WHERE e.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "Episode.findByCreatedDate", query = "SELECT e FROM Episode e WHERE e.createdDate = :createdDate"),
    @NamedQuery(name = "Episode.findByLegacy", query = "SELECT e FROM Episode e WHERE e.legacy = :legacy"),
    @NamedQuery(name = "Episode.findByDeleted", query = "SELECT e FROM Episode e WHERE e.deleted = :deleted"),
    @NamedQuery(name = "Episode.findBySupportServices", query = "SELECT e FROM Episode e WHERE e.supportServices = :supportServices")})
public class Episode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Basic(optional = false)
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "legacy")
    private Boolean legacy;
    @Basic(optional = false)
    @Column(name = "deleted")
    private int deleted;
    @Basic(optional = false)
    @Column(name = "support_services")
    private boolean supportServices;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Patient patientId;
    @JoinColumn(name = "firm_id", referencedColumnName = "id")
    @ManyToOne
    private Firm firmId;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "disorder_id", referencedColumnName = "id")
    @ManyToOne
    private Disorder disorderId;
    @JoinColumn(name = "episode_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EpisodeStatus episodeStatusId;
    @JoinColumn(name = "eye_id", referencedColumnName = "id")
    @ManyToOne
    private Eye eyeId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @OneToMany(mappedBy = "episodeId")
    private Collection<Event> eventCollection;

    public Episode() {
    }

    public Episode(Integer id) {
        this.id = id;
    }

    public Episode(Integer id, Date startDate, Date lastModifiedDate, Date createdDate, int deleted, boolean supportServices) {
        this.id = id;
        this.startDate = startDate;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.deleted = deleted;
        this.supportServices = supportServices;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Boolean getLegacy() {
        return legacy;
    }

    public void setLegacy(Boolean legacy) {
        this.legacy = legacy;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public boolean getSupportServices() {
        return supportServices;
    }

    public void setSupportServices(boolean supportServices) {
        this.supportServices = supportServices;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public Firm getFirmId() {
        return firmId;
    }

    public void setFirmId(Firm firmId) {
        this.firmId = firmId;
    }

    public User getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(User createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Disorder getDisorderId() {
        return disorderId;
    }

    public void setDisorderId(Disorder disorderId) {
        this.disorderId = disorderId;
    }

    public EpisodeStatus getEpisodeStatusId() {
        return episodeStatusId;
    }

    public void setEpisodeStatusId(EpisodeStatus episodeStatusId) {
        this.episodeStatusId = episodeStatusId;
    }

    public Eye getEyeId() {
        return eyeId;
    }

    public void setEyeId(Eye eyeId) {
        this.eyeId = eyeId;
    }

    public User getLastModifiedUserId() {
        return lastModifiedUserId;
    }

    public void setLastModifiedUserId(User lastModifiedUserId) {
        this.lastModifiedUserId = lastModifiedUserId;
    }

    @XmlTransient
    public Collection<Event> getEventCollection() {
        return eventCollection;
    }

    public void setEventCollection(Collection<Event> eventCollection) {
        this.eventCollection = eventCollection;
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
        if (!(object instanceof Episode)) {
            return false;
        }
        Episode other = (Episode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.Episode[ id=" + id + " ]";
    }
    
}
