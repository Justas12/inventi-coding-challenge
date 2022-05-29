package com.example.demo.service;

import com.example.demo.model.view.FileView;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    <T> List<T> read(MultipartFile content, Class<T> type);

    <T> FileView write(List<T> content, String filename);

}
