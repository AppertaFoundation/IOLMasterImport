/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.util.HashMap;

/**
 *
 * @author veta
 */
public class DICOMTools {
 
    HashMap<String, Class> DICOMTypes = new HashMap<String, Class>();
    
    /**
     *
     */
    public DICOMTools(){
        // based on http://dicom.nema.org/dicom/2013/output/chtml/part04/sect_B.5.html
        // might be useful to use the implementation class UID + version here 
        // that looks more unique and file type specific
        
        DICOMTypes.put("1.2.840.10008.5.1.4.1.1.7.4", DICOMIOLMaster500.class);
        DICOMTypes.put("1.2.840.10008.5.1.4.1.1.104.1", DICOMIOLMaster700.class);
        //DICOMTypes.put("1.2.840.10008.5.1.4.1.1.104.1", DICOMHFAVF.class);
        DICOMTypes.put("1.2.840.10008.5.1.4.1.1.77.1.5.1", DICOMKOWA.class);
    }
    
    /**
     *
     * @param TypeTag
     * @return
     */
    public Class getDICOMType(String TypeTag){
        return DICOMTypes.get(TypeTag);
    }
}
