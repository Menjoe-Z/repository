package com.plzt.onenet.main.commmon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

public class ApiHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String apiKey = request.getHeader("api-key");
		/*if (StringUtils.isEmpty(apiKey) || !Constant.API_KEY.equals(apiKey)) {
			ResultMsg body = new ResultMsg(ErrorCode.认证失败.getCode(), ErrorCode.认证失败.getMsg());
			response.getWriter().print(JSONObject.fromObject(body).toString());
			response.getWriter().flush();
			response.getWriter().close();
			return false;
		}*/
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
