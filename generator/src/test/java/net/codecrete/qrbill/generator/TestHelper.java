//
// Swiss QR Bill Generator
// Copyright (c) 2017 Manuel Bleichenbacher
// Licensed under MIT License
// https://opensource.org/licenses/MIT
//
package net.codecrete.qrbill.generator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.assertArrayEquals;

public class TestHelper {

    public static void assertFileContentsEqual(byte[] actualContent, String expectedFileName) {
        byte[] exptectedContent = loadReferenceFile(expectedFileName);
        try {
            assertArrayEquals(exptectedContent, actualContent);
        } catch (AssertionError e) {
            saveActualFile(actualContent);
            throw e;
        }
    }

    private static byte[] loadReferenceFile(String filename) {
        try (InputStream is = QRCodeTest.class.getResourceAsStream("/" + filename)) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] chunk = new byte[8192];
            while (true) {
                int len = is.read(chunk);
                if (len == -1)
                    break;
                buffer.write(chunk, 0, len);
            }
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveActualFile(byte[] data) {
        Path file = Paths.get("actual.svg");
        try (OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            os.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}