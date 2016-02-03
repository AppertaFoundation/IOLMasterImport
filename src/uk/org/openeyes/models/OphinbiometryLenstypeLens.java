/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes.models;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "ophinbiometry_lenstype_lens")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OphinbiometryLenstypeLens.findAll", query = "SELECT o FROM OphinbiometryLenstypeLens o"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findById", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.id = :id"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByName", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.name = :name"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByDisplayOrder", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.displayOrder = :displayOrder"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByLastModifiedDate", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByCreatedDate", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.createdDate = :createdDate"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByDeleted", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.deleted = :deleted"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByDescription", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.description = :description"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByPositionId", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.positionId = :positionId"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByComments", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.comments = :comments"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByAcon", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.acon = :acon"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findBySf", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.sf = :sf"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByPACD", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.pACD = :pACD"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByA0", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.a0 = :a0"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByA1", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.a1 = :a1"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByA2", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.a2 = :a2"),
    @NamedQuery(name = "OphinbiometryLenstypeLens.findByActive", query = "SELECT o FROM OphinbiometryLenstypeLens o WHERE o.active = :active")})
public class OphinbiometryLenstypeLens implements Serializable {
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
    private int displayOrder;
    @Basic(optional = false)
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "position_id")
    private int positionId;
    @Basic(optional = false)
    @Column(name = "comments")
    private String comments;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "acon")
    private BigDecimal acon;
    @Column(name = "sf")
    private Float sf;
    @Column(name = "pACD")
    private Float pACD;
    @Column(name = "a0")
    private Float a0;
    @Column(name = "a1")
    private Float a1;
    @Column(name = "a2")
    private Float a2;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lensId")
    private Collection<EtOphinbiometryIolRefValues> etOphinbiometryIolRefValuesCollection;

    public OphinbiometryLenstypeLens() {
    }

    public OphinbiometryLenstypeLens(Integer id) {
        this.id = id;
    }

    public OphinbiometryLenstypeLens(Integer id, String name, int displayOrder, Date lastModifiedDate, Date createdDate, boolean deleted, String description, int positionId, String comments, BigDecimal acon) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.deleted = deleted;
        this.description = description;
        this.positionId = positionId;
        this.comments = comments;
        this.acon = acon;
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

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
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

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getAcon() {
        return acon;
    }

    public void setAcon(BigDecimal acon) {
        this.acon = acon;
    }

    public Float getSf() {
        return sf;
    }

    public void setSf(Float sf) {
        this.sf = sf;
    }

    public Float getPACD() {
        return pACD;
    }

    public void setPACD(Float pACD) {
        this.pACD = pACD;
    }

    public Float getA0() {
        return a0;
    }

    public void setA0(Float a0) {
        this.a0 = a0;
    }

    public Float getA1() {
        return a1;
    }

    public void setA1(Float a1) {
        this.a1 = a1;
    }

    public Float getA2() {
        return a2;
    }

    public void setA2(Float a2) {
        this.a2 = a2;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
    public Collection<EtOphinbiometryIolRefValues> getEtOphinbiometryIolRefValuesCollection() {
        return etOphinbiometryIolRefValuesCollection;
    }

    public void setEtOphinbiometryIolRefValuesCollection(Collection<EtOphinbiometryIolRefValues> etOphinbiometryIolRefValuesCollection) {
        this.etOphinbiometryIolRefValuesCollection = etOphinbiometryIolRefValuesCollection;
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
        if (!(object instanceof OphinbiometryLenstypeLens)) {
            return false;
        }
        OphinbiometryLenstypeLens other = (OphinbiometryLenstypeLens) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.OphinbiometryLenstypeLens[ id=" + id + " ]";
    }
    
}
