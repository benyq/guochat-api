package com.benyq.guochatapi.orm.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class BaseAddParam {

    protected Long updateTime = System.currentTimeMillis();
    protected Long createTime = System.currentTimeMillis();
    protected Long id;
}
