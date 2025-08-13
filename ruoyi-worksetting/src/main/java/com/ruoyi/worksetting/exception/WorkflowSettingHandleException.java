package com.ruoyi.worksetting.exception;

import com.ruoyi.common.exception.base.BaseException;

/**
 * <p> 工作流设置异常 </p>
 *
 * @Author wocurr.com
 */
public class WorkflowSettingHandleException extends BaseException {

    public WorkflowSettingHandleException(String module, String code, Object[] args, String defaultMessage) {
        super(module, code, args, defaultMessage);
    }

    public WorkflowSettingHandleException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public WorkflowSettingHandleException(String module, String defaultMessage) {
        super(module, defaultMessage);
    }

    public WorkflowSettingHandleException(String code, Object[] args) {
        super(code, args);
    }

    public WorkflowSettingHandleException(String defaultMessage) {
        super(defaultMessage);
    }
}
