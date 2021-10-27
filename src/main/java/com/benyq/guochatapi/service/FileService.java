package com.benyq.guochatapi.service;

import com.benyq.guochatapi.base.error.ErrorCode;
import com.benyq.guochatapi.orm.dao.FilePathDao;
import com.benyq.guochatapi.orm.dao.UserDao;
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

        String filePath = FileUtil.saveUploadedImg(multipartFile);
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


}
