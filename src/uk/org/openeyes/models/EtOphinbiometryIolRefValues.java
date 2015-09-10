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
import org.hibernate.annotations.Type;

/**
 *
 * @author VEDELEKT
 */
@Entity
@Table(name = "et_ophinbiometry_iol_ref_values")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EtOphinbiometryIolRefValues.findAll", query = "SELECT e FROM EtOphinbiometryIolRefValues e"),
    @NamedQuery(name = "EtOphinbiometryIolRefValues.findById", query = "SELECT e FROM EtOphinbiometryIolRefValues e WHERE e.id = :id")})
public class EtOphinbiometryIolRefValues implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Event eventId;   
    @JoinColumn(name = "eye_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Eye eyeId;   
    @JoinColumn(name = "lens_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OphinbiometryLenstypeLens lensId;
    @JoinColumn(name = "formula_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OphinbiometryCalculationFormula formulaId;
    @Column(name = "iol_ref_values_left")
    @Type(type="text")
    private String iolRefValuesLeft;
    @Column(name = "iol_ref_values_right")
    @Type(type="text")
    private String iolRefValuesRight;
    @Column(name = "emmetropia_left")
    private BigDecimal emmetropiaLeft;
    @Column(name = "emmetropia_right")
    private BigDecimal emmetropiaRight;
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

    public EtOphinbiometryIolRefValues() {
    }

    public EtOphinbiometryIolRefValues(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIolRefValuesLeft() {
        return iolRefValuesLeft;
    }

    public void setIolRefValuesLeft(String iolRefValuesLeft) {
        this.iolRefValuesLeft = iolRefValuesLeft;
    }
    
    public String getIolRefValuesRight() {
        return iolRefValuesRight;
    }

    public void setIolRefValuesRight(String iolRefValuesRight) {
        this.iolRefValuesRight = iolRefValuesRight;
    }

    public BigDecimal getEmmetropiaLeft() {
        return emmetropiaLeft;
    }

    public void setEmmetropiaLeft(BigDecimal emmetropiaLeft) {
        this.emmetropiaLeft = emmetropiaLeft;
    }
    
    public BigDecimal getEmmetropiaRight() {
        return emmetropiaRight;
    }

    public void setEmmetropiaRight(BigDecimal emmetropiaRight) {
        this.emmetropiaRight = emmetropiaRight;
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

    public OphinbiometryCalculationFormula getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(OphinbiometryCalculationFormula formulaId) {
        this.formulaId = formulaId;
    }
    
    public OphinbiometryLenstypeLens getLensId() {
        return lensId;
    }

    public void setLensId(OphinbiometryLenstypeLens lensId) {
        this.lensId = lensId;
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
        if (!(object instanceof EtOphinbiometryIolRefValues)) {
            return false;
        }
        EtOphinbiometryIolRefValues other = (EtOphinbiometryIolRefValues) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.EtOphinbiometryIolRefValues[ id=" + id + " ]";
    }
    
}
