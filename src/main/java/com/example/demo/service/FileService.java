package com.example.demo.service;


import com.example.demo.model.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    FileModel saveFile(MultipartFile file) throws IOException;
        FileModel getFileById(Long id);
    List<FileModel> getAllFiles();
    boolean deleteFileById(Long id);
}