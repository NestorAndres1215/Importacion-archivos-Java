package com.example.demo.service;

import com.example.demo.model.FileModel;
import com.example.demo.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

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
        // 1. Validar archivo vacío
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        // 2. Validar tipo permitido
        String fileType = file.getContentType();
        if (!ALLOWED_TYPES.contains(fileType)) {
            throw new UnsupportedOperationException("Tipo de archivo no soportado: " + fileType);
        }

        // 3. Convertir MultipartFile a FileModel
        FileModel fileModel = new FileModel();
        fileModel.setName(file.getOriginalFilename());
        fileModel.setType(fileType);
        fileModel.setData(file.getBytes());

        // 4. Guardar en BD
        return fileRepository.save(fileModel);
    }


    @Override
    public FileModel getFileById(Long id) {
        // Utiliza el repositorio para buscar el archivo por su ID
        Optional<FileModel> optionalFileModel = fileRepository.findById(id);
        return optionalFileModel.orElse(null); // Devuelve el archivo o null si no existe
    }

    @Override
    public List<FileModel> getAllFiles() {
        return fileRepository.findAll();  // Recupera todos los archivos
    }

    @Override
    public boolean deleteFileById(Long id) {
        Optional<FileModel> existingFile = fileRepository.findById(id);

        if (existingFile.isPresent()) {
            fileRepository.deleteById(id);
            return true; // Eliminado correctamente
        }

        return false; // No existe el archivo
    }


}