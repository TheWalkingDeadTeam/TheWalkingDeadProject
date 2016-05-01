package ua.nc.validator;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Hlib on 01.05.2016.
 */
public class PhotoValidator implements Validator {

    private final Set<String> PHOTO_TYPES = new HashSet<>(Arrays.asList(new String[]{"jpg","jpeg","png"}));

    @Override
    public Set<ValidationError> validate(Object obj) {
        Set<ValidationError> errors = new LinkedHashSet<>();
        MultipartFile photo = (MultipartFile) obj;
        if (photo.isEmpty()){
            errors.add(new ValidationError("photo","File is empty"));
        }else {
            String fileName = photo.getOriginalFilename();
            String[] splittedFileName = fileName.split("[,.]");
            if (!PHOTO_TYPES.contains(splittedFileName[splittedFileName.length - 1])) {
                errors.add(new ValidationError("photo", "Wrong filename extension"));
            }
        }
        return errors;
    }
}
