package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;



@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserService userService;

    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, HashService hashService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }


    public List<Credential> getAllCredentials(String username) {
        User user = userService.getUserFromName(username);
        List <Credential> credentials = credentialMapper.getAllCredentials(user.getUserId());

        for (Credential credential: credentials){
            String decryptedPassword = decryptPassword(credential.getPassword(),credential.getKey());
            credential.setDecryptedPassword(decryptedPassword);
        }

        return credentials;
    }

    public int storeCredential(CredentialForm credentialForm, String username) {
        User user = userService.getUserFromName(username);

        String key = generateKey();
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), key);

        Credential credential = new Credential(
                credentialForm.getCredentialId(),
                credentialForm.getUrl(),
                credentialForm.getUsername(),
                key,
                encryptedPassword,
                user.getUserId()
        );

        if (credentialForm.getCredentialId() == null) {
            return credentialMapper.insert(credential);
        } else {
            int id = credentialForm.getCredentialId();
            credential.setCredentialId(id);
            return credentialMapper.update(credential);
        }
    }


    public int deleteCredential(int credentialId) {
        return credentialMapper.delete (credentialId);
    }


    private String generateKey () {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String keyString = Base64.getEncoder().encodeToString(key);

        return keyString;
    }


    public String decryptPassword (String encryptedPassword, String key) {
        return encryptionService.decryptValue(encryptedPassword, key);
    }


}

