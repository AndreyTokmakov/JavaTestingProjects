package Asserts;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Groupped_Asserts {
	@Test
	void groupedAssertions() {
		Address address = new Address("City", "Street", 42);
		
		assertAll("address", 
				  () -> assertEquals("City", address.city, "City missmatch"),
				  () -> assertEquals("Street", address.street, "Street missmatch"),
				  () -> assertEquals(42, address.code, "Code missmatch")
		);
	}
}


class Address {
	public  String city;
	public String street;
	public int code;
	
	public Address(String city, String street, int code) {
		this.city = city;
		this.street = street;
		this.code = code;
	}
};