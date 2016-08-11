package com.jztey.framework.mvc;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class PermissionInterceptor extends HandlerInterceptorAdapter {
	public interface AuthenticationManager {
		boolean isLogin(String token);

		boolean isPermission(String token, String permission);
	}

	public static final String TOKEN_KEY = "TOKEN";
	public static final String NOT_PERMISSION_METHOD = "NPM";
	private ConcurrentHashMap<Method, String> methodPermissionCache = new ConcurrentHashMap<>();

	@Inject
	private AuthenticationManager authenticationManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;

			String permission = methodPermissionCache.get(method.getMethod());
			if (null == permission) {
				Permission classPermission = AnnotatedElementUtils.findMergedAnnotation(method.getBeanType(),
						Permission.class);
				Permission methodPermission = AnnotatedElementUtils.findMergedAnnotation(method.getMethod(),
						Permission.class);

				if (classPermission == null && methodPermission == null) { // class
																			// method
																			// 均无
																			// Permission
																			// 注解
					permission = NOT_PERMISSION_METHOD;
				} else {
					permission = "";
					if (null != classPermission) {
						permission += classPermission.value();
					}
					if (null != methodPermission) {
						if (Permission.IGNORE.equals(methodPermission.value())) {
							permission = NOT_PERMISSION_METHOD;
						} else {
							permission += methodPermission.value();
						}
					}
				}

				methodPermissionCache.put(method.getMethod(), permission);
			}

			if (!NOT_PERMISSION_METHOD.equals(permission)) {
				Cookie tokenCookie = WebUtils.getCookie(request, TOKEN_KEY);
				String token = null;
				if (null != tokenCookie) {
					token = tokenCookie.getValue();
				}
				if (null == token) {
					token = request.getParameter(TOKEN_KEY);
				}

				if (!authenticationManager.isLogin(token)) {
					response.sendError(HttpStatus.UNAUTHORIZED.value(), "请重新登录");
					return false;
				}

				if (!StringUtils.isEmpty(permission) && !authenticationManager.isPermission(token, permission)) {
					response.sendError(HttpStatus.UNAUTHORIZED.value());
					return false;
				}
			}
		}
		return true;
	}
}
