/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.annotation.Nullable;
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
@Table(name = "et_ophinbiometry_calculation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EtOphinbiometryCalculation.findAll", query = "SELECT e FROM EtOphinbiometryCalculation e"),
    @NamedQuery(name = "EtOphinbiometryCalculation.findById", query = "SELECT e FROM EtOphinbiometryCalculation e WHERE e.id = :id"),
    @NamedQuery(name = "EtOphinbiometryCalculation.findByTargetRefractionLeft", query = "SELECT e FROM EtOphinbiometryCalculation e WHERE e.targetRefractionLeft = :targetRefractionLeft"),
    @NamedQuery(name = "EtOphinbiometryCalculation.findByTargetRefractionRight", query = "SELECT e FROM EtOphinbiometryCalculation e WHERE e.targetRefractionRight = :targetRefractionRight"),
    @NamedQuery(name = "EtOphinbiometryCalculation.findByLastModifiedDate", query = "SELECT e FROM EtOphinbiometryCalculation e WHERE e.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "EtOphinbiometryCalculation.findByCreatedDate", query = "SELECT e FROM EtOphinbiometryCalculation e WHERE e.createdDate = :createdDate"),
    @NamedQuery(name = "EtOphinbiometryCalculation.findByDeleted", query = "SELECT e FROM EtOphinbiometryCalculation e WHERE e.deleted = :deleted")})
public class EtOphinbiometryCalculation implements Serializable {
    @Column(name = "comments")
    private String comments;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = true)
    @Column(name = "target_refraction_left", nullable = true)
    private BigDecimal targetRefractionLeft;
    @Basic(optional = true)
    @Column(name = "target_refraction_right", nullable = true)
    private BigDecimal targetRefractionRight;
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
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Event eventId;
    @JoinColumn(name = "eye_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Eye eyeId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @JoinColumn(name = "formula_id_left", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OphinbiometryCalculationFormula formulaIdLeft;
    @JoinColumn(name = "formula_id_right", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OphinbiometryCalculationFormula formulaIdRight;

    public EtOphinbiometryCalculation() {
    }

    public EtOphinbiometryCalculation(Integer id) {
        this.id = id;
    }

    public EtOphinbiometryCalculation(Integer id, BigDecimal targetRefractionLeft, BigDecimal targetRefractionRight, Date lastModifiedDate, Date createdDate, boolean deleted) {
        this.id = id;
        this.targetRefractionLeft = targetRefractionLeft;
        this.targetRefractionRight = targetRefractionRight;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTargetRefractionLeft() {
        return targetRefractionLeft;
    }

    public void setTargetRefractionLeft(BigDecimal targetRefractionLeft) {
        this.targetRefractionLeft = null;
    }

    public BigDecimal getTargetRefractionRight() {
        return targetRefractionRight;
    }

    public void setTargetRefractionRight(BigDecimal targetRefractionRight) {
        this.targetRefractionRight = null;
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

    public User getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(User createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
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

    public OphinbiometryCalculationFormula getFormulaIdLeft() {
        return formulaIdLeft;
    }

    public void setFormulaIdLeft(OphinbiometryCalculationFormula formulaIdLeft) {
        this.formulaIdLeft = formulaIdLeft;
    }

    public OphinbiometryCalculationFormula getFormulaIdRight() {
        return formulaIdRight;
    }

    public void setFormulaIdRight(OphinbiometryCalculationFormula formulaIdRight) {
        this.formulaIdRight = formulaIdRight;
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
        if (!(object instanceof EtOphinbiometryCalculation)) {
            return false;
        }
        EtOphinbiometryCalculation other = (EtOphinbiometryCalculation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.EtOphinbiometryCalculation[ id=" + id + " ]";
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        
        this.comments = comments.substring(0,999);
    }
    
}
