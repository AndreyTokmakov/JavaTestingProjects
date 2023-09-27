/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* WireMockClient.java class
*
* @name    : WireMockClient.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 14, 2021
****************************************************************************/

package configure_remote_server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.apache.http.HttpHeaders;

public class WireMockClient {
	/** Connection timeout: **/
	private static final Duration CONNECTION_TIMEOUT = Duration.ofSeconds(10);

	/** REAL TEST LAB SERVER ADDRESS. !!! **/
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
	
	/** HTTP Client. **/
	private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(CONNECTION_TIMEOUT)
            .build();
	
	public void getAllStubs() 
	{
		HttpRequest request = HttpRequest.newBuilder().GET()
						                 .uri(URI.create(MAPPINGS_URL))
						                 .setHeader(HttpHeaders.USER_AGENT, "Java WireMock Client") 
							             .build();	
		HttpResponse<String> response;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException exc) {
			System.err.println(exc.getMessage());
			return;
		}

		System.out.println(response.statusCode());
		System.out.println(response.body());
	}
	
    public void addMapping_Test1()
    {
    	// TODO: Add JSON parser builder
        String json = new StringBuilder().append("{")
                .append("\"request\": {")
                .append("\"method\": \"GET\",")
                .append("\"url\": \"/something\"")
                .append("},")
                .append("\"response\": {")
                .append("\"body\": \"Hello world!\",")
                .append("\"headers\": {")
                .append("\"Content-Type\": \"text/plain\"")
                .append("},")
                .append("\"status\": 200")
                .append("}}").toString();
    	
		HttpRequest request = HttpRequest.newBuilder()
						.POST(HttpRequest.BodyPublishers.ofString(json))
		                .uri(URI.create(MAPPINGS_URL))
		                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
		                .setHeader(HttpHeaders.USER_AGENT, "Java WireMock Client") 
		                .build();	
		
        HttpResponse<String> response;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException exc) {
			System.err.println(exc.getMessage());
			return;
		}
		
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
    
    public void addStub(final String jsonStub) {
		HttpRequest request = HttpRequest.newBuilder()
						.POST(HttpRequest.BodyPublishers.ofString(jsonStub))
		                .uri(URI.create(MAPPINGS_URL))
		                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
		                .setHeader(HttpHeaders.USER_AGENT, "Java WireMock Client") 
		                .build();	
		
        HttpResponse<String> response;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException exc) {
			System.err.println(exc.getMessage());
			return;
		}
		
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
    
    public void deleteStub(String stubUuid) {
		HttpRequest request = HttpRequest.newBuilder()
						.POST(HttpRequest.BodyPublishers.noBody())
		                .uri(URI.create(MAPPINGS_URL + stubUuid))
		                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
		                .setHeader(HttpHeaders.USER_AGENT, "Java WireMock Client") 
		                .build();	
		
        HttpResponse<String> response;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException exc) {
			System.err.println(exc.getMessage());
			return;
		}
		
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
    
    public void deleteMappings() {
		HttpRequest request = HttpRequest.newBuilder()
						.DELETE()
		                .uri(URI.create(MAPPINGS_URL))
		                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
		                .setHeader(HttpHeaders.USER_AGENT, "Java WireMock Client") 
		                .build();	
		
        HttpResponse<String> response;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException exc) {
			System.err.println(exc.getMessage());
			return;
		}
		
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
    
    public void resetMappings() {
		HttpRequest request = HttpRequest.newBuilder()
						.POST(HttpRequest.BodyPublishers.noBody())
		                .uri(URI.create(RESET_STUB_MAPPINGS))
		                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
		                .setHeader(HttpHeaders.USER_AGENT, "Java WireMock Client") 
		                .build();	
		
        HttpResponse<String> response;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException exc) {
			System.err.println(exc.getMessage());
			return;
		}
		
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
    
    public void getAllRequests() {
		HttpRequest request = HttpRequest.newBuilder()
						.GET()
		                .uri(URI.create(GET_ALL_REQUESTS_URL))
		                .setHeader(HttpHeaders.USER_AGENT, "Java WireMock Client") 
		                .build();	
		
        HttpResponse<String> response;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException exc) {
			System.err.println(exc.getMessage());
			return;
		}
		
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    // ****************************************************************** //
    
	public static void main(String[] args) {
		WireMockClient client = new WireMockClient();
		
		// client.getAllStubs();

		// client.resetMappings();
		// client.deleteMappings();
		
		 client.addMapping_Test1();
		 
		// client.getAllRequests();
		
		// addStubs(client);
	}
	
	private static void addStubs(WireMockClient client) {
    	// TODO: Add JSON parser builder
        String json = new StringBuilder().append("{")
                .append("\"request\": {")
                .append("\"method\": \"GET\",")
                .append("\"url\": \"/some/thing\"")
                .append("},")
                .append("\"response\": {")
                .append("\"body\": \"Hello world!\",")
                .append("\"headers\": {")
                .append("\"Content-Type\": \"text/plain\"")
                .append("},")
                .append("\"status\": 200")
                .append("}}").toString();
        
        String timeoutStub = new StringBuilder().append("{")
                .append("\"request\": {")
                .append("\"method\": \"GET\",")
                .append("\"url\": \"/stubs/timeout\"")
                .append("},")
                .append("\"response\": {")
                .append("\"body\": \"Hello world!\",\"fixedDelayMilliseconds\": 15000,")
                .append("\"headers\": {")
                .append("\"Content-Type\": \"text/plain\"")
                .append("},")
                .append("\"status\": 200")
                .append("}}").toString();
        
		
		client.addStub(timeoutStub);
	}
}
