package com.xecoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.xecoder.base.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.MultivaluedMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoServerTests extends BaseTest {

	@Test
	public void userList() {
		MultivaluedMap<String, String> param = new MultivaluedMapImpl();
//		param.add("habitId", userData.getHabitId());
		String output = this.post("/user/list", param);
		JSONObject json = JSON.parseObject(output);
		assertThat(json.get("apistatus").equals(1)).isTrue();
	}

}
