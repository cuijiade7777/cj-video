package com.cj.service;

import java.util.List;

import com.cj.pojo.Bgm;

public interface BgmService {
	
	/*
	 * 查询背景音乐列表
	 */
	public List<Bgm> queryBgmList();

	/**
	 * 通过id查询背景音乐信息
	 */
	public Bgm queryBgmById(String bgmId);
}
