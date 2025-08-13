package com.ruoyi.workflow.exception;

import com.ruoyi.common.exception.base.BaseException;

/**
 * <p> 工作流处理异常 </p>
 *
 * @Author wocurr.com
 */
public class WorkflowHandleException extends BaseException {

    public WorkflowHandleException(String module, String code, Object[] args, String defaultMessage) {
        super(module, code, args, defaultMessage);
    }

    public WorkflowHandleException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public WorkflowHandleException(String module, String defaultMessage) {
        super(module, defaultMessage);
    }

    public WorkflowHandleException(String code, Object[] args) {
        super(code, args);
    }

    public WorkflowHandleException(String defaultMessage) {
        super(defaultMessage);
    }
}
