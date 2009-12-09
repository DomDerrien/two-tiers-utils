package javamocks.io;

import java.io.IOException;
import java.io.OutputStream;

public class MockOutputStream extends OutputStream {
    private StringBuilder stream = new StringBuilder();

    public MockOutputStream() {
        this(-1); // Default: no limit
    }

    private int limit;

    public MockOutputStream(int limit) {
        this.limit = limit;
    }
    public boolean contains(String excerpt) {
        return stream.indexOf(excerpt) != -1;
    }

    public String toString() {
        return stream.toString();
    }

    public StringBuilder getStream() {
        return stream;
    }

    public int length() {
        return stream.length();
    }

    public void clear() {
        if (0 < stream.length()) {
            stream.delete(0, stream.length() - 1);
            index = 0;
        }
    }

    private int index = 0;

    @Override
    public void write(int b) throws IOException {
        if (limit == -1 || index < limit) {
            stream.append((char) b);
            ++ index;
        }
    }
}
