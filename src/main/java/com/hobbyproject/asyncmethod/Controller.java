package com.hobbyproject.asyncmethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
public class Controller {
    @Autowired
    Service service;

    @Autowired
    ProgressService progressService;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file")MultipartFile multipartFile){
        String progressId = UUID.randomUUID().toString();
        File file = convertMultiPartToFile(multipartFile);
        service.saveToDatabase(file, progressId);
        return progressId;
    }

    @GetMapping("/getProgress")
    public String getProgress(@RequestParam String progressId){
        return progressService.getProgress(progressId);
    }

    public File convertMultiPartToFile(MultipartFile file)   {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convFile;
    }

}
