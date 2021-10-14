package cwall.club.core.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class FileService {
    public void saveImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = "/home/img";
        if (file.isEmpty()) {
            return;
        }
        try {
            uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
        }
    }

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath +"/"+ fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
