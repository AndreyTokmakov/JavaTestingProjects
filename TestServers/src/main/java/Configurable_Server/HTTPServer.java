/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* TestConfigurableHTTPServer.java class
*
* @name    : TestConfigurableHTTPServer.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 10, 2020
****************************************************************************/

package Configurable_Server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
	
	/** Server configuration reader. **/
	private Configurator configuration = new Configurator();
	
	/** Context handlers map: **/
	protected Map<String, HttpHandler> contextHandlers = 
			new HashMap<String, HttpHandler>();
	
	public HTTPS_Configurable_Server() throws IOException {
		this(HTTPS_Configurable_Server.host, 
			 HTTPS_Configurable_Server.port);
	}
	
	public HTTPS_Configurable_Server(String host, int port) throws IOException {
		// TODO: This is default context. Rework!
		this.addHandler("");
		
		this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
	}
	
	public HTTPS_Configurable_Server(String file) throws IOException {
		// TODO: This is default context. Rework!
		this.addHandler("");
		
		if (true == configuration.ReadConfig(file)) {
			this.server = HttpServer.create(
					new InetSocketAddress(configuration.getIpAddress(), 
										  configuration.getPort()), 0);
			for (final ServerContext ctx: configuration.getContexts()) {
				this.addHandler(ctx.getContext(), ctx.getText());
			}
		}
		else {
			this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
		}
	}
	
	public void start() throws IOException {
		// HttpContext context = server.createContext("/");
		// context.setHandler(new Handler());

		this.setContextHandlers();
		server.start();
	}
	
	public void stop() {
		this.server.stop(1);
	}
	
	public void addHandler(String context) {
		this.contextHandlers.put(context, new Handler());
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
		// private String httpResponse = "<html><center><h1>Welcome to test server!</h1></center></html>";
		
		private String httpResponse = "[1,\"Sent\",\"16136555496680266\"]";
		
		private static long count = 0;
		
		/** Date time formatter: **/
		private final static DateTimeFormatter DATE_FORMATTER = 
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		
		
		// Default Handler class constructor.
		// Returns the default HTML welcome page
		public Handler() {
		}
		
		public Handler(String response) {
			httpResponse = response;
		}
		
		@Override
		public void handle(HttpExchange httpExchange) throws IOException {
			URI requestURI = httpExchange.getRequestURI();
			
			
			Handler.count++;
			if (0 == Handler.count % 100) {
				System.out.println(Handler.count);
			}
			
			/*
			System.out.println(new String("[").concat(DATE_FORMATTER.format(LocalDateTime.now())).
					concat("] Url len: ").concat(String.valueOf(requestURI.toString().length())));
			
			try {
				TimeUnit.SECONDS.sleep(120);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			System.out.println(new String("[").concat(DATE_FORMATTER.format(LocalDateTime.now())).
					concat("] OK."));
			*/
	
			httpExchange.sendResponseHeaders(200, httpResponse.getBytes().length);
			OutputStream os = httpExchange.getResponseBody();
			os.write(httpResponse.getBytes());
			os.close();
		}
	}
}

public class HTTPServer {
	public static void main(String[] args) throws IOException {
		HTTPS_Configurable_Server server = null;
		if (0 == args.length ) {
			server = new HTTPS_Configurable_Server();
		} else if (1 == args.length ) {
			server = new HTTPS_Configurable_Server(args[0]);
		} else if (2 == args.length ) {
			server = new HTTPS_Configurable_Server(args[0], Integer.valueOf(args[1]));
		}
		server.start();
	}
}
