package com.github.aistomin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;

/**
 * Created by aistomin on 2019-05-01.
 * <p>
 * Class that demonstrates how to decrypt PDF files and read the content their
 * content.
 */
public final class DecryptDemo {

    /**
     * Entry point of the program :)
     *
     * @param args Arguments :)
     * @throws Exception If something goes wrong :)
     */
    public static void main(final String[] args) throws Exception {
        printFileInfo(resourceFile("not-encrypted.pdf"), null);
        printFileInfo(resourceFile("encrypted.pdf"), "bach");
//        printFileInfo(resourceFile("path_to_your_file"), "your_password");
    }

    /**
     * Prints the details of the file to console.
     *
     * @param file     The PDF file.
     * @param password Optional password in case the file is encrypted.
     * @throws Exception If something goes wrong :)
     */
    private static void printFileInfo(
        final File file, final String password
    ) throws Exception {
        final PDDocument doc = PDDocument.load(file);
        System.out.println(
            String.format("****** INFORMATION ABOUT %s ******", file)
        );
        System.out.println(
            String.format("IS ENCRYPTED: %b", doc.isEncrypted())
        );
        if (doc.isEncrypted()) {
            System.out.println(
                String.format(
                    "The document %s is encrypted. Let's try to decrypt it.",
                    file.getAbsolutePath()
                )
            );
            decrypt(doc, password);
            System.out.println(
                String.format(
                    "The document %s is decrypted.", file.getAbsolutePath()
                )
            );
        }
        System.out.println(
            String.format("CONTENT OF THE FILE:\n%s", loadText(doc))
        );
        System.out.println(
            "***********************************************\n\n"
        );
    }

    /**
     * Decrypt the PDF document.
     *
     * @param doc      The document.
     * @param password The password.
     * @throws Exception If something goes wrong :)
     */
    private static void decrypt(
        final PDDocument doc, final String password
    ) throws Exception {
        if (password == null) {
            throw new NullPointerException(
                "Password is required for the encrypted document."
            );
        }
        doc.decrypt(password);
        doc.setAllSecurityToBeRemoved(true);
        doc.save(
            String.format("target/%s.pdf", UUID.randomUUID().toString())
        );
    }

    /**
     * Load the content of the PDF document.
     *
     * @param doc The document.
     * @return The text.
     * @throws IOException If the file is not readable.
     */
    private static String loadText(final PDDocument doc) throws IOException {
        final PDFTextStripper stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);
        return new PDFTextStripper().getText(doc);
    }

    /**
     * Get the file of the resource file.
     *
     * @param name Resource file name.
     * @return The {@link PDDocument} instance.
     * @throws Exception If the resource is not found.
     */
    private static File resourceFile(final String name)
        throws Exception {
        final URL resource = DecryptDemo.class.getClassLoader()
            .getResource(name);
        if (resource == null) {
            throw new Exception(
                String.format(
                    "The resource with the name %s is not found.", name
                )
            );
        }
        return new File(resource.getFile());
    }
}
