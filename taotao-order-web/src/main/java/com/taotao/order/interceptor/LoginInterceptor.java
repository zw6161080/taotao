package com.taotao.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Value("${TOKEN_KRY}")
	private String TOKEN_KRY;
	@Value("${SSO_URL}")
	private String SSO_URL;
	
	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 执行handler之前调用此方法
		//从cookie中取token
		String token = CookieUtils.getCookieValue(request, TOKEN_KRY);
		//如果取不到token，说明用户未登录，跳转到用户登录页面，需要把当前请求的url当做参数传递过去，登陆成功之后跳转回请求页面
		if(StringUtils.isBlank(token)){
			//取当前请求的url
			String requestURL = request.getRequestURL().toString();
			//跳转到登录页面
			response.sendRedirect(SSO_URL+"/page/login?url="+requestURL);
			//拦截
			return false;
		}
		//取到token，调用sso系统服务判断用户是否登录
		TaotaoResult taotaoResult = userService.getUserByToken(token);
		//如果用户未登录，taotaoresault状态不是200，则跳转到用户登录页面
		if(taotaoResult.getStatus()!=200){
			//跟上面跳转登陆页面一样
			//取当前请求的url
			String requestURL = request.getRequestURL().toString();
			//跳转到登录页面
			response.sendRedirect(SSO_URL+"/page/login?url="+requestURL);
			//拦截
			return false;
		}
		//如果查询到用户登录信息，放行
		//取用户信息，将用户信息放到request中
		TbUser user = (TbUser) taotaoResult.getData();
		request.setAttribute("user", user);
		// 返回值，true放行，false拦截
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 执行handler之后，modelAndView返回之前调用此方法

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// modelAndView返回之后调用此方法，一般用来处理异常

	}

}
