/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes.models;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VEDELEKT
 */
@Entity
@Table(name = "ophinbiometry_lens_position")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OphinbiometryLensPosition.findAll", query = "SELECT o FROM OphinbiometryLensPosition o"),
    @NamedQuery(name = "OphinbiometryLensPosition.findById", query = "SELECT o FROM OphinbiometryLensPosition o WHERE o.id = :id"),
    @NamedQuery(name = "OphinbiometryLensPosition.findByName", query = "SELECT o FROM OphinbiometryLensPosition o WHERE o.name = :name"),
    @NamedQuery(name = "OphinbiometryLensPosition.findByDisplayOrder", query = "SELECT o FROM OphinbiometryLensPosition o WHERE o.displayOrder = :displayOrder"),
    @NamedQuery(name = "OphinbiometryLensPosition.findByLastModifiedDate", query = "SELECT o FROM OphinbiometryLensPosition o WHERE o.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "OphinbiometryLensPosition.findByCreatedDate", query = "SELECT o FROM OphinbiometryLensPosition o WHERE o.createdDate = :createdDate")})
public class OphinbiometryLensPosition implements Serializable {
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
    @Column(name = "display_order")
    private boolean displayOrder;
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
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;

    public OphinbiometryLensPosition() {
    }

    public OphinbiometryLensPosition(Integer id) {
        this.id = id;
    }

    public OphinbiometryLensPosition(Integer id, String name, boolean displayOrder, Date lastModifiedDate, Date createdDate) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
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

    public boolean getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(boolean displayOrder) {
        this.displayOrder = displayOrder;
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
        if (!(object instanceof OphinbiometryLensPosition)) {
            return false;
        }
        OphinbiometryLensPosition other = (OphinbiometryLensPosition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.OphinbiometryLensPosition[ id=" + id + " ]";
    }
    
}
