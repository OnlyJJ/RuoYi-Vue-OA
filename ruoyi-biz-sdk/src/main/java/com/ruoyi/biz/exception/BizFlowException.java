package com.ruoyi.biz.exception;

import com.ruoyi.common.exception.base.BaseException;

/**
 * <p> 业务流程处理异常 </p>
 *
 * @Author wocurr.com
 */
public class BizFlowException extends BaseException {

    public BizFlowException(String module, String code, Object[] args, String defaultMessage) {
        super(module, code, args, defaultMessage);
    }

    public BizFlowException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public BizFlowException(String module, String defaultMessage) {
        super(module, defaultMessage);
    }

    public BizFlowException(String code, Object[] args) {
        super(code, args);
    }

    public BizFlowException(String defaultMessage) {
        super(defaultMessage);
    }
}
