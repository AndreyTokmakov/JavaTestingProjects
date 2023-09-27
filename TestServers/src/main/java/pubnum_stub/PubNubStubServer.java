/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* PubNubStubServer.java class
*
* @name    : PubNubStubServer.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Feb 18, 2021
****************************************************************************/

package pubnum_stub;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


// Server statistics:
final class Statistics {
	/** **/
	public long requestsCount = 0;
	
	/** Server start up date: **/
	public final static LocalDateTime startUpDate = LocalDateTime.now();
}


// Server parameters:
final class Params {
	/** Server default binding port. **/
	public final static int DEFAULT_PORT = 52525;
	
	/** Handlers thread pool size. **/
	public final static int THREAD_POOL_SIZE = 50;
	
	/** **/
	public int execution_threads = THREAD_POOL_SIZE;
	
	/** **/
	public int bind_port = DEFAULT_PORT;
	
	/** **/
	public int response_delay = 0;
}


abstract class ServerBase {
	/** Server default binding host address. **/
	private final static String DEFAULT_HOST = "0.0.0.0";
	
	/** Delay the maximum time in seconds to wait until exchanges have finished. **/
	private final static int SHUTDOWN_DELAY = 1;
	
	/** This is the maximum number of
     *  queued incoming connections to allow on the listening socket. **/
	protected final static int DEFAULT_BACKLOG = 10;
	
	/** Server handle: **/
	protected HttpServer server = null;
	
	/** Statistics: **/
	protected final Statistics statistics;

	/** Statistics: **/
	protected final InetSocketAddress addr;
	
	/** Properties: **/
	protected Params params;
	
	protected ServerBase(int port, Statistics statistics) {
		this.statistics = statistics;
		this.addr = new InetSocketAddress(DEFAULT_HOST, port);
	}
	
	protected ServerBase(Params params, Statistics statistics) {
		this.params = params;
		this.statistics = statistics;
		this.addr = new InetSocketAddress(DEFAULT_HOST, params.bind_port);
	}
	
	protected HttpServer create() throws IOException {
		return HttpServer.create(addr, DEFAULT_BACKLOG);
	}
	
	protected void sleep(int milliSeconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(milliSeconds);
		} catch (InterruptedException e) {
			System.err.print(e.getMessage());
		}
	}
	
	public void stop() {
		this.server.stop(SHUTDOWN_DELAY);
	}
}

class StubPubNumServer extends ServerBase implements HttpHandler  {
	/** PubNub publish path. **/
	private final static String PUBNUB_PUBLISH_PATH = "/publish";
	
	/** Stub HTTP response: **/
	private final byte[] DEFAULT_RESPONSE 
		= new String("[1,\"Sent\",\"16136555496680266\"]").getBytes();
	
	public StubPubNumServer(Params params, Statistics statistics) {
		super(params, statistics);
	}
	
	public void start() throws IOException {
		this.server = create();
		ThreadPoolExecutor threadPoolExecutor = 
				(ThreadPoolExecutor) Executors.newFixedThreadPool(params.execution_threads);
		server.createContext(PUBNUB_PUBLISH_PATH, this);
		server.setExecutor(threadPoolExecutor);
		server.start();
	}
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		statistics.requestsCount++;
		if (params.response_delay > 0) {
			sleep(params.response_delay );
		}
		
		httpExchange.sendResponseHeaders(200, DEFAULT_RESPONSE.length);
		OutputStream stream = httpExchange.getResponseBody();
		stream.write(DEFAULT_RESPONSE);
		stream.close();
	}
}

class GatewayServer extends ServerBase implements HttpHandler {
	/** Server default binding port. **/
	private final static int GATEWAY_PORT = 8888;
	
	/** Date time formatter: **/
	private final static SimpleDateFormat DATE_FORMATTER = 
			new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
	
	public GatewayServer(Params params, Statistics statistics) {
		super(GATEWAY_PORT, statistics);
	}
	
	public void start() throws IOException {
		this.server = create();
		server.createContext("/", this);
		server.start();
	}
	
	private String buildResponse() {
		StringBuilder strBuilder = new StringBuilder("<body>");

	    Duration upTime = Duration.between(statistics.startUpDate, LocalDateTime.now());
	    String upTimeStr = DateTimeFormatter.ISO_LOCAL_TIME.format(upTime.addTo(LocalTime.of(0, 0)));
		
		strBuilder.append("Up time: ").append(upTimeStr).append("<br>");
		strBuilder.append("Count  : ").append(statistics.requestsCount).append("<br>");
		strBuilder.append("</body>");
		
		return strBuilder.toString();
	}
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		final String response = buildResponse();
			
		httpExchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream stream = httpExchange.getResponseBody();
		stream.write(response.getBytes());
		stream.close();
	}
}

public class PubNubStubServer {
	/** Properties file name: **/
	private final static String DEFAULT_PROPERTIES_FILE = "src/main/java/pubnum_stub/config.properties";
	
	private static Params readStartUpParam() {
		Properties properties = new Properties();
		try (InputStream input = new FileInputStream(DEFAULT_PROPERTIES_FILE)) {
			properties.load(input);
        } catch (IOException exc) {
        	System.err.println(exc.getMessage());
        	return new Params();
        }
		
		Params params = new Params();
		try {
			// Yes. one fail - all fail
			params.execution_threads = Integer.valueOf(properties.getProperty("pubnub.execution_threads"));
			params.bind_port = Integer.valueOf(properties.getProperty("pubnub.bind_port"));
			params.response_delay = Integer.valueOf(properties.getProperty("pubnub.response_delay"));
		} catch (Exception exc) {
			System.err.println(exc.getMessage());
		}
		return params;
	}
	
	public static void main(String[] args) throws IOException
	{
		final Statistics statistics = new Statistics();
		final Params params = readStartUpParam();

		new StubPubNumServer(params, statistics).start();
		new GatewayServer(params, statistics).start();
	}
}
