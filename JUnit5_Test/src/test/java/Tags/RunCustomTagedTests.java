/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* RunCustomTagedTests.java class
*
* @name    : RunCustomTagedTests.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 9, 2021
****************************************************************************/

package Tags;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages("Tags.custom_tags")
// @IncludeTags({"accessability", "load"})
@IncludeTags({"accessability", "load", "api", "performance"})
public class RunCustomTagedTests {

}
