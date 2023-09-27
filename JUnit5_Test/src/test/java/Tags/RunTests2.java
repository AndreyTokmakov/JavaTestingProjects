/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* RunTests2.java class
*
* @name    : RunTests2.java
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
@SelectPackages("Tags.tests2")
//@IncludeTags({"acceptance", "security", "api"})
@IncludeTags({"acceptance", "security", "api", "accessability", "performance", "load"})
public class RunTests2 {
}
