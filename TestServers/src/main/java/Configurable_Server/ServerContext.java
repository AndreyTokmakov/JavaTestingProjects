/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* ServerContext.java class
*
* @name    : ServerContext.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 10, 2020
****************************************************************************/

package Configurable_Server;

/** ServerContext class. **/
class ServerContext {
	/** Context name. **/
	private String context = "/";
	
	/** Response text. **/
	private String text = "";

	public ServerContext() {
		
	}
	
	public ServerContext(String context, String text) {
		this.context = context;
		this.text = text;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
