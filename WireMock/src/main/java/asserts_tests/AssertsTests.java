/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* AssertsTests.java class
*
* @name    : AssertsTests.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 13, 2020
****************************************************************************/

package asserts_tests;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.unauthorized;
import static com.github.tomakehurst.wiremock.client.WireMock.temporaryRedirect;
import static com.github.tomakehurst.wiremock.client.WireMock.status;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import utilities.Utilities;

class Asserts_Worker {
	/** **/
	private final static String host = "127.0.0.1";
	/** **/
	private final static int port = 8888;
	/** **/
	private WireMockServer wireMockServer = null; 
	
	public Asserts_Worker() {
		Options options = new WireMockConfiguration().port(port).bindAddress(host);
		this.wireMockServer = new WireMockServer(options);
		wireMockServer.start();
		
		configureFor(host, port);
		stubFor(get(urlEqualTo("/context")).willReturn(aResponse()
	                						   .withHeader("Content-Type", "text/plain")
	                						   .withStatus(200)
	                						   .withBody("You've reached a valid WireMock endpoint")));
		
		stubFor(delete("/fine").willReturn(ok()));
		stubFor(get("/fine-with-body").willReturn(ok("body content")));
		stubFor(get("/json").willReturn(okJson("{ \"message\": \"Hello\" }")));
		stubFor(post("/redirect").willReturn(temporaryRedirect("/new/place")));
		stubFor(post("/sorry-no").willReturn(unauthorized()));
		stubFor(put("/status-only").willReturn(status(418)));
		stubFor(get("/not_existing").willReturn(notFound()));		
	}
	
	
	
	//@Rule
	//private WireMockRule wireMockRule = new WireMockRule(Asserts_Worker.port);
	
	protected void DEBUG() throws InterruptedException, IOException {
		Options options = new WireMockConfiguration().port(port).bindAddress(host);
		WireMockServer wireMockServer = new WireMockServer(options); 
		wireMockServer.start();
		
		configureFor(host, port);
		stubFor(get(urlEqualTo("/context1")).willReturn(aResponse().withBody("Response for ctx1")));
		stubFor(get(urlEqualTo("/context2")).willReturn(aResponse().withBody("Response for ctx2")));
		stubFor(get(urlEqualTo("/context3")).willReturn(aResponse().withBody("Response for ctx3")));
		
		//Create the HTTP apache client.
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/context1"));
		HttpResponse httpResponse = httpClient.execute(request);
		System.out.println(Utilities.convertResponseToString(httpResponse));
		
		request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/context2"));
		httpResponse = httpClient.execute(request);
		System.out.println(Utilities.convertResponseToString(httpResponse));
		
		// request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/context3"));
		// httpResponse = httpClient.execute(request);
		// System.out.println(Utilities.convertResponseToString(httpResponse));

		verify(getRequestedFor(urlEqualTo("/context1")));
		verify(getRequestedFor(urlEqualTo("/context2")));
		verify(getRequestedFor(urlEqualTo("/context3")));
		
		wireMockServer.stop();
	}
	
	public void Check_Status_Code() throws IOException {		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/context"));
		HttpResponse httpResponse = httpClient.execute(request);
		System.out.println(Utilities.convertResponseToString(httpResponse));
		
		assertThat(httpResponse.getStatusLine().getStatusCode(), is(200));
		wireMockServer.stop();
	}
	
	public void Check_Status_Code_404() throws IOException {		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet request = new HttpGet(new String("http://").concat(host).concat(":").concat(String.valueOf(port)).concat("/not_existing"));
		HttpResponse httpResponse = httpClient.execute(request);
		//System.out.println(Utilities.convertResponseToString(httpResponse));
		
		assertThat(httpResponse.getStatusLine().getStatusCode(), is(404));
		wireMockServer.stop();
	}
}

public class AssertsTests {
	/** **/
	private final static Asserts_Worker tests = new Asserts_Worker();
	
	public static void main(String[] args) throws IOException 
	{
		// tests.Check_Status_Code();
		tests.Check_Status_Code_404();
	}
}
