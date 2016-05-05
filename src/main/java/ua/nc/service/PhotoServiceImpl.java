package ua.nc.service;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by Hlib on 29.04.2016.
 */
public class PhotoServiceImpl implements PhotoService {

    private final static String PHOTOS_HOME = System.getProperty("catalina.home")+ File.separator+"photos";

    @Override
    public void uploadPhoto(MultipartFile photo, int userID) throws IOException{
        byte[] photoBytes = photo.getBytes();
        File dir = new File(PHOTOS_HOME + File.separator + userID);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File localFile = new File(dir.getAbsolutePath() + File.separator + "photo");
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(localFile));
        stream.write(photoBytes);
        stream.close();
    }

    @Override
    public byte[] getPhotoById(int userID) {
        File file = new File(PHOTOS_HOME + File.separator + userID + File.separator + "photo");
        byte[] fileByteArray = null;
        try{
            fileByteArray =  IOUtils.toByteArray(new FileInputStream(file));
        }
        catch (IOException ex) {
            System.out.println(ex.toString());
        }
        return fileByteArray;
    }
}
