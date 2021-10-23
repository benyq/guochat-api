package com.benyq.guochatapi.controller;

import com.benyq.guochatapi.base.annotation.ApiMethod;
import com.benyq.guochatapi.orm.entity.FilePathEntity;
import com.benyq.guochatapi.orm.entity.Result;
import com.benyq.guochatapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/file/")
public class FileController {

    @Autowired
    FileService fileService;

    @ApiMethod("上传图片文件，返回id")
    @PostMapping("upload-img")
    public Result<FilePathEntity> uploadImg(HttpServletRequest req,
                                            MultipartHttpServletRequest multiReq) {
        return fileService.uploadImg(req, multiReq);
    }
}
