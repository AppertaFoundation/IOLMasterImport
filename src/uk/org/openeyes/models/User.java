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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByActive", query = "SELECT u FROM User u WHERE u.active = :active"),
    @NamedQuery(name = "User.findByGlobalFirmRights", query = "SELECT u FROM User u WHERE u.globalFirmRights = :globalFirmRights"),
    @NamedQuery(name = "User.findByTitle", query = "SELECT u FROM User u WHERE u.title = :title"),
    @NamedQuery(name = "User.findByQualifications", query = "SELECT u FROM User u WHERE u.qualifications = :qualifications"),
    @NamedQuery(name = "User.findByRole", query = "SELECT u FROM User u WHERE u.role = :role"),
    @NamedQuery(name = "User.findByCode", query = "SELECT u FROM User u WHERE u.code = :code"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findBySalt", query = "SELECT u FROM User u WHERE u.salt = :salt"),
    @NamedQuery(name = "User.findByLastModifiedDate", query = "SELECT u FROM User u WHERE u.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "User.findByCreatedDate", query = "SELECT u FROM User u WHERE u.createdDate = :createdDate"),
    @NamedQuery(name = "User.findByIsDoctor", query = "SELECT u FROM User u WHERE u.isDoctor = :isDoctor"),
    @NamedQuery(name = "User.findByIsClinical", query = "SELECT u FROM User u WHERE u.isClinical = :isClinical"),
    @NamedQuery(name = "User.findByIsConsultant", query = "SELECT u FROM User u WHERE u.isConsultant = :isConsultant"),
    @NamedQuery(name = "User.findByIsSurgeon", query = "SELECT u FROM User u WHERE u.isSurgeon = :isSurgeon"),
    @NamedQuery(name = "User.findByHasSelectedFirms", query = "SELECT u FROM User u WHERE u.hasSelectedFirms = :hasSelectedFirms"),
    @NamedQuery(name = "User.findByRegistrationCode", query = "SELECT u FROM User u WHERE u.registrationCode = :registrationCode")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @Column(name = "global_firm_rights")
    private boolean globalFirmRights;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "qualifications")
    private String qualifications;
    @Basic(optional = false)
    @Column(name = "role")
    private String role;
    @Column(name = "code")
    private String code;
    @Column(name = "password")
    private String password;
    @Column(name = "salt")
    private String salt;
    @Basic(optional = false)
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "is_doctor")
    private boolean isDoctor;
    @Basic(optional = false)
    @Column(name = "is_clinical")
    private boolean isClinical;
    @Basic(optional = false)
    @Column(name = "is_consultant")
    private boolean isConsultant;
    @Basic(optional = false)
    @Column(name = "is_surgeon")
    private boolean isSurgeon;
    @Basic(optional = false)
    @Column(name = "has_selected_firms")
    private boolean hasSelectedFirms;
    @Column(name = "registration_code")
    private String registrationCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<ServiceSubspecialtyAssignment> serviceSubspecialtyAssignmentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<ServiceSubspecialtyAssignment> serviceSubspecialtyAssignmentCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Practice> practiceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Practice> practiceCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Specialty> specialtyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Specialty> specialtyCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<EtOphinbiometryBiometrydat> etOphinbiometryBiometrydatCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<EtOphinbiometryBiometrydat> etOphinbiometryBiometrydatCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Episode> episodeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Episode> episodeCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<EtOphinbiometrySelection> etOphinbiometrySelectionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<EtOphinbiometrySelection> etOphinbiometrySelectionCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<OphinbiometryLenstypeLens> ophinbiometryLenstypeLensCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<OphinbiometryLenstypeLens> ophinbiometryLenstypeLensCollection1;
    @OneToMany(mappedBy = "consultantId")
    private Collection<Firm> firmCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Firm> firmCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Firm> firmCollection2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Institution> institutionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Institution> institutionCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Subspecialty> subspecialtyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Subspecialty> subspecialtyCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<EventType> eventTypeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<EventType> eventTypeCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Patient> patientCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Patient> patientCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Contact> contactCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Contact> contactCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<EthnicGroup> ethnicGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<EthnicGroup> ethnicGroupCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<ContactLabel> contactLabelCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<ContactLabel> contactLabelCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Event> eventCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Event> eventCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<SpecialtyType> specialtyTypeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<SpecialtyType> specialtyTypeCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<EtOphinbiometryCalculation> etOphinbiometryCalculationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<EtOphinbiometryCalculation> etOphinbiometryCalculationCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<EpisodeStatus> episodeStatusCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<EpisodeStatus> episodeStatusCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Gp> gpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Gp> gpCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<OphinbiometryMeasurement> ophinbiometryMeasurementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<OphinbiometryMeasurement> ophinbiometryMeasurementCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Eye> eyeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Eye> eyeCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Disorder> disorderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Disorder> disorderCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Site> siteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Site> siteCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<Service> serviceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<Service> serviceCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<OphinbiometryCalculationFormula> ophinbiometryCalculationFormulaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<OphinbiometryCalculationFormula> ophinbiometryCalculationFormulaCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<OphinbiometryLensPosition> ophinbiometryLensPositionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<OphinbiometryLensPosition> ophinbiometryLensPositionCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<ImportSource> importSourceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<ImportSource> importSourceCollection1;
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    @ManyToOne
    private Contact contactId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdUserId")
    private Collection<User> userCollection;
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdUserId;
    @JoinColumn(name = "doctor_grade_id", referencedColumnName = "id")
    @ManyToOne
    private DoctorGrade doctorGradeId;
    @JoinColumn(name = "last_firm_id", referencedColumnName = "id")
    @ManyToOne
    private Firm lastFirmId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedUserId")
    private Collection<User> userCollection1;
    @JoinColumn(name = "last_modified_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User lastModifiedUserId;
    @JoinColumn(name = "last_site_id", referencedColumnName = "id")
    @ManyToOne
    private Site lastSiteId;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String username, String firstName, String lastName, String email, boolean active, boolean globalFirmRights, String title, String qualifications, String role, Date lastModifiedDate, Date createdDate, boolean isDoctor, boolean isClinical, boolean isConsultant, boolean isSurgeon, boolean hasSelectedFirms) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.active = active;
        this.globalFirmRights = globalFirmRights;
        this.title = title;
        this.qualifications = qualifications;
        this.role = role;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.isDoctor = isDoctor;
        this.isClinical = isClinical;
        this.isConsultant = isConsultant;
        this.isSurgeon = isSurgeon;
        this.hasSelectedFirms = hasSelectedFirms;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getGlobalFirmRights() {
        return globalFirmRights;
    }

    public void setGlobalFirmRights(boolean globalFirmRights) {
        this.globalFirmRights = globalFirmRights;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public boolean getIsDoctor() {
        return isDoctor;
    }

    public void setIsDoctor(boolean isDoctor) {
        this.isDoctor = isDoctor;
    }

    public boolean getIsClinical() {
        return isClinical;
    }

    public void setIsClinical(boolean isClinical) {
        this.isClinical = isClinical;
    }

    public boolean getIsConsultant() {
        return isConsultant;
    }

    public void setIsConsultant(boolean isConsultant) {
        this.isConsultant = isConsultant;
    }

    public boolean getIsSurgeon() {
        return isSurgeon;
    }

    public void setIsSurgeon(boolean isSurgeon) {
        this.isSurgeon = isSurgeon;
    }

    public boolean getHasSelectedFirms() {
        return hasSelectedFirms;
    }

    public void setHasSelectedFirms(boolean hasSelectedFirms) {
        this.hasSelectedFirms = hasSelectedFirms;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    @XmlTransient
    public Collection<ServiceSubspecialtyAssignment> getServiceSubspecialtyAssignmentCollection() {
        return serviceSubspecialtyAssignmentCollection;
    }

    public void setServiceSubspecialtyAssignmentCollection(Collection<ServiceSubspecialtyAssignment> serviceSubspecialtyAssignmentCollection) {
        this.serviceSubspecialtyAssignmentCollection = serviceSubspecialtyAssignmentCollection;
    }

    @XmlTransient
    public Collection<ServiceSubspecialtyAssignment> getServiceSubspecialtyAssignmentCollection1() {
        return serviceSubspecialtyAssignmentCollection1;
    }

    public void setServiceSubspecialtyAssignmentCollection1(Collection<ServiceSubspecialtyAssignment> serviceSubspecialtyAssignmentCollection1) {
        this.serviceSubspecialtyAssignmentCollection1 = serviceSubspecialtyAssignmentCollection1;
    }

    @XmlTransient
    public Collection<Practice> getPracticeCollection() {
        return practiceCollection;
    }

    public void setPracticeCollection(Collection<Practice> practiceCollection) {
        this.practiceCollection = practiceCollection;
    }

    @XmlTransient
    public Collection<Practice> getPracticeCollection1() {
        return practiceCollection1;
    }

    public void setPracticeCollection1(Collection<Practice> practiceCollection1) {
        this.practiceCollection1 = practiceCollection1;
    }

    @XmlTransient
    public Collection<Specialty> getSpecialtyCollection() {
        return specialtyCollection;
    }

    public void setSpecialtyCollection(Collection<Specialty> specialtyCollection) {
        this.specialtyCollection = specialtyCollection;
    }

    @XmlTransient
    public Collection<Specialty> getSpecialtyCollection1() {
        return specialtyCollection1;
    }

    public void setSpecialtyCollection1(Collection<Specialty> specialtyCollection1) {
        this.specialtyCollection1 = specialtyCollection1;
    }

    @XmlTransient
    public Collection<EtOphinbiometryBiometrydat> getEtOphinbiometryBiometrydatCollection() {
        return etOphinbiometryBiometrydatCollection;
    }

    public void setEtOphinbiometryBiometrydatCollection(Collection<EtOphinbiometryBiometrydat> etOphinbiometryBiometrydatCollection) {
        this.etOphinbiometryBiometrydatCollection = etOphinbiometryBiometrydatCollection;
    }

    @XmlTransient
    public Collection<EtOphinbiometryBiometrydat> getEtOphinbiometryBiometrydatCollection1() {
        return etOphinbiometryBiometrydatCollection1;
    }

    public void setEtOphinbiometryBiometrydatCollection1(Collection<EtOphinbiometryBiometrydat> etOphinbiometryBiometrydatCollection1) {
        this.etOphinbiometryBiometrydatCollection1 = etOphinbiometryBiometrydatCollection1;
    }

    @XmlTransient
    public Collection<Episode> getEpisodeCollection() {
        return episodeCollection;
    }

    public void setEpisodeCollection(Collection<Episode> episodeCollection) {
        this.episodeCollection = episodeCollection;
    }

    @XmlTransient
    public Collection<Episode> getEpisodeCollection1() {
        return episodeCollection1;
    }

    public void setEpisodeCollection1(Collection<Episode> episodeCollection1) {
        this.episodeCollection1 = episodeCollection1;
    }

    @XmlTransient
    public Collection<EtOphinbiometrySelection> getEtOphinbiometrySelectionCollection() {
        return etOphinbiometrySelectionCollection;
    }

    public void setEtOphinbiometrySelectionCollection(Collection<EtOphinbiometrySelection> etOphinbiometrySelectionCollection) {
        this.etOphinbiometrySelectionCollection = etOphinbiometrySelectionCollection;
    }

    @XmlTransient
    public Collection<EtOphinbiometrySelection> getEtOphinbiometrySelectionCollection1() {
        return etOphinbiometrySelectionCollection1;
    }

    public void setEtOphinbiometrySelectionCollection1(Collection<EtOphinbiometrySelection> etOphinbiometrySelectionCollection1) {
        this.etOphinbiometrySelectionCollection1 = etOphinbiometrySelectionCollection1;
    }

    @XmlTransient
    public Collection<OphinbiometryLenstypeLens> getOphinbiometryLenstypeLensCollection() {
        return ophinbiometryLenstypeLensCollection;
    }

    public void setOphinbiometryLenstypeLensCollection(Collection<OphinbiometryLenstypeLens> ophinbiometryLenstypeLensCollection) {
        this.ophinbiometryLenstypeLensCollection = ophinbiometryLenstypeLensCollection;
    }

    @XmlTransient
    public Collection<OphinbiometryLenstypeLens> getOphinbiometryLenstypeLensCollection1() {
        return ophinbiometryLenstypeLensCollection1;
    }

    public void setOphinbiometryLenstypeLensCollection1(Collection<OphinbiometryLenstypeLens> ophinbiometryLenstypeLensCollection1) {
        this.ophinbiometryLenstypeLensCollection1 = ophinbiometryLenstypeLensCollection1;
    }

    @XmlTransient
    public Collection<Firm> getFirmCollection() {
        return firmCollection;
    }

    public void setFirmCollection(Collection<Firm> firmCollection) {
        this.firmCollection = firmCollection;
    }

    @XmlTransient
    public Collection<Firm> getFirmCollection1() {
        return firmCollection1;
    }

    public void setFirmCollection1(Collection<Firm> firmCollection1) {
        this.firmCollection1 = firmCollection1;
    }

    @XmlTransient
    public Collection<Firm> getFirmCollection2() {
        return firmCollection2;
    }

    public void setFirmCollection2(Collection<Firm> firmCollection2) {
        this.firmCollection2 = firmCollection2;
    }

    @XmlTransient
    public Collection<Institution> getInstitutionCollection() {
        return institutionCollection;
    }

    public void setInstitutionCollection(Collection<Institution> institutionCollection) {
        this.institutionCollection = institutionCollection;
    }

    @XmlTransient
    public Collection<Institution> getInstitutionCollection1() {
        return institutionCollection1;
    }

    public void setInstitutionCollection1(Collection<Institution> institutionCollection1) {
        this.institutionCollection1 = institutionCollection1;
    }

    @XmlTransient
    public Collection<Subspecialty> getSubspecialtyCollection() {
        return subspecialtyCollection;
    }

    public void setSubspecialtyCollection(Collection<Subspecialty> subspecialtyCollection) {
        this.subspecialtyCollection = subspecialtyCollection;
    }

    @XmlTransient
    public Collection<Subspecialty> getSubspecialtyCollection1() {
        return subspecialtyCollection1;
    }

    public void setSubspecialtyCollection1(Collection<Subspecialty> subspecialtyCollection1) {
        this.subspecialtyCollection1 = subspecialtyCollection1;
    }

    @XmlTransient
    public Collection<EventType> getEventTypeCollection() {
        return eventTypeCollection;
    }

    public void setEventTypeCollection(Collection<EventType> eventTypeCollection) {
        this.eventTypeCollection = eventTypeCollection;
    }

    @XmlTransient
    public Collection<EventType> getEventTypeCollection1() {
        return eventTypeCollection1;
    }

    public void setEventTypeCollection1(Collection<EventType> eventTypeCollection1) {
        this.eventTypeCollection1 = eventTypeCollection1;
    }

    @XmlTransient
    public Collection<Patient> getPatientCollection() {
        return patientCollection;
    }

    public void setPatientCollection(Collection<Patient> patientCollection) {
        this.patientCollection = patientCollection;
    }

    @XmlTransient
    public Collection<Patient> getPatientCollection1() {
        return patientCollection1;
    }

    public void setPatientCollection1(Collection<Patient> patientCollection1) {
        this.patientCollection1 = patientCollection1;
    }

    @XmlTransient
    public Collection<Contact> getContactCollection() {
        return contactCollection;
    }

    public void setContactCollection(Collection<Contact> contactCollection) {
        this.contactCollection = contactCollection;
    }

    @XmlTransient
    public Collection<Contact> getContactCollection1() {
        return contactCollection1;
    }

    public void setContactCollection1(Collection<Contact> contactCollection1) {
        this.contactCollection1 = contactCollection1;
    }

    @XmlTransient
    public Collection<EthnicGroup> getEthnicGroupCollection() {
        return ethnicGroupCollection;
    }

    public void setEthnicGroupCollection(Collection<EthnicGroup> ethnicGroupCollection) {
        this.ethnicGroupCollection = ethnicGroupCollection;
    }

    @XmlTransient
    public Collection<EthnicGroup> getEthnicGroupCollection1() {
        return ethnicGroupCollection1;
    }

    public void setEthnicGroupCollection1(Collection<EthnicGroup> ethnicGroupCollection1) {
        this.ethnicGroupCollection1 = ethnicGroupCollection1;
    }

    @XmlTransient
    public Collection<ContactLabel> getContactLabelCollection() {
        return contactLabelCollection;
    }

    public void setContactLabelCollection(Collection<ContactLabel> contactLabelCollection) {
        this.contactLabelCollection = contactLabelCollection;
    }

    @XmlTransient
    public Collection<ContactLabel> getContactLabelCollection1() {
        return contactLabelCollection1;
    }

    public void setContactLabelCollection1(Collection<ContactLabel> contactLabelCollection1) {
        this.contactLabelCollection1 = contactLabelCollection1;
    }

    @XmlTransient
    public Collection<Event> getEventCollection() {
        return eventCollection;
    }

    public void setEventCollection(Collection<Event> eventCollection) {
        this.eventCollection = eventCollection;
    }

    @XmlTransient
    public Collection<Event> getEventCollection1() {
        return eventCollection1;
    }

    public void setEventCollection1(Collection<Event> eventCollection1) {
        this.eventCollection1 = eventCollection1;
    }

    @XmlTransient
    public Collection<SpecialtyType> getSpecialtyTypeCollection() {
        return specialtyTypeCollection;
    }

    public void setSpecialtyTypeCollection(Collection<SpecialtyType> specialtyTypeCollection) {
        this.specialtyTypeCollection = specialtyTypeCollection;
    }

    @XmlTransient
    public Collection<SpecialtyType> getSpecialtyTypeCollection1() {
        return specialtyTypeCollection1;
    }

    public void setSpecialtyTypeCollection1(Collection<SpecialtyType> specialtyTypeCollection1) {
        this.specialtyTypeCollection1 = specialtyTypeCollection1;
    }

    @XmlTransient
    public Collection<EtOphinbiometryCalculation> getEtOphinbiometryCalculationCollection() {
        return etOphinbiometryCalculationCollection;
    }

    public void setEtOphinbiometryCalculationCollection(Collection<EtOphinbiometryCalculation> etOphinbiometryCalculationCollection) {
        this.etOphinbiometryCalculationCollection = etOphinbiometryCalculationCollection;
    }

    @XmlTransient
    public Collection<EtOphinbiometryCalculation> getEtOphinbiometryCalculationCollection1() {
        return etOphinbiometryCalculationCollection1;
    }

    public void setEtOphinbiometryCalculationCollection1(Collection<EtOphinbiometryCalculation> etOphinbiometryCalculationCollection1) {
        this.etOphinbiometryCalculationCollection1 = etOphinbiometryCalculationCollection1;
    }

    @XmlTransient
    public Collection<EpisodeStatus> getEpisodeStatusCollection() {
        return episodeStatusCollection;
    }

    public void setEpisodeStatusCollection(Collection<EpisodeStatus> episodeStatusCollection) {
        this.episodeStatusCollection = episodeStatusCollection;
    }

    @XmlTransient
    public Collection<EpisodeStatus> getEpisodeStatusCollection1() {
        return episodeStatusCollection1;
    }

    public void setEpisodeStatusCollection1(Collection<EpisodeStatus> episodeStatusCollection1) {
        this.episodeStatusCollection1 = episodeStatusCollection1;
    }

    @XmlTransient
    public Collection<Gp> getGpCollection() {
        return gpCollection;
    }

    public void setGpCollection(Collection<Gp> gpCollection) {
        this.gpCollection = gpCollection;
    }

    @XmlTransient
    public Collection<Gp> getGpCollection1() {
        return gpCollection1;
    }

    public void setGpCollection1(Collection<Gp> gpCollection1) {
        this.gpCollection1 = gpCollection1;
    }

    @XmlTransient
    public Collection<OphinbiometryMeasurement> getOphinbiometryMeasurementCollection() {
        return ophinbiometryMeasurementCollection;
    }

    public void setOphinbiometryMeasurementCollection(Collection<OphinbiometryMeasurement> ophinbiometryMeasurementCollection) {
        this.ophinbiometryMeasurementCollection = ophinbiometryMeasurementCollection;
    }

    @XmlTransient
    public Collection<OphinbiometryMeasurement> getOphinbiometryMeasurementCollection1() {
        return ophinbiometryMeasurementCollection1;
    }

    public void setOphinbiometryMeasurementCollection1(Collection<OphinbiometryMeasurement> ophinbiometryMeasurementCollection1) {
        this.ophinbiometryMeasurementCollection1 = ophinbiometryMeasurementCollection1;
    }

    @XmlTransient
    public Collection<Eye> getEyeCollection() {
        return eyeCollection;
    }

    public void setEyeCollection(Collection<Eye> eyeCollection) {
        this.eyeCollection = eyeCollection;
    }

    @XmlTransient
    public Collection<Eye> getEyeCollection1() {
        return eyeCollection1;
    }

    public void setEyeCollection1(Collection<Eye> eyeCollection1) {
        this.eyeCollection1 = eyeCollection1;
    }

    @XmlTransient
    public Collection<Disorder> getDisorderCollection() {
        return disorderCollection;
    }

    public void setDisorderCollection(Collection<Disorder> disorderCollection) {
        this.disorderCollection = disorderCollection;
    }

    @XmlTransient
    public Collection<Disorder> getDisorderCollection1() {
        return disorderCollection1;
    }

    public void setDisorderCollection1(Collection<Disorder> disorderCollection1) {
        this.disorderCollection1 = disorderCollection1;
    }

    @XmlTransient
    public Collection<Site> getSiteCollection() {
        return siteCollection;
    }

    public void setSiteCollection(Collection<Site> siteCollection) {
        this.siteCollection = siteCollection;
    }

    @XmlTransient
    public Collection<Site> getSiteCollection1() {
        return siteCollection1;
    }

    public void setSiteCollection1(Collection<Site> siteCollection1) {
        this.siteCollection1 = siteCollection1;
    }

    @XmlTransient
    public Collection<EtOphinbiometryMeasurement> getEtOphinbiometryMeasurementCollection() {
        return etOphinbiometryMeasurementCollection;
    }

    public void setEtOphinbiometryMeasurementCollection(Collection<EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection) {
        this.etOphinbiometryMeasurementCollection = etOphinbiometryMeasurementCollection;
    }

    @XmlTransient
    public Collection<EtOphinbiometryMeasurement> getEtOphinbiometryMeasurementCollection1() {
        return etOphinbiometryMeasurementCollection1;
    }

    public void setEtOphinbiometryMeasurementCollection1(Collection<EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection1) {
        this.etOphinbiometryMeasurementCollection1 = etOphinbiometryMeasurementCollection1;
    }

    @XmlTransient
    public Collection<Service> getServiceCollection() {
        return serviceCollection;
    }

    public void setServiceCollection(Collection<Service> serviceCollection) {
        this.serviceCollection = serviceCollection;
    }

    @XmlTransient
    public Collection<Service> getServiceCollection1() {
        return serviceCollection1;
    }

    public void setServiceCollection1(Collection<Service> serviceCollection1) {
        this.serviceCollection1 = serviceCollection1;
    }

    @XmlTransient
    public Collection<OphinbiometryCalculationFormula> getOphinbiometryCalculationFormulaCollection() {
        return ophinbiometryCalculationFormulaCollection;
    }

    public void setOphinbiometryCalculationFormulaCollection(Collection<OphinbiometryCalculationFormula> ophinbiometryCalculationFormulaCollection) {
        this.ophinbiometryCalculationFormulaCollection = ophinbiometryCalculationFormulaCollection;
    }

    @XmlTransient
    public Collection<OphinbiometryCalculationFormula> getOphinbiometryCalculationFormulaCollection1() {
        return ophinbiometryCalculationFormulaCollection1;
    }

    public void setOphinbiometryCalculationFormulaCollection1(Collection<OphinbiometryCalculationFormula> ophinbiometryCalculationFormulaCollection1) {
        this.ophinbiometryCalculationFormulaCollection1 = ophinbiometryCalculationFormulaCollection1;
    }

    @XmlTransient
    public Collection<OphinbiometryLensPosition> getOphinbiometryLensPositionCollection() {
        return ophinbiometryLensPositionCollection;
    }

    public void setOphinbiometryLensPositionCollection(Collection<OphinbiometryLensPosition> ophinbiometryLensPositionCollection) {
        this.ophinbiometryLensPositionCollection = ophinbiometryLensPositionCollection;
    }

    @XmlTransient
    public Collection<OphinbiometryLensPosition> getOphinbiometryLensPositionCollection1() {
        return ophinbiometryLensPositionCollection1;
    }

    public void setOphinbiometryLensPositionCollection1(Collection<OphinbiometryLensPosition> ophinbiometryLensPositionCollection1) {
        this.ophinbiometryLensPositionCollection1 = ophinbiometryLensPositionCollection1;
    }

    @XmlTransient
    public Collection<ImportSource> getImportSourceCollection() {
        return importSourceCollection;
    }

    public void setImportSourceCollection(Collection<ImportSource> importSourceCollection) {
        this.importSourceCollection = importSourceCollection;
    }

    @XmlTransient
    public Collection<ImportSource> getImportSourceCollection1() {
        return importSourceCollection1;
    }

    public void setImportSourceCollection1(Collection<ImportSource> importSourceCollection1) {
        this.importSourceCollection1 = importSourceCollection1;
    }

    public Contact getContactId() {
        return contactId;
    }

    public void setContactId(Contact contactId) {
        this.contactId = contactId;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    public User getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(User createdUserId) {
        this.createdUserId = createdUserId;
    }

    public DoctorGrade getDoctorGradeId() {
        return doctorGradeId;
    }

    public void setDoctorGradeId(DoctorGrade doctorGradeId) {
        this.doctorGradeId = doctorGradeId;
    }

    public Firm getLastFirmId() {
        return lastFirmId;
    }

    public void setLastFirmId(Firm lastFirmId) {
        this.lastFirmId = lastFirmId;
    }

    @XmlTransient
    public Collection<User> getUserCollection1() {
        return userCollection1;
    }

    public void setUserCollection1(Collection<User> userCollection1) {
        this.userCollection1 = userCollection1;
    }

    public User getLastModifiedUserId() {
        return lastModifiedUserId;
    }

    public void setLastModifiedUserId(User lastModifiedUserId) {
        this.lastModifiedUserId = lastModifiedUserId;
    }

    public Site getLastSiteId() {
        return lastSiteId;
    }

    public void setLastSiteId(Site lastSiteId) {
        this.lastSiteId = lastSiteId;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.User[ id=" + id + " ]";
    }
    
}
