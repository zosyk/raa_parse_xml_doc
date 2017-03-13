package com.rightandabove.utils.validators;

import com.rightandabove.models.UploadedFile;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by alex on 28.02.17.
 */

@Component
public class FileValidator implements Validator {


    public boolean supports(Class<?> clazz) {
        return false;
    }

    public void validate(Object target, Errors errors) {

        UploadedFile file = (UploadedFile) target;

        if(file.getFile().getSize() == 0) {
            errors.rejectValue("file", "uploadForm.selectFile", "Please select a file!");
        }

    }
}
