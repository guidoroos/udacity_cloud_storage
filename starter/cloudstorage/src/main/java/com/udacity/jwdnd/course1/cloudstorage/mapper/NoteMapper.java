package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{user.userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);


    @Update("UPDATE NOTES SET(noteTitle = #{noteTitle},noteDescription = #{noteDescription})"+
    "WHERE noteId = #{noteId}")
    int update(Note note);


    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<File> getAllNotes(int userId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int delete(int noteId);

}
