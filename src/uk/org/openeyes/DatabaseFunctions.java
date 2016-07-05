/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dcm4che3.util.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.ini4j.Wini;
import uk.org.openeyes.models.Episode;
import uk.org.openeyes.models.Event;
import uk.org.openeyes.models.EventType;
import uk.org.openeyes.models.OphinbiometryImportedEvents;
import uk.org.openeyes.models.Patient;
import uk.org.openeyes.models.User;

/**
 *
 * @author VEDELEKT
 */
public class DatabaseFunctions {
    private SessionFactory sessionFactory;
    protected Patient selectedPatient;
    protected Episode selectedEpisode;
    protected Session session;
    protected Transaction transaction;
    protected User selectedUser;
    protected StudyData eventStudy;
    protected BiometryData eventBiometry;
    public OphinbiometryImportedEvents importedBiometryEvent;
    protected boolean isNewEvent = true;
    protected DICOMLogger dicomLogger;
    
    protected User searchStudyUser(String userName){

        Criteria crit = session.createCriteria(User.class);
        Disjunction or = Restrictions.disjunction();
        
        User returnUser = null;
        
        String[] userNameArr = userName.split(" ");
        String lastName = "";
        
        // we cannot rely on the user name format coming from the dicom file!!!
        if(userNameArr.length == 1){
            lastName = "";
        }else{
            for(int i=1; i<userNameArr.length; i++){
                lastName += userNameArr[i]+" "; 
            }
        }
        crit.add(Restrictions.eq("firstName", userNameArr[0]));
        crit.add(Restrictions.eq("lastName", lastName));
        
        if(crit.list().isEmpty()){
            // we search for unknown user
            Criteria crit2 = session.createCriteria(User.class);
            crit2.add(Restrictions.eq("firstName", "Unknown"));
            crit2.add(Restrictions.eq("lastName", "IOLMaster"));
            
            if(crit2.list().isEmpty()){
                // we create the user
                returnUser = new User();
                returnUser.setUsername("UNKNOWN");
                returnUser.setFirstName("Unknown");
                returnUser.setLastName("IOLMaster");
                returnUser.setEmail("");
                returnUser.setActive(true);
                returnUser.setGlobalFirmRights(false);
                returnUser.setTitle("N/A");
                returnUser.setQualifications("Generated by IOLMaster import");
                returnUser.setRole("Import");
                returnUser.setLastModifiedUserId(new User(1));
                returnUser.setLastModifiedDate(new Date());
                returnUser.setCreatedUserId(new User(1));
                returnUser.setCreatedDate(new Date());
                returnUser.setIsClinical(false);
                returnUser.setIsDoctor(false);
                returnUser.setIsConsultant(false);
                returnUser.setIsSurgeon(false);
                returnUser.setHasSelectedFirms(false);
                session.save(returnUser);
            }else{
                returnUser = (User) crit2.list().get(0);
            }
            
        }else{
            returnUser = (User) crit.list().get(0);
        }
        return returnUser;
    }
    
