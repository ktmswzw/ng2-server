package com.xecoder.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试基础类,用户token,请求封装,配置获取
 * Created by vincent on 16/8/26.
 * Duser.name = 224911261@qq.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties({ServiceConfig.class, UserData.class})
public abstract class BaseTest {


    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    @Autowired
    public ServiceConfig serviceConfig;//服务配置

    @Autowired
    public UserData userData;//测试数据

    public String service;//服务地址

    protected String TOKEN = "";
    /**
     * 测试前配置
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        service = serviceConfig.getUrl() + ":" + serviceConfig.getPort();
        logger.debug("service = " + service);
        getToken();
    }


    /**
     * 测试后处理
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {

    }

    /**
     * 统一提交请求
     * @param path
     * @param param
     * @return
     */
    public String post(String path, MultivaluedMap<String, String> param) {

        Client client = Client.create();
        WebResource webResource = client.resource(service).path("/api"+path);
        ClientResponse response = webResource.queryParams(param)
                .header("CLIENT-VERSION", userData.getClientVsersion())
                .header("Authorization", TOKEN).type(MediaType.APPLICATION_FORM_URLENCODED)
                .post(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return response.getEntity(String.class);
    }

    /**
     * 用户登录,获取token
     */
    private void getToken() {

        MultivaluedMap<String, String> param = new MultivaluedMapImpl();
        param.add("username", userData.getUsername());
        param.add("password", userData.getPassword());
        param.add("device", userData.getDevice());
        String output = post("/login", param);
        logger.debug("output = " + output);
        JSONObject json = JSON.parseObject(output);
        JSONObject o = (JSONObject) json.get("result");
        TOKEN = String.valueOf(o.get("token"));
        assertThat(output.contains("nickname")).isTrue();

    }


}
