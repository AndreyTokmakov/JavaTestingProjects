/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* VerifyingTests.java class
*
* @name    : VerifyingTests.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 14, 2020
****************************************************************************/

package verifying;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
// import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.assertj.core.api.Assertions;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;

import utilities.Utilities;

class Tester implements AutoCloseable {
	/** **/
	private final static String host = "127.0.0.1";
	/** **/
	private final static int port = 8888;
	/** **/
	private WireMockServer wireMockServer = null;
	
	/** **/
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	
	public Tester() {
		Options options = new WireMockConfiguration().port(port).bindAddress(host);
		this.wireMockServer = new WireMockServer(options);
		wireMockServer.start();
		
		// Check server status:
		Assertions.assertThat(wireMockServer.isRunning()).as("200 OK expeced here").isTrue();

		WireMock.configureFor(host, port);
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/context")).willReturn(WireMock.aResponse()
	                						   .withHeader("Content-Type", "text/plain")
	                						   .withStatus(200)
	                						   .withBody("You've reached a valid WireMock endpoint")));
		
		// GET requests stabs:
		WireMock.stubFor(WireMock.get("/get_requst_1").willReturn(WireMock.ok("Plane_Text_Response1")));
		WireMock.stubFor(WireMock.get("/get_requst_2").willReturn(WireMock.ok("Plane_Text_Response2")));
		WireMock.stubFor(WireMock.get("/get_requst_3").willReturn(WireMock.ok("Plane_Text_Response3")));
		WireMock.stubFor(WireMock.get("/get_requst_json").willReturn(WireMock.okJson("{ \"message\": \"Hello\" }")));
		WireMock.stubFor(WireMock.get("/get_requst_not_existing").willReturn(WireMock.notFound()));	
		
		// POST requests stabs:
		WireMock.stubFor(WireMock.post("/post_requst_1").willReturn(WireMock.ok("Plane_Text_Response1_For_POST_HTTP")));
		WireMock.stubFor(WireMock.post("/post_requst_2").willReturn(WireMock.ok("Plane_Text_Response2_For_POST_HTTP")));
		WireMock.stubFor(WireMock.post("/post_requst_redirect").willReturn(WireMock.temporaryRedirect("/new/place")));
		WireMock.stubFor(WireMock.post("/post_requst_unauthorized").willReturn(WireMock.unauthorized()));
		
		// PUT requests stabs:
		WireMock.stubFor(WireMock.put("/put_requst_status").willReturn(WireMock.status(418)));
		
		// DELETE requests stabs:
		WireMock.stubFor(WireMock.delete("/delete_requst_ok").willReturn(WireMock.ok()));
	}
	

	@Override
	public void close() throws Exception {
		System.out.println("**** Stopping server ****");
		wireMockServer.stop();
	}
	
	private String buildRequestPath(String context) {
		return new StringBuilder("http://").append(host).append(":").append(port).append(context).toString();
	}
	
	private void getHTTPRequest(String context) {
		try {
			HttpGet request = new HttpGet(this.buildRequestPath(context));
			HttpResponse httpResponse = httpClient.execute(request);
			System.out.println(Utilities.convertResponseToString(httpResponse));
		} catch (final IOException exc) {
			exc.printStackTrace();
		}
	}
	
	private void postHTTPRequest(String context) {
		try {
			HttpPost postRequest = new HttpPost(this.buildRequestPath(context));


			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", "John"));
			params.add(new BasicNameValuePair("password", "pass"));
			postRequest.setEntity(new UrlEncodedFormEntity(params));
			
			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Content-type", "application/json");
			

			HttpResponse httpResponse = httpClient.execute(postRequest);
			System.out.println(Utilities.convertResponseToString(httpResponse));
		} catch (final IOException exc) {
			exc.printStackTrace();
		}
	}
	
	//----------------------------------------- TESTS: -----------------------------------------------//
	
	protected void Verify_RequestsMade_GET_OK() throws InterruptedException, IOException {
		getHTTPRequest("/get_requst_1");
		getHTTPRequest("/get_requst_json");
		getHTTPRequest("/get_requst_2");
		
		WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_1")));
		WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_json")));
		WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_2")));
	}
	
	protected void Verify_RequestsMade_GET_Failed_1() throws InterruptedException, IOException {
		getHTTPRequest("/get_requst_1");
		getHTTPRequest("/get_requst_json");
		// getHTTPRequest("/get_requst_2");
		
		WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_1")));
		WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_json")));
		WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_2")));
	}
	
	protected void Verify_RequestsMade_POST_OK() throws InterruptedException, IOException {
		postHTTPRequest("/post_requst_1");

		WireMock.verify(WireMock.postRequestedFor(WireMock.urlEqualTo("/post_requst_1")));
	}
	
	protected void Verify_RequestsMade_POST_Fail() throws InterruptedException, IOException {
		postHTTPRequest("/post_requst_1");
		postHTTPRequest("/post_requst_2");

		WireMock.verify(WireMock.postRequestedFor(WireMock.urlEqualTo("/post_requst_1")));
		
		WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/post_requst_2")));    // WRONG: Waiting for get
		// WireMock.verify(WireMock.postRequestedFor(WireMock.urlEqualTo("/post_requst_2")));
	}
	
	protected void Verify_RequestsMade_POST_CheckHeader_OK() throws InterruptedException, IOException {
		postHTTPRequest("/post_requst_1");

		WireMock.verify(WireMock.postRequestedFor(WireMock.urlEqualTo("/post_requst_1"))
								.withHeader("Content-Type", WireMock.equalTo("application/json")));
	}
	
	protected void Verify_RequestsMade_POST_CheckHeader_Fail() throws InterruptedException, IOException {
		postHTTPRequest("/post_requst_1");

		WireMock.verify(WireMock.postRequestedFor(WireMock.urlEqualTo("/post_requst_1"))
								.withHeader("Content-Type", WireMock.equalTo("text/xml")));
	}
	
	
	protected void Verify_RequestsCount_GET() throws InterruptedException, IOException {
		final int min = 10;
		final int max = 20;
		
		int reqCount = ThreadLocalRandom.current().nextInt(min, max + 1);
		for (int i = 0;i < reqCount; ++i)
			getHTTPRequest("/get_requst_1");


		WireMock.verify(WireMock.moreThanOrExactly(min), WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_1")));
		WireMock.verify(WireMock.moreThan(min - 1), WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_1")));
		WireMock.verify(WireMock.lessThanOrExactly(max), WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_1")));
		WireMock.verify(WireMock.lessThan(max + 1), WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_1")));
		
		
		reqCount = ThreadLocalRandom.current().nextInt(min, max + 1);
		for (int i = 0;i < reqCount; ++i)
			postHTTPRequest("/post_requst_1");
		
		WireMock.verify(WireMock.moreThanOrExactly(min), WireMock.postRequestedFor(WireMock.urlEqualTo("/post_requst_1")));
		WireMock.verify(WireMock.moreThan(min - 1), WireMock.postRequestedFor(WireMock.urlEqualTo("/post_requst_1")));
		WireMock.verify(WireMock.lessThanOrExactly(max), WireMock.postRequestedFor(WireMock.urlEqualTo("/post_requst_1")));
		WireMock.verify(WireMock.lessThan(max + 1), WireMock.postRequestedFor(WireMock.urlEqualTo("/post_requst_1")));
		
		for (int i = 0; i < 5; ++i)
			getHTTPRequest("/get_requst_2");
		
		WireMock.verify(WireMock.exactly(5), WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_2")));
	}
	
	
	protected void Verify_GetAllEvents() throws InterruptedException, IOException {
		final int min = 10;
		final int max = 20;
		
		int reqCount = ThreadLocalRandom.current().nextInt(min, max + 1);
		for (int i = 0;i < reqCount; ++i)
			getHTTPRequest("/get_requst_1");
		
		reqCount = ThreadLocalRandom.current().nextInt(min, max + 1);
		for (int i = 0;i < reqCount; ++i)
			postHTTPRequest("/post_requst_1");
		
		for (int i = 0; i < 5; ++i)
			getHTTPRequest("/get_requst_2");
		
		List<ServeEvent> allEvents = WireMock.getAllServeEvents();
		for (final ServeEvent event: allEvents) {
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println(event.getRequest());
			// System.out.println(event.getTiming());
			//System.out.println(event.getRequest());
		}
	}
	
	
	protected void Verify_Get_UnmatchedRequests() throws InterruptedException, IOException {
		getHTTPRequest("/get_requst_1");
		getHTTPRequest("/get_requst_json");
		
		getHTTPRequest("/get_requst_2333");
		getHTTPRequest("/get_requst_3333");
		
		WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_1")));
		WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_json")));
		//WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/get_requst_2")));
		
		List<LoggedRequest> unmatched = WireMock.findUnmatchedRequests();
		System.out.println("Size = " + unmatched.size());
		for (final LoggedRequest event: unmatched) {
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println(event);
		}
	}
}

public class VerifyingTests {

	private final static void runTests(Tester tests ) 
			throws InterruptedException, IOException 
	{
		// tests.Verify_RequestsMade_GET_OK();
		// tests.Verify_RequestsMade_GET_Failed_1();
		
		// tests.Verify_RequestsMade_POST_OK();
		// tests.Verify_RequestsMade_POST_Fail();
		// tests.Verify_RequestsMade_POST_CheckHeader_OK();
		// tests.Verify_RequestsMade_GET_Failed_1();
		
		// tests.Verify_RequestsCount_GET();
		
		// tests.Verify_GetAllEvents();
		
		tests.Verify_Get_UnmatchedRequests();
	}
	
	public static void main(String[] args) {
		try (Tester tests = new Tester()) {
			runTests(tests);
		} catch (final Exception exc) {
			exc.printStackTrace();
		} finally {
			System.out.println("We ve done!");
		}
	}
}