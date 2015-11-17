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
@Table(name = "audit_dicom_import")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditDicomImport.findAll", query = "SELECT a FROM AuditDicomImport a"),
    @NamedQuery(name = "AuditDicomImport.findById", query = "SELECT a FROM AuditDicomImport a WHERE a.id = :id"),
    @NamedQuery(name = "AuditDicomImport.findByImportDatetime", query = "SELECT a FROM AuditDicomImport a WHERE a.importDatetime = :importDatetime"),
    @NamedQuery(name = "AuditDicomImport.findByStudyDatetime", query = "SELECT a FROM AuditDicomImport a WHERE a.studyDatetime = :studyDatetime"),
    @NamedQuery(name = "AuditDicomImport.findByStudyInstanceId", query = "SELECT a FROM AuditDicomImport a WHERE a.studyInstanceId = :studyInstanceId"),
    @NamedQuery(name = "AuditDicomImport.findByStationId", query = "SELECT a FROM AuditDicomImport a WHERE a.stationId = :stationId"),
    @NamedQuery(name = "AuditDicomImport.findByStudyLocation", query = "SELECT a FROM AuditDicomImport a WHERE a.studyLocation = :studyLocation"),
    @NamedQuery(name = "AuditDicomImport.findByMachineManufacturer", query = "SELECT a FROM AuditDicomImport a WHERE a.machineManufacturer = :machineManufacturer"),
    @NamedQuery(name = "AuditDicomImport.findByMachineModel", query = "SELECT a FROM AuditDicomImport a WHERE a.machineModel = :machineModel"),
    @NamedQuery(name = "AuditDicomImport.findByMachineSoftwareVersion", query = "SELECT a FROM AuditDicomImport a WHERE a.machineSoftwareVersion = :machineSoftwareVersion"),
    @NamedQuery(name = "AuditDicomImport.findByReportType", query = "SELECT a FROM AuditDicomImport a WHERE a.reportType = :reportType"),
    @NamedQuery(name = "AuditDicomImport.findByPatientNumber", query = "SELECT a FROM AuditDicomImport a WHERE a.patientNumber = :patientNumber"),
    @NamedQuery(name = "AuditDicomImport.findByStatus", query = "SELECT a FROM AuditDicomImport a WHERE a.status = :status"),
    @NamedQuery(name = "AuditDicomImport.findByComment", query = "SELECT a FROM AuditDicomImport a WHERE a.comment = :comment")})
public class AuditDicomImport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "import_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date importDatetime;
    @Column(name = "study_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date studyDatetime;
    @Column(name = "study_instance_id")
    private String studyInstanceId;
    @Column(name = "station_id")
    private String stationId;
    @Column(name = "study_location")
    private String studyLocation;
    @Column(name = "machine_manufacturer")
    private String machineManufacturer;
    @Column(name = "machine_model")
    private String machineModel;
    @Column(name = "machine_software_version")
    private String machineSoftwareVersion;
    @Column(name = "report_type")
    private String reportType;
    @Column(name = "patient_number")
    private String patientNumber;
    @Column(name = "status")
    private String status;
    @Column(name = "comment")
    private String comment;

    public AuditDicomImport() {
    }

    public AuditDicomImport(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getImportDatetime() {
        return importDatetime;
    }

    public void setImportDatetime(Date importDatetime) {
        this.importDatetime = importDatetime;
    }

    public Date getStudyDatetime() {
        return studyDatetime;
    }

    public void setStudyDatetime(Date studyDatetime) {
        this.studyDatetime = studyDatetime;
    }

    public String getStudyInstanceId() {
        return studyInstanceId;
    }

    public void setStudyInstanceId(String studyInstanceId) {
        this.studyInstanceId = studyInstanceId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStudyLocation() {
        return studyLocation;
    }

    public void setStudyLocation(String studyLocation) {
        this.studyLocation = studyLocation;
    }

    public String getMachineManufacturer() {
        return machineManufacturer;
    }

    public void setMachineManufacturer(String machineManufacturer) {
        this.machineManufacturer = machineManufacturer;
    }

    public String getMachineModel() {
        return machineModel;
    }

    public void setMachineModel(String machineModel) {
        this.machineModel = machineModel;
    }

    public String getMachineSoftwareVersion() {
        return machineSoftwareVersion;
    }

    public void setMachineSoftwareVersion(String machineSoftwareVersion) {
        this.machineSoftwareVersion = machineSoftwareVersion;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        if (!(object instanceof AuditDicomImport)) {
            return false;
        }
        AuditDicomImport other = (AuditDicomImport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.AuditDicomImport[ id=" + id + " ]";
    }
    
}
