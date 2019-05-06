package com.github.aistomin;

import java.io.File;
import java.net.URL;

/**
 * Created by aistomin on 2019-05-01.
 * <p>
 * Class that demonstrates how to extract the content of the PDF files using
 * PDFBox library.
 */
public final class PdfBoxDemo {

    /**
     * Entry point of the program :)
     *
     * @param args Arguments :)
     * @throws Exception If something goes wrong :)
     */
    public static void main(final String[] args) throws Exception {
        final String pdf = "encrypted.pdf";
        final URL resource = PdfBoxDemo.class.getClassLoader()
            .getResource(pdf);
        if (resource == null) {
            throw new Exception(
                String.format(
                    "The resource with the name %s is not found.", pdf
                )
            );
        }
        final File file = new File(resource.getFile());
        final String text = new PdfDocument(file, "bach").read();
        System.out.println(
            String.format(
                "CONTENT OF THE FILE %s:\n%s", file.getAbsolutePath(), text
            )
        );
        System.out.println(
            "***********************************************\n\n"
        );
    }
}
