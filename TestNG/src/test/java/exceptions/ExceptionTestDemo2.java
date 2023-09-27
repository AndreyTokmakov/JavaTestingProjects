package exceptions;

import java.io.IOException;

import org.testng.annotations.Test;

public class ExceptionTestDemo2 
{
    @Test(expectedExceptions = { IOException.class }, expectedExceptionsMessageRegExp = "Pass Message test")
    public void exceptionTestOne() throws Exception {
        throw new IOException("Pass Message test");
    }
 
    @Test(expectedExceptions = { IOException.class }, expectedExceptionsMessageRegExp = ".* Message .*")
    public void exceptionTestTwo() throws Exception {
        throw new IOException("Pass Message test");
    }
 
    @Test(expectedExceptions = { IOException.class }, expectedExceptionsMessageRegExp = "Pass Message test")
    public void exceptionTestThree() throws Exception {
        throw new IOException("Fail Message test");
    }
}