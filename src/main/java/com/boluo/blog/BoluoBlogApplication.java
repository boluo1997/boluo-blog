package com.boluo.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.boluo.blog.dao")
@EnableCaching
public class BoluoBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoluoBlogApplication.class, args);
	}
}
