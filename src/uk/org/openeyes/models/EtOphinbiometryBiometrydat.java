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
@Table(name = "et_ophinbiometry_biometrydat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findAll", query = "SELECT e FROM EtOphinbiometryBiometrydat e"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findById", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.id = :id"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByAxialLengthLeft", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.axialLengthLeft = :axialLengthLeft"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByR1Left", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.r1Left = :r1Left"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByR2Left", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.r2Left = :r2Left"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByR1AxisLeft", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.r1AxisLeft = :r1AxisLeft"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByR2AxisLeft", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.r2AxisLeft = :r2AxisLeft"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByAcdLeft", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.acdLeft = :acdLeft"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByScleralThicknessLeft", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.scleralThicknessLeft = :scleralThicknessLeft"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByAxialLengthRight", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.axialLengthRight = :axialLengthRight"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByR1Right", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.r1Right = :r1Right"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByR2Right", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.r2Right = :r2Right"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByR1AxisRight", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.r1AxisRight = :r1AxisRight"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByR2AxisRight", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.r2AxisRight = :r2AxisRight"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByAcdRight", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.acdRight = :acdRight"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByScleralThicknessRight", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.scleralThicknessRight = :scleralThicknessRight"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByLastModifiedDate", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByCreatedDate", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.createdDate = :createdDate"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findByDeleted", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.deleted = :deleted"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findBySnrLeft", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.snrLeft = :snrLeft"),
    @NamedQuery(name = "EtOphinbiometryBiometrydat.findBySnrRight", query = "SELECT e FROM EtOphinbiometryBiometrydat e WHERE e.snrRight = :snrRight")})
