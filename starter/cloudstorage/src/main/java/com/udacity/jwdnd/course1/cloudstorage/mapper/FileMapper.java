package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES(filename, contentType, fileSize, fileData, userId) " +
            "VALUES(#{filename}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES WHERE fileName = #{filename}")
    File getFileFromName(String fileName);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getAllFiles(int userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int delete(int fileId);


}
