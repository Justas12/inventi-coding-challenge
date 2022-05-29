package com.example.demo.service;

import com.example.demo.model.query.BankAccountOperationsQuery;
import com.example.demo.model.view.FileView;
import org.springframework.web.multipart.MultipartFile;

public interface BankAccountOperationService {

    FileView exportToFile(BankAccountOperationsQuery query);

    void importFromFile(MultipartFile file);
}
