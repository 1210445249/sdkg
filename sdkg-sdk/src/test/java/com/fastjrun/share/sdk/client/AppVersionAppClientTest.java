
package com.fastjrun.share.sdk.client;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import com.fastjrun.share.sdk.client.AppVersionAppClient;
import com.fastjrun.share.sdk.packet.app.VersionListResponseBody;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

public class AppVersionAppClientTest {

    final Logger log = LogManager.getLogger(this.getClass());
    AppVersionAppClient appVersionAppClient = new AppVersionAppClient();
    Properties propParams = new Properties();

    @BeforeTest
    @org.testng.annotations.Parameters({
        "envName"
    })
    public void init(String envName) {
        appVersionAppClient.initSDKConfig("apiworld");
        try {
            InputStream inParam = AppVersionAppClient.class.getResourceAsStream((("/testdata/"+ envName)+".properties"));
            propParams.load(inParam);
        } catch (IOException _x) {
            _x.printStackTrace();
        }
    }

    @DataProvider(name = "loadParam")
    public Object[][] loadParam(Method method) {
        Set<String> keys = propParams.stringPropertyNames();
        List<String[]> parameters = new ArrayList<String[]>();
        for (String key: keys) {
            if (key.startsWith((("AppVersionAppClient"+".")+(method.getName()+".")))) {
                String value = propParams.getProperty(key);
                parameters.add(new String[] {value });
            }
        }
        Object[][] object = new Object[parameters.size()][] ;
        for (int i = 0; (i<object.length); i ++) {
            String[] str = parameters.get(i);
            object[i] = new String[str.length] ;
            for (int j = 0; (j<str.length); j ++) {
                object[i][j] = str[j];
            }
        }
        return object;
    }

    @org.testng.annotations.Test(dataProvider = "loadParam")
    @org.testng.annotations.Parameters({
        "reqParamsJsonStr"
    })
    public void check(String reqParamsJsonStr) {
        JSONObject reqParamsJson = JSONObject.fromObject(reqParamsJsonStr);
        String appKey = reqParamsJson.optString("appKey");
        String appVersion = reqParamsJson.optString("appVersion");
        String appSource = reqParamsJson.optString("appSource");
        String deviceId = reqParamsJson.optString("deviceId");
        try {
            appVersionAppClient.check(appKey, appVersion, appSource, deviceId);
        } catch (Exception _x) {
            _x.printStackTrace();
        }
    }

    @org.testng.annotations.Test(dataProvider = "loadParam")
    @org.testng.annotations.Parameters({
        "reqParamsJsonStr"
    })
    public void latests(String reqParamsJsonStr) {
        JSONObject reqParamsJson = JSONObject.fromObject(reqParamsJsonStr);
        String appKey = reqParamsJson.optString("appKey");
        String appVersion = reqParamsJson.optString("appVersion");
        String appSource = reqParamsJson.optString("appSource");
        String deviceId = reqParamsJson.optString("deviceId");
        try {
            VersionListResponseBody responseBody = appVersionAppClient.latests(appKey, appVersion, appSource, deviceId);
            log.info(responseBody);
        } catch (Exception _x) {
            _x.printStackTrace();
        }
    }

}
