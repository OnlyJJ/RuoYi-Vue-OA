package com.ruoyi.workfile.exception;

import com.ruoyi.common.exception.base.BaseException;

/**
 * <p> 工作文件处理异常 </p>
 *
 * @Author wocurr.com
 */
public class WorkfileHandleException extends BaseException {

    public WorkfileHandleException(String module, String code, Object[] args, String defaultMessage) {
        super(module, code, args, defaultMessage);
    }

    public WorkfileHandleException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public WorkfileHandleException(String module, String defaultMessage) {
        super(module, defaultMessage);
    }

    public WorkfileHandleException(String code, Object[] args) {
        super(code, args);
    }

    public WorkfileHandleException(String defaultMessage) {
        super(defaultMessage);
    }
}
