package com.example.db1;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Db1Application{

	public static void main(String[] args) {
		SpringApplication.run(Db1Application.class, args);
	}


}
