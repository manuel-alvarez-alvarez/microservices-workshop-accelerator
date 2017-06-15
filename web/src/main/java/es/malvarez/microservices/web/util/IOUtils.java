package es.malvarez.microservices.web.util;

import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Shame sh$#%@R#T as usual
 */
public abstract class IOUtils {

    private IOUtils() {

    }

    public static String read(final InputStream inputStream, final Charset charset) {
        return read(new InputStreamReader(inputStream, charset));
    }

    public static String read(final InputStream inputStream) {
        return read(new InputStreamReader(inputStream));
    }

    public static String read(final Resource resource) {
        try {
            return read(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(final Reader reader) {
        try {
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                StringBuilder builder = new StringBuilder();
                String theLine;
                while ((theLine = bufferedReader.readLine()) != null) {
                    builder.append(theLine);
                }
                return builder.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
