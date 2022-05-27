package com.ngocnb20.travel.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface FileStorageService {
    String saveFile(MultipartFile file, String folderUser,String fileLocation) throws IOException;
    List<String> saveMultipartFile(MultipartFile[] files, String folderUser,String fileLocation);

    Resource loadFileAsResource(String filePath, String fileLocation) throws MalformedURLException, FileNotFoundException;

    String buildUrl(String fileName);
}
