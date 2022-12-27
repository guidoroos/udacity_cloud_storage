package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/note")
@Controller
public class NoteController {
    String error = null;
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getNotes(Authentication authentication,

                           Model model) {
        String username = authentication.getName();
        model.addAttribute("notes", noteService.getAllNotes(username));
        return "home";
    }

    @PostMapping
    public String storeNote(Authentication authentication,
                            NoteForm noteForm,
                            CredentialForm credentialForm,
                            Model model) {
        String username = authentication.getName();

        int notesInserted = noteService.storeNote(noteForm, username);
        if (notesInserted == 1) {
            model.addAttribute("successMessage", "Your note was saved successfully!");
        } else {
            model.addAttribute("error", error);
        }

        model.addAttribute("notes", noteService.getAllNotes(username));

        return "home";
    }

    @GetMapping("/delete")
    public String handleNoteDelete(
            NoteForm noteForm,
            CredentialForm credentialForm,
            Authentication authentication,
            @RequestParam("noteId")
            Integer noteId,
            Model model
    ) {
        String username = authentication.getName();
        if (noteId != null) {
            int numberDeleted = noteService.deleteNote(noteId);

            if (numberDeleted == 0) {
                error = "something went wrong deleting your note";
            }
        } else {
            error = "no file found to delete";
        }

        if (error == null) {
            model.addAttribute("successMessage", "Your note was removed successfully!");
        } else {
            model.addAttribute("error", error);
        }

        model.addAttribute("notes", noteService.getAllNotes(username));
        return "home";
    }


}
