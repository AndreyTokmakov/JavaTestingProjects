/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Stubs_Test.java class
*
* @name    : Stubs_Test.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 13, 2020
****************************************************************************/

package stubs;

// import static org.hamcrest.CoreMatchers.is;
// import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

import utilities.Utilities;

class Asserts_Worker {
	/** **/
	private final static String host = "127.0.0.1";
	/** **/
	private final static int port = 52525;
	/** **/
	private WireMockServer wireMockServer = null; 
	
	private StubMapping stubMapping = null;
	
	public Asserts_Worker() {
		Options options = new WireMockConfiguration().port(port).bindAddress(host);
		this.wireMockServer = new WireMockServer(options);
		wireMockServer.start();
		
		WireMock.configureFor(host, port);
		stubMapping = WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/context"))
				              .willReturn(WireMock.aResponse().withHeader("Content-Type", "text/plain")
	                					   					  .withStatus(200)
	                					   					  .withBody("<html><body bgcolor='gray'>Hello!</body></html>")));
	}

	public void Check_Status_Code() throws IOException, InterruptedException 
	{		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/context"));
		HttpResponse httpResponse = httpClient.execute(request);
		System.out.println(Utilities.convertResponseToString(httpResponse));
		
		// Assertions.assertThat(httpResponse.getStatusLine().getStatusCode(), CoreMatchers.is(200));
		
		// Using AssertJ
		Assertions.assertThat(httpResponse.getStatusLine().getStatusCode()).as("200 OK expeced here").isEqualTo(200);
		
		// TimeUnit.SECONDS.sleep(30);
		
		wireMockServer.stop();
		System.out.println("Ok!");
	}
	
	public void Check_Status_AfterReset() throws IOException 
	{		
		WireMock.removeStub(stubMapping);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/context"));
		HttpResponse httpResponse = httpClient.execute(request);
		System.out.println(Utilities.convertResponseToString(httpResponse));
		
		// We'll get 404! Since the removeStub() called
		
		// assertThat(httpResponse.getStatusLine().getStatusCode(), CoreMatchers.is(404));
		
		// Using AssertJ
		Assertions.assertThat(httpResponse.getStatusLine().getStatusCode()).as("404 Not Found expeced here").isEqualTo(404);
		
		wireMockServer.stop();
	}
	
	public void TEST() throws IOException 
	{		
		WireMock.removeStub(stubMapping);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/context"));
		HttpResponse httpResponse = httpClient.execute(request);
		System.out.println(Utilities.convertResponseToString(httpResponse));
		
		// We'll get 404! Since the removeStub() called
		
		// assertThat(httpResponse.getStatusLine().getStatusCode(), CoreMatchers.is(404));
		
		// Using AssertJ
		Assertions.assertThat(httpResponse.getStatusLine().getStatusCode()).as("404 Not Found expeced here").isEqualTo(404);
		
		wireMockServer.stop();
	}
}	

public class Stubs_Test {
	/** **/
	private final static Asserts_Worker tests = new Asserts_Worker();
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		 tests.Check_Status_Code();
		// tests.Check_Status_AfterReset();
		// tests.TEST();
	}
}
