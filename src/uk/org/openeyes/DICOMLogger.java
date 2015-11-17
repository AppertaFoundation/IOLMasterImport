/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import org.hibernate.Session;
import uk.org.openeyes.models.AuditDicomImport;

/**
 *
 * @author VEDELEKT
 */
public class DICOMLogger {
    
    private AuditDicomImport logger;
    
    public void  DICOMLogger(){
        this.logger= new AuditDicomImport();
    }
    
    public AuditDicomImport getLogger(){
        return this.logger;
    }
    
    public boolean saveLogEntry(Session currentSession){
        currentSession.save(this.logger);
        return true;
    }
    
}
