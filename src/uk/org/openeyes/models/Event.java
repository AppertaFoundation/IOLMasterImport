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
@Table(name = "event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
    @NamedQuery(name = "Event.findById", query = "SELECT e FROM Event e WHERE e.id = :id"),
    @NamedQuery(name = "Event.findByLastModifiedDate", query = "SELECT e FROM Event e WHERE e.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "Event.findByCreatedDate", query = "SELECT e FROM Event e WHERE e.createdDate = :createdDate"),
    @NamedQuery(name = "Event.findByEventDate", query = "SELECT e FROM Event e WHERE e.eventDate = :eventDate"),
    @NamedQuery(name = "Event.findByInfo", query = "SELECT e FROM Event e WHERE e.info = :info"),
    @NamedQuery(name = "Event.findByDeleted", query = "SELECT e FROM Event e WHERE e.deleted = :deleted"),
    @NamedQuery(name = "Event.findByDeleteReason", query = "SELECT e FROM Event e WHERE e.deleteReason = :deleteReason"),
    @NamedQuery(name = "Event.findByDeletePending", query = "SELECT e FROM Event e WHERE e.deletePending = :deletePending")})
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "event_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;
    @Column(name = "info")
    private String info;
    @Basic(optional = false)
    @Column(name = "deleted")
    private int deleted;
    @Column(name = "delete_reason")
    private String deleteReason;
    @Basic(optional = false)
    @Column(name = "delete_pending")
    private boolean deletePending;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId")
    private Collection<EtOphinbiometrySelection> etOphinbiometrySelectionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId")
    private Collection<EtOphinbiometryIolRefValues> etOphinbiometryIolRefValuesCollection;
    @JoinColumn(name = "episode_id", referencedColumnName = "id")
    @ManyToOne
    private Episode episodeId;
    @JoinColumn(name = "event_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EventType eventTypeId;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId")
    private Collection<EtOphinbiometryCalculation> etOphinbiometryCalculationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId")
    private Collection<OphinbiometryImportedEvents> ophinbiometryImportedEventsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId")
    private Collection<EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection;

    public Event() {
    }

    public Event(Integer id) {
        this.id = id;
    }

    public Event(Integer id, Date lastModifiedDate, Date createdDate, Date eventDate, int deleted, boolean deletePending) {
        this.id = id;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.eventDate = eventDate;
        this.deleted = deleted;
        this.deletePending = deletePending;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

    public boolean getDeletePending() {
        return deletePending;
    }

    public void setDeletePending(boolean deletePending) {
        this.deletePending = deletePending;
    }

    @XmlTransient
    public Collection<EtOphinbiometrySelection> getEtOphinbiometrySelectionCollection() {
        return etOphinbiometrySelectionCollection;
    }

    public void setEtOphinbiometrySelectionCollection(Collection<EtOphinbiometrySelection> etOphinbiometrySelectionCollection) {
        this.etOphinbiometrySelectionCollection = etOphinbiometrySelectionCollection;
    }

    @XmlTransient
    public Collection<EtOphinbiometryIolRefValues> getEtOphinbiometryIolRefValuesCollection() {
        return etOphinbiometryIolRefValuesCollection;
    }

    public void setEtOphinbiometryIolRefValuesCollection(Collection<EtOphinbiometryIolRefValues> etOphinbiometryIolRefValuesCollection) {
        this.etOphinbiometryIolRefValuesCollection = etOphinbiometryIolRefValuesCollection;
    }

    public Episode getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(Episode episodeId) {
        this.episodeId = episodeId;
    }

    public EventType getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(EventType eventTypeId) {
        this.eventTypeId = eventTypeId;
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
    public Collection<EtOphinbiometryCalculation> getEtOphinbiometryCalculationCollection() {
        return etOphinbiometryCalculationCollection;
    }

    public void setEtOphinbiometryCalculationCollection(Collection<EtOphinbiometryCalculation> etOphinbiometryCalculationCollection) {
        this.etOphinbiometryCalculationCollection = etOphinbiometryCalculationCollection;
    }

    @XmlTransient
    public Collection<OphinbiometryImportedEvents> getOphinbiometryImportedEventsCollection() {
        return ophinbiometryImportedEventsCollection;
    }

    public void setOphinbiometryImportedEventsCollection(Collection<OphinbiometryImportedEvents> ophinbiometryImportedEventsCollection) {
        this.ophinbiometryImportedEventsCollection = ophinbiometryImportedEventsCollection;
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.Event[ id=" + id + " ]";
    }
    
}
