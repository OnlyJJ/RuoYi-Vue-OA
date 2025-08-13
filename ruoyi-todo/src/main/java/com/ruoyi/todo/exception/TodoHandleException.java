package com.ruoyi.todo.exception;

import com.ruoyi.common.exception.base.BaseException;

/**
 * <p> 待办处理异常 </p>
 *
 * @Author wocurr.com
 */
public class TodoHandleException extends BaseException {

    public TodoHandleException(String module, String code, Object[] args, String defaultMessage) {
        super(module, code, args, defaultMessage);
    }

    public TodoHandleException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public TodoHandleException(String module, String defaultMessage) {
        super(module, defaultMessage);
    }

    public TodoHandleException(String code, Object[] args) {
        super(code, args);
    }

    public TodoHandleException(String defaultMessage) {
        super(defaultMessage);
    }
}
