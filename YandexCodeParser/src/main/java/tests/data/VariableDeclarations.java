package tests.data;


import tests.test_types.Cookie;
import tests.test_types.CookieEx;
import tests.test_types.MyTest;

public class VariableDeclarations {

    @MyTest
    void test() {
        Cookie cookieVar1 = new Cookie("my_first_cookie", "15");
        cookieVar1 = new Cookie("my_first_cookie", "20");
        Cookie cookieVar2 = new Cookie("my_first_cookie", "25");
        Cookie cookieVar3 = new CookieEx("my_first_cookie", "25");
        String str = "";
        int v = 123;
    }

}
