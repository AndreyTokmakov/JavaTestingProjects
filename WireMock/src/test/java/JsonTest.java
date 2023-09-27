/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* JsonTest.java class
*
* @name    : JsonTest.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 3, 2020
****************************************************************************/

import com.github.tomakehurst.wiremock.common.Json;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonTest {
	private static final String TEST_VALUE = "test-value";
	private static final String JSON_WITH_NO_COMMENTS = 
			"{                                          \n" +
                "\"property\": \"" + TEST_VALUE + "\"   \n" +
            "}";

	private static final String JSON_WITH_SINGLE_QUOTES =
			"{                                            \n" +
				"'property': '" + TEST_VALUE + "'         \n" +
			"}";

	private static final String JSON_WITH_COMMENTS =
			"// this is the first comment                                                   \n" +
            "{                                                                              \n" +
                    "//this is a comment                                                    \n" +
                    "\"property\": \"" + TEST_VALUE + "\"// comment on same line as code    \n" +
            "}                                                                              \n" +
             "//this is the last comment";

	@Test
	public void testReadNoComments() {
		TestPojo pojo = Json.read(JSON_WITH_NO_COMMENTS, TestPojo.class);
		assertNotNull(pojo);
		assertThat(TEST_VALUE, is(pojo.property));
	}
	
	@Test
	public void testReadWithComments() {
		TestPojo pojo = Json.read(JSON_WITH_COMMENTS, TestPojo.class);
		assertNotNull(pojo);
		assertThat(TEST_VALUE, is(pojo.property));
	}

	@Test
	public void testReadWithSingleQuotes() {
		TestPojo pojo = Json.read(JSON_WITH_SINGLE_QUOTES, TestPojo.class);
		assertNotNull(pojo);
		assertThat(TEST_VALUE, is(pojo.property));
	}

	@Test
    public void countsAllNodesInADocument() {
        int count = Json.deepSize(Json.node(
            "{\n" +
            "    \"one\": \"GET\",\n" +
            "    \"two\": 2,\n" +
            "    \"queryParameters\": {\n" +
            "        \"param1\": {\n" +
            "            \"equalTo\": \"1\"\n" +
            "        },\n" +
            "        \"param2\": {\n" +
            "            \"matches\": \"2\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"things\": [1, 2, 3],\n" +
            "    \"deepThings\": [\n" +
            "        { \"deep1\": 1 },\n" +
            "        { \"deep2\": 2 }\n" +
            "    ]\n" +
            "}"
        ));

        assertThat(count, is(24));
    }

    @Test
    public void counts1ForEmptyArray() {
        int count = Json.deepSize(Json.node(
            "[]"
        ));

        assertThat(count, is(1));
    }

    @Test
    public void counts1ForEmptyObject() {
        int count = Json.deepSize(Json.node(
            "{}"
        ));

        assertThat(count, is(1));
    }
	
	private static class TestPojo {
		public String property;
	}
}
