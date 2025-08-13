package com.ruoyi.workflow.exception;

import com.ruoyi.common.exception.base.BaseException;

/**
 * <p> 待办异步处理异常 </p>
 *
 * @Author wocurr.com
 */
public class TodoAsyncException extends BaseException {

    public TodoAsyncException(String module, String code, Object[] args, String defaultMessage) {
        super(module, code, args, defaultMessage);
    }

    public TodoAsyncException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public TodoAsyncException(String module, String defaultMessage) {
        super(module, defaultMessage);
    }

    public TodoAsyncException(String code, Object[] args) {
        super(code, args);
    }

    public TodoAsyncException(String defaultMessage) {
        super(defaultMessage);
    }
}
