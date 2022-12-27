package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Controller
public class FileController {

    String error = null;
    private final FileService fileService;


    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/file/delete")
    public String handleFileDelete(
            NoteForm noteForm,
            CredentialForm credentialForm,
            Authentication authentication,
            @RequestParam("id")
            int fileId,
            Model model
    ) {
        String username = authentication.getName();
        if (fileId > 0) {
            int numberDeleted = fileService.deleteFile(fileId);

            if (numberDeleted == 0) {
                error = "something went wrong deleting file";
            }
        } else {
            error = "no file found to delete";
        }

        if (error == null) {
            model.addAttribute("successMessage", "Your file was removed successfully!");
        } else {
            model.addAttribute("error", error);
        }

        List<File> files = fileService.getAllFiles(username);
        model.addAttribute("files", files);
        return "home";
    }

    @GetMapping("/file/view")
    public ResponseEntity<Resource> viewFile(
            NoteForm noteForm,
            CredentialForm credentialForm,
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
            NoteForm noteForm,
            CredentialForm credentialForm,
            @RequestParam("fileUpload")
            MultipartFile file,
            Authentication authentication,
            Model model
    ) {
        String username = authentication.getName();

        try {
            if (!file.isEmpty()) {
                if (!fileService.checkFilenameAvailable(file.getOriginalFilename())) {
                    error = "Cannot upload, there is already a file with same filename";
                } else {
                    fileService.storeFile(file, username);
                }
            } else {
                error = "Cannot upload, no file selected";
            }
        } catch (IOException e) {
            error = "Something went wrong uploading your file";
        }

        if (error == null) {
            model.addAttribute("successMessage", "Your file was uploaded successfully!");
        } else {
            model.addAttribute("error", error);
        }


        List<File> files = fileService.getAllFiles(username);
        model.addAttribute("files", files);

        return "home";
    }
}



