package com.iceAge.server.auth.application.exception;

import com.iceAge.server.global.exception.BaseException;
import com.iceAge.server.global.response.CommonCode;

public class NOT_SUPPORTED_SOCIAL_PROVIDER extends BaseException {
    public NOT_SUPPORTED_SOCIAL_PROVIDER(String message) {
        super(CommonCode.NOT_SUPPORTED_SOCIAL_PROVIDER + message);
    }
}
