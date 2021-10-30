package com.benyq.guochatapi.service;

import com.benyq.guochatapi.base.error.ErrorCode;
import com.benyq.guochatapi.orm.dao.FilePathDao;
import com.benyq.guochatapi.orm.dao.UserDao;
import com.benyq.guochatapi.orm.entity.ChatFileEntity;
import com.benyq.guochatapi.orm.entity.FilePathEntity;
import com.benyq.guochatapi.orm.entity.Result;
import com.benyq.guochatapi.orm.param.AddFilePathParam;
import com.benyq.guochatapi.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;
import java.util.function.BiConsumer;

@Service
public class FileService {

    @Autowired
    FilePathDao filePathDao;
    @Autowired
    UserDao userDao;

    @Transactional
    public Result<FilePathEntity> uploadAvatar(String id, MultipartHttpServletRequest multiReq) {

        MultipartFile multipartFile = multiReq.getFile("avatar");
        if (multipartFile == null) {
            return Result.error(ErrorCode.UPLOAD_EMPTY_FILE);
        }

        String filePath = FileUtil.saveAvatarImg(multipartFile);
        AddFilePathParam pathParam = new AddFilePathParam();
        pathParam.setType(1);
        pathParam.setFilePath(filePath);
        long effectRows = filePathDao.addPath(pathParam);
        if (effectRows <= 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(ErrorCode.UPLOAD_FILE_ERROR);
        }
        effectRows = userDao.editAvatar(id, pathParam.getId(), System.currentTimeMillis());

        if (effectRows <= 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(ErrorCode.UPLOAD_FILE_ERROR);
        }

        FilePathEntity entity = new FilePathEntity();
        entity.setFilePath(filePath);
        entity.setType(1);

        return Result.success(entity);
    }

    /**
     *
     * @param multiReq
     * @param type 文件类型 1 text 2 file  3 img  4 video  5 voice
     * @return
     */
    public Result<String> uploadChatFile(MultipartHttpServletRequest multiReq, int type) {

        Map<String, MultipartFile> multipartFileMap = multiReq.getFileMap();
        if (multipartFileMap.isEmpty()) {
            return Result.error(ErrorCode.UPLOAD_EMPTY_FILE);
        }

        String resultPath = null;

        MultipartFile multipartFile = null;
        if (type == 2) {
            multipartFile = multiReq.getFile("file");
            resultPath = FileUtil.saveChatFile(multipartFile);
        }else if (type == 3) {
            multipartFile = multiReq.getFile("img");
            resultPath = FileUtil.saveChatImg(multipartFile);
        }else if (type == 4) {
            multipartFile = multiReq.getFile("video");
            resultPath = FileUtil.saveChatVideo(multipartFile);
        }else if (type == 5) {
            multipartFile = multiReq.getFile("voice");
            resultPath = FileUtil.saveChatVoice(multipartFile);
        }

        if (resultPath == null) {
            return Result.error(ErrorCode.UPLOAD_EMPTY_FILE);
        }
        return Result.success(resultPath);
    }

}
