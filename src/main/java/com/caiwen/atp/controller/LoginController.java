package com.caiwen.atp.controller;

import com.caiwen.atp.entity.User;

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
  public String doLogin(User user, RedirectAttributes model) {
    model.addFlashAttribute(user);
    return "redirect:dashboard";
  }

  @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
  public String dashboard(User user, Model model) {
    if (!model.containsAttribute("user")) {
      model.addAttribute(user);
    }
    return "dashboard";
  }
}
