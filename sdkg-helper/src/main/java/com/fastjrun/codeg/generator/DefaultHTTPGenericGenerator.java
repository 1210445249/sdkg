package com.fastjrun.codeg.generator;

import com.sun.codemodel.JMethod;

public class DefaultHTTPGenericGenerator extends BaseHTTPGenerator {

    @Override
    protected String processRequestHead(ControllerType controllerType, JMethod controllerMethod, String
            methodPath) {
        return methodPath;
    }
}