package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {

        //File name of original file
        String oGFileName=image.getOriginalFilename();

        //Rename the file unique-->Generate the unique FileName
        String randomId= UUID.randomUUID().toString();
        //String randomId= UUID.randomUUID().toString().concat(oGFileName.substring(oGFileName.lastIndexOf('.')));
        String fileName=randomId.concat(oGFileName.substring(oGFileName.lastIndexOf('.')));

        String filePath=path+ File.separator+fileName;
        //Check if path exists or Create
        File folder= new File(path);

        if(!folder.exists())
            folder.mkdir();

        //upload the file to server
        Files.copy(image.getInputStream(), Paths.get(filePath));

        return fileName;
    }
}
