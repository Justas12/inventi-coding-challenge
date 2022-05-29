package com.example.demo.service.impl;

import com.example.demo.model.view.FileView;
import com.example.demo.service.FileService;
import com.opencsv.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CsvFileServiceImpl implements FileService {

    private final static char SEPARATOR = ';';
    private final static String FILE_EXTENSION = ".csv";

    @Override
    public <T> List<T> read(MultipartFile content, Class<T> type) {
        try {
            CSVReader reader = buildCsvReader(buildReader(content.getBytes()));
            CsvToBean<T> csvReader = new CsvToBeanBuilder(reader)
                .withType(type)
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build();
            return csvReader.parse();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> FileView write(List<T> content, String filename) {
        return FileView.builder()
            .bytes(toBytes(content))
            .name(filename)
            .extension(FILE_EXTENSION)
            .build();
    }

    private <T> byte[] toBytes(List<T> content) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
            CSVWriter writer = new CSVWriter(streamWriter);

            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            beanToCsv.write(content);
            streamWriter.flush();
            return stream.toByteArray();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private CSVReader buildCsvReader(Reader reader) {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(SEPARATOR).build();
        return new CSVReaderBuilder(reader).withCSVParser(csvParser).build();
    }

    private Reader buildReader(byte[] content) {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8));
    }

}
