package com.example.demo.controller;

import com.example.demo.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/imports")
public class ImportController {

    private final ImportService importService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void importCsv(@RequestParam("file") MultipartFile file) {
        importService.importFile(file);
    }

}
