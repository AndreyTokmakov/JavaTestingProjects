/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* HTTPServer.java class
*
* @name    : HTTPServer.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 7, 2020
****************************************************************************/

package HttpServers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTPServer {
	/** **/
	private final static String host = "0.0.0.0";
	
	/** **/
	private final static int port = 52525;
	
	/** Server handle: **/
	protected HttpServer server = null;
	
	/** Context handlers map: **/
	protected Map<String, HttpHandler> contextHandlers = 
			new HashMap<String, HttpHandler>();
	
	public HTTPServer() throws IOException {
		this(HTTPServer.host, HTTPServer.port);
	}
	
	public HTTPServer(String host, int port) throws IOException {
		System.out.close();
		System.err.close();
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
	
	public void addHandler(String context, String response) {
		this.contextHandlers.put(context, new Handler(response));
	}
	
	protected void setContextHandlers() {
		for (final Map.Entry<String, HttpHandler> entry: contextHandlers.entrySet()) {
			server.createContext("/" + entry.getKey(), entry.getValue());
		}
	}
	
	/** HTTP request handler class: **/
	private static class Handler implements HttpHandler {
		/** Stud HTTP response: **/
		private String httpResponse = 
				"<html><body bgcolor = \"gray\"><h1>Hello</h1></body></html>";
		
		public Handler(String response) {
			httpResponse = response;
		}
		
		@Override
		public void handle(HttpExchange httpExchange) throws IOException {
			// URI requestURI = httpExchange.getRequestURI();
			// printRequestInfo(httpExchange);
			
			httpExchange.sendResponseHeaders(200, httpResponse.getBytes().length);
			OutputStream os = httpExchange.getResponseBody();
			os.write(httpResponse.getBytes());
			os.close();
		}
	}
}
