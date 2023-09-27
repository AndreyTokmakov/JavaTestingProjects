/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* AssertsJ collections asserts demo. 
*
* @name      : Collections_Asserts.java
* @author    : Tokmakov Andrey
* @version   : 1.0
* @since     : October 30, 2020
****************************************************************************/

import java.util.Set;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.util.Sets.newLinkedHashSet;
import static org.assertj.core.api.Assertions.assertThat;

public class Collections_Asserts {
	static Set<String> JEDIS = newLinkedHashSet("Luke", "Yoda", "Obiwan");
	
	// implementation with Java 8 :)
	final Condition<String> jediPower = new Condition<>(JEDIS::contains, "jedi power"); 
	
	Condition<String> jedi = new Condition<String>("jedi") {
		@Override
		public boolean matches(String value) {
			return JEDIS.contains(value);
		}
	};

	@Test
    @Disabled("for demonstration purposes") 
	public void assert_contains() {
		assertThat("Yoda").is(jedi);
		assertThat("Vader").isNot(jedi);

	}
	
	@Test
    @Disabled("for demonstration purposes") 
	public void assert_contains_Error() {
		try {
		  // condition not verified to show the clean error message
		  assertThat("Vader").is(jedi);
		} catch (AssertionError e) {
		  assertThat(e).hasMessage("expecting:<'Vader'> to be:<jedi>");
		}
	}
	
	@Test
    //@Disabled("for demonstration purposes") 
	public void assert_areNot() 
	{
		Set<String> jedi2 = newLinkedHashSet("Luke", "Yoda");
		Condition<String> jedi_cond = new Condition<>(jedi2::contains, "<TraTata>"); 
		
		assertThat(newLinkedHashSet("Luke", "Yoda", "Obiwan")).are(jedi_cond);
		//assertThat(newLinkedHashSet("Leia", "Solo")).areNot(jedi);
	}
	
	@Test
    @Disabled("for demonstration purposes") 
	public void assert_doNotHave()
	{
		assertThat(newLinkedHashSet("Luke", "Yoda")).have(jediPower);
		assertThat(newLinkedHashSet("Leia", "Solo")).doNotHave(jediPower);
	}
	
	@Test
    @Disabled("for demonstration purposes") 
	public void assert_haveAtLeast() 
	{
		assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areAtLeast(2, jedi);
		assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).haveAtLeast(2, jediPower);
	}
	
	@Test
    @Disabled("for demonstration purposes") 
	public void assert_haveAtMost() 
	{
		assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areAtMost(2, jedi);
		assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).haveAtMost(2, jediPower);
	}
	
	@Test
    @Disabled("for demonstration purposes") 
	public void assert_haveExactly() 
	{
		assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areExactly(2, jedi);
		assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).haveExactly(2, jediPower);
	}
}
