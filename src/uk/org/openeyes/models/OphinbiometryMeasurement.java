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
@Table(name = "ophinbiometry_measurement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OphinbiometryMeasurement.findAll", query = "SELECT o FROM OphinbiometryMeasurement o"),
    @NamedQuery(name = "OphinbiometryMeasurement.findById", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.id = :id"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLastName", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lastName = :lastName"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByFirstName", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.firstName = :firstName"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByMiddleName", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.middleName = :middleName"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByNamePrefix", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.namePrefix = :namePrefix"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByNameSuffix", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.nameSuffix = :nameSuffix"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByPatientId", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.patientId = :patientId"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByPatientsBirthDate", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.patientsBirthDate = :patientsBirthDate"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByPatientsComment", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.patientsComment = :patientsComment"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByPatientsPrivId", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.patientsPrivId = :patientsPrivId"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByMeasurementDate", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.measurementDate = :measurementDate"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRSphere", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rSphere = :rSphere"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRCylinder", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rCylinder = :rCylinder"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRAxis", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rAxis = :rAxis"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRVisualAcuity", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rVisualAcuity = :rVisualAcuity"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByREyeState", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rEyeState = :rEyeState"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRAxialLengthMean", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rAxialLengthMean = :rAxialLengthMean"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRAxialLengthCnt", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rAxialLengthCnt = :rAxialLengthCnt"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRAxialLengthStd", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rAxialLengthStd = :rAxialLengthStd"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRAxialLengthChanged", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rAxialLengthChanged = :rAxialLengthChanged"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRRadiusSeMean", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rRadiusSeMean = :rRadiusSeMean"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRRadiusSeCnt", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rRadiusSeCnt = :rRadiusSeCnt"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRRadiusSeStd", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rRadiusSeStd = :rRadiusSeStd"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRRadiusR1", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rRadiusR1 = :rRadiusR1"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRRadiusR2", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rRadiusR2 = :rRadiusR2"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRRadiusR1Axis", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rRadiusR1Axis = :rRadiusR1Axis"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRRadiusR2Axis", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rRadiusR2Axis = :rRadiusR2Axis"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRAcdMean", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rAcdMean = :rAcdMean"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRAcdCnt", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rAcdCnt = :rAcdCnt"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRAcdStd", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rAcdStd = :rAcdStd"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRWtwMean", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rWtwMean = :rWtwMean"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRWtwCnt", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rWtwCnt = :rWtwCnt"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRWtwStd", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.rWtwStd = :rWtwStd"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLSphere", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lSphere = :lSphere"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLCylinder", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lCylinder = :lCylinder"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLAxis", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lAxis = :lAxis"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLVisualAcuity", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lVisualAcuity = :lVisualAcuity"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLEyeState", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lEyeState = :lEyeState"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLAxialLengthMean", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lAxialLengthMean = :lAxialLengthMean"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLAxialLengthCnt", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lAxialLengthCnt = :lAxialLengthCnt"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLAxialLengthStd", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lAxialLengthStd = :lAxialLengthStd"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLAxialLengthChanged", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lAxialLengthChanged = :lAxialLengthChanged"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLRadiusSeMean", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lRadiusSeMean = :lRadiusSeMean"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLRadiusSeCnt", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lRadiusSeCnt = :lRadiusSeCnt"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLRadiusSeStd", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lRadiusSeStd = :lRadiusSeStd"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLRadiusR1", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lRadiusR1 = :lRadiusR1"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLRadiusR2", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lRadiusR2 = :lRadiusR2"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLRadiusR1Axis", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lRadiusR1Axis = :lRadiusR1Axis"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLRadiusR2Axis", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lRadiusR2Axis = :lRadiusR2Axis"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLAcdMean", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lAcdMean = :lAcdMean"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLAcdCnt", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lAcdCnt = :lAcdCnt"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLAcdStd", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lAcdStd = :lAcdStd"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLWtwMean", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lWtwMean = :lWtwMean"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLWtwCnt", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lWtwCnt = :lWtwCnt"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLWtwStd", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lWtwStd = :lWtwStd"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByRefractiveIndex", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.refractiveIndex = :refractiveIndex"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByIolMachineId", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.iolMachineId = :iolMachineId"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByIolPollId", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.iolPollId = :iolPollId"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByLastModifiedDate", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "OphinbiometryMeasurement.findByCreatedDate", query = "SELECT o FROM OphinbiometryMeasurement o WHERE o.createdDate = :createdDate")})
