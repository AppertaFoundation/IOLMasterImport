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
@Table(name = "et_ophinbiometry_selection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EtOphinbiometrySelection.findAll", query = "SELECT e FROM EtOphinbiometrySelection e"),
    @NamedQuery(name = "EtOphinbiometrySelection.findById", query = "SELECT e FROM EtOphinbiometrySelection e WHERE e.id = :id"),
    @NamedQuery(name = "EtOphinbiometrySelection.findByIolPowerLeft", query = "SELECT e FROM EtOphinbiometrySelection e WHERE e.iolPowerLeft = :iolPowerLeft"),
    @NamedQuery(name = "EtOphinbiometrySelection.findByPredictedRefractionLeft", query = "SELECT e FROM EtOphinbiometrySelection e WHERE e.predictedRefractionLeft = :predictedRefractionLeft"),
    @NamedQuery(name = "EtOphinbiometrySelection.findByIolPowerRight", query = "SELECT e FROM EtOphinbiometrySelection e WHERE e.iolPowerRight = :iolPowerRight"),
    @NamedQuery(name = "EtOphinbiometrySelection.findByPredictedRefractionRight", query = "SELECT e FROM EtOphinbiometrySelection e WHERE e.predictedRefractionRight = :predictedRefractionRight"),
    @NamedQuery(name = "EtOphinbiometrySelection.findByLastModifiedDate", query = "SELECT e FROM EtOphinbiometrySelection e WHERE e.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "EtOphinbiometrySelection.findByCreatedDate", query = "SELECT e FROM EtOphinbiometrySelection e WHERE e.createdDate = :createdDate"),
    @NamedQuery(name = "EtOphinbiometrySelection.findByDeleted", query = "SELECT e FROM EtOphinbiometrySelection e WHERE e.deleted = :deleted"),
    @NamedQuery(name = "EtOphinbiometrySelection.findByLensIdLeft", query = "SELECT e FROM EtOphinbiometrySelection e WHERE e.lensIdLeft = :lensIdLeft"),
    @NamedQuery(name = "EtOphinbiometrySelection.findByLensIdRight", query = "SELECT e FROM EtOphinbiometrySelection e WHERE e.lensIdRight = :lensIdRight")})
public class EtOphinbiometrySelection implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "iol_power_left")
    private BigDecimal iolPowerLeft;
    @Basic(optional = false)
    @Column(name = "predicted_refraction_left")
    private BigDecimal predictedRefractionLeft;
    @Basic(optional = false)
    @Column(name = "iol_power_right")
    private BigDecimal iolPowerRight;
    @Basic(optional = false)
    @Column(name = "predicted_refraction_right")
    private BigDecimal predictedRefractionRight;
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
    @Column(name = "lens_id_left")
    private int lensIdLeft;
    @Basic(optional = false)
    @Column(name = "lens_id_right")
    private int lensIdRight;
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

    public EtOphinbiometrySelection() {
    }

    public EtOphinbiometrySelection(Integer id) {
        this.id = id;
    }

    public EtOphinbiometrySelection(Integer id, BigDecimal iolPowerLeft, BigDecimal predictedRefractionLeft, BigDecimal iolPowerRight, BigDecimal predictedRefractionRight, Date lastModifiedDate, Date createdDate, boolean deleted, int lensIdLeft, int lensIdRight) {
        this.id = id;
        this.iolPowerLeft = iolPowerLeft;
        this.predictedRefractionLeft = predictedRefractionLeft;
        this.iolPowerRight = iolPowerRight;
        this.predictedRefractionRight = predictedRefractionRight;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.deleted = deleted;
        this.lensIdLeft = lensIdLeft;
        this.lensIdRight = lensIdRight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getIolPowerLeft() {
        return iolPowerLeft;
    }

    public void setIolPowerLeft(BigDecimal iolPowerLeft) {
        this.iolPowerLeft = iolPowerLeft;
    }

    public BigDecimal getPredictedRefractionLeft() {
        return predictedRefractionLeft;
    }

    public void setPredictedRefractionLeft(BigDecimal predictedRefractionLeft) {
        this.predictedRefractionLeft = predictedRefractionLeft;
    }

    public BigDecimal getIolPowerRight() {
        return iolPowerRight;
    }

    public void setIolPowerRight(BigDecimal iolPowerRight) {
        this.iolPowerRight = iolPowerRight;
    }

    public BigDecimal getPredictedRefractionRight() {
        return predictedRefractionRight;
    }

    public void setPredictedRefractionRight(BigDecimal predictedRefractionRight) {
        this.predictedRefractionRight = predictedRefractionRight;
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

    public int getLensIdLeft() {
        return lensIdLeft;
    }

    public void setLensIdLeft(int lensIdLeft) {
        this.lensIdLeft = lensIdLeft;
    }

    public int getLensIdRight() {
        return lensIdRight;
    }

    public void setLensIdRight(int lensIdRight) {
        this.lensIdRight = lensIdRight;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EtOphinbiometrySelection)) {
            return false;
        }
        EtOphinbiometrySelection other = (EtOphinbiometrySelection) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.EtOphinbiometrySelection[ id=" + id + " ]";
    }
    
}
