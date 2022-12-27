package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/credential")
@Controller
public class CredentialController {
    String error = null;
    private final CredentialService credentialService;


    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getCredentials(Authentication authentication,
                                 NoteForm noteForm,
                                 CredentialForm credentialForm,
                                 Model model) {
        String username = authentication.getName();
        model.addAttribute("credentials", credentialService.getAllCredentials(username));

        return "home";
    }

    @PostMapping
    public String storeCredential(Authentication authentication,
                                  NoteForm noteForm,
                                  CredentialForm credentialForm,
                                  Model model) {
        String username = authentication.getName();

        int credentialsInserted = credentialService.storeCredential(credentialForm, username);
        if (credentialsInserted == 1) {
            model.addAttribute("successMessage", "Your credential was saved successfully!");
        } else {
            model.addAttribute("error", error);
        }

        model.addAttribute("credentials", credentialService.getAllCredentials(username));
        return "home";
    }

    @GetMapping("/delete")
    public String handleCredentialDelete(
            Authentication authentication,
            NoteForm noteForm,
            CredentialForm credentialForm,
            @RequestParam("credentialId")
            Integer credentialId,
            Model model
    ) {
        String username = authentication.getName();
        if (credentialId != null) {
            int numberDeleted = credentialService.deleteCredential(credentialId);

            if (numberDeleted == 0) {
                error = "something went wrong deleting your credential";
            }
        } else {
            error = "no file found to delete";
        }

        if (error == null) {
            model.addAttribute("successMessage", "Your credential was removed successfully!");
        } else {
            model.addAttribute("error", error);
        }

        model.addAttribute("credentials", credentialService.getAllCredentials(username));
        return "home";
    }


}
