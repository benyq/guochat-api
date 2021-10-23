package com.benyq.guochatapi.orm.dao;

import com.benyq.guochatapi.orm.param.AddFilePathParam;
import org.springframework.stereotype.Repository;

@Repository
public interface FilePathDao {

    Long addPath(AddFilePathParam param);

}
