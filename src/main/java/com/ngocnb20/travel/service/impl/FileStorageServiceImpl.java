package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FileStorageServiceImpl implements FileStorageService {

//
//    @Value("${app.file.location}")//tuyệt đối
//    String fileLocation;

    @Value("${app.file.url-prefix}")//server
    String urlPrefix;

    @Override
    public String saveFile(MultipartFile file, String folderUser,String fileLocation) throws IOException {
        Path filePath = getFilePath(file.getOriginalFilename(), folderUser,fileLocation);

        // Save file to success
        file.transferTo(filePath);

        return filePath.toString().replace(fileLocation, "");
    }

    @Override
    public List<String> saveMultipartFile(MultipartFile[] files, String folderUser, String fileLocation)  {
        List<String> fileNames = new ArrayList<>();
        Arrays.asList(files).stream().forEach(file->{
            try {
                fileNames.add(saveFile(file,folderUser,fileLocation));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return fileNames ;
    }

    @Override
    public Resource loadFileAsResource(String filePath,String fileLocation ) throws MalformedURLException, FileNotFoundException {
        // fileLocation: /Users/phuongdp/Projects/FA/Class/FJB_02/Courseware/9. JWD/Demo/cms_spring/assets
        // filePath: user3/favicon.png
        Path absolutePath = Paths.get(fileLocation).resolve(filePath);
        Resource resource = new UrlResource(absolutePath.toUri());
        if (resource.exists()) {
            return resource;
        }

        throw new FileNotFoundException("File not found: " + absolutePath.toString());
    }

    private Path getFilePath(String fileName, String folderUser,String fileLocation) throws IOException {
        // product1.png
        // user_1 + "/" +  random
        // .../assets
        // => /Users/phuongdp/Projects/FA/Class/FJB_02/Courseware/9. JWD/Demo/cms_spring/assets/user_1/abcd123/product1.png
        Path fileLocationPath = Paths.get(fileLocation);

        if (Objects.nonNull(folderUser)) {
            fileLocationPath = fileLocationPath.resolve(folderUser);
        }
        Files.createDirectories(fileLocationPath);

        return fileLocationPath.resolve(fileName);
    }

    public String buildUrl(String relativePath) {
        return urlPrefix + relativePath;
    }
}
