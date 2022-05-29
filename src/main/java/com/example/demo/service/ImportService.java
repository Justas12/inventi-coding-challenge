package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImportService {

    void importFile(MultipartFile file);
}
