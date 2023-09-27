/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* ClientTest.java class
*
* @name    : ClientTest.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 3, 2020
****************************************************************************/

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.HttpAdminClient;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import utilities.Utilities;

public class ClientTest {
	/** **/
	private final static String host = "127.0.0.1";
	/** **/
	private final static int port = 8888;	
	
	
	protected void Create_Client() {
		 Options options = new WireMockConfiguration().port(8080).bindAddress("localhost");
		 HttpAdminClient client = new HttpAdminClient("localhost", 8080);
		 
		 System.out.println(client.getOptions().portNumber());
		 System.out.println(client.getOptions().bindAddress());
	}
	
	protected void StartServer() {
		WireMockServer wireMockServer = new WireMockServer();
		wireMockServer.start();
	}
	
	protected void SimpleRequest() throws InterruptedException, IOException {
		Options options = new WireMockConfiguration().port(port).bindAddress(host);
		WireMockServer wireMockServer = new WireMockServer(options); 
		wireMockServer.start();
		
	
		/** Create the HTTP apache client. **/
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)));
		HttpResponse httpResponse = httpClient.execute(request);
		final String response = Utilities.convertResponseToString(httpResponse);

		System.out.println(response);

		wireMockServer.stop();
	}
	
	protected void SimpleRequest_DifferentStubs() throws InterruptedException, IOException {
		Options options = new WireMockConfiguration().port(port).bindAddress(host);
		WireMockServer wireMockServer = new WireMockServer(options); 
		wireMockServer.start();
		
		
		configureFor(host, port);
		stubFor(get(urlEqualTo("/")).willReturn(aResponse().withBody("OK")));
		stubFor(get(urlEqualTo("/context1")).willReturn(aResponse().withBody("Response for ctx1")));
		stubFor(get(urlEqualTo("/context2")).willReturn(aResponse().withBody("Response for ctx2")));
		
	
		/** Create the HTTP apache client. **/
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)));
		HttpResponse httpResponse = httpClient.execute(request);
		System.out.println(Utilities.convertResponseToString(httpResponse));
		
		
		request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/context1"));
		httpResponse = httpClient.execute(request);
		System.out.println(Utilities.convertResponseToString(httpResponse));
		
		request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/context2"));
		httpResponse = httpClient.execute(request);
		System.out.println(Utilities.convertResponseToString(httpResponse));
		
		verify(getRequestedFor(urlEqualTo("/baeldung")));
		

		wireMockServer.stop();
	}
	
	protected void SimpleRequest_Validate_RequestedResources() throws InterruptedException, IOException {
		Options options = new WireMockConfiguration().port(port).bindAddress(host);
		WireMockServer wireMockServer = new WireMockServer(options); 
		wireMockServer.start();
		
		
		configureFor(host, port);
		stubFor(get(urlEqualTo("/context1")).willReturn(aResponse().withBody("Response for ctx1")));
		stubFor(get(urlEqualTo("/context2")).willReturn(aResponse().withBody("Response for ctx2")));
		stubFor(get(urlEqualTo("/context3")).willReturn(aResponse().withBody("Response for ctx3")));
		
	
		/** Create the HTTP apache client. **/
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/context1"));
		HttpResponse httpResponse = httpClient.execute(request);
		System.out.println(Utilities.convertResponseToString(httpResponse));
		
		request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/context2"));
		httpResponse = httpClient.execute(request);
		System.out.println(Utilities.convertResponseToString(httpResponse));
		
		/*
		request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/context3"));
		httpResponse = httpClient.execute(request);
		System.out.println(Utilities.convertResponseToString(httpResponse));
		*/
		
		verify(getRequestedFor(urlEqualTo("/context1")));
		verify(getRequestedFor(urlEqualTo("/context2")));
		verify(getRequestedFor(urlEqualTo("/context3")));
		

		wireMockServer.stop();
	}
	
	public static void main(String[] args) throws InterruptedException, IOException 
	{
		ClientTest client = new ClientTest();
		
		// cleint.StartServer();
		// client.Create_Client();
		
		// client.SimpleRequest();
		// client.SimpleRequest_DifferentStubs();
		client.SimpleRequest_Validate_RequestedResources();
	}
}
