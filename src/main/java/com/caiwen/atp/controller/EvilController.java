package com.caiwen.atp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author caiwen
 */
@Controller
public class EvilController {

  @RequestMapping(value = "/evil", method = RequestMethod.GET)
  public String evil() {
    return "evil";
  }

  @RequestMapping(value = "/evilPost", method = RequestMethod.GET)
  public String evilPost() {
    return "evilPost";
  }
}
