package utils;

public interface ExitCodeProducer {
    /**
     * 0 - OK
     * NOT 0 - FAIL
     *
     * @return
     */
    public int getExitCode();
}
