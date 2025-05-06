package View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileInputSource {
    private BufferedReader reader;
    public FileInputSource(File file)throws IOException {
        reader = new BufferedReader(new FileReader(file));
    }
    public String readLine() throws IOException {
        return reader.readLine();
    }
    public boolean hasNextLine() throws IOException {
        return reader.ready();
    }
    public void close() throws IOException {
        reader.close();
    }
}
