package tests.data;


import tests.test_types.Cookie;
import tests.test_types.MyTest;

public class MethodsDeclaration {

    @MyTest
    void test() {
        Cookie cookieVar1 = new Cookie("my_first_cookie", "151");
        Cookie cookieVar2 = new Cookie("my_first_cookie", "251");
    }

    @MyTest
    void test2() {
        Cookie cookieVar1 = new Cookie("my_first_cookie", "151");
        Cookie cookieVar2 = new Cookie("my_first_cookie", "25");
    }

    void test3() {
        Cookie cookieVar1 = new Cookie("my_first_cookie", "15");

        String str = "123";
        int len = str.length();
    }
}

class MethodsDeclarationTwo {

    @MyTest
    void test() {
        Cookie cookieVar1 = new Cookie("my_first_cookie", "151");
        Cookie cookieVar2 = new Cookie("my_first_cookie", "251");
    }

    @MyTest
    void test2() {
        Cookie cookieVar1 = new Cookie("my_first_cookie", "151");
        Cookie cookieVar2 = new Cookie("my_first_cookie", "25");
    }

    void test3() {
        Cookie cookieVar1 = new Cookie("my_first_cookie", "15");

        String str = "123";
        int len = str.length();
    }
}