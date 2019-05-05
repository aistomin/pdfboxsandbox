package com.github.aistomin;

import java.io.File;
import java.net.URL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by aistomin on 2019-05-05.
 * <p>
 * The tests for {@link PdfReader}
 */
final class PdfReaderTest {

    private final String expectedText = "Bourrée in E minor pg. 2/2";

    /**
     * Check that we can correctly read the PDF file which is not encrypted.
     *
     * @throws Exception If something goes wrong.
     */
    @Test
    void testReadNonEncryptedFile() throws Exception {
        final String pdf = "not-encrypted.pdf";
        final URL resource = PdfBoxDemo.class.getClassLoader()
            .getResource(pdf);
        Assertions.assertNotNull(resource);
        final String text = new PdfReader(
            new File(resource.getFile()), null
        ).read();
        Assertions.assertNotNull(text);
        Assertions.assertTrue(text.contains(expectedText));
    }

    /**
     * Check that we can correctly read the PDF file which is encrypted.
     *
     * @throws Exception If something goes wrong.
     */
    @Test
    void testReadEncryptedFile() throws Exception {
        final String pdf = "encrypted.pdf";
        final URL resource = PdfBoxDemo.class.getClassLoader()
            .getResource(pdf);
        Assertions.assertNotNull(resource);
        final String text = new PdfReader(
            new File(resource.getFile()), "bach"
        ).read();
        Assertions.assertNotNull(text);
        Assertions.assertTrue(text.contains(expectedText));
    }
}
