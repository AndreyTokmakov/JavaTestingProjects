/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* SimpleHttpServer.java class
*
* @name    : SimpleHttpServer.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 7, 2020
****************************************************************************/

package HttpServers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpPrincipal;
import com.sun.net.httpserver.HttpServer;


@SuppressWarnings("unused")
public class SimpleHttpServer {
	public static void main(String[] args) 
			throws IOException, InterruptedException 
	{
		HTTPServer server = new HTTPServer();
		server.addHandler("gray","<html><body bgcolor = \"gray\"><h1>Hello</h1></body></html>");
		server.addHandler("red", "<html><body bgcolor = \"red\"><h1>Hello</h1></body></html>");
		
		server.start();
		
		
		// Handler
		
		// TimeUnit.SECONDS.sleep(7);
		// server.stop();
	}
}
