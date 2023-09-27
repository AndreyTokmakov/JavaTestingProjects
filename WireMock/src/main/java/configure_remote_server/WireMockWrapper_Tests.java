/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* WireMockWrapper_Tests.java class
*
* @name    : WireMockWrapper_Tests.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Mar 10, 2021
****************************************************************************/

package configure_remote_server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import utilities.Utilities;

public class WireMockWrapper_Tests {

	public static void main(String[] args) throws IOException {
		AddStub_Request_Delete();
	}
	
	public static void AddStub_Request_Delete() throws IOException {
		WireMockWrapper wrapper = new WireMockWrapper();
		wrapper.stubFor(WireMock.get(WireMock.urlEqualTo("/context"))
					.willReturn(WireMock.aResponse()
					.withHeader(HttpHeaders.CONTENT_TYPE, "text/plain")
					.withStatus(200)
					.withBody("<html><body bgcolor='gray'>1111222223333</body></html>")));
		 
		 new ApacheWireMockClient().GET_Request("/context");
		 Utilities.sleep(5000);
		 
		 wrapper.removeMappings();
	 }
}


final class WireMockWrapper implements AutoCloseable {
	/** **/
	private final static WireMock wireMock = new WireMock();
	
	/** **/
	private final static List<StubMapping> mappings = new ArrayList<StubMapping>();
	
	static {
		wireMock.configureFor("127.0.0.1", 8085);
	}
	
	public static StubMapping stubFor(MappingBuilder mappingBuilder) {
		StubMapping mapping = wireMock.givenThat(mappingBuilder);
		/** Check if OK.**/
		mappings.add(mapping);
		return mapping;
	}
	
	public void removeMappings() {
		for (final StubMapping stub: mappings) {
			wireMock.removeStub(stub);
		}
	}

	@Override
	public void close() throws Exception {
		removeMappings();
	}
}