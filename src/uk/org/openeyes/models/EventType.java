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
@Table(name = "event_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventType.findAll", query = "SELECT e FROM EventType e"),
    @NamedQuery(name = "EventType.findById", query = "SELECT e FROM EventType e WHERE e.id = :id"),
    @NamedQuery(name = "EventType.findByName", query = "SELECT e FROM EventType e WHERE e.name = :name"),
    @NamedQuery(name = "EventType.findByLastModifiedDate", query = "SELECT e FROM EventType e WHERE e.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "EventType.findByCreatedDate", query = "SELECT e FROM EventType e WHERE e.createdDate = :createdDate"),
    @NamedQuery(name = "EventType.findByClassName", query = "SELECT e FROM EventType e WHERE e.className = :className"),
    @NamedQuery(name = "EventType.findBySupportServices", query = "SELECT e FROM EventType e WHERE e.supportServices = :supportServices")})
public class EventType implements Serializable {
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
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "class_name")
    private String className;
    @Basic(optional = false)
    @Column(name = "support_services")
    private boolean supportServices;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "event_group_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EventGroup eventGroupId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @OneToMany(mappedBy = "parentEventTypeId")
    private Collection<EventType> eventTypeCollection;
    @JoinColumn(name = "parent_event_type_id", referencedColumnName = "id")
    @ManyToOne
    private EventType parentEventTypeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventTypeId")
    private Collection<Event> eventCollection;

    public EventType() {
    }

    public EventType(Integer id) {
        this.id = id;
    }

    public EventType(Integer id, String name, Date lastModifiedDate, Date createdDate, String className, boolean supportServices) {
        this.id = id;
        this.name = name;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.className = className;
        this.supportServices = supportServices;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean getSupportServices() {
        return supportServices;
    }

    public void setSupportServices(boolean supportServices) {
        this.supportServices = supportServices;
    }

    public User getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(User createdUserId) {
        this.createdUserId = createdUserId;
    }

    public EventGroup getEventGroupId() {
        return eventGroupId;
    }

    public void setEventGroupId(EventGroup eventGroupId) {
        this.eventGroupId = eventGroupId;
    }

    public User getLastModifiedUserId() {
        return lastModifiedUserId;
    }

    public void setLastModifiedUserId(User lastModifiedUserId) {
        this.lastModifiedUserId = lastModifiedUserId;
    }

    @XmlTransient
    public Collection<EventType> getEventTypeCollection() {
        return eventTypeCollection;
    }

    public void setEventTypeCollection(Collection<EventType> eventTypeCollection) {
        this.eventTypeCollection = eventTypeCollection;
    }

    public EventType getParentEventTypeId() {
        return parentEventTypeId;
    }

    public void setParentEventTypeId(EventType parentEventTypeId) {
        this.parentEventTypeId = parentEventTypeId;
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
        if (!(object instanceof EventType)) {
            return false;
        }
        EventType other = (EventType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.EventType[ id=" + id + " ]";
    }
    
}
