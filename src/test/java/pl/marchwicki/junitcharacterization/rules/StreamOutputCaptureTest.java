package pl.marchwicki.junitcharacterization.rules;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

public class StreamOutputCaptureTest {

    private StreamOutputCapture capture;

    @Test
    public void should_capture_output() throws Throwable {
        //given
        final ByteArrayOutputStream capturedStream = new ByteArrayOutputStream();
        final PrintStream originalStream = System.out;
        capture = new StreamOutputCapture(capturedStream);

        //when
        capture.before();

        System.out.println("first output line");
        System.out.println("second output line");

        capture.after();

        //then
        assertThat(System.out)
                .isSameAs(originalStream);
        assertThat(capturedStream.toString())
                .isNotEmpty()
                .contains("first output line")
                .contains("second output line");
    }

}
