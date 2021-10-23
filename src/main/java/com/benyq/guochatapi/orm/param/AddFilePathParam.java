package com.benyq.guochatapi.orm.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddFilePathParam extends BaseAddParam {
    private String filePath;
    private int type = 0;
}
