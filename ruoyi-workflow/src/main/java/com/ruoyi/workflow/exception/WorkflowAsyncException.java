package com.ruoyi.workflow.exception;

import com.ruoyi.common.exception.base.BaseException;

/**
 * <p> 工作流异步处理异常 </p>
 *
 * @Author wocurr.com
 */
public class WorkflowAsyncException extends BaseException {

    public WorkflowAsyncException(String module, String code, Object[] args, String defaultMessage) {
        super(module, code, args, defaultMessage);
    }

    public WorkflowAsyncException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public WorkflowAsyncException(String module, String defaultMessage) {
        super(module, defaultMessage);
    }

    public WorkflowAsyncException(String code, Object[] args) {
        super(code, args);
    }

    public WorkflowAsyncException(String defaultMessage) {
        super(defaultMessage);
    }
}
