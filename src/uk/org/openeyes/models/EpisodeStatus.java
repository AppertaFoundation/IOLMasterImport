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
@Table(name = "episode_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EpisodeStatus.findAll", query = "SELECT e FROM EpisodeStatus e"),
    @NamedQuery(name = "EpisodeStatus.findById", query = "SELECT e FROM EpisodeStatus e WHERE e.id = :id"),
    @NamedQuery(name = "EpisodeStatus.findByName", query = "SELECT e FROM EpisodeStatus e WHERE e.name = :name"),
    @NamedQuery(name = "EpisodeStatus.findByLastModifiedDate", query = "SELECT e FROM EpisodeStatus e WHERE e.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "EpisodeStatus.findByCreatedDate", query = "SELECT e FROM EpisodeStatus e WHERE e.createdDate = :createdDate"),
    @NamedQuery(name = "EpisodeStatus.findByOrder", query = "SELECT e FROM EpisodeStatus e WHERE e.order = :order")})
public class EpisodeStatus implements Serializable {
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
    @Column(name = "order")
    private int order;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodeStatusId")
    private Collection<Episode> episodeCollection;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;

    public EpisodeStatus() {
    }

    public EpisodeStatus(Integer id) {
        this.id = id;
    }

    public EpisodeStatus(Integer id, String name, Date lastModifiedDate, Date createdDate, int order) {
        this.id = id;
        this.name = name;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.order = order;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EpisodeStatus)) {
            return false;
        }
        EpisodeStatus other = (EpisodeStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.EpisodeStatus[ id=" + id + " ]";
    }
    
}
