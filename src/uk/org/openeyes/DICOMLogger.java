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
    
    public DICOMLogger(){
        this.logger= new DicomImportLog();
    }
    
    public DicomImportLog getLogger(){
        return this.logger;
    }
    
    public boolean saveLogEntry(Session currentSession){
        this.saveRawOutput();
        currentSession.save(this.logger);
        return true;
    }
    
    public void addToRawOutput(String logMessage){
        this.rawOutput += logMessage+"\n";
        System.out.println(logMessage);
    }
    
    public void saveRawOutput(){
        this.logger.setRawImporterOutput(rawOutput);
    }
    
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
