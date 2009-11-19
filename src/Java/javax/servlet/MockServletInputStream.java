package javax.servlet;

import java.io.IOException;

import javax.servlet.ServletInputStream;

public class MockServletInputStream extends ServletInputStream {
    private StringBuilder stream;
    private int cursor = 0;
    private int limit;

    /** Default constructor. Use <code>reset(String)</code> to set the stream content. */
    public MockServletInputStream() {
        setData("");
    }
    /** Constructor with the initial stream content */
    public MockServletInputStream(String data) {
        setData(data);
    }

    /** Accessor */
    public void setData(String data) {
        stream = new StringBuilder(data);
        limit = stream.length();
    }

    @Override
    public int read() throws IOException {
        if(cursor < limit) {
            char c = stream.charAt(cursor);
            ++ cursor;
            return (int) c;
        }
        return -1;
    }

    /** Return the initial stream content */
    public String getContents() {
        return stream.toString();
    }
    /** Return the yet processed stream content */
    public String getProcessedContents() {
        return stream.substring(0, cursor - 1);
    }
    /** Return the non yet processed stream content */
    public String getNotProcessedContents() {
        return stream.substring(cursor);
    }
};
