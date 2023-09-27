/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Utilities.java class
*
* @name    : Utilities.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 13, 2020
****************************************************************************/

package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;

public class Utilities {

	public static String convertResponseToString(HttpResponse response) {
		try {
			InputStream responseStream = response.getEntity().getContent();
			Scanner scanner = new Scanner(responseStream, StandardCharsets.UTF_8);
			String responseString = scanner.useDelimiter("\\Z").next();
			scanner.close();
			return responseString;
		} 
		catch (final UnsupportedOperationException | IOException exc) {
			exc.printStackTrace();
		}
		return "";
	}
	
	public static void sleep(int timeout ) {
		try {
			TimeUnit.MILLISECONDS.sleep(timeout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
