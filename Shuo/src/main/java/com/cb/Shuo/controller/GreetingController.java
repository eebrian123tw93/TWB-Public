package com.cb.Shuo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  @GetMapping("/greeting")
  public String greeting(
      @RequestParam(name = "name", required = false, defaultValue = "World") String name) {
    return "hello, " + name;
  }

  @GetMapping("/test")
  public String test() {
    return "test";
  }

  @GetMapping("/goodluck")
  public String goodluck() {
    return "goodluck";
  }

  @GetMapping("/bryan")
  public String bryan() {
    return "789";
  }

}
