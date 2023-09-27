/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* BaseTests.java class
*
* @name    : BaseTests.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 3, 2020
****************************************************************************/

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.util.concurrent.TimeUnit;

public class BaseTests {

	public static void main(String[] args) throws InterruptedException {
		WireMockServer wireMockServer = new WireMockServer(options().port(8089)); 
		wireMockServer.start();

		TimeUnit.SECONDS.sleep(30);

		wireMockServer.stop();
	}
}
