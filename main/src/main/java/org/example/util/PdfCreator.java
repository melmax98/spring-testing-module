package org.example.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.model.Ticket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class PdfCreator {

    @Value("${data.pdf.output.path}")
    private String pdfOutputPath;

    public byte[] createPdfForTickets(List<Ticket> tickets) throws IOException, DocumentException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyy h:mm:s");
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(pdfOutputPath));
        document.open();

        document.add(new Paragraph("Report date: " + simpleDateFormat.format(new Date())));

        Paragraph title = new Paragraph("Ticket list");
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(30);
        document.add(title);

        for (Ticket ticket : tickets) {
            Paragraph paragraph = new Paragraph(ticket.toString());
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setSpacingAfter(10);
            paragraph.setFont(new Font(Font.FontFamily.SYMBOL, 10, Font.FontStyle.BOLD.ordinal()));
            document.add(paragraph);
        }

        document.close();

        byte[] pdfBytes = null;
        File file = new File(pdfOutputPath);
        if (file.isFile()) {
            pdfBytes = Files.readAllBytes(Paths.get(pdfOutputPath));
        }

        return pdfBytes;
    }


}
