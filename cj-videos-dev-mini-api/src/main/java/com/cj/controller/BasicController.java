package com.cj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.cj.utils.RedisOperator;

@RestController
public class BasicController {

	@Autowired
	public RedisOperator redis;
	
	public static final String USER_REDIS_SESSION = "user-redis-session";
	
	public static final String FILE_SPACE = "D:/cj_videos_dev";
	
	public static final String FFMPEG_EXE = "D:\\backup\\ffmpeg\\bin\\ffmpeg.exe";
}
