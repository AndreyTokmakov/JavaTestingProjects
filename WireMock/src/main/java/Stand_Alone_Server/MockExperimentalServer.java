/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* MockExperimentalServer.java class
*
* @name    : MockExperimentalServer.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Feb 21, 2021
****************************************************************************/

package Stand_Alone_Server;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class MockExperimentalServer {
	/** **/
	private static final org.slf4j.Logger logger = 
			org.slf4j.LoggerFactory.getLogger(MockExperimentalServer.class);

	public static void main(String... args) 
	{
		int requestHandlingThreads = 40;
	    int jettyAcceptorsThreads = 10;
	    int responseThreads = 10;
		int port = 8085;
		
        WireMockServer server = new WireMockServer(WireMockConfiguration.options()
                .usingFilesUnderDirectory("./conf/wiremock")
                //.extensions(MITMProxyTransformer.class, SubscriptionWebHookTransformer.class)
                .containerThreads(requestHandlingThreads)
                .jettyAcceptors(jettyAcceptorsThreads)
                .asynchronousResponseEnabled(true)
                .asynchronousResponseThreads(responseThreads) // Set the number of asynchronous response threads
                .notifier(new ConsoleNotifier(true))
                .notifier(new Slf4jNotifier(true))
                .port(port));
        
        server.start();
		logger.info("Server started");
	}
}
