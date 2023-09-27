/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* WireMockClient__Apache.java class
*
* @name    : WireMockClient__Apache.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Feb 21, 2021
****************************************************************************/

package configure_remote_server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

final class ApacheWireMockClient {
	/** Test lab MockServer: **/
	private static final String BASE_URL = "http://127.0.0.1:8085";
	
	/** TODO. Add description **/
	private static final String SERVER_ADMIN_URL = BASE_URL + "/__admin";
	
	/** TODO. Add description **/
	private static final String MAPPINGS_URL = SERVER_ADMIN_URL + "/mappings";
	
	/** TODO. Add description **/
	private static final String RESET_STUB_MAPPINGS = MAPPINGS_URL + "/reset";
	
	/** TODO. Add description **/
	private static final String GET_ALL_REQUESTS_URL = SERVER_ADMIN_URL + "/requests";
	
	
	
	public void GET_Request(String url) throws IOException 
	{
        HttpGet request = new HttpGet(BASE_URL + url);
        request.addHeader(HttpHeaders.USER_AGENT, "ApacheTestClient");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            logResponse(response);
        }
    }
	
	//--------------------------------------------------------------------------------------//
	
	
	public void AddStub_Request_Delete() throws IOException {
		WireMock.configureFor("127.0.0.1", 8085);
		StubMapping stubMapping  = WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/context"))
				.willReturn(WireMock.aResponse()
				.withHeader(HttpHeaders.CONTENT_TYPE, "text/plain")
				.withStatus(200)
				.withBody("<html><body bgcolor='gray'>1111222223333</body></html>")));
		
		GET_Request("/context");
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
		}
		
		WireMock.removeStub(stubMapping);
	}
	
	public void addMapping2() throws IOException {
		WireMock.configureFor("127.0.0.1", 8085);
		StubMapping stubMapping = WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/context"))
				.willReturn(WireMock.aResponse()
				.withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
				.withStatus(200)
				.withBody("<html><body bgcolor='gray'>Hello!</body></html>")));
	}
	
	public void addMapping_Timeout() throws IOException {
		WireMock.configureFor("127.0.0.1", 8085);
		StubMapping stubMapping = WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/timeout"))
				.willReturn(WireMock.aResponse()
				.withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
				.withStatus(200)
				.withFixedDelay(10000)
				.withBody("<html><body bgcolor='gray'>Hello!</body></html>")));
	}
	
	public void addMapping() throws IOException {
		String mappingJson = """
			{
			    "request" : {
			      "url" : "/something",
			      "method" : "GET"
			    },
			    "response" : {
			      "status" : 200,
			      "body" : "Hello world!",
			      "headers" : {
			        "Content-Type" : "text/plain"
			      }
			    }
			}""";

        HttpPost request = new HttpPost(MAPPINGS_URL);
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(mappingJson));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            logResponse(response);
        }
	}
	
	
	public void reset_All_Mapping() throws IOException 
	{
		HttpPost request = new HttpPost(RESET_STUB_MAPPINGS);
        request.addHeader(HttpHeaders.USER_AGENT, "ApacheTestClient");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            logResponse(response);
        }
    }
	
	public void getAllStubs() throws IOException 
	{
        HttpGet request = new HttpGet(MAPPINGS_URL);
        request.addHeader(HttpHeaders.USER_AGENT, "ApacheTestClient");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            logResponse(response);
        }
    }
	
	private void logResponse(CloseableHttpResponse response)
			throws ParseException, IOException {
		 System.out.println(response.getStatusLine().toString()); 
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // return it as a String
            String result = EntityUtils.toString(entity);
            System.out.println(result);
        }
	}
}

public class WireMockClient__Apache {
	/** **/
	private static final ApacheWireMockClient client = new ApacheWireMockClient();
	
	public static void main(String[] args) throws IOException 
	{
		// client.getAllStubs();

		// client.addMapping();
		// client.reset_All_Mapping();
		
		// client.addMapping_Timeout();
		// client.addMapping2();
		
		 client.AddStub_Request_Delete();
		
		// client.GET_Request("/context");
	}
}
