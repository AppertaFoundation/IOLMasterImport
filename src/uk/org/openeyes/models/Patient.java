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
@Table(name = "patient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
    @NamedQuery(name = "Patient.findById", query = "SELECT p FROM Patient p WHERE p.id = :id"),
    @NamedQuery(name = "Patient.findByPasKey", query = "SELECT p FROM Patient p WHERE p.pasKey = :pasKey"),
    @NamedQuery(name = "Patient.findByDob", query = "SELECT p FROM Patient p WHERE p.dob = :dob"),
    @NamedQuery(name = "Patient.findByGender", query = "SELECT p FROM Patient p WHERE p.gender = :gender"),
    @NamedQuery(name = "Patient.findByHosNum", query = "SELECT p FROM Patient p WHERE p.hosNum = :hosNum"),
    @NamedQuery(name = "Patient.findByNhsNum", query = "SELECT p FROM Patient p WHERE p.nhsNum = :nhsNum"),
    @NamedQuery(name = "Patient.findByLastModifiedDate", query = "SELECT p FROM Patient p WHERE p.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "Patient.findByCreatedDate", query = "SELECT p FROM Patient p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "Patient.findByDateOfDeath", query = "SELECT p FROM Patient p WHERE p.dateOfDeath = :dateOfDeath")})
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "pas_key")
    private Integer pasKey;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "gender")
    private String gender;
    @Column(name = "hos_num")
    private String hosNum;
    @Column(name = "nhs_num")
    private String nhsNum;
    @Basic(optional = false)
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "date_of_death")
    @Temporal(TemporalType.DATE)
    private Date dateOfDeath;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private Collection<Episode> episodeCollection;
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contact contactId;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "ethnic_group_id", referencedColumnName = "id")
    @ManyToOne
    private EthnicGroup ethnicGroupId;
    @JoinColumn(name = "gp_id", referencedColumnName = "id")
    @ManyToOne
    private Gp gpId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @JoinColumn(name = "practice_id", referencedColumnName = "id")
    @ManyToOne
    private Practice practiceId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private Collection<OphinbiometryImportedEvents> ophinbiometryImportedEventsCollection;

    public Patient() {
    }

    public Patient(Integer id) {
        this.id = id;
    }

    public Patient(Integer id, Date lastModifiedDate, Date createdDate) {
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

    public Integer getPasKey() {
        return pasKey;
    }

    public void setPasKey(Integer pasKey) {
        this.pasKey = pasKey;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHosNum() {
        return hosNum;
    }

    public void setHosNum(String hosNum) {
        this.hosNum = hosNum;
    }

    public String getNhsNum() {
        return nhsNum;
    }

    public void setNhsNum(String nhsNum) {
        this.nhsNum = nhsNum;
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

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    @XmlTransient
    public Collection<Episode> getEpisodeCollection() {
        return episodeCollection;
    }

    public void setEpisodeCollection(Collection<Episode> episodeCollection) {
        this.episodeCollection = episodeCollection;
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

    public EthnicGroup getEthnicGroupId() {
        return ethnicGroupId;
    }

    public void setEthnicGroupId(EthnicGroup ethnicGroupId) {
        this.ethnicGroupId = ethnicGroupId;
    }

    public Gp getGpId() {
        return gpId;
    }

    public void setGpId(Gp gpId) {
        this.gpId = gpId;
    }

    public User getLastModifiedUserId() {
        return lastModifiedUserId;
    }

    public void setLastModifiedUserId(User lastModifiedUserId) {
        this.lastModifiedUserId = lastModifiedUserId;
    }

    public Practice getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(Practice practiceId) {
        this.practiceId = practiceId;
    }

    @XmlTransient
    public Collection<OphinbiometryImportedEvents> getOphinbiometryImportedEventsCollection() {
        return ophinbiometryImportedEventsCollection;
    }

    public void setOphinbiometryImportedEventsCollection(Collection<OphinbiometryImportedEvents> ophinbiometryImportedEventsCollection) {
        this.ophinbiometryImportedEventsCollection = ophinbiometryImportedEventsCollection;
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
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.Patient[ id=" + id + " ]";
    }
    
}
