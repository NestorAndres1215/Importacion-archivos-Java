package com.example.demo.controller;

import com.example.demo.model.FileModel;
import com.example.demo.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;


    @PostMapping("/upload")
    public ResponseEntity<FileModel> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileModel savedFile = fileService.saveFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFile);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<FileModel>> listFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {

        FileModel file = fileService.getFileById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.parseMediaType(file.getType()))
                .contentLength(file.getData().length)
                .body(file.getData());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) throws FileNotFoundException {
        fileService.deleteFileById(id);
        return ResponseEntity.noContent().build();
    }

}