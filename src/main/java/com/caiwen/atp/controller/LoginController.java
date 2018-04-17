package com.caiwen.atp.controller;

import com.caiwen.atp.entity.User;

import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author caiwen
 */
@Controller
@RequestMapping
public class LoginController {

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login() {
    return "login";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String doLogin(HttpServletRequest request, HttpServletResponse response, User user,
      RedirectAttributes model) {
    Cookie un = new Cookie("un", user.getUsername());
    un.setHttpOnly(true);
    un.setMaxAge(86400);
    response.addCookie(un);
    Cookie key = new Cookie("key",
        Md5Crypt.md5Crypt(un.getValue().getBytes(), "$1$" + un.getValue()));
    key.setMaxAge(86400);
    key.setHttpOnly(true);
    response.addCookie(key);

    model.addFlashAttribute("user", un.getValue());
    return "redirect:dashboard";
  }

  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    Arrays.stream(request.getCookies()).forEach(cookie -> {
          cookie.setMaxAge(0);
          response.addCookie(cookie);
        }
    );
    return "redirect:login";
  }

  @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
  public String dashboard(HttpServletRequest request, Model model) {
    Arrays.stream(request.getCookies()).filter(cookie -> "un".equals(cookie.getName())).findAny()
        .ifPresent(
            cookie -> model.addAttribute("username", cookie.getValue())
        );
    return "dashboard";
  }
}
