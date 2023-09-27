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
			URI requestURI = httpExchange.getRequestURI();
			System.out.println(requestURI);
			
			httpExchange.sendResponseHeaders(200, httpResponse.getBytes().length);
			OutputStream os = httpExchange.getResponseBody();
			os.write(httpResponse.getBytes());
			os.close();
		}
	}

	/** Server class: **/
	private static class Server {
		/** **/
		private final static String host = "0.0.0.0";
		
		/** **/
		private final static int port = 52525;
		
		/** Server handle: **/
		protected HttpServer server = null;
		
		/** Context handlers map: **/
		protected Map<String, HttpHandler> contextHandlers = 
				new HashMap<String, HttpHandler>();
		
		public Server() throws IOException {
			this(Server.host, Server.port);
		}
		
		public Server(String host, int port) throws IOException {
			//System.out.close();
			//System.err.close();
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
		
		public void addHandler(String context, HttpHandler handler) {
			this.contextHandlers.put(context, handler);
		}
		
		protected void setContextHandlers() {
			for (final Map.Entry<String, HttpHandler> entry: contextHandlers.entrySet()) {
				server.createContext("/" + entry.getKey(), entry.getValue());
			}
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		Server server = new Server();
		server.addHandler("gray", new Handler("<html><body bgcolor = \"gray\"><h1>Hello</h1></body></html>"));
		server.addHandler("red",  new Handler("<html><body bgcolor =  \"red\"><h1>Hello</h1></body></html>"));
		
		server.start();
		
		// Handler
		// TimeUnit.SECONDS.sleep(7);
		// server.stop();
	}
}
