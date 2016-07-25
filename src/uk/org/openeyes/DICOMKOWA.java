/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.io.FileOutputStream;
import java.io.IOException;
import org.dcm4che3.data.Attributes;

/**
 *
 * @author veta
 */
public class DICOMKOWA extends DICOMParser{
    
    private DICOMParser parser;
    
    /**
     *
     * @param mainParser
     */
    public DICOMKOWA(DICOMParser mainParser){
        this.parser = mainParser;
    }

    /**
     *
     * @param Attrs
     * @throws IOException
     */
    public void collectData(Attributes Attrs) throws IOException{
        System.out.println("File type is KOWA");
        byte[] imagebytes;
        imagebytes = Attrs.getBytes(getTagInteger("7FE00010"));
        FileOutputStream fos = new FileOutputStream("d:\\work\\wombex\\WombexUK\\AcrossHealth\\KOWA_test.jpg");
        fos.write(imagebytes);
        fos.close();

    }
    
}
