package com.ecommorce.project.service.impl;

import com.ecommorce.project.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // File names of current/original file
        String originalfileName = file.getOriginalFilename();

        // Generate a unique file name
        String randomId = UUID.randomUUID().toString();
        // mat.jpg --> 1234 --> 1234.jpg
        String filename = randomId.concat(originalfileName.substring(originalfileName.lastIndexOf('.')));
        String filePath = path + File.separator + filename;

        // Check if path exist or else create
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();

        //Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        //returning file name
        return filename;

    }
}
