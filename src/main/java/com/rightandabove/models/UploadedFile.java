package com.rightandabove.models;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by alex on 28.02.17.
 */
public class UploadedFile {

    private MultipartFile file;

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }
}
