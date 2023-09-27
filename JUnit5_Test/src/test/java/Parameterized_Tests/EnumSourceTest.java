package Parameterized_Tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

public class EnumSourceTest {

    enum Size {
        XXS, XS, S, M, L, XL, XXL, XXXL;
    }
    
	enum Role {
        ADMIN, SUBSCRIBER, OBSERVER, AUTHOR, PUBLISHER, ANONYMOUS
    }

    @ParameterizedTest
    @EnumSource(Size.class)
    void test_enum(Size size) {
        assertNotNull(size);
    }
    
    @ParameterizedTest
    @EnumSource(Role.class)
    void testWith_EnumSource(Role role) {
    	System.out.println("arg => "+role);
        assertNotNull(role);
    }  

    @ParameterizedTest(name = "#{index} - Is size contains {0}?")
    @EnumSource(value = Size.class, names = {"L", "XL", "XXL", "XXXL"})
    void test_enum_include(Size size) {
        assertTrue(EnumSet.allOf(Size.class).contains(size));
    }
    
    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"AUTHOR", "SUBSCRIBER"})
    void testWith_EnumSource_include(Role role) {
    	System.out.println("arg => "+role);
        assertNotNull(role);
    } 

    // Size = M, L, XL, XXL, XXXL
    @ParameterizedTest
    @EnumSource(value = Size.class, mode = EXCLUDE, names = {"XXS", "XS", "S"})
    void test_enum_exclude(Size size) {
        EnumSet<Size> excludeSmallSize = EnumSet.range(Size.M, Size.XXXL);
        assertTrue(excludeSmallSize.contains(size));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, mode = Mode.EXCLUDE, names = {"AUTHOR", "SUBSCRIBER"})
    void testWith_EnumSource_exclude(Role role) {
    	System.out.println("arg => "+role);
        assertNotNull(role);
    } 
    
    @ParameterizedTest
    @EnumSource(value = Role.class, mode = Mode.MATCH_ALL, names = "^(A|S).*R$")
    void testWith_EnumSource_Regex(Role role) {
    	System.out.println("---------");
    	System.out.println("arg => "+role);
        assertNotNull(role);
    }  
}