package com.xecoder;

import com.xecoder.config.MybatisMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.xecoder.core.mapper", markerInterface = MybatisMapper.class)
public class Ng2ServerApplication{
	public static void main(String[] args) {
		SpringApplication.run(Ng2ServerApplication.class, args);
	}


}
