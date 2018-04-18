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
import javax.persistence.Lob;
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
@Table(name = "dicom_import_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DicomImportLog.findAll", query = "SELECT d FROM DicomImportLog d"),
    @NamedQuery(name = "DicomImportLog.findById", query = "SELECT d FROM DicomImportLog d WHERE d.id = :id"),
    @NamedQuery(name = "DicomImportLog.findByImportDatetime", query = "SELECT d FROM DicomImportLog d WHERE d.importDatetime = :importDatetime"),
    @NamedQuery(name = "DicomImportLog.findByStudyDatetime", query = "SELECT d FROM DicomImportLog d WHERE d.studyDatetime = :studyDatetime"),
    @NamedQuery(name = "DicomImportLog.findByStudyInstanceId", query = "SELECT d FROM DicomImportLog d WHERE d.studyInstanceId = :studyInstanceId"),
    @NamedQuery(name = "DicomImportLog.findByStationId", query = "SELECT d FROM DicomImportLog d WHERE d.stationId = :stationId"),
    @NamedQuery(name = "DicomImportLog.findByStudyLocation", query = "SELECT d FROM DicomImportLog d WHERE d.studyLocation = :studyLocation"),
    @NamedQuery(name = "DicomImportLog.findByMachineManufacturer", query = "SELECT d FROM DicomImportLog d WHERE d.machineManufacturer = :machineManufacturer"),
    @NamedQuery(name = "DicomImportLog.findByMachineModel", query = "SELECT d FROM DicomImportLog d WHERE d.machineModel = :machineModel"),
    @NamedQuery(name = "DicomImportLog.findByMachineSoftwareVersion", query = "SELECT d FROM DicomImportLog d WHERE d.machineSoftwareVersion = :machineSoftwareVersion"),
    @NamedQuery(name = "DicomImportLog.findByReportType", query = "SELECT d FROM DicomImportLog d WHERE d.reportType = :reportType"),
    @NamedQuery(name = "DicomImportLog.findByPatientNumber", query = "SELECT d FROM DicomImportLog d WHERE d.patientNumber = :patientNumber"),
    @NamedQuery(name = "DicomImportLog.findByStatus", query = "SELECT d FROM DicomImportLog d WHERE d.status = :status"),
    @NamedQuery(name = "DicomImportLog.findByComment", query = "SELECT d FROM DicomImportLog d WHERE d.comment = :comment"),
    @NamedQuery(name = "DicomImportLog.findByImportType", query = "SELECT d FROM DicomImportLog d WHERE d.importType = :importType")
})

public class DicomImportLog implements Serializable {
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
    @Column(name = "import_type")
    private String importType;
    @JoinColumn(name = "dicom_file_id", referencedColumnName = "id")
    @ManyToOne
    private DicomFiles dicomFileId;
    @Column(name = "raw_importer_output", columnDefinition = "TEXT")
    private String rawImporterOutput;
    @Column(name = "series_instance_id")
    private String seriesInstanceId;
    @Column(name = "sop_uid")
    private String sopUId;


    public DicomImportLog() {
    }

    public DicomImportLog(Integer id) {
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

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public DicomFiles getDicomFileIame() {
        return dicomFileId;
    }

    public void setDicomFileId(DicomFiles dicomFileId) {
        this.dicomFileId = dicomFileId;
    }

    public String getRawImporterOutput() {
        return rawImporterOutput;
    }

    public void setRawImporterOutput(String rawImporterOutput) {
        this.rawImporterOutput = rawImporterOutput;
    }

    public String getSeriesInstanceId() {
        return seriesInstanceId;
    }

    public void setSeriesInstanceId(String seriesInstanceId) {
        this.seriesInstanceId = seriesInstanceId;
    }

    public String getSopUId() {
        return sopUId;
    }

    public void setSopUId(String sopUId) {
        this.sopUId = sopUId;
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
        if (!(object instanceof DicomImportLog)) {
            return false;
        }
        DicomImportLog other = (DicomImportLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.DicomImportLog[ id=" + id + " ]";
    }

}
