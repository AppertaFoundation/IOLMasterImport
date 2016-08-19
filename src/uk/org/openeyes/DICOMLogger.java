/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import org.hibernate.Session;
import uk.org.openeyes.models.DicomImportLog;

/**
 *
 * @author VEDELEKT
 */
public class DICOMLogger {
    
    private DicomImportLog logger;
    private String rawOutput = "";
    
    /**
     *
     */
    public DICOMLogger(){
        this.logger= new DicomImportLog();
    }
    
    /**
     *
     * @return
     */
    public DicomImportLog getLogger(){
        return this.logger;
    }
    
    /**
     *
     * @param currentSession
     * @return
     */
    public boolean saveLogEntry(Session currentSession){
        this.saveRawOutput();
        currentSession.save(this.logger);
        return true;
    }
    
    /**
     *
     * @param logMessage
     */
    public void addToRawOutput(String logMessage){
        this.rawOutput += logMessage+"\n";
        System.out.println(logMessage);
    }
    
    /**
     *
     */
    public void saveRawOutput(){
        this.logger.setRawImporterOutput(rawOutput);
    }
    
    /**
     *
     * @param code
     * @param Message
     * @param database
     */
    public void systemExitWithLog(Integer code, String Message, DatabaseFunctions database){
        this.logger.setComment(Message);
        this.logger.setStatus("failed");
        addToRawOutput(Message);
        saveLogEntry(database.getSession());
        database.getSession().flush();
        database.getTransaction().commit();
        database.getSession().close();
        database.closeSessionFactory();
        System.exit(code);
    }
    
}
