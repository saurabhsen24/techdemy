package com.techdemy.service.impl;

import com.cloudinary.Cloudinary;
import com.techdemy.exception.BadRequestException;
import com.techdemy.service.FileUploadService;
import com.techdemy.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;


@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        File uploadedFile = convertFile( file );
        getFileExtension( file.getOriginalFilename() );
        Map uploadResult = cloudinary.uploader().upload(uploadedFile, Collections.emptyMap());
        return uploadResult.get("url").toString();
    }

    private File convertFile(MultipartFile file) {
        if(file.isEmpty() || StringUtils.isBlank(file.getOriginalFilename())){
            throw new BadRequestException("File is not uploaded or file name is empty");
        }

        File convertedFile = null;
        try {
            convertedFile = new File(file.getOriginalFilename());
            FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            log.debug("Converting multipart file: {}", convertedFile);
        } catch ( Exception e ) {
            log.warn("Error occurred while writing to a file {}", ExceptionUtils.getStackTrace(e));
        }

        return convertedFile;
    }

    private String getFileExtension( String fileName ) {
        if(StringUtils.isNotBlank(fileName) && fileName.contains(".")) {
            for(String extension: Utils.getExtensionList()) {
                if(fileName.endsWith(extension)) {
                    log.debug("Accepted file type: {}", extension);
                    return extension;
                }
            }
        }

        throw new BadRequestException("This file type not permitted");
    }

}
