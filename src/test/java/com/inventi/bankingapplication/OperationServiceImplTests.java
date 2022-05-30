package com.inventi.bankingapplication;

import com.inventi.bankingapplication.entity.Operation;
import com.inventi.bankingapplication.model.CsvRecordBankStatement;
import com.inventi.bankingapplication.repository.OperationRepository;
import com.inventi.bankingapplication.service.OperationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BankingApplication.class)
class OperationServiceImplTests {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private OperationService operationService;

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
