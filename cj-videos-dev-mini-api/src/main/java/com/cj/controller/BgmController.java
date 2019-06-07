package com.cj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cj.service.BgmService;
import com.cj.utils.IMoocJSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="背景音乐的接口",tags= {"背景音乐业务的Controller"})
@RequestMapping("/bgm")
public class BgmController extends BasicController{

	@Autowired
	private BgmService bgmService;
	
	@ApiOperation(value="获取背景音乐列表", notes="获取背景音乐列表的接口")
	@PostMapping("/list")
	public IMoocJSONResult list(){
		return IMoocJSONResult.ok(bgmService.queryBgmList());
	}
}
