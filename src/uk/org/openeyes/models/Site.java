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
@Table(name = "site")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Site.findAll", query = "SELECT s FROM Site s"),
    @NamedQuery(name = "Site.findById", query = "SELECT s FROM Site s WHERE s.id = :id"),
    @NamedQuery(name = "Site.findByName", query = "SELECT s FROM Site s WHERE s.name = :name"),
    @NamedQuery(name = "Site.findByRemoteId", query = "SELECT s FROM Site s WHERE s.remoteId = :remoteId"),
    @NamedQuery(name = "Site.findByShortName", query = "SELECT s FROM Site s WHERE s.shortName = :shortName"),
    @NamedQuery(name = "Site.findByFax", query = "SELECT s FROM Site s WHERE s.fax = :fax"),
    @NamedQuery(name = "Site.findByTelephone", query = "SELECT s FROM Site s WHERE s.telephone = :telephone"),
    @NamedQuery(name = "Site.findByLastModifiedDate", query = "SELECT s FROM Site s WHERE s.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "Site.findByCreatedDate", query = "SELECT s FROM Site s WHERE s.createdDate = :createdDate"),
    @NamedQuery(name = "Site.findByLocation", query = "SELECT s FROM Site s WHERE s.location = :location"),
    @NamedQuery(name = "Site.findByActive", query = "SELECT s FROM Site s WHERE s.active = :active")})
public class Site implements Serializable {
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
    @Column(name = "remote_id")
    private String remoteId;
    @Basic(optional = false)
    @Column(name = "short_name")
    private String shortName;
    @Basic(optional = false)
    @Column(name = "fax")
    private String fax;
    @Basic(optional = false)
    @Column(name = "telephone")
    private String telephone;
    @Basic(optional = false)
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contact contactId;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "institution_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Institution institutionId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @JoinColumn(name = "replyto_contact_id", referencedColumnName = "id")
    @ManyToOne
    private Contact replytoContactId;
    @JoinColumn(name = "source_id", referencedColumnName = "id")
    @ManyToOne
    private ImportSource sourceId;
    @OneToMany(mappedBy = "lastSiteId")
    private Collection<User> userCollection;

    public Site() {
    }

    public Site(Integer id) {
        this.id = id;
    }

    public Site(Integer id, String name, String remoteId, String shortName, String fax, String telephone, Date lastModifiedDate, Date createdDate, String location, boolean active) {
        this.id = id;
        this.name = name;
        this.remoteId = remoteId;
        this.shortName = shortName;
        this.fax = fax;
        this.telephone = telephone;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.location = location;
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

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Contact getContactId() {
        return contactId;
    }

    public void setContactId(Contact contactId) {
        this.contactId = contactId;
    }

    public User getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(User createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Institution getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Institution institutionId) {
        this.institutionId = institutionId;
    }

    public User getLastModifiedUserId() {
        return lastModifiedUserId;
    }

    public void setLastModifiedUserId(User lastModifiedUserId) {
        this.lastModifiedUserId = lastModifiedUserId;
    }

    public Contact getReplytoContactId() {
        return replytoContactId;
    }

    public void setReplytoContactId(Contact replytoContactId) {
        this.replytoContactId = replytoContactId;
    }

    public ImportSource getSourceId() {
        return sourceId;
    }

    public void setSourceId(ImportSource sourceId) {
        this.sourceId = sourceId;
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
        if (!(object instanceof Site)) {
            return false;
        }
        Site other = (Site) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.Site[ id=" + id + " ]";
    }
    
}
