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
@Table(name = "ophinbiometry_imported_events")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OphinbiometryImportedEvents.findAll", query = "SELECT o FROM OphinbiometryImportedEvents o"),
    @NamedQuery(name = "OphinbiometryImportedEvents.findById", query = "SELECT o FROM OphinbiometryImportedEvents o WHERE o.id = :id"),
    @NamedQuery(name = "OphinbiometryImportedEvents.findByStudyId", query = "SELECT o FROM OphinbiometryImportedEvents o WHERE o.studyId = :studyId"),
    @NamedQuery(name = "OphinbiometryImportedEvents.findByDeviceId", query = "SELECT o FROM OphinbiometryImportedEvents o WHERE o.deviceId = :deviceId"),
    @NamedQuery(name = "OphinbiometryImportedEvents.findByDeviceName", query = "SELECT o FROM OphinbiometryImportedEvents o WHERE o.deviceName = :deviceName"),
    @NamedQuery(name = "OphinbiometryImportedEvents.findByDeviceModel", query = "SELECT o FROM OphinbiometryImportedEvents o WHERE o.deviceModel = :deviceModel"),
    @NamedQuery(name = "OphinbiometryImportedEvents.findByDeviceManufacturer", query = "SELECT o FROM OphinbiometryImportedEvents o WHERE o.deviceManufacturer = :deviceManufacturer"),
    @NamedQuery(name = "OphinbiometryImportedEvents.findByDeviceSoftwareVersion", query = "SELECT o FROM OphinbiometryImportedEvents o WHERE o.deviceSoftwareVersion = :deviceSoftwareVersion"),
    @NamedQuery(name = "OphinbiometryImportedEvents.findByIsLinked", query = "SELECT o FROM OphinbiometryImportedEvents o WHERE o.isLinked = :isLinked"),
    @NamedQuery(name = "OphinbiometryImportedEvents.findByLastModifiedDate", query = "SELECT o FROM OphinbiometryImportedEvents o WHERE o.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "OphinbiometryImportedEvents.findByCreatedDate", query = "SELECT o FROM OphinbiometryImportedEvents o WHERE o.createdDate = :createdDate")})
public class OphinbiometryImportedEvents implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "study_id")
    private String studyId;
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "device_name")
    private String deviceName;
    @Column(name = "device_model")
    private String deviceModel;
    @Column(name = "device_manufacturer")
    private String deviceManufacturer;
    @Column(name = "device_software_version")
    private String deviceSoftwareVersion;
    @Column(name = "device_serial_number")
    private String deviceSerialNumber;
    @Column(name = "acquisition_datetime")
    private String acquisitionDatetime;
    @Column(name = "is_linked")
    private Boolean isLinked;
    @Column(name = "is_merged")
    private Boolean isMerged;
    @Column(name = "content_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contentDateTime;
    @Column(name = "surgeon_name")
    private String surgeonName;
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
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Event eventId;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Patient patientId;
    @Column(name = "series_id")
    private String seriesId;

    public OphinbiometryImportedEvents() {
    }

    public OphinbiometryImportedEvents(Integer id) {
        this.id = id;
    }

    public OphinbiometryImportedEvents(Integer id, Date lastModifiedDate, Date createdDate) {
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

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(String deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    public String getDeviceSoftwareVersion() {
        return deviceSoftwareVersion;
    }

    public void setDeviceSoftwareVersion(String deviceSoftwareVersion) {
        this.deviceSoftwareVersion = deviceSoftwareVersion;
    }

    public String getDeviceSerialNumber(){
        return this.deviceSerialNumber;
    }
    
    public void setDeviceSerialNumber(String devSerialNumber){
        this.deviceSerialNumber = devSerialNumber;
    }
    
    public String getAcquisitionDatetime(){
        return this.acquisitionDatetime;
    }
    
    public void setAcquisitionDatetime(String acDatetime){
        this.acquisitionDatetime = acDatetime;
    }
    
    public Boolean getIsLinked() {
        return isLinked;
    }

    public void setIsLinked(Boolean isLinked) {
        this.isLinked = isLinked;
    }

    public Boolean getIsMerged() {
        return isMerged;
    }

    public void setIsMerged(Boolean isMerged) {
        this.isMerged = isMerged;
    }
    
    public Date getContentDateTime() {
        return contentDateTime;
    }

    public void setContentDateTime(Date contentDateTime) {
        this.contentDateTime = contentDateTime;
    }
    
    public String getSurgeonName() {
        return surgeonName;
    }

    public void setSurgeonName(String surgeonName) {
        this.surgeonName = surgeonName;
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

    public User getLastModifiedUserId() {
        return lastModifiedUserId;
    }

    public void setLastModifiedUserId(User lastModifiedUserId) {
        this.lastModifiedUserId = lastModifiedUserId;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }
    
    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
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
        if (!(object instanceof OphinbiometryImportedEvents)) {
            return false;
        }
        OphinbiometryImportedEvents other = (OphinbiometryImportedEvents) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.OphinbiometryImportedEvents[ id=" + id + " ]";
    }
    
}
