package com.boluo.boluoblog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

//@SpringBootTest
class BoluoBlogApplicationTests {

	@Test
	void contextLoads() {

	}


	@Test
	public void test1(){

		Date date = new Date();
		System.out.println("日期是："+date);

	}

}
