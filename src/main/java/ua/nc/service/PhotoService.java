package ua.nc.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Hlib on 29.04.2016.
 */
public interface PhotoService {
    void uploadPhoto(MultipartFile photo, int id) throws IOException;
    byte[] getPhotoById(int id);
}