package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @ModelAttribute
    public NoteForm getNoteForm() {
        return new NoteForm();
    }

    @ModelAttribute
    public CredentialForm getCredentialForm() {
        return new CredentialForm();
    }

    @GetMapping()
    public String homeView(Authentication authentication,
                           Model model) {
        String username = authentication.getName();
        List<File> files = fileService.getAllFiles(username);
        model.addAttribute("files", files);
        return "home";

    }


}

