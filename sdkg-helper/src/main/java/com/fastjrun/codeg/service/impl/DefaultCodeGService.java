package com.fastjrun.codeg.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.dom4j.Document;

import com.fastjrun.codeg.common.CodeGException;
import com.fastjrun.codeg.common.CodeGMsgContants;
import com.fastjrun.codeg.common.CodeModelConstants;
import com.fastjrun.codeg.common.CommonController;
import com.fastjrun.codeg.common.PacketObject;
import com.fastjrun.codeg.generator.BaseControllerGenerator;
import com.fastjrun.codeg.generator.PacketGenerator;
import com.fastjrun.codeg.helper.CodeGeneratorFactory;
import com.fastjrun.codeg.service.CodeGService;
import com.fastjrun.codeg.util.BundleXMLParser;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.writer.FileCodeWriter;

public class DefaultCodeGService extends BaseCodeGServiceImpl implements CodeGService, CodeModelConstants {

    @Override
    public boolean generateAPI(String moduleName) {
        Date begin = new Date();
        commonLog.getLog().info("begin genreate at " + begin);
        this.beforeGenerate(moduleName);

        Map<String, CommonController> controllerMap = this.generatePacket(moduleName,
                MockModel.MockModel_Common);
        List<CommonController> rpcDubboList = new ArrayList<>();
        Map<String, Properties> clientTestParamMap = new HashMap<>();
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        CompletionService<Boolean> completionService = new ExecutorCompletionService<>(threadPool);
        for (CommonController commonController : controllerMap.values()) {
            BaseControllerGenerator baseControllerGenerator = CodeGeneratorFactory.createBaseControllerGenerator
                    (commonController.getControllerType(),
                            this.packageNamePrefix, MockModel.MockModel_Common, this.author, this.company);
            baseControllerGenerator.setCommonController(commonController);
            if (commonController.getControllerType().name.equals("Dubbo")) {
                rpcDubboList.add(commonController);
            }

            Callable<Boolean> callable = () -> {
                baseControllerGenerator.processApiModule();
                clientTestParamMap
                        .put(commonController.getClientName(), baseControllerGenerator.getClientTestParam());
                return true;
            };
            completionService.submit(callable);

        }

        for (int i = 0; i < controllerMap.size(); i++) {
            try {
                completionService.take().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        try {
            // 生成代码为UTF-8编码
            CodeWriter src = new FileCodeWriter(this.srcDir, "UTF-8");
            CodeWriter srcTest = new FileCodeWriter(this.testSrcDir, "UTF-8");
            // 自上而下地生成类、方法等
            cm.build(src);
            cmTest.build(srcTest);
        } catch (IOException e) {
            this.commonLog.getLog().error("", e);
            throw new CodeGException(CodeGMsgContants.CODEG_CODEG_FAIL, "code generating failed", e);
        }

        if (clientTestParamMap != null) {
            this.saveTestParams(moduleName, clientTestParamMap);
        }

        Document document = this.generateTestngXml(controllerMap, 5, 5, 5);
        if (document != null) {
            String fileName = "testng.xml";
            File file = new File(moduleName + this.getTestResourcesName() + File
                    .separator + fileName);
            this.saveDocument(file, document);
        }

        if (rpcDubboList != null && rpcDubboList.size() > 0) {
            Document dubboXml = this.generateDubboClientXml(rpcDubboList);
            String dubboFileName = "applicationContext-dubbo-consumer.xml";
            File dubboFile = new File(moduleName + this.getResourcesName() + File
                    .separator + dubboFileName);
            this.saveDocument(dubboFile, dubboXml);
        }

        Date end = new Date();

        commonLog.getLog()
                .info("end genreate at " + end + ",cast " + String.valueOf(end.getTime() - begin.getTime()) + " ms");

        return true;
    }

    private Map<String, CommonController> generatePacket(String moduleName, MockModel mockModel) {

        Map<String, PacketObject> packetAllMap = new HashMap<>();
        Map<String, CommonController> controllerAllMap = new HashMap<>();
        if (this.bundleFiles != null && this.bundleFiles.length > 0) {
            for (String bundleFile : bundleFiles) {
                BundleXMLParser bundleXMLParser = new BundleXMLParser();
                bundleXMLParser.init();
                bundleXMLParser.setBundleFile(bundleFile);
                bundleXMLParser.doParse();
                packetAllMap.putAll(bundleXMLParser.getPacketMap());
                controllerAllMap.putAll(bundleXMLParser.getControllerMap());
            }
        }
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        CompletionService<Boolean> completionService = new ExecutorCompletionService<>(threadPool);

        for (PacketObject packetObject : packetAllMap.values()) {
            PacketGenerator packetGenerator = CodeGeneratorFactory
                    .createPacketGenerator(this.packageNamePrefix, mockModel, this.author, this.company);
            packetGenerator.setPacketObject(packetObject);
            Callable<Boolean> callable = () -> {
                return packetGenerator.process();
            };
            completionService.submit(callable);
        }

        for (int i = 0; i < packetAllMap.size(); i++) {
            try {
                completionService.take().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return controllerAllMap;
    }

    @Override
    public boolean generateBundle(String moduleName, MockModel mockModel) {
        Date begin = new Date();
        commonLog.getLog().info("begin genreate at " + begin);
        this.beforeGenerate(moduleName);

        Map<String, CommonController> controllerMap = this.generatePacket(moduleName,
                MockModel.MockModel_Common);
        List<CommonController> rpcDubboList = new ArrayList<>();
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        CompletionService<Boolean> completionService = new ExecutorCompletionService<>(threadPool);

        for (CommonController commonController : controllerMap.values()) {
            BaseControllerGenerator baseControllerGenerator = CodeGeneratorFactory.createBaseControllerGenerator
                    (commonController.getControllerType(),
                            this.packageNamePrefix, mockModel, this.author, this.company);
            baseControllerGenerator.setCommonController(commonController);
            if (commonController.getControllerType().name.equals("Dubbo")) {
                rpcDubboList.add(commonController);
            }
            Callable<Boolean> callable = () -> {
                baseControllerGenerator.processProviderModule();
                return true;
            };
            completionService.submit(callable);
        }

        for (int i = 0; i < controllerMap.size(); i++) {
            try {
                completionService.take().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        try {
            // 生成代码为UTF-8编码
            CodeWriter src = new FileCodeWriter(this.srcDir, "UTF-8");
            // 自上而下地生成类、方法等
            cm.build(src);
        } catch (IOException e) {
            this.commonLog.getLog().error("", e);
            throw new CodeGException(CodeGMsgContants.CODEG_CODEG_FAIL, "code generating failed", e);
        }

        if (rpcDubboList != null && rpcDubboList.size() > 0) {
            Document dubboXml = this.generateDubboServerXml(rpcDubboList);
            String dubboFileName = "applicationContext-dubbo-provider.xml";
            File dubboFile = new File(moduleName + this.getResourcesName() + File
                    .separator + dubboFileName);
            this.saveDocument(dubboFile, dubboXml);
        }

        Date end = new Date();

        commonLog.getLog()
                .info("end genreate at " + end + ",cast " + String.valueOf(end.getTime() - begin.getTime()) + " ms");

        return true;
    }

    @Override
    public boolean generateProvider(String moduleName) {
        return this.generateBundle(moduleName, MockModel.MockModel_Common);
    }
}
