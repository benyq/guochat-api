package com.benyq.guochatapi.service;

import com.benyq.guochatapi.base.error.ErrorCode;
import com.benyq.guochatapi.orm.dao.FilePathDao;
import com.benyq.guochatapi.orm.entity.FilePathEntity;
import com.benyq.guochatapi.orm.entity.Result;
import com.benyq.guochatapi.orm.param.AddFilePathParam;
import com.benyq.guochatapi.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Service
public class FileService {

    @Autowired
    FilePathDao filePathDao;

    public Result<FilePathEntity> uploadImg(HttpServletRequest req,
                                            MultipartHttpServletRequest multiReq) {

        MultipartFile multipartFile = multiReq.getFile("img");
        if (multipartFile == null) {
            return Result.error(ErrorCode.UPLOAD_EMPTY_FILE);
        }

        return Result.success(addPath(multipartFile, 0));

    }

    private FilePathEntity addPath(MultipartFile multipartFile, int type) {
        String filePath = FileUtil.saveUploadedImg(multipartFile);
        AddFilePathParam pathParam = new AddFilePathParam();
        pathParam.setType(type);
        pathParam.setFilePath(filePath);
        filePathDao.addPath(pathParam);

        FilePathEntity entity = new FilePathEntity();
        entity.setFileId(String.valueOf(pathParam.getId()));
        entity.setType(type);
        return entity;
    }

}
