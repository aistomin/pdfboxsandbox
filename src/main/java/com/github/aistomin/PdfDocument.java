package com.github.aistomin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;

/**
 * Created by aistomin on 2019-05-05.
 * <p>
 * Class that represents PDF document. Using this class you can read the content
 * of the PDF file.
 */
@SuppressWarnings("WeakerAccess")
public final class PdfDocument {

    /**
     * The PDF file.
     */
    private final File pdf;

    /**
     * Optional password in case the file is encrypted.
     */
    private final String password;

    /**
     * Ctor.
     *
     * @param pdf      The PDF file.
     * @param password Optional password in case the file is encrypted.
     */
    public PdfDocument(final File pdf, final String password) {
        this.pdf = pdf;
        this.password = password;
    }

    /**
     * Reads the content of the PDF file.
     *
     * @throws Exception If something goes wrong :)
     */
    public String read() throws Exception {
        final PDDocument doc = PDDocument.load(this.pdf);
        if (doc.isEncrypted()) {
            decrypt(doc, this.password);
        }
        return loadText(doc);
    }

    /**
     * Decrypt the PDF document.
     *
     * @param doc      The document.
     * @param password The password.
     * @throws Exception If something goes wrong :)
     */
    private void decrypt(
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
    private String loadText(final PDDocument doc) throws IOException {
        final PDFTextStripper stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);
        return new PDFTextStripper().getText(doc);
    }
}
