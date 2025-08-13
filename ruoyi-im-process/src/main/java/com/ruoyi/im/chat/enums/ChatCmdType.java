package com.ruoyi.im.chat.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ChatCmdType {

    /**
     * 登陆
     */
    LOGIN(0, "登陆"),
    /**
     * 心跳
     */
    HEART_BEAT(1, "心跳"),
    /**
     * 强制下线
     */
    FORCE_LOGOUT(2, "强制下线"),
    /**
     * 业务系统消息（订单，营销，支付等）
     */
    BUSINESS_SYSTEM_MESSAGE(6,"业务系统消息");


    private final Integer code;

    private final String desc;

    public Integer code() {
        return code;
    }


    public static ChatCmdType fromCode(Integer code) {
        for (ChatCmdType typeEnum : values()) {
            if (typeEnum.code.equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }
}

