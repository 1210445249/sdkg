package com.fastjrun.helper;

import com.fastjrun.common.BaseCodeMsgConstants;
import com.fastjrun.common.CodeException;
import com.fastjrun.dto.DefaultListResponse;
import com.fastjrun.dto.DefaultResponse;
import com.fastjrun.dto.DefaultResponseHead;

public class BaseResponseHelper {

    public static DefaultResponse getSuccessResult() {
        DefaultResponse response = getResult();
        return response;

    }

    public static DefaultListResponse getSuccessResultList() {

        DefaultListResponse response = getResultList();
        return response;
    }

    public static <T> DefaultResponse<T> getResult() {
        DefaultResponse<T> response = new DefaultResponse<>();
        DefaultResponseHead responseHead = new DefaultResponseHead();
        responseHead.setCode(BaseCodeMsgConstants.BaseCodeMsg.OK.getCode());
        responseHead.setMsg(BaseCodeMsgConstants.BaseCodeMsg.OK.getMsg());
        response.setHead(responseHead);
        return response;
    }

    public static <T> DefaultListResponse<T> getResultList() {
        DefaultListResponse<T> response = new DefaultListResponse<>();
        DefaultResponseHead responseHead = new DefaultResponseHead();
        responseHead.setCode(BaseCodeMsgConstants.BaseCodeMsg.OK.getCode());
        responseHead.setMsg(BaseCodeMsgConstants.BaseCodeMsg.OK.getMsg());
        response.setHead(responseHead);
        return response;
    }

    public static DefaultResponse getResult(CodeException codeException) {
        DefaultResponse response = new DefaultResponse();
        DefaultResponseHead responseHead = new DefaultResponseHead();
        responseHead.setCode(codeException.getCode());
        responseHead.setMsg(codeException.getMsg());
        response.setHead(responseHead);
        return response;
    }

    public static DefaultListResponse getResultList(CodeException codeException) {
        DefaultListResponse response = new DefaultListResponse();
        DefaultResponseHead responseHead = new DefaultResponseHead();
        responseHead.setCode(codeException.getCode());
        responseHead.setMsg(codeException.getMsg());
        response.setHead(responseHead);
        return response;
    }

}
