package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.service.FileStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FileController {

    //controller advied xử lí exp
    @Value("${app.file.location-blog}")
    String fileLocationBlog;
    @Value("${app.file.location-place}")
    String fileLocationPlace;
    @Value("${app.file.location-hotel}")
    String fileLocationHotel;
    @Value("${app.file.location-restaurant}")
    String fileLocationRestaurant;
    @Value("${app.file.location-tour}")
    String fileLocationTour;
    @Value("${app.file.location-agency}")
    String fileLocationAgency;
    @Value("${app.file.location-post}")
    String fileLocationPost;
    @Autowired
    FileStorageService fileStorageService;




    @GetMapping("/file/blogs/**")
    public ResponseEntity<Resource> getImageBlog(HttpServletRequest request) throws IOException {
        System.out.println("http://159.223.41.207:8080/file/blogs/anh1.jpeg".toString().split("file/blogs/")[1]);
        String filePath = request.getRequestURL().toString().split("file/blogs/")[1];
        System.out.println("path");
        System.out.println(filePath);
        // Load resource by filePath
        Resource resource = fileStorageService.loadFileAsResource(filePath,fileLocationBlog);

        String mimeType = request.getServletContext()
                .getMimeType(resource.getFile().getAbsolutePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }
    @GetMapping("/file/places/**")
    public ResponseEntity<Resource> getImagePlace(HttpServletRequest request) throws IOException {
        String filePath = request.getRequestURL().toString().split("file/places/")[1];
        System.out.println(filePath);
        // Load resource by filePath
        Resource resource = fileStorageService.loadFileAsResource(filePath,fileLocationPlace);

        String mimeType = request.getServletContext()
                .getMimeType(resource.getFile().getAbsolutePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }

    @GetMapping("/file/hotels/**")
    public ResponseEntity<Resource> getImageHotel(HttpServletRequest request) throws IOException {
        String filePath = request.getRequestURL().toString().split("file/hotels/")[1];
        System.out.println(filePath);
        // Load resource by filePath
        Resource resource = fileStorageService.loadFileAsResource(filePath,fileLocationHotel);

        String mimeType = request.getServletContext()
                .getMimeType(resource.getFile().getAbsolutePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }
    @GetMapping("/file/restaurants/**")
    public ResponseEntity<Resource> getImageRestaurant(HttpServletRequest request) throws IOException {
        String filePath = request.getRequestURL().toString().split("file/restaurants/")[1];
        System.out.println(filePath);
        // Load resource by filePath
        Resource resource = fileStorageService.loadFileAsResource(filePath,fileLocationRestaurant);

        String mimeType = request.getServletContext()
                .getMimeType(resource.getFile().getAbsolutePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }
    @GetMapping("/file/tours/**")
    public ResponseEntity<Resource> getImageTour(HttpServletRequest request) throws IOException {
        String filePath = request.getRequestURL().toString().split("file/tours/")[1];
        System.out.println(filePath);
        // Load resource by filePath
        Resource resource = fileStorageService.loadFileAsResource(filePath,fileLocationTour);

        String mimeType = request.getServletContext()
                .getMimeType(resource.getFile().getAbsolutePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }
    @GetMapping("/file/agencies/**")
    public ResponseEntity<Resource> getImageAgency(HttpServletRequest request) throws IOException {
        String filePath = request.getRequestURL().toString().split("file/agencies/")[1];
        System.out.println(filePath);
        // Load resource by filePath
        Resource resource = fileStorageService.loadFileAsResource(filePath,fileLocationAgency);

        String mimeType = request.getServletContext()
                .getMimeType(resource.getFile().getAbsolutePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }
    @GetMapping("/file/posts/**")
    public ResponseEntity<Resource> getImagePost(HttpServletRequest request) throws IOException {
        String filePath = request.getRequestURL().toString().split("file/posts/")[1];
        System.out.println(filePath);
        // Load resource by filePath
        Resource resource = fileStorageService.loadFileAsResource(filePath,fileLocationPost);

        String mimeType = request.getServletContext()
                .getMimeType(resource.getFile().getAbsolutePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }


    public static void main(String[] args) {
        // user1\image.png => user1/image.png

        String filePath = "http://localhost/user1\\image.png";
        String unixPath = FilenameUtils.separatorsToUnix(filePath);
        System.out.println(unixPath);

    }
}
