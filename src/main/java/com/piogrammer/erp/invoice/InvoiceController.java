package com.piogrammer.erp.invoice;

import com.piogrammer.erp.invoice.dto.InvoiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<InvoiceResponse> getAllInvoices() {
        return service.getAllInvoicesResponse();
    }

/*
    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id){
        service.deleteInvoice(id);
    }
*/


    /*
    @GetMapping("/{id}")  ///to spring wyciaga z url parametr i wrzuca go  do metody
    public Invoice getInvoice(@PathVariable Long id){
        return service.getInvoice(id);
    }*/

    //nowe getInvoice
    @GetMapping("/{id}")
    public InvoiceResponse getInvoice(@PathVariable Long id) {

        Invoice invoice = service.getInvoice(id);

        return service.mapToResponse(invoice);
    }

    @GetMapping("/test")
    public String test() {
        return "ERP WORKS";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        service.deleteInvoice(id);
    }


    @PutMapping("/{id}")
    public void updateInvoice(@PathVariable Long id, @RequestBody CreateInvoiceRequest request) {
        service.updateInvoice(id, request);
    }
}