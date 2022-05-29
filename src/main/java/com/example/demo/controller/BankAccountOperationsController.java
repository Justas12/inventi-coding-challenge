package com.example.demo.controller;

import com.example.demo.model.query.BankAccountOperationsQuery;
import com.example.demo.model.view.FileView;
import com.example.demo.service.BankAccountOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/operations")
public class BankAccountOperationsController {

    private final BankAccountOperationService service;

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void importFromFile(@RequestParam("file") MultipartFile file) {
        service.importFromFile(file);
    }

    @GetMapping("/export")
    public HttpEntity<byte[]> exportToFile(BankAccountOperationsQuery query) {
        HttpHeaders headers = new HttpHeaders();
        FileView fileView = service.exportToFile(query);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(fileView.getBytes().length);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename: " + fileView.getName() + fileView.getExtension());
        return new HttpEntity<>(fileView.getBytes(), headers);
    }

}
