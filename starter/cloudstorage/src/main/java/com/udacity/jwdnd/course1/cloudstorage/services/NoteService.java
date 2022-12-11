package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public List<File> getAllNotes(String username) {
        User user = userService.getUserFromName(username);
        return noteMapper.getAllNotes(user.getUserId());
    }

    public int storeNote(NoteForm noteForm, String username) {
        User user = userService.getUserFromName(username);

        Note note = new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), user);

        if (noteForm.getNoteId() == null) {
            return noteMapper.insert(note);
        } else {
            int id = noteForm.getNoteId();
            note.setNoteId(id);
            return noteMapper.update(note);
        }
    }



}
