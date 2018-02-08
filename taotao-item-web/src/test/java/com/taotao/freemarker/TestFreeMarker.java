//package com.taotao.freemarker;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.Writer;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.Test;
//
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//
//public class TestFreeMarker {
//	
//	@Test
//	public void testFreeMarker() throws Exception{
//		
//		//创建一个配置文件
//		Configuration configuration=new Configuration(Configuration.getVersion());
//		//声明模板所在路径跟字符集
//		configuration.setDirectoryForTemplateLoading(new File("D:/workspace-itcast/JavaEE1/taotao-item-web/src/main/webapp/WEB-INF/ftl"));
//		configuration.setDefaultEncoding("utf-8");
//		//加载模板
//		Template template = configuration.getTemplate("hello.ftl");
//		//创建一个数据集，可以使pojo与map，推荐map
//		Map data=new HashMap<>();
//		//将模板中传入的参数转为自己需要的
//		data.put("hello", "hello freemarker");
//		//创建一个writer对象，指定输出文件路径以及文件名
//		Writer out=new FileWriter("E:/freemarker/hello.txt");
//		//使用模板输出文件
//		template.process(data, out);
//		out.close();
//		
//		
//	}
//
//}
