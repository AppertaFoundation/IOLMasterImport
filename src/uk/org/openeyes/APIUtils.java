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
 *
 * @author VEDELEKT
 */
public class APIUtils {
    
        // default values match with the default localhost settings (admin need API access rights!)
        private String host = "localhost";
        private Integer port = 8888;
        private String authUserName = "admin";
        private String authUserPassword = "admin";
        
        private String response;
    
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
        
        public void setHost(String host){
            this.host = host;
        }
        
        public void setPort(Integer port){
            this.port=port;
        }
        
        public void setAuthUserName(String authUserName){
            this.authUserName = authUserName;
        }
        
        public void setAuthUserPassword(String authUserPassword){
            this.authUserPassword = authUserPassword;
        }

        public int searchPatient(String hospitalNumber) throws ConnectException{
            return this.read("Patient", "pat", "identifier="+hospitalNumber);
        }
        
        public String getResponse(){
            return this.response;
        }
        
    	/**
	 * 
	 * @param resourceType
	 * @param jsonType
	 * @param requestParams
	 * @return
	 * @throws ConnectException 
	 */
	public int read(String resourceType, String jsonType, String requestParams)
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
