package View;

import java.io.IOException;

public interface InputSource {
    String readLine() throws IOException;
    boolean hasNextLine() throws IOException;
    void close() throws IOException;

}
