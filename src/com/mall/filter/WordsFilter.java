package com.mall.filter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.mall.wrapper.HttpReqWrapper;

public class WordsFilter implements Filter {
	
	private Map<String, String>map = new HashMap<String, String>();
	//过滤器的初始化
	public void init(FilterConfig config) throws ServletException {
		String filePath = config.getInitParameter("filePath");//从配置文件中取得文件的相对路径
		ServletContext context = config.getServletContext();
		String realPath = context.getRealPath(filePath);//根据相对路径取得绝对路径
		try {
			FileReader freader = new FileReader(realPath);//根据绝对路径，通过文件流读取文件
			BufferedReader br = new BufferedReader(freader);
			String line = null;
			while((line=br.readLine()) != null) {
				String []str = line.split("=");
				map.put(str[0], str[1]);
			}
		} catch (FileNotFoundException e) {  
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//乱码处理
		request.setCharacterEncoding("gb2312");
		response.setContentType("text/html;charset=gb2312");
		HttpServletRequest HttpReq = (HttpServletRequest) request;
		HttpReqWrapper hrw = new HttpReqWrapper(HttpReq);
		hrw.setMap(map);
		chain.doFilter(hrw, response);
	}

	public void destroy() {
		
	}
	
}
