package com.caiwen.atp.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author caiwen
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new HandlerInterceptorAdapter() {
      @Override
      public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
          Object handler) throws Exception {
        // 静态资源请求直接放行
        if (request.getServletPath().startsWith("/static")) {
          return true;
        } else if (hasLogin(request)) {
          if (request.getServletPath().startsWith("/login")) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
          }
          return true;

        } else {
          if (request.getServletPath().startsWith("/login")) {
            return true;
          } else {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login"));
            return false;
          }

        }
      }
    });
  }

  private boolean hasLogin(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return false;
    }
    Optional<Cookie> un = Arrays.stream(cookies).filter(cookie -> "un".equals(cookie.getName()))
        .findAny();
    Optional<Cookie> key = Arrays.stream(cookies).filter(cookie -> "key".equals(cookie.getName()))
        .findAny();

    if (un.isPresent() && key.isPresent()
        && key.get().getValue()
        .equals(Md5Crypt.md5Crypt(un.get().getValue().getBytes(), "$1$" + un.get().getValue()))) {
      return true;
    } else {
      return false;
    }
  }
}
