package com.taotao.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class TestFastDFS {
	
	@Test
	public void upLoadFile() throws Exception{
		
		//1、向工程中添加jar包
		//2、创建一个配置文件client.conf，配置tracker服务器地址
		//3、加载配置文件
		ClientGlobal.init("D:/workspace-itcast/JavaEE1/taotao-manager-web/src/main/resources/resource/client.conf");
		//4、创建一个TrackerClient对象
		TrackerClient trackerClient=new TrackerClient();
		//5、使用TrackerClient对象对象获得一个trackerserver对象
		TrackerServer trackerServer=trackerClient.getConnection();
		//6、创建一个StorageServer的引用null就可以
		StorageServer storageServer=null;
		//7、创建一个StorageClient对象，trackerserver，StorageServer两个参数
		StorageClient storageClient=new StorageClient(trackerServer,storageServer);
		//8、使用StorageClient对象上传文件
		String[] strings = storageClient.upload_file("E:/学习视频/17商城综合项目/参考资料/广告图片/1.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
		
	}
	
	
	

}
