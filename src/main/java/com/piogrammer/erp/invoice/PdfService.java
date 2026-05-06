package com.piogrammer.erp.invoice;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

@Service
public class PdfService {

    public byte[] generatePdf(Invoice invoice) throws Exception {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        // 🔥 FONTY
        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Font bold = new Font(Font.HELVETICA, 12, Font.BOLD);
        Font normal = new Font(Font.HELVETICA, 12);

        // 🧾 TYTUŁ
        document.add(new Paragraph("FAKTURA", titleFont));
        document.add(new Paragraph(" "));

        // 🔢 NUMER (jeśli masz pole number)
        if (invoice.getNumber() != null) {
            document.add(new Paragraph("Nr: " + invoice.getNumber(), normal));
        }

        document.add(new Paragraph("Data: " + invoice.getDate(), normal));
        document.add(new Paragraph("Klient: " + invoice.getCustomer().getName(), normal));

        document.add(new Paragraph(" "));

        // 📊 TABELA
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        // nagłówki
        table.addCell(new Phrase("Produkt", bold));
        table.addCell(new Phrase("Ilość", bold));
        table.addCell(new Phrase("Cena", bold));
        table.addCell(new Phrase("Suma", bold));

        // dane
        for (InvoiceItem item : invoice.getItems()) {

            table.addCell(new Phrase(item.getProduct().getName(), normal));
            table.addCell(new Phrase(String.valueOf(item.getQuantity()), normal));
            table.addCell(new Phrase(item.getPrice().toString(), normal));

            BigDecimal sum = item.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            table.addCell(new Phrase(sum.toString(), normal));
        }

        document.add(table);

        document.add(new Paragraph(" "));

        // 💰 TOTAL
        document.add(new Paragraph("TOTAL: " + invoice.getTotal(), bold));

        document.close();

        return out.toByteArray();
    }
}