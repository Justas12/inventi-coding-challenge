package com.example.demo.service;

import com.example.demo.model.query.OperationQuery;
import com.example.demo.model.view.FileView;
import org.springframework.web.multipart.MultipartFile;

public interface OperationService {

    FileView exportToFile(OperationQuery query);

    void importFromFile(MultipartFile file);
}