    private Configuration configureHibernate(String iniFile){
        try {
            Wini ini = new Wini(new File(iniFile));
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://"+ini.get("?","host")+":"+ini.get("?","port")+"/"+ini.get("?","dbname"));
            configuration.setProperty("hibernate.connection.username", ""+ini.get("?","username"));
            configuration.setProperty("hibernate.connection.password", ""+ini.get("?","password"));
            configuration.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
            if(ini.get("?", "devmode", int.class) == 1){
                configuration.setProperty("show_sql", "true");
                configuration.setProperty("hbm2ddl.auto", "validate");
            }
            configuration.addAnnotatedClass (uk.org.openeyes.models.Contact.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.ContactLabel.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.DicomEyeStatus.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.DicomFiles.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.DicomImportLog.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Disorder.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.DoctorGrade.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Episode.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.EpisodeStatus.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.EthnicGroup.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.EtOphinbiometryCalculation.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.EtOphinbiometryMeasurement.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.EtOphinbiometrySelection.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.EtOphinbiometryIolRefValues.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.OphinbiometryImportedEvents.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.OphinbiometryCalculationFormula.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.OphinbiometryLenstypeLens.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.OphinbiometrySurgeon.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Event.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.EventGroup.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.EventType.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Eye.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Firm.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Gp.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.ImportSource.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Institution.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Patient.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Practice.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Service.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.ServiceSubspecialtyAssignment.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Site.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Specialty.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.SpecialtyType.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.Subspecialty.class);
            configuration.addAnnotatedClass (uk.org.openeyes.models.User.class);
            
            return configuration;
        } catch (IOException ex) {
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public void initSessionFactory(String configFile, DICOMLogger SystemLogger){
        // A SessionFactory is set up once for an application!
        // if no config specified we should use the default one
        
        // TODO: need to check for /etc/openeyes/db.conf here!!
        
        if(this.dicomLogger == null){
            this.dicomLogger = SystemLogger;
        }
        
        if(configFile.matches("(?i).*hibernate.cfg.xml") || configFile.equals("")){
            String defaultConfig = "resources/hibernate.cfg.xml";
            File inputFile = null;
            final StandardServiceRegistry registry;

            if( ! configFile.equals("")){
               inputFile = new File(configFile);
            }

            if( inputFile != null){
                registry = new StandardServiceRegistryBuilder()
                            .configure(inputFile) // configures settings from hibernate.cfg.xml
                            .build();
            }else{
                registry = new StandardServiceRegistryBuilder()
                            .configure(defaultConfig) // configures settings from hibernate.cfg.xml
                            .build();
            }

            try {
                sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
                setSession();
                setTransaction();
            }
            catch (Exception e) {
                // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
                // so destroy it manually.
                //System.out.println("Failed to connect to the database, please check your hibernate configuration file!");
                dicomLogger.addToRawOutput("Failed to connect to the database, please check your hibernate configuration file!");

                // TODO: need to add debug config here!
                e.printStackTrace();
                StandardServiceRegistryBuilder.destroy( registry );
                dicomLogger.systemExitWithLog(5, "Failed to connect to the database, please check your hibernate configuration file!", this);
                //System.exit(5);
            }
        }else{
            // try to open /etc/ database config
            
            sessionFactory = configureHibernate(configFile).buildSessionFactory();
            setSession();
            setTransaction();
        }
    }
    
    public int addVersionTableData(Object originalTable, Integer originalID){
          // http://www.tutorialspoint.com/hibernate/hibernate_native_sql.htm
          AbstractEntityPersister aep=((AbstractEntityPersister)session.getSessionFactory().getClassMetadata(originalTable.getClass()));  
          String tableName = aep.getTableName();
          String[] properties=aep.getPropertyNames();  
          String[] originalColumns = new String[properties.length];
          
          for(int nameIndex=0;nameIndex!=properties.length;nameIndex++){  
             //System.out.println("Property name: "+properties[nameIndex]);  
             String[] columns=aep.getPropertyColumnNames(nameIndex);  
             for(int columnIndex=0;columnIndex!=columns.length;columnIndex++){  
                //System.out.println("Column name: "+columns[columnIndex]);  
                originalColumns[nameIndex] = columns[columnIndex];
             }  
          } 
  
          //System.out.println("INSERT INTO "+tableName+"_version (id, "+String.join(",", originalColumns)+",`version_date`,`version_id`) SELECT id, "+String.join(",", originalColumns)+", now(), NULL FROM "+tableName+" WHERE id="+originalID.toString());
          Query query = this.session.createSQLQuery("INSERT INTO "+tableName+"_version (id, "+StringUtils.concat(originalColumns, ',')+",`version_date`,`version_id`) SELECT id, "+StringUtils.concat(originalColumns, ',')+", now(), NULL FROM "+tableName+" WHERE id="+originalID.toString());
          dicomLogger.addToRawOutput("Version audit trail has been added to: "+tableName+"_version");
          return query.executeUpdate();
    }

    public boolean checkConnection(){
        //Session session = sessionFactory.openSession();
        return session.isConnected();
    }
    
    public void closeSessionFactory(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
	}
    }
    
    public Patient getSelectedPatient(){
        return this.selectedPatient;
    }
    
    public void searchPatient(String hosNum, char gender, Calendar birthDate){
        Session session = sessionFactory.openSession();
        Criteria crit = session.createCriteria(Patient.class);
        
        // add leading 0s to the hosNum string
        if(hosNum.length() < 7){
            hosNum = ("0000000" + hosNum).substring(hosNum.length());
        }
        
        crit.add(Restrictions.eq("hosNum",hosNum));
        // we should search for M or F only
        if( Character.toString(gender).equals("F") || Character.toString(gender).equals("M")){
            crit.add(Restrictions.eq("gender", Character.toString(gender)));
        }
        int dateMonth;
        int dateYear;
        if(birthDate.get(Calendar.MONTH) == 0){
            dateMonth = 12;
            dateYear = birthDate.get(Calendar.YEAR)-1;
        }else{
            dateMonth = birthDate.get(Calendar.MONTH);
            dateYear = birthDate.get(Calendar.YEAR);
        }
        crit.add(Restrictions.sqlRestriction("dob = '"+dateYear+"-"+dateMonth+"-"+birthDate.get(Calendar.DAY_OF_MONTH)+"'"));
        List patientList = crit.list();
        
        if(patientList.isEmpty()){
            // TODO: How to handle this case??
            dicomLogger.addToRawOutput("ERROR: Patient not found for the data specified (hos_num: "+hosNum+", gender: "+gender+", dob: "+dateYear+"-"+dateMonth+"-"+birthDate.get(Calendar.DAY_OF_MONTH)+")");
        }else if(patientList.size() > 1){
            // TODO: How to handle this case??
            dicomLogger.addToRawOutput("ERROR: More than 1 record found for patient (hos_num: "+hosNum+", gender: "+gender+", dob: "+dateYear+"-"+dateMonth+"-"+birthDate.get(Calendar.DAY_OF_MONTH)+")");
        }else{
            // TODO: is everything OK?
            selectedPatient = (Patient) patientList.get(0);
        }
        if(selectedPatient != null){
            dicomLogger.addToRawOutput("Selected patient: "+selectedPatient);
        }
        session.close();
    }
    
    public void selectActiveEpisode(){
        selectedEpisode = null;
        /*
        // New requirement: always follow the manual linking process, so this part has been removed
        if(this.selectedPatient != null){
            Session session = sessionFactory.openSession();
            Criteria episodeCrit = session.createCriteria(Episode.class);
            Criteria patientJoin = episodeCrit.createCriteria("patientId");

            patientJoin.add(Restrictions.eq("id", selectedPatient.getId()));
            episodeCrit.add(Restrictions.eq("deleted",0));
            Criteria episodeStatusJoin = episodeCrit.createCriteria("episodeStatusId");
            episodeStatusJoin.add(Restrictions.ne("name", "Discharged"));
            List episodesList = episodeCrit.list();

            if(episodesList.isEmpty()){
                //System.out.println("ERROR: No open episodes found!");
                dicomLogger.addToRawOutput("ERROR: No open episodes found!");
            }else if(episodesList.size() != 1){
                //System.out.println("ERROR: More than 1 open episodes found!");
                dicomLogger.addToRawOutput("ERROR: More than 1 open episodes found!");
            }else{
                selectedEpisode = (Episode) episodesList.get(0);
                //System.out.println("Selected episode: "+selectedEpisode.toString());
                dicomLogger.addToRawOutput("Selected episode: "+selectedEpisode.toString());
            }
            
            session.close();
        }
        if(selectedEpisode == null){
            //System.out.println("ERROR: No unique open episode found, will create data without episode!");
            dicomLogger.addToRawOutput("ERROR: No unique open episode found, will create data without episode!");
        }
        */
    }
    
    public Episode getSelectedEpisode(){
        return this.selectedEpisode;        
    }
    
    /**
    *
    * 
    **/
    public String getStudyYMD(Calendar studyDate) {
        
        String formattedStudyDate = String.format("%04d-%02d-%02d %02d:%02d:%02d",
                studyDate.get(Calendar.YEAR),
                studyDate.get(Calendar.MONTH),
                studyDate.get(Calendar.DAY_OF_MONTH),
                studyDate.get(Calendar.HOUR_OF_DAY),
                studyDate.get(Calendar.MINUTE),
                studyDate.get(Calendar.SECOND)
        );
        return formattedStudyDate;
    }
    
    public String getSQLFormattedDate(Date inputDate){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        return df.format(inputDate);
    }
    
    protected Event createNewEvent(){
        Event newBiometryEvent = new Event();
        
        //System.out.println("Starting event...");
        dicomLogger.addToRawOutput("Starting event...");
        if(this.selectedEpisode != null){
            newBiometryEvent.setEpisodeId(selectedEpisode);
        }else{
            newBiometryEvent.setEpisodeId(null);
        }
        newBiometryEvent.setCreatedUserId(selectedUser);
        // search for event type name "Biometry"
        Criteria eventTypeCrit = session.createCriteria(EventType.class);
        eventTypeCrit.add(Restrictions.eq("name", "Biometry"));
        newBiometryEvent.setEventTypeId((EventType) eventTypeCrit.list().get(0));
        newBiometryEvent.setCreatedDate(new Date());
        newBiometryEvent.setLastModifiedDate(new Date());
        newBiometryEvent.setLastModifiedUserId(selectedUser);

        // TODO: need to check, because it display one month more!!!!
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        try {
            newBiometryEvent.setEventDate(df.parse(getStudyYMD(eventStudy.getStudyDateTime())));
        } catch (ParseException ex) {
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

        // let's save it!
        // 1. create new event
        session.save(newBiometryEvent);
        //System.out.println("Event saved...");
        dicomLogger.addToRawOutput("Event saved... Event id: "+newBiometryEvent.getId());
        
        return newBiometryEvent;
    }
    

    // for unit testing it need to be public
    public void setSession(){
        if(this.session == null || !(this.session.isConnected()) ){
            this.session = sessionFactory.openSession();
            session.setFlushMode(FlushMode.MANUAL);
            ManagedSessionContext.bind(session);
        }
    }
    
    public Session getSession(){
        setSession();
        return this.session;
    }
    
    // for unit testing it need to be public
    public void setTransaction(){
        //if(this.session == null){
        //    this.setSession();
        // }
        this.transaction = this.session.beginTransaction();
    }
    
    public Transaction getTransaction(){
        return this.transaction;
    }
    
    // for unit testing it need to be public
    public void setEventStudy(StudyData inputStudy){
        this.eventStudy = inputStudy;
    }
    
    private StudyData getEventStudy(){
        return this.eventStudy;
    }
    
    // for unit testing it need to be public
    public void setEventBiometry(BiometryData inputBiometry){
        this.eventBiometry = inputBiometry;
    }
    
    private BiometryData getEventBiometry(){
        return this.eventBiometry;
    }
    
    // for unit testing it need to be public
    public void setSelectedUser(){
        if(eventStudy != null){
            String SurgeonName="";
            if(eventStudy.getSurgeonName() != null){
                SurgeonName = eventStudy.getSurgeonName();
            }
            this.selectedUser = searchStudyUser(SurgeonName);
        }
    }
    
}
