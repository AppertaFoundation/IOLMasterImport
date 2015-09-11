/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author VEDELEKT
 */
@Entity
@Table(name = "audit_dicom_import")
public class AuditDicomImport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "import_datetime")
    private Date importDatetime;
    @Column(name = "study_datetime")
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

    public String getstationId(){
        return stationId;
    }
    
    public void setstationId(String stationId){
        this.stationId = stationId;
    }
    
    public String getStudyInstanceId(){
        return studyInstanceId;
    }
    
    public void setStudyInstanceId(String studyInstanceId){
        this.studyInstanceId = studyInstanceId;
    }
    
    public String getStudyLocation(){
        return studyLocation;
    }
    
    public void setStudyLocation(String studyLocation){
        this.studyLocation = studyLocation;
    }
    
    public String getMachineManufacturer(){
        return machineManufacturer;
    }
    
    public void setMachineManufacturer(String machineManufacturer){
        this.machineManufacturer = machineManufacturer;
    }
    
    public String getMachineModel(){
        return machineModel;
    }
    
    public void setMachineModel(String machineModel){
        this.machineModel = machineModel;
    }
    
    public String getMachineSoftwareVersion(){
        return machineSoftwareVersion;
    }
    
    public void setMachineSoftwareVersion(String machineSoftwareVersion){
        this.machineSoftwareVersion = machineSoftwareVersion;
    }
    
    public String getReportType(){
        return reportType;
    }
    
    public void setReportType(String reportType){
        this.reportType = reportType;
    }
    
    public String getPatientNumber(){
        return patientNumber;
    }
    
    public void setPatientNumber(String patientNumber){
        this.patientNumber = patientNumber;
    }
    
    public String getStatus(){
        return status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
       
    public String getComment(){
        return comment;
    }
    
    public void setComment(String comment){
        this.comment = comment;
    }
    
    public Date getStudyDatetime(){
        return studyDatetime;
    }
    
    public void setStudyDatetime(Date studyDatetime){
        this.studyDatetime = studyDatetime;
    }

    
    public Date getImportDatetime(){
        return importDatetime;
    }
    
    public void setImportDatetime(Date importDatetime){
        this.importDatetime = importDatetime;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
