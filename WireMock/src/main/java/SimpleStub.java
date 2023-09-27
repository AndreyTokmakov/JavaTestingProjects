/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* SimpleStub.java class
*
* @name    : SimpleStub.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 3, 2020
****************************************************************************/

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.github.tomakehurst.wiremock.WireMockServer;

import utilities.Utilities;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class SimpleStub {

	public static void main(String[] args) throws InterruptedException, ClientProtocolException, IOException {                                                
		WireMockServer wireMockServer = new WireMockServer(options().port(8081));
		wireMockServer.start();

		configureFor("localhost", 8081);
		stubFor(get(urlEqualTo("/baeldung")).willReturn(aResponse().withBody("Welcome to Baeldung!")));
		
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet request = new HttpGet("http://localhost:8081/baeldung");
		HttpResponse httpResponse = httpClient.execute(request);
		String responseString = Utilities.convertResponseToString(httpResponse);
		
		verify(getRequestedFor(urlEqualTo("/baeldung")));
		
		// assertEquals("Welcome to Baeldung!", responseString);
		System.out.println(responseString);
		
		wireMockServer.stop();
	}

}
