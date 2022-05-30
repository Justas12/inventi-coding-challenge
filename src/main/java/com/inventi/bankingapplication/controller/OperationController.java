package com.inventi.bankingapplication.controller;

import com.inventi.bankingapplication.model.query.OperationQuery;
import com.inventi.bankingapplication.model.view.FileView;
import com.inventi.bankingapplication.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/operations")
public class OperationController {

    private final OperationService service;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void importFromFile(@RequestParam("file") MultipartFile file) {
        service.importFromFile(file);
    }

    @GetMapping
    public HttpEntity<byte[]> exportToFile(OperationQuery query) {
        HttpHeaders headers = new HttpHeaders();
        FileView fileView = service.exportToFile(query);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(fileView.getBytes().length);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename: " + fileView.getName() + fileView.getExtension());
        return new HttpEntity<>(fileView.getBytes(), headers);
    }

}
