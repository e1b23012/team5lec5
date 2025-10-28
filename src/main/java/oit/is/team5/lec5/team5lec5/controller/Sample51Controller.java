package oit.is.team5.lec5.team5lec5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sample51Controller {
  @GetMapping("/sample5/step1")
  public String sample51() {
    return "sample51.html";
  }
}
