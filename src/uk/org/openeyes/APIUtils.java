/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.ini4j.Wini;

/**
 * Methods to connect to REST WS using for search for patient data 
 * 
 * @author vetusko
 */
public class APIUtils {
    
    // default values match with the default localhost settings (admin need API access rights!)
    private String host = "localhost";
    private Integer port = 8888;
    private String authUserName = "admin";
    private String authUserPassword = "admin";

    private String response;
    
    /**
     * Constructor - load and parse a config file (Windows ini format) and set the variable values
     * 
     * @param configFile can create a new instance using an external config file
     */
    public APIUtils(String configFile){
            File APIConfig = new File(configFile);
            if(APIConfig.exists() && !APIConfig.isDirectory()){
                Wini ini = null;
                try {
                    ini = new Wini(new File(configFile));
                    this.host = ini.get("?","api_host");
                    this.port = Integer.parseInt(ini.get("?","api_port"));
                    this.authUserName = ini.get("?","api_user");
                    this.authUserPassword = ini.get("?","api_password");
                } catch (IOException ex) {
                    Logger.getLogger(APIUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    /**
     * Set the host variable
     * 
     * @param host the name of the host to use for the connection (String)
     */
    public void setHost(String host){
            this.host = host;
        }
        
    /**
     * Set the port variable
     * 
     * @param port the port number to use for the connection (Integer)
     */
    public void setPort(Integer port){
            this.port=port;
        }
        
    /**
     * Set the value of the authUserName variable
     * 
     * @param authUserName the user to use for the connection 
     */
    public void setAuthUserName(String authUserName){
            this.authUserName = authUserName;
        }
        
    /**
     * Set the value of the authUserPassword variable
     * 
     * @param authUserPassword the password to be used for the connection
     */
    public void setAuthUserPassword(String authUserPassword){
            this.authUserPassword = authUserPassword;
        }

    /**
     * Trigger a search for patient WS call
     * 
     * @param hospitalNumber the hospital number of the patient to search for
     * @return The status code of the HTTP response
     * @throws ConnectException
     */
    public int searchPatient(String hospitalNumber) throws ConnectException{
            return this.read("Patient", "identifier="+hospitalNumber);
        }
        
    /**
     * Return the response data
     * 
     * @return the response of the HTTP call
     */
    public String getResponse(){
        return this.response;
    }
        
    /**
     * Trigger a WS call through HTTP for patient search
     * 
     * @param resourceType The REST resource name (only "Patient" supported now)
     * @param requestParams The arguments for the HTTP call 
     * @return The status code from the HTTP answer
     * @throws ConnectException 
     */
    public int read(String resourceType, String requestParams)
                    throws ConnectException {

        DefaultHttpClient http = new DefaultHttpClient();

        int result = -1;
        String strURL = "http://" + host + ":" + port + "/api/"
                        + resourceType + "?resource_type=Patient&_format=xml";
        if (requestParams != null) {
                strURL += "&" + requestParams;
        }
        HttpGet get = new HttpGet(strURL);
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
                        authUserName, authUserPassword);

        get.addHeader(BasicScheme.authenticate(creds, "US-ASCII", false));

        try {
                get.addHeader("Content-type", "text/xml");
                HttpClientBuilder builder = HttpClientBuilder.create();
                CloseableHttpClient httpclient = builder.build();

                CloseableHttpResponse httpResponse = httpclient.execute(get);
                result = httpResponse.getStatusLine().getStatusCode();
                HttpEntity entity2 = httpResponse.getEntity();
                StringWriter writer = new StringWriter();
                //IOUtils.copy(entity2.getContent(), writer);
                this.response = entity2.getContent().toString();
                EntityUtils.consume(entity2);
        } catch (ConnectException e) {
                // this happens when there's no server to connect to
            e.printStackTrace();
                throw e;
        } catch (IOException e) {
                e.printStackTrace();
        } finally {
                get.releaseConnection();
        }
        return result;
    }
}
