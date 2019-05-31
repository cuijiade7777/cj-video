package com.cj.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cj.pojo.Users;
import com.cj.pojo.vo.UsersVO;
import com.cj.service.UserService;
import com.cj.utils.IMoocJSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="用户相关业务的接口",tags= {"用户相关业务的Controller"})
@RequestMapping("/user")
public class UserController extends BasicController{

	@Autowired
	private UserService userService;
	
	
	@ApiOperation(value="用户上传头像", notes="用户上传头像的接口")
	@ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String", paramType="query")
	@PostMapping("/uploadFace")
	public IMoocJSONResult uploadFace(String userId, @RequestParam("file")MultipartFile[] files) throws Exception {
		
		if(StringUtils.isBlank(userId)) {
			return IMoocJSONResult.errorMsg("用户ID不能为空...");
		}
		
		//文件保存的命名空间
		String fileSpace = "D:/cj_videos_dev";
		//保存到数据库的相对路径
		String uploadPathDB = "/"+userId+"/face";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if(files != null && files.length > 0) {
				String fileName = files[0].getOriginalFilename();
				if(StringUtils.isNotBlank(fileName)) {
					//文件上传的最终保存路径
					String finalFacePath = fileSpace + uploadPathDB + "/" + fileName;
					//设置数据库保存的路径
					uploadPathDB += ("/"+fileName);
					File outFile = new File(finalFacePath);
					if(outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
						//创建父文件夹
						outFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = files[0].getInputStream();
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
		
		Users user = new Users();
		user.setId(userId);
		user.setFaceImage(uploadPathDB);
		
		userService.updateUserInfo(user);
		return IMoocJSONResult.ok(uploadPathDB);
	}
	
	@ApiOperation(value="查询用户信息", notes="查询用户信息的接口")
	@ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String", paramType="query")
	@PostMapping("/query")
	public IMoocJSONResult query(String userId){
		
		if(StringUtils.isBlank(userId)) {
			return IMoocJSONResult.errorMsg("用户ID不能为空...");
		}
		Users user = userService.queryUserInfo(userId);
		UsersVO userVO = new UsersVO();
		BeanUtils.copyProperties(user, userVO);
		
		return IMoocJSONResult.ok(userVO);
	}
}
