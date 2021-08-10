package me.zhengjie.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bron
 */

@Getter
@AllArgsConstructor
public enum RegularExpEnum {
    /* 只允許數字、字母、底線 */
    NUM_LETTER(Constants.NUM_LETTER_CODE, Constants.NUM_LETTER_MESSAGE),

    ;
    private final String code;
    private final String message;

    public static RegularExpEnum find(Integer code) {
        for (RegularExpEnum value : RegularExpEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }

    public static class Constants {
        public static final String NUM_LETTER_CODE = "[a-zA-Z0-9_]+";
        public static final String NUM_LETTER_MESSAGE = "只允許數字、字母、底線";
    }
}
