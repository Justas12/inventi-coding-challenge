package com.inventi.bankingapplication;

import com.inventi.bankingapplication.entity.Operation;
import com.inventi.bankingapplication.model.CsvRecordBankStatement;
import com.inventi.bankingapplication.repository.AccountRepository;
import com.inventi.bankingapplication.repository.OperationRepository;
import com.inventi.bankingapplication.service.FileService;
import com.inventi.bankingapplication.service.OperationService;
import com.inventi.bankingapplication.service.impl.OperationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OperationServiceImplTests {

    @Autowired
    ResourceLoader resourceLoader;

    @Spy
    private OperationRepository operationRepository;

    @Spy
    private AccountRepository accountRepository;

    @Spy
    private FileService fileService;

    @InjectMocks
    private OperationServiceImpl operationService;

    @Test
    void importFromFile() {
        List<CsvRecordBankStatement> csvContent = new ArrayList<>();
        csvContent.add(CsvRecordBankStatement.builder().accountNumber("87234564").build());
        try {
            File file = resourceLoader.getResource("classpath:import/CSV_Data_2022_5_28 20_30_test.csv").getFile();
            byte[] content = Files.readAllBytes(file.toPath());
            MultipartFile result = new MockMultipartFile("test", content);
            operationService.importFromFile(result);
            List<Operation> databaseContent = operationRepository.findAll();
            assertThat(csvContent.size()).isEqualTo(databaseContent.size());
            for (int i = 0; i < csvContent.size(); i++) {
                assertThat(csvContent.get(i).getAccountNumber()).isEqualTo(databaseContent.get(i).getAccount().getNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
