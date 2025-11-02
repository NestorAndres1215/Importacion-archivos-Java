package com.example.demo.service;

import com.example.demo.exception.FileEmptyException;
import com.example.demo.exception.FileTypeNotAllowedException;
import com.example.demo.model.FileModel;
import com.example.demo.repository.FileRepository;
import com.example.demo.util.MensajesValidacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {


    private final FileRepository fileRepository;

    private static final List<String> ALLOWED_TYPES = Arrays.asList(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "image/jpeg",
            "image/png"
    );

    @Override
    @Transactional
    public FileModel saveFile(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new FileEmptyException(MensajesValidacion.ERROR_ARCHIVO_VACIO);
        }

        String fileType = file.getContentType();
        if (!ALLOWED_TYPES.contains(fileType)) {
            throw new FileTypeNotAllowedException(MensajesValidacion.ERROR_TIPO_NO_SOPORTADO + fileType);
        }

        FileModel fileModel = FileModel.builder()
                .name(file.getOriginalFilename())
                .type(fileType)
                .data(file.getBytes())
                .build();

        return fileRepository.save(fileModel);
    }

    @Override
    public FileModel getFileById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException(MensajesValidacion.ERROR_ARCHIVO_NO_ENCONTRADO + id));
    }

    @Override
    public List<FileModel> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteFileById(Long id) throws FileNotFoundException {
        if (!fileRepository.existsById(id)) {
            throw new FileNotFoundException(MensajesValidacion.ERROR_ARCHIVO_NO_ENCONTRADO + id);
        }

        fileRepository.deleteById(id);
    }

}