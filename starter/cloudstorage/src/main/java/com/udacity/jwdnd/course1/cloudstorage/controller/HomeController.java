package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;

    String uploadError = null;
    String deleteError = null;

    public HomeController(FileService fileService) {
        this.fileService = fileService;

    }

    @GetMapping()
    public String homeView( Authentication authentication,
                            Model model) {
        String username = authentication.getName();
        if (username == null) {
            return "login";
        }

        List<File> files = fileService.getAllFiles(username);
        model.addAttribute("files", files);
        return "home";

    }


}

