package com.piogrammer.erp.invoice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService service;
    private final InvoiceRepository invoiceRepo;
    private final PdfService pdfService;

    public InvoiceController(InvoiceService service,
                             InvoiceRepository invoiceRepo,
                             PdfService pdfService) {
        this.service = service;
        this.invoiceRepo = invoiceRepo;
        this.pdfService = pdfService;
    }

    @PostMapping
    public Invoice create(@RequestBody CreateInvoiceRequest request) {
        return service.createInvoice(request);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> getPdf(@PathVariable Long id) throws Exception {

        Invoice invoice = invoiceRepo.findById(id).orElseThrow();

        byte[] pdf = pdfService.generatePdf(invoice);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=faktura.pdf")
                .body(pdf);
    }
}