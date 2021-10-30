package com.benyq.guochatapi.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    private static final String ROOT_PATH = "file/";
    private static final String AVATAR_PATH = ROOT_PATH + "avatar/";
    private static final String VIDEO_PATH = ROOT_PATH + "chat/video/";
    //其他文件
    private static final String FILE_PATH = ROOT_PATH + "chat/file/";
    private static final String IMG_PATH = ROOT_PATH + "chat/img/";
    private static final String VOICE_PATH = ROOT_PATH + "chat/voice/";

    public static String saveAvatarImg(MultipartFile multipartFile) {
        return saveUploadFile(multipartFile, AVATAR_PATH, null);
    }

    public static String saveChatImg(MultipartFile multipartFile) {
        return saveUploadFile(multipartFile, IMG_PATH, null);
    }

    public static String saveChatVideo(MultipartFile multipartFile) {
        return saveUploadFile(multipartFile, VIDEO_PATH, null);
    }

    public static String saveChatFile(MultipartFile multipartFile) {
        return saveUploadFile(multipartFile, FILE_PATH, null);
    }

    public static String saveChatVoice(MultipartFile multipartFile) {
        return saveUploadFile(multipartFile, VOICE_PATH, null);
    }

    private static String saveUploadFile(MultipartFile multipartFile, String parentPath, String fileName) {

        if (multipartFile == null) {
            return null;
        }

        String uploadFilePath = multipartFile.getOriginalFilename();
        // 截取上传文件的文件名
        String uploadFileName = uploadFilePath.substring(
                uploadFilePath.lastIndexOf('\\') + 1, uploadFilePath.indexOf('.'));
        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(
                uploadFilePath.indexOf('.') + 1);

        FileOutputStream fos = null;
        FileInputStream fis = null;

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
