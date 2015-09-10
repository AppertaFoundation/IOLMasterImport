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
@Table(name = "service_subspecialty_assignment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServiceSubspecialtyAssignment.findAll", query = "SELECT s FROM ServiceSubspecialtyAssignment s"),
    @NamedQuery(name = "ServiceSubspecialtyAssignment.findById", query = "SELECT s FROM ServiceSubspecialtyAssignment s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceSubspecialtyAssignment.findByLastModifiedDate", query = "SELECT s FROM ServiceSubspecialtyAssignment s WHERE s.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "ServiceSubspecialtyAssignment.findByCreatedDate", query = "SELECT s FROM ServiceSubspecialtyAssignment s WHERE s.createdDate = :createdDate")})
public class ServiceSubspecialtyAssignment implements Serializable {
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
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Service serviceId;
    @JoinColumn(name = "subspecialty_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Subspecialty subspecialtyId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @OneToMany(mappedBy = "serviceSubspecialtyAssignmentId")
    private Collection<Firm> firmCollection;

    public ServiceSubspecialtyAssignment() {
    }

    public ServiceSubspecialtyAssignment(Integer id) {
        this.id = id;
    }

    public ServiceSubspecialtyAssignment(Integer id, Date lastModifiedDate, Date createdDate) {
        this.id = id;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
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

    public User getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(User createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Service getServiceId() {
        return serviceId;
    }

    public void setServiceId(Service serviceId) {
        this.serviceId = serviceId;
    }

    public Subspecialty getSubspecialtyId() {
        return subspecialtyId;
    }

    public void setSubspecialtyId(Subspecialty subspecialtyId) {
        this.subspecialtyId = subspecialtyId;
    }

    public User getLastModifiedUserId() {
        return lastModifiedUserId;
    }

    public void setLastModifiedUserId(User lastModifiedUserId) {
        this.lastModifiedUserId = lastModifiedUserId;
    }

    @XmlTransient
    public Collection<Firm> getFirmCollection() {
        return firmCollection;
    }

    public void setFirmCollection(Collection<Firm> firmCollection) {
        this.firmCollection = firmCollection;
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
        if (!(object instanceof ServiceSubspecialtyAssignment)) {
            return false;
        }
        ServiceSubspecialtyAssignment other = (ServiceSubspecialtyAssignment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.ServiceSubspecialtyAssignment[ id=" + id + " ]";
    }
    
}
