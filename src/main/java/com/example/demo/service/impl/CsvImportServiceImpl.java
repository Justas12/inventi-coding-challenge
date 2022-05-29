package com.example.demo.service.impl;

import com.example.demo.model.CsvRecordBankStatement;
import com.example.demo.service.ImportService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CsvImportServiceImpl implements ImportService {

    @Override
    public void importFile(MultipartFile file) {
        try {
            CSVReader reader = buildCsvReader(buildReader(file.getBytes()));
            CsvToBean<CsvRecordBankStatement> csvReader = new CsvToBeanBuilder(reader)
                .withType(CsvRecordBankStatement.class)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build();
            List<CsvRecordBankStatement> results = csvReader.parse();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CSVReader buildCsvReader(Reader reader) {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        return new CSVReaderBuilder(reader).withCSVParser(csvParser).build();
    }

    private Reader buildReader(byte[] content) {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8));
    }

}