public class EtOphinbiometryBiometrydat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "axial_length_left")
    private BigDecimal axialLengthLeft;
    @Basic(optional = false)
    @Column(name = "r1_left")
    private BigDecimal r1Left;
    @Basic(optional = false)
    @Column(name = "r2_left")
    private BigDecimal r2Left;
    @Column(name = "r1_axis_left")
    private Integer r1AxisLeft;
    @Column(name = "r2_axis_left")
    private Integer r2AxisLeft;
    @Basic(optional = false)
    @Column(name = "acd_left")
    private BigDecimal acdLeft;
    @Basic(optional = false)
    @Column(name = "scleral_thickness_left")
    private BigDecimal scleralThicknessLeft;
    @Basic(optional = false)
    @Column(name = "axial_length_right")
    private BigDecimal axialLengthRight;
    @Basic(optional = false)
    @Column(name = "r1_right")
    private BigDecimal r1Right;
    @Basic(optional = false)
    @Column(name = "r2_right")
    private BigDecimal r2Right;
    @Column(name = "r1_axis_right")
    private Integer r1AxisRight;
    @Column(name = "r2_axis_right")
    private Integer r2AxisRight;
    @Basic(optional = false)
    @Column(name = "acd_right")
    private BigDecimal acdRight;
    @Basic(optional = false)
    @Column(name = "scleral_thickness_right")
    private BigDecimal scleralThicknessRight;
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
    @Column(name = "snr_left")
    private BigDecimal snrLeft;
    @Basic(optional = false)
    @Column(name = "snr_right")
    private BigDecimal snrRight;
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

    public EtOphinbiometryBiometrydat() {
    }

    public EtOphinbiometryBiometrydat(Integer id) {
        this.id = id;
    }

    public EtOphinbiometryBiometrydat(Integer id, BigDecimal axialLengthLeft, BigDecimal r1Left, BigDecimal r2Left, BigDecimal acdLeft, BigDecimal scleralThicknessLeft, BigDecimal axialLengthRight, BigDecimal r1Right, BigDecimal r2Right, BigDecimal acdRight, BigDecimal scleralThicknessRight, Date lastModifiedDate, Date createdDate, boolean deleted, BigDecimal snrLeft, BigDecimal snrRight) {
        this.id = id;
        this.axialLengthLeft = axialLengthLeft;
        this.r1Left = r1Left;
        this.r2Left = r2Left;
        this.acdLeft = acdLeft;
        this.scleralThicknessLeft = scleralThicknessLeft;
        this.axialLengthRight = axialLengthRight;
        this.r1Right = r1Right;
        this.r2Right = r2Right;
        this.acdRight = acdRight;
        this.scleralThicknessRight = scleralThicknessRight;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.deleted = deleted;
        this.snrLeft = snrLeft;
        this.snrRight = snrRight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAxialLengthLeft() {
        return axialLengthLeft;
    }

    public void setAxialLengthLeft(BigDecimal axialLengthLeft) {
        this.axialLengthLeft = axialLengthLeft;
    }

    public BigDecimal getR1Left() {
        return r1Left;
    }

    public void setR1Left(BigDecimal r1Left) {
        this.r1Left = r1Left;
    }

    public BigDecimal getR2Left() {
        return r2Left;
    }

    public void setR2Left(BigDecimal r2Left) {
        this.r2Left = r2Left;
    }

    public Integer getR1AxisLeft() {
        return r1AxisLeft;
    }

    public void setR1AxisLeft(Integer r1AxisLeft) {
        this.r1AxisLeft = r1AxisLeft;
    }

    public Integer getR2AxisLeft() {
        return r2AxisLeft;
    }

    public void setR2AxisLeft(Integer r2AxisLeft) {
        this.r2AxisLeft = r2AxisLeft;
    }

    public BigDecimal getAcdLeft() {
        return acdLeft;
    }

    public void setAcdLeft(BigDecimal acdLeft) {
        this.acdLeft = acdLeft;
    }

    public BigDecimal getScleralThicknessLeft() {
        return scleralThicknessLeft;
    }

    public void setScleralThicknessLeft(BigDecimal scleralThicknessLeft) {
        this.scleralThicknessLeft = scleralThicknessLeft;
    }

    public BigDecimal getAxialLengthRight() {
        return axialLengthRight;
    }

    public void setAxialLengthRight(BigDecimal axialLengthRight) {
        this.axialLengthRight = axialLengthRight;
    }

    public BigDecimal getR1Right() {
        return r1Right;
    }

    public void setR1Right(BigDecimal r1Right) {
        this.r1Right = r1Right;
    }

    public BigDecimal getR2Right() {
        return r2Right;
    }

    public void setR2Right(BigDecimal r2Right) {
        this.r2Right = r2Right;
    }

    public Integer getR1AxisRight() {
        return r1AxisRight;
    }

    public void setR1AxisRight(Integer r1AxisRight) {
        this.r1AxisRight = r1AxisRight;
    }

    public Integer getR2AxisRight() {
        return r2AxisRight;
    }

    public void setR2AxisRight(Integer r2AxisRight) {
        this.r2AxisRight = r2AxisRight;
    }

    public BigDecimal getAcdRight() {
        return acdRight;
    }

    public void setAcdRight(BigDecimal acdRight) {
        this.acdRight = acdRight;
    }

    public BigDecimal getScleralThicknessRight() {
        return scleralThicknessRight;
    }

    public void setScleralThicknessRight(BigDecimal scleralThicknessRight) {
        this.scleralThicknessRight = scleralThicknessRight;
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

    public BigDecimal getSnrLeft() {
        return snrLeft;
    }

    public void setSnrLeft(BigDecimal snrLeft) {
        this.snrLeft = snrLeft;
    }

    public BigDecimal getSnrRight() {
        return snrRight;
    }

    public void setSnrRight(BigDecimal snrRight) {
        this.snrRight = snrRight;
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
        if (!(object instanceof EtOphinbiometryBiometrydat)) {
            return false;
        }
        EtOphinbiometryBiometrydat other = (EtOphinbiometryBiometrydat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.EtOphinbiometryBiometrydat[ id=" + id + " ]";
    }
    
}
