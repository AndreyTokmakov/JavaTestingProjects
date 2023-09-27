/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* TestConfigurableHTTPServer.java class
*
* @name    : TestConfigurableHTTPServer.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 10, 2020
****************************************************************************/

package HttpServers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class HTTPS_Configurable_Server {
	/** **/
	private final static String host = "0.0.0.0";
	
	/** **/
	private final static int port = 52525;
	
	/** Server handle: **/
	protected HttpServer server = null;
	
	/** Context handlers map: **/
	protected Map<String, HttpHandler> contextHandlers = 
			new HashMap<String, HttpHandler>();
	
	public HTTPS_Configurable_Server() throws IOException {
		this(HTTPS_Configurable_Server.host, 
			 HTTPS_Configurable_Server.port);
	}
	
	public HTTPS_Configurable_Server(String host, int port) throws IOException {
		// System.out.close();
		// System.err.close();
		
		this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
	}
	
	public void start() throws IOException {
		// HttpContext context = server.createContext("/");
		// context.setHandler(new Handler());

		setContextHandlers();
		server.start();
	}
	
	public void stop() {
		this.server.stop(1);
	}
	
	public void addDefaultHandler(String context) {
		this.contextHandlers.put(context, new Handler());
	}

	public void addHandler(String context, String response) {
		this.contextHandlers.put(context, new Handler(response));
	}
	
	protected void setContextHandlers() {
		for (final Entry<String, HttpHandler> entry: contextHandlers.entrySet()) {
			server.createContext("/" + entry.getKey(), entry.getValue());
		}
	}
	
	/** HTTP request handler class: **/
	private static class Handler implements HttpHandler {
		/** Stud HTTP response: **/
		private String httpResponse = 
				"<html><center><h1>Welcome to test server!</h1></center></html>";
		
		// Default Handler class constructor.
		// Returns the default HTML welcome page
		public Handler() {
		}
		
		public Handler(String response) {
			httpResponse = response;
		}
		
		@Override
		public void handle(HttpExchange httpExchange) throws IOException 
		{
			URI requestURI = httpExchange.getRequestURI();
	
			System.out.println("=====================================================");
			System.out.println( requestURI);
			
			final Headers requestHeaders = httpExchange.getRequestHeaders();
			for (final Entry<String, List<String>> header: requestHeaders.entrySet()) {
				System.out.println("  " + header);
			}
			
			httpExchange.sendResponseHeaders(200, httpResponse.getBytes().length);
			OutputStream os = httpExchange.getResponseBody();
			os.write(httpResponse.getBytes());
			os.close();
		}
	}
}

public class HTTPConfigurableServer {
	
	public static void main(String[] args) throws IOException 
	{
		HTTPS_Configurable_Server server = new HTTPS_Configurable_Server();
		server.addDefaultHandler("");
		server.addHandler("gray", "<html><body bgcolor = \"gray\"><h1>Hello</h1></body></html>");
		server.addHandler("red", "<html><body bgcolor =  \"red\"><h1>Hello</h1></body></html>");
		
		server.start();
	}
}
