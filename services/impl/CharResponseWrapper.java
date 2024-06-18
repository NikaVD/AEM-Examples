package com.traineeproject.core.services.impl;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class CharResponseWrapper extends HttpServletResponseWrapper {
    private final CharArrayWriter outputWriter;

    @Override
    public String toString() {

        return outputWriter.toString();

    }
    public CharResponseWrapper(HttpServletResponse response) {
        super(response);
        outputWriter = new CharArrayWriter();
    }

    @Override

    public PrintWriter getWriter() {

        return new PrintWriter(outputWriter);

    }
}
