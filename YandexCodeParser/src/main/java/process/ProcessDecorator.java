package process;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stream.ErrorStreamReader;
import stream.OutputStreamReader;
import utils.ExitCodeProducer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessDecorator implements OutputStreamReader, ErrorStreamReader, ExitCodeProducer {
    private static final Logger log = LogManager.getLogger(ProcessDecorator.class.getName());

    private final Process process;

    /**
     * For cases, when output from process will be reused many times
     */
    private String cachedOutput = "";

    public Process getProcess() {
        return process;
    }

    ProcessDecorator(Process process) {
        this.process = process;
    }

    @Override
    public String readOutput() {
        try {
            final String output = readText(process.getInputStream());
            if (output.isBlank() && !cachedOutput.isBlank())
                return cachedOutput;
            else {
                cachedOutput = output;
                return output;
            }
        } catch (IOException e) {
            log.error("Ошибка чтения результатов выполнения программы");
            log.trace(e);
        }

        return "";
    }

    @Override
    public String readErrors() {
        try {
            return readText(process.getErrorStream());
        } catch (IOException e) {
            log.error("Ошибка чтения потока ошибок от программы");
            log.trace(e);
        }

        return "ERROR";
    }

    private String readText(InputStream ins) throws IOException {
        var stringBuilder = new StringBuilder();

        var in = new BufferedReader(new InputStreamReader(ins));
        String line;

        while ((line = in.readLine()) != null) {
            stringBuilder.append(line)
                    .append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }

    @Override
    public int getExitCode() {
        return process.exitValue();
    }
}