public class OphinbiometryMeasurement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "name_prefix")
    private String namePrefix;
    @Column(name = "name_suffix")
    private String nameSuffix;
    @Column(name = "patient_id")
    private String patientId;
    @Column(name = "patients_birth_date")
    private String patientsBirthDate;
    @Column(name = "patients_comment")
    private String patientsComment;
    @Column(name = "patients_priv_id")
    private String patientsPrivId;
    @Column(name = "measurement_date")
    private String measurementDate;
    @Column(name = "r_sphere")
    private String rSphere;
    @Column(name = "r_cylinder")
    private String rCylinder;
    @Column(name = "r_axis")
    private String rAxis;
    @Column(name = "r_visual_acuity")
    private String rVisualAcuity;
    @Column(name = "r_eye_state")
    private String rEyeState;
    @Column(name = "r_axial_length_mean")
    private String rAxialLengthMean;
    @Column(name = "r_axial_length_cnt")
    private String rAxialLengthCnt;
    @Column(name = "r_axial_length_std")
    private String rAxialLengthStd;
    @Column(name = "r_axial_length_changed")
    private String rAxialLengthChanged;
    @Column(name = "r_radius_se_mean")
    private String rRadiusSeMean;
    @Column(name = "r_radius_se_cnt")
    private String rRadiusSeCnt;
    @Column(name = "r_radius_se_std")
    private String rRadiusSeStd;
    @Column(name = "r_radius_r1")
    private String rRadiusR1;
    @Column(name = "r_radius_r2")
    private String rRadiusR2;
    @Column(name = "r_radius_r1_axis")
    private String rRadiusR1Axis;
    @Column(name = "r_radius_r2_axis")
    private String rRadiusR2Axis;
    @Column(name = "r_acd_mean")
    private String rAcdMean;
    @Column(name = "r_acd_cnt")
    private String rAcdCnt;
    @Column(name = "r_acd_std")
    private String rAcdStd;
    @Column(name = "r_wtw_mean")
    private String rWtwMean;
    @Column(name = "r_wtw_cnt")
    private String rWtwCnt;
    @Column(name = "r_wtw_std")
    private String rWtwStd;
    @Column(name = "l_sphere")
    private String lSphere;
    @Column(name = "l_cylinder")
    private String lCylinder;
    @Column(name = "l_axis")
    private String lAxis;
    @Column(name = "l_visual_acuity")
    private String lVisualAcuity;
    @Column(name = "l_eye_state")
    private String lEyeState;
    @Column(name = "l_axial_length_mean")
    private String lAxialLengthMean;
    @Column(name = "l_axial_length_cnt")
    private String lAxialLengthCnt;
    @Column(name = "l_axial_length_std")
    private String lAxialLengthStd;
    @Column(name = "l_axial_length_changed")
    private String lAxialLengthChanged;
    @Column(name = "l_radius_se_mean")
    private String lRadiusSeMean;
    @Column(name = "l_radius_se_cnt")
    private String lRadiusSeCnt;
    @Column(name = "l_radius_se_std")
    private String lRadiusSeStd;
    @Column(name = "l_radius_r1")
    private String lRadiusR1;
    @Column(name = "l_radius_r2")
    private String lRadiusR2;
    @Column(name = "l_radius_r1_axis")
    private String lRadiusR1Axis;
    @Column(name = "l_radius_r2_axis")
    private String lRadiusR2Axis;
    @Column(name = "l_acd_mean")
    private String lAcdMean;
    @Column(name = "l_acd_cnt")
    private String lAcdCnt;
    @Column(name = "l_acd_std")
    private String lAcdStd;
    @Column(name = "l_wtw_mean")
    private String lWtwMean;
    @Column(name = "l_wtw_cnt")
    private String lWtwCnt;
    @Column(name = "l_wtw_std")
    private String lWtwStd;
    @Column(name = "refractive_index")
    private String refractiveIndex;
    @Column(name = "iol_machine_id")
    private String iolMachineId;
    @Column(name = "iol_poll_id")
    private String iolPollId;
    @Basic(optional = false)
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;

    public OphinbiometryMeasurement() {
    }

    public OphinbiometryMeasurement(Integer id) {
        this.id = id;
    }

    public OphinbiometryMeasurement(Integer id, Date lastModifiedDate, Date createdDate) {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public void setNameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientsBirthDate() {
        return patientsBirthDate;
    }

    public void setPatientsBirthDate(String patientsBirthDate) {
        this.patientsBirthDate = patientsBirthDate;
    }

    public String getPatientsComment() {
        return patientsComment;
    }

    public void setPatientsComment(String patientsComment) {
        this.patientsComment = patientsComment;
    }

    public String getPatientsPrivId() {
        return patientsPrivId;
    }

    public void setPatientsPrivId(String patientsPrivId) {
        this.patientsPrivId = patientsPrivId;
    }

    public String getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(String measurementDate) {
        this.measurementDate = measurementDate;
    }

    public String getRSphere() {
        return rSphere;
    }

    public void setRSphere(String rSphere) {
        this.rSphere = rSphere;
    }

    public String getRCylinder() {
        return rCylinder;
    }

    public void setRCylinder(String rCylinder) {
        this.rCylinder = rCylinder;
    }

    public String getRAxis() {
        return rAxis;
    }

    public void setRAxis(String rAxis) {
        this.rAxis = rAxis;
    }

    public String getRVisualAcuity() {
        return rVisualAcuity;
    }

    public void setRVisualAcuity(String rVisualAcuity) {
        this.rVisualAcuity = rVisualAcuity;
    }

    public String getREyeState() {
        return rEyeState;
    }

    public void setREyeState(String rEyeState) {
        this.rEyeState = rEyeState;
    }

    public String getRAxialLengthMean() {
        return rAxialLengthMean;
    }

    public void setRAxialLengthMean(String rAxialLengthMean) {
        this.rAxialLengthMean = rAxialLengthMean;
    }

    public String getRAxialLengthCnt() {
        return rAxialLengthCnt;
    }

    public void setRAxialLengthCnt(String rAxialLengthCnt) {
        this.rAxialLengthCnt = rAxialLengthCnt;
    }

    public String getRAxialLengthStd() {
        return rAxialLengthStd;
    }

    public void setRAxialLengthStd(String rAxialLengthStd) {
        this.rAxialLengthStd = rAxialLengthStd;
    }

    public String getRAxialLengthChanged() {
        return rAxialLengthChanged;
    }

    public void setRAxialLengthChanged(String rAxialLengthChanged) {
        this.rAxialLengthChanged = rAxialLengthChanged;
    }

    public String getRRadiusSeMean() {
        return rRadiusSeMean;
    }

    public void setRRadiusSeMean(String rRadiusSeMean) {
        this.rRadiusSeMean = rRadiusSeMean;
    }

    public String getRRadiusSeCnt() {
        return rRadiusSeCnt;
    }

    public void setRRadiusSeCnt(String rRadiusSeCnt) {
        this.rRadiusSeCnt = rRadiusSeCnt;
    }

    public String getRRadiusSeStd() {
        return rRadiusSeStd;
    }

    public void setRRadiusSeStd(String rRadiusSeStd) {
        this.rRadiusSeStd = rRadiusSeStd;
    }

    public String getRRadiusR1() {
        return rRadiusR1;
    }

    public void setRRadiusR1(String rRadiusR1) {
        this.rRadiusR1 = rRadiusR1;
    }

    public String getRRadiusR2() {
        return rRadiusR2;
    }

    public void setRRadiusR2(String rRadiusR2) {
        this.rRadiusR2 = rRadiusR2;
    }

    public String getRRadiusR1Axis() {
        return rRadiusR1Axis;
    }

    public void setRRadiusR1Axis(String rRadiusR1Axis) {
        this.rRadiusR1Axis = rRadiusR1Axis;
    }

    public String getRRadiusR2Axis() {
        return rRadiusR2Axis;
    }

    public void setRRadiusR2Axis(String rRadiusR2Axis) {
        this.rRadiusR2Axis = rRadiusR2Axis;
    }

    public String getRAcdMean() {
        return rAcdMean;
    }

    public void setRAcdMean(String rAcdMean) {
        this.rAcdMean = rAcdMean;
    }

    public String getRAcdCnt() {
        return rAcdCnt;
    }

    public void setRAcdCnt(String rAcdCnt) {
        this.rAcdCnt = rAcdCnt;
    }

    public String getRAcdStd() {
        return rAcdStd;
    }

    public void setRAcdStd(String rAcdStd) {
        this.rAcdStd = rAcdStd;
    }

    public String getRWtwMean() {
        return rWtwMean;
    }

    public void setRWtwMean(String rWtwMean) {
        this.rWtwMean = rWtwMean;
    }

    public String getRWtwCnt() {
        return rWtwCnt;
    }

    public void setRWtwCnt(String rWtwCnt) {
        this.rWtwCnt = rWtwCnt;
    }

    public String getRWtwStd() {
        return rWtwStd;
    }

    public void setRWtwStd(String rWtwStd) {
        this.rWtwStd = rWtwStd;
    }

    public String getLSphere() {
        return lSphere;
    }

    public void setLSphere(String lSphere) {
        this.lSphere = lSphere;
    }

    public String getLCylinder() {
        return lCylinder;
    }

    public void setLCylinder(String lCylinder) {
        this.lCylinder = lCylinder;
    }

    public String getLAxis() {
        return lAxis;
    }

    public void setLAxis(String lAxis) {
        this.lAxis = lAxis;
    }

    public String getLVisualAcuity() {
        return lVisualAcuity;
    }

    public void setLVisualAcuity(String lVisualAcuity) {
        this.lVisualAcuity = lVisualAcuity;
    }

    public String getLEyeState() {
        return lEyeState;
    }

    public void setLEyeState(String lEyeState) {
        this.lEyeState = lEyeState;
    }

    public String getLAxialLengthMean() {
        return lAxialLengthMean;
    }

    public void setLAxialLengthMean(String lAxialLengthMean) {
        this.lAxialLengthMean = lAxialLengthMean;
    }

    public String getLAxialLengthCnt() {
        return lAxialLengthCnt;
    }

    public void setLAxialLengthCnt(String lAxialLengthCnt) {
        this.lAxialLengthCnt = lAxialLengthCnt;
    }

    public String getLAxialLengthStd() {
        return lAxialLengthStd;
    }

    public void setLAxialLengthStd(String lAxialLengthStd) {
        this.lAxialLengthStd = lAxialLengthStd;
    }

    public String getLAxialLengthChanged() {
        return lAxialLengthChanged;
    }

    public void setLAxialLengthChanged(String lAxialLengthChanged) {
        this.lAxialLengthChanged = lAxialLengthChanged;
    }

    public String getLRadiusSeMean() {
        return lRadiusSeMean;
    }

    public void setLRadiusSeMean(String lRadiusSeMean) {
        this.lRadiusSeMean = lRadiusSeMean;
    }

    public String getLRadiusSeCnt() {
        return lRadiusSeCnt;
    }

    public void setLRadiusSeCnt(String lRadiusSeCnt) {
        this.lRadiusSeCnt = lRadiusSeCnt;
    }

    public String getLRadiusSeStd() {
        return lRadiusSeStd;
    }

    public void setLRadiusSeStd(String lRadiusSeStd) {
        this.lRadiusSeStd = lRadiusSeStd;
    }

    public String getLRadiusR1() {
        return lRadiusR1;
    }

    public void setLRadiusR1(String lRadiusR1) {
        this.lRadiusR1 = lRadiusR1;
    }

    public String getLRadiusR2() {
        return lRadiusR2;
    }

    public void setLRadiusR2(String lRadiusR2) {
        this.lRadiusR2 = lRadiusR2;
    }

    public String getLRadiusR1Axis() {
        return lRadiusR1Axis;
    }

    public void setLRadiusR1Axis(String lRadiusR1Axis) {
        this.lRadiusR1Axis = lRadiusR1Axis;
    }

    public String getLRadiusR2Axis() {
        return lRadiusR2Axis;
    }

    public void setLRadiusR2Axis(String lRadiusR2Axis) {
        this.lRadiusR2Axis = lRadiusR2Axis;
    }

    public String getLAcdMean() {
        return lAcdMean;
    }

    public void setLAcdMean(String lAcdMean) {
        this.lAcdMean = lAcdMean;
    }

    public String getLAcdCnt() {
        return lAcdCnt;
    }

    public void setLAcdCnt(String lAcdCnt) {
        this.lAcdCnt = lAcdCnt;
    }

    public String getLAcdStd() {
        return lAcdStd;
    }

    public void setLAcdStd(String lAcdStd) {
        this.lAcdStd = lAcdStd;
    }

    public String getLWtwMean() {
        return lWtwMean;
    }

    public void setLWtwMean(String lWtwMean) {
        this.lWtwMean = lWtwMean;
    }

    public String getLWtwCnt() {
        return lWtwCnt;
    }

    public void setLWtwCnt(String lWtwCnt) {
        this.lWtwCnt = lWtwCnt;
    }

    public String getLWtwStd() {
        return lWtwStd;
    }

    public void setLWtwStd(String lWtwStd) {
        this.lWtwStd = lWtwStd;
    }

    public String getRefractiveIndex() {
        return refractiveIndex;
    }

    public void setRefractiveIndex(String refractiveIndex) {
        this.refractiveIndex = refractiveIndex;
    }

    public String getIolMachineId() {
        return iolMachineId;
    }

    public void setIolMachineId(String iolMachineId) {
        this.iolMachineId = iolMachineId;
    }

    public String getIolPollId() {
        return iolPollId;
    }

    public void setIolPollId(String iolPollId) {
        this.iolPollId = iolPollId;
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

    public User getLastModifiedUserId() {
        return lastModifiedUserId;
    }

    public void setLastModifiedUserId(User lastModifiedUserId) {
        this.lastModifiedUserId = lastModifiedUserId;
    }

    public User getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(User createdUserId) {
        this.createdUserId = createdUserId;
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
        if (!(object instanceof OphinbiometryMeasurement)) {
            return false;
        }
        OphinbiometryMeasurement other = (OphinbiometryMeasurement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.OphinbiometryMeasurement[ id=" + id + " ]";
    }
    
}
