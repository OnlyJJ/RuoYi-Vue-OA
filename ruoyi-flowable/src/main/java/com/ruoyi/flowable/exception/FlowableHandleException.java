package com.ruoyi.flowable.exception;

import com.ruoyi.common.exception.base.BaseException;

/**
 * <p> 工作流处理异常 </p>
 *
 * @Author wocurr.com
 */
public class FlowableHandleException extends BaseException {

    public FlowableHandleException(String module, String code, Object[] args, String defaultMessage) {
        super(module, code, args, defaultMessage);
    }

    public FlowableHandleException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public FlowableHandleException(String module, String defaultMessage) {
        super(module, defaultMessage);
    }

    public FlowableHandleException(String code, Object[] args) {
        super(code, args);
    }

    public FlowableHandleException(String defaultMessage) {
        super(defaultMessage);
    }
}
