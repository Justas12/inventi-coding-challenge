package com.inventi.bankingapplication.service;

import com.inventi.bankingapplication.model.query.OperationQuery;
import com.inventi.bankingapplication.model.view.FileView;
import org.springframework.web.multipart.MultipartFile;

public interface OperationService {

    FileView exportToFile(OperationQuery query);

    void importFromFile(MultipartFile file);
}
