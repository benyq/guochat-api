package com.benyq.guochatapi.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    private static final String ROOT_PATH = "file/";
    private static final String IMG_PATH = ROOT_PATH + "img/";

    public static String saveUploadedImg(MultipartFile multipartFile) {
        return saveUploadedImg(multipartFile, null, null);
    }

    public static String saveUploadedImg(MultipartFile multipartFile, String parentPath, String fileName) {
        String uploadFilePath = multipartFile.getOriginalFilename();
        // 截取上传文件的文件名
        String uploadFileName = uploadFilePath.substring(
                uploadFilePath.lastIndexOf('\\') + 1, uploadFilePath.indexOf('.'));
        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(
                uploadFilePath.indexOf('.') + 1);

        FileOutputStream fos = null;
        FileInputStream fis = null;

        if (StringUtils.hasLength(parentPath)) {
            parentPath = IMG_PATH + File.separator + parentPath + File.separator;
        }else {
            parentPath = IMG_PATH;
        }
        File parentFile = new File(parentPath);
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        if (StringUtils.hasLength(fileName)) {
            uploadFileName = fileName;
        }

        String outFile = parentPath + uploadFileName + "." + uploadFileSuffix;

        try {
            fis = (FileInputStream) multipartFile.getInputStream();
            fos = new FileOutputStream(outFile);
            byte[] temp = new byte[1024];
            int i = fis.read(temp);
            while (i != -1){
                fos.write(temp,0,temp.length);
                fos.flush();
                i = fis.read(temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return outFile;
    }
}
