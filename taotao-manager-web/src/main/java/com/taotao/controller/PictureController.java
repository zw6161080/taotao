package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.utils.FastDFSClient;

@Controller
public class PictureController {
	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String picUpload(MultipartFile uploadFile){
		
		try{
			//接受上传的文件，即方法力的参数uploadFile
			//取文件扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			//将文件扩展名从原始文件名称中截取出来，查找.在originalFilename最后出现的位置，用.将原始名称分割开来
			String extName=originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			//上传到图片服务器
			FastDFSClient fastDFSClient=new FastDFSClient("classpath:resource/client.conf");
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			url=IMAGE_SERVER_URL+url;
			//响应上传图片的url
			Map result=new HashMap<>();
			result.put("error", 0);
			result.put("url",url);
			return JsonUtils.objectToJson(result);
			
		}catch(Exception e){
			e.printStackTrace();
			Map result=new HashMap<>();
			result.put("error", 1);
			result.put("message","图片上传失败");
			return JsonUtils.objectToJson(result);
			
		}
		
	}

}
