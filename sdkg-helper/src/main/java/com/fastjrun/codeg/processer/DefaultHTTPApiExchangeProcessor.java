package com.fastjrun.codeg.processer;

import com.fastjrun.codeg.generator.method.BaseControllerMethodGenerator;
import com.fastjrun.exchange.DefaultHTTPApiExchange;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JVar;

public class DefaultHTTPApiExchangeProcessor extends BaseExchangeProcessor implements DefaultHTTPApiExchange {

    @Override
    public String processRequest(BaseControllerMethodGenerator baseControllerMethodGenerator, JMethod
            jcontrollerMethod, MockModel mockModel) {
        JBlock controllerMethodBlk = jcontrollerMethod.body();
        JVar requestHeadVar = controllerMethodBlk.decl(cm.ref(REQUEST_HEAD_CLASS_NAME), "requestHead",
                JExpr._new(cm.ref(REQUEST_HEAD_CLASS_NAME)));
        JVar accessKeyJVar = jcontrollerMethod.param(cm.ref("String"), "accessKey");
        accessKeyJVar.annotate(cm.ref("org.springframework.web.bind.annotation.PathVariable"))
                .param("value", "accessKey");
        controllerMethodBlk.invoke(JExpr.ref("requestHead"), "setAccessKey").arg(JExpr.ref("accessKey"));
        if (mockModel == MockModel.MockModel_Swagger) {
            accessKeyJVar.annotate(cm.ref("io.swagger.annotations.ApiParam")).param("name", "accessKey")
                    .param("value", "接入客户端的accessKey").param("required", true);
        }
        JVar txTimeJVar = jcontrollerMethod.param(cm.ref("Long"), "txTime");
        txTimeJVar.annotate(cm.ref("org.springframework.web.bind.annotation.PathVariable")).param("value",
                "txTime");
        controllerMethodBlk.invoke(JExpr.ref("requestHead"), "setTxTime").arg(JExpr.ref("txTime"));
        if (mockModel == MockModel.MockModel_Swagger) {
            txTimeJVar.annotate(cm.ref("io.swagger.annotations.ApiParam")).param("name", "txTime")
                    .param("value", "接口请求时间戳").param("required", true);
        }
        JVar md5HashJVar = jcontrollerMethod.param(cm.ref("String"), "md5Hash");
        md5HashJVar.annotate(cm.ref("org.springframework.web.bind.annotation.PathVariable")).param("value",
                "md5Hash");
        controllerMethodBlk.invoke(JExpr.ref("requestHead"), "setMd5Hash").arg(JExpr.ref("md5Hash"));
        if (mockModel == MockModel.MockModel_Swagger) {
            md5HashJVar.annotate(cm.ref("io.swagger.annotations.ApiParam")).param("name", "md5Hash")
                    .param("value", "md5Hash").param("required", true);
        }
        return "/{accessKey}/{txTime}/{md5Hash}";
    }

    @Override
    public void parseRequestClass(BaseControllerMethodGenerator baseControllerMethodGenerator) {
        if (baseControllerMethodGenerator.getRequestBodyClass() != null) {
            baseControllerMethodGenerator.setRequestClass(
                    cm.ref(REQUEST_CLASS_NAME).narrow(baseControllerMethodGenerator.getRequestBodyClass())
            );
        } else {
            baseControllerMethodGenerator.setRequestClass(
                    cm.ref(REQUEST_CLASS_NAME)
            );
        }
    }
}
