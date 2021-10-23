package com.benyq.guochatapi.orm.enumeration;

/**
 * @author benyq
 * @time 2021/10/23
 * @e-mail 1520063035@qq.com
 * @note
 */
public enum GenderEnum {
    MALE(1), FEMALE(2), UNKNOWN(3);

    private int gender;

    GenderEnum(int gender) {
        this.gender = gender;
    }
}
