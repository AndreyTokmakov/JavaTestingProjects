package exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Worker
{
    Service service;

    public String callService()
    {
        try {
            return "Success: " + service.getMessage();
        }
        catch (Exception exc) {
            return "Failure: " + exc.getMessage();
        }
    }
}
