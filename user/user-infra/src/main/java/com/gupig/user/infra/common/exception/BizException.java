package com.gupig.user.infra.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 *
 * @author hanbiuk
 * @date 2024-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {

    private String errCode;

    public BizException(String errMessage) {
        super(errMessage);
    }

    public BizException(String errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
    }

    public BizException(String errMessage, Throwable e) {
        super(errMessage, e);
    }

    public BizException(String errCode, String errMessage, Throwable e) {
        super(errMessage, e);
        this.errCode = errCode;
    }

}
