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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "event_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventGroup.findAll", query = "SELECT e FROM EventGroup e"),
    @NamedQuery(name = "EventGroup.findById", query = "SELECT e FROM EventGroup e WHERE e.id = :id"),
    @NamedQuery(name = "EventGroup.findByName", query = "SELECT e FROM EventGroup e WHERE e.name = :name"),
    @NamedQuery(name = "EventGroup.findByCode", query = "SELECT e FROM EventGroup e WHERE e.code = :code")})
public class EventGroup implements Serializable {
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
    private String code;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventGroupId")
    private Collection<EventType> eventTypeCollection;

    public EventGroup() {
    }

    public EventGroup(Integer id) {
        this.id = id;
    }

    public EventGroup(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlTransient
    public Collection<EventType> getEventTypeCollection() {
        return eventTypeCollection;
    }

    public void setEventTypeCollection(Collection<EventType> eventTypeCollection) {
        this.eventTypeCollection = eventTypeCollection;
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
        if (!(object instanceof EventGroup)) {
            return false;
        }
        EventGroup other = (EventGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.EventGroup[ id=" + id + " ]";
    }
    
}
