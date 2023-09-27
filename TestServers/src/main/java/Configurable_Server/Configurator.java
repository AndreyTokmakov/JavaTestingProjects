/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Configurator.java class
*
* @name    : Configurator.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 10, 2020
****************************************************************************/

package Configurable_Server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** Configuration class. **/
class Configurator {
	/** Server bind configuration block parameter name: **/
    private final static String BIND_BLOCK_NAME = "configuration"; 
    
    /** Server contexts configuration block parameter name: **/
    private final static String CONTEXTS_BLOCK_NAME = "contexts"; 
    
    /** Server bind address parameter name: **/
    private final static String BIND_IP_PARAM_NAME = "ip_address";
    /** Server bind port parameter name: **/
    private final static String BIND_PORT_PARAM_NAME = "port";
    
    /** Server context name parameter name: **/
    private final static String CONTEXT_PARAM_NAME = "context";
    /** Server context name parameter name: **/
    private final static String CONTEXT_NAME_PARAM_NAME = "name";
    /** Server context type parameter name: **/
    private final static String CONTEXT_TYPE_PARAM_NAME = "type";
    /** Server context text parameter name: **/
    private final static String CONTEXT_TEXT_PARAM_NAME = "text";
    
    /** Server bind address. **/
    private String ipAddress = "0.0.0.0";
    
    /** Server bind port. **/
    private int port = 52525;
    
    /** Server context list. **/
    private List<ServerContext> contexts = new ArrayList<ServerContext>();

    /** Parse server bind configuration block. **/
    private boolean ParseBindingBlock(final Document document) {
	    NodeList configuration = document.getElementsByTagName(Configurator.BIND_BLOCK_NAME);
	    
	    // Expecting only one block of this type: 
	    if (1 != configuration.getLength())
	    	return false;
	    
	    configuration = (NodeList)configuration.item(0);
	    for (int i = 0; i < configuration.getLength(); i++) {
	        if (configuration.item(i).getNodeType() == Node.ELEMENT_NODE) 
	        {
	            Element element = (Element) configuration.item(i);
	            if (element.getNodeName().equals(Configurator.BIND_IP_PARAM_NAME)) 
	            	ipAddress = element.getTextContent();
	            else if (element.getNodeName().equals(Configurator.BIND_PORT_PARAM_NAME)) 
	            	port = Integer.valueOf(element.getTextContent());
	        }
	    }
	    return true;
	}
	
	/** Parse server context configuration block. **/
	private boolean ParseContextsBlock(final Document document) {
		final NodeList contexts = document.getElementsByTagName(Configurator.CONTEXTS_BLOCK_NAME);
	    if (1 != contexts.getLength())
	    	return false;
	    
	    final NodeList contextsList = (NodeList) contexts.item(0);
	    for (int i = 0; i < contextsList.getLength(); i++) {
	    	Node ctx = contextsList.item(i);
	    	if (ctx.getNodeType() == Node.ELEMENT_NODE && 
	    		ctx.getNodeName().equals(Configurator.CONTEXT_PARAM_NAME)) {
	    		ParseContextParams(ctx);
	    	}
	    }
	    return true;
	}
	
	/** Parse single context configuration. **/
	private boolean ParseContextParams(final Node ctx) {
		final NodeList params = (NodeList) ctx;
		
		String type = "";
		ServerContext conxet = new ServerContext();
		for (int i = 0; i < params.getLength(); i++) {
			if (Node.ELEMENT_NODE == params.item(i).getNodeType()) {
				final Node parameter = params.item(i);
		    	
		    	if (parameter.getNodeName().equals(Configurator.CONTEXT_NAME_PARAM_NAME)) 
		    		conxet.setContext(parameter.getTextContent());
	            else if (parameter.getNodeName().equals(Configurator.CONTEXT_TEXT_PARAM_NAME)) 
		    		conxet.setText(parameter.getTextContent());
	            else if (parameter.getNodeName().equals(Configurator.CONTEXT_TYPE_PARAM_NAME)) 
	            	type = parameter.getTextContent();		    	
			}
	    }
		
		/** We need to read file content if case if type == FILE. **/ 
		if (type.equals("FILE")) {
			try {
				String text = new String(Files.readAllBytes(Paths.get(conxet.getText())));
				conxet.setText(text);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		contexts.add(conxet);
		return true;
	}
	
	public boolean ReadConfig(String file)  {
		try {
			final DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			final Document document = dBuilder.parse(new File(file));
			document.getDocumentElement().normalize();
		    return ParseBindingBlock(document) && ParseContextsBlock(document);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getPort() {
		return port;
	}

	public List<ServerContext> getContexts() {
		return contexts;
	}
	
	// TODO: For tests
	public void test() {
		System.out.println(ipAddress + ":" + port );
		for (final ServerContext ctx: contexts) {
			System.out.println(ctx.getContext());
			System.out.println(ctx.getText());
		}
	}

}