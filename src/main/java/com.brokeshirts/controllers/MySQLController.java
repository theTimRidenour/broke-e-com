package com.brokeshirts.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MySQLController {

  @RequestMapping(value="")
  public String index(Model model) {
    model.addAttribute("title", "Broke Shirts");
    return "index";
  }

  @RequestMapping(value="add")
    public String add(Model model) {
      model.addAttribute("title", "Add Product");
      return "add";
    }

}
