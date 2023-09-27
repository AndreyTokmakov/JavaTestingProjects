/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* StartSimpleMockServer.java class
*
* @name    : StartSimpleMockServer.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 3, 2020
****************************************************************************/

import java.util.concurrent.TimeUnit;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class SimpleMockServer {
	public static void main(String[] args) throws InterruptedException {
		WireMockServer wireMockServer = new WireMockServer(options().port(8089)); 
		wireMockServer.start();
		TimeUnit.SECONDS.sleep(30);
		wireMockServer.stop();
	}
}

