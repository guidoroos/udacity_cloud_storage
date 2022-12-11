package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/note")
@Controller
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getNotes(Authentication authentication,
                           NoteForm noteForm,
                           Model model) {
        String username = authentication.getName();
        model.addAttribute("notes", noteService.getAllNotes(username));
        return "home";
    }

    @PostMapping
    public String storeNote(Authentication authentication,
                            NoteForm noteForm,
                            Model model) {
        String username = authentication.getName();

        noteService.storeNote(noteForm, username);
        noteForm.clearAll();
        model.addAttribute("notes", noteService.getAllNotes(username));
        return "home";
    }


}
