package com.cj.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cj.enums.VideoStatusEnum;
import com.cj.pojo.Bgm;
import com.cj.pojo.Videos;
import com.cj.service.BgmService;
import com.cj.service.VideoService;
import com.cj.utils.FetchVideoCover;
import com.cj.utils.IMoocJSONResult;
import com.cj.utils.MergeVideoMp3;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value="视频相关业务的接口", tags="视频相关业务的controller")
@RequestMapping("/video")
public class VideoController extends BasicController{
	
	@Autowired
	private BgmService bgmService;
	
	@Autowired
	private VideoService videoService;
	
	@ApiOperation(value="用户上传视频", notes="用户上传视频的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String", paramType="form"),
		@ApiImplicitParam(name="bgmId", value="背景音乐ID", required=false, dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoSeconds", value="背景音乐播放长度", required=true, dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoWidth", value="视频宽度", required=true, dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoHeight", value="视频高度", required=true, dataType="String", paramType="form"),
		@ApiImplicitParam(name="desc", value="视频描述", required=false, dataType="String", paramType="form")
	})
	@PostMapping(value="/upload", headers="content-type=multipart/form-data")
	public IMoocJSONResult upload(String userId, String bgmId
								  ,double videoSeconds, int videoWidth
								  ,int videoHeight, String desc
								  ,@ApiParam(value="短视频", required=true)MultipartFile file) throws Exception {
		
		if(StringUtils.isBlank(userId)) {
			return IMoocJSONResult.errorMsg("用户ID不能为空...");
		}
		
		//保存到数据库的相对路径
		String uploadPathDB = "/"+userId+"/video";
		String coverPathDB = "/"+userId+"/video";
		
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		String finalVideoPath = "";
		try {
			if(file != null) {
				String fileName = file.getOriginalFilename();
				String fileNamePrifix = fileName.split("\\.")[0];
				
				if(StringUtils.isNotBlank(fileName)) {
					//文件上传的最终保存路径
					finalVideoPath = FILE_SPACE + uploadPathDB + "/" + fileName;
					
					//设置数据库保存的路径
					uploadPathDB += ("/"+fileName);
					coverPathDB = coverPathDB + "/" +fileNamePrifix + ".jpg";
					
					File outFile = new File(finalVideoPath);
					if(outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
						//创建父文件夹
						outFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = file.getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
				}
			}else {
				return IMoocJSONResult.errorMsg("上传出错...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return IMoocJSONResult.errorMsg("上传出错...");
		} finally {
			if(fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
		
		//判断bgmId是否为空，如果不为空，那就查询BGM信息，并且合并视频，生成新的视频
		if(StringUtils.isNotBlank(bgmId)) {
			Bgm bgm = bgmService.queryBgmById(bgmId);
			String mp3InputPath = FILE_SPACE + bgm.getPath();
			MergeVideoMp3 tool = new MergeVideoMp3(FFMPEG_EXE);
			String videoInputPath = finalVideoPath;
			String videoOutputName = UUID.randomUUID().toString()+".mp4";
			uploadPathDB = "/"+userId+"/video"+"/"+videoOutputName;
			finalVideoPath = FILE_SPACE + uploadPathDB;
			tool.convertor(mp3InputPath, videoInputPath, videoSeconds, finalVideoPath);
		}
		
		FetchVideoCover fetchVideoCover = new FetchVideoCover(FFMPEG_EXE);
		fetchVideoCover.getCover(finalVideoPath, FILE_SPACE + coverPathDB);
		
		//保存信息到数据库
		Videos video = new Videos();
		video.setAudioId(bgmId);
		video.setCreateTime(new Date());
		video.setStatus(VideoStatusEnum.SUCCESS.value);
		video.setUserId(userId);
		video.setVideoDesc(desc);
		video.setVideoHeight(videoHeight);
		video.setVideoWidth(videoWidth);
		video.setVideoPath(uploadPathDB);
		video.setCoverPath(coverPathDB);
		video.setVideoSeconds((float)videoSeconds);
		
		String id = videoService.saveVideo(video);
		return IMoocJSONResult.ok(id);
	}
	
	@ApiOperation(value="用户上传封面", notes="用户上传封面的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoId", value="视频主键id", required=true, dataType="String", paramType="form")
	})
	@PostMapping(value="/uploadCover", headers="content-type=multipart/form-data")
	public IMoocJSONResult uploadCover(String userId, String videoId
								  ,@ApiParam(value="封面", required=true)MultipartFile file) throws Exception {
		
		if(StringUtils.isBlank(videoId)) {
			return IMoocJSONResult.errorMsg("视频主键ID不能为空");
		}
		if(StringUtils.isBlank(userId)) {
			return IMoocJSONResult.errorMsg("用户ID不能为空");
		}
		
		//保存到数据库的相对路径
		String uploadPathDB = "/"+userId+"/video";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		String finalCoverPath = "";
		try {
			if(file != null) {
				String fileName = file.getOriginalFilename();
				if(StringUtils.isNotBlank(fileName)) {
					//文件上传的最终保存路径
					finalCoverPath = FILE_SPACE + uploadPathDB + "/" + fileName;
					//设置数据库保存的路径
					uploadPathDB += ("/"+fileName);
					File outFile = new File(finalCoverPath);
					if(outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
						//创建父文件夹
						outFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = file.getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
				}
			}else {
				return IMoocJSONResult.errorMsg("上传出错...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return IMoocJSONResult.errorMsg("上传出错...");
		} finally {
			if(fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
		
		videoService.updateVideo(videoId, uploadPathDB);
		return IMoocJSONResult.ok();
	}
	
}
