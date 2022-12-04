package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class FileService {
    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public boolean storeFile(MultipartFile file, String username) throws IOException {
        User user = userService.getUserFromName(username);
        String filename = file.getOriginalFilename();
        String fileSize = Long.toString(file.getSize());
        String contentType = file.getContentType();
        byte[] bytes = file.getInputStream().readAllBytes();

        File newFile = new File (null,filename,contentType,fileSize,bytes,user);
        int inserts = fileMapper.insert(newFile);

        if (inserts == 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<File> getAllFiles (String username) {
        User user = userService.getUserFromName(username);
        List<File> files = fileMapper.getAllFiles(user.getUserId());
        return fileMapper.getAllFiles(user.getUserId());
    }

    public File getFileFromName (String filename) {
       return fileMapper.getFileFromName(filename);
    }

    public boolean checkFilenameAvailable (String filename) {
        return fileMapper.getFileFromName(filename) == null;
    }

    public int deleteFile (int fileId) {
        return fileMapper.delete (fileId);
    }

}
