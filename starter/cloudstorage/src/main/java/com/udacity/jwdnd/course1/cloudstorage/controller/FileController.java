package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
public class FileController {

    private FileService fileService;

    String uploadError = null;
    String deleteError = null;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/file/delete")
    public String handleFileDelete(
            Authentication authentication,
            @RequestParam("id")
            int fileId,
            Model model
    ) {
        String username = authentication.getName();
        if (fileId > 0) {
            int numberDeleted = fileService.deleteFile(fileId);

            if (numberDeleted == 0) {
                deleteError = "something went wrong deleting file";
            }
        } else {
            deleteError = "no file found to delete";
        }

        if (deleteError == null) {
            model.addAttribute("deleteSuccess", true);
        } else {
            model.addAttribute("deleteError", uploadError);
        }

        List<File> files = fileService.getAllFiles(username);
        model.addAttribute("files", files);
        return "home";
    }

    @GetMapping("/file/view")
    public ResponseEntity<Resource> viewFile(
            @RequestParam("filename")
            String filename
    ) {
        File file = fileService.getFileFromName(filename);
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(Long.parseLong(file.getFileSize()))
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(resource);
    }


    @PostMapping("/file")
    public String handleFileUpload(
            @RequestParam("fileUpload")
            MultipartFile file,
            Authentication authentication,
            Model model
    ) {
        String username = authentication.getName();

        try {
            if (!file.isEmpty()) {
                if (!fileService.checkFilenameAvailable(file.getOriginalFilename())) {
                    uploadError = "Cannot upload, there is already a file with same filename";
                } else {
                    fileService.storeFile(file, username);
                }
            } else {
                uploadError = "Cannot upload, no file selected";
            }
        } catch (IOException e) {
            uploadError = "Something went wrong uploading your file";
        }

        if (uploadError == null) {
            model.addAttribute("uploadSuccess", true);
        } else {
            model.addAttribute("uploadError", uploadError);
        }


        List<File> files = fileService.getAllFiles(username);
        model.addAttribute("files", files);

        return "home";
    }
}

