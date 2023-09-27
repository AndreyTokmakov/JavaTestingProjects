/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* GuiceModule.java class
*
* @name    : GuiceModule.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 23, 2020
****************************************************************************/

package DI_Guice;


import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class GuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(String.class).annotatedWith(Names.named("guice-string-0")).toInstance("Hello, ");
	}

	@Named("guice-string-1")
	@Inject
	@Singleton
	@Provides
	public String provideGuiceString() {
		return "World!!!";
	}
}