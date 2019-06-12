package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.models.User;
import com.brokeshirts.ecom.service.SecurityService;
import com.brokeshirts.ecom.service.UserService;
import com.brokeshirts.ecom.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public String registration(Model model) {

        model.addAttribute("userForm", new User());

        return "user/registration";
    }

    @RequestMapping(value="/registration", method = RequestMethod.POST)
    public String registration(@Valid User userForm, BindingResult bindingResult) {

        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username or password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully!");

        model.addAttribute("title", "LOGIN");

        return "user/login";
    }

}
