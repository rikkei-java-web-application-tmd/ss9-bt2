package com.re.cookie.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(@CookieValue(value = "guest_name", defaultValue = "Khách lạ") String guestName, Model model) {

        if ("Khách lạ".equals(guestName)) {
            model.addAttribute("msg", "Chào khách lạ!");
        } else {
            model.addAttribute("msg", "Chào mừng " + guestName + " trở lại!");
        }

        return "home-page";
    }

    @PostMapping("/buy")
    public String buyProduct(@RequestParam("name") String name, HttpServletResponse response) {
        Cookie cookie = new Cookie("guest_name", name);
        cookie.setMaxAge(7 * 24 * 60 * 60); // Sống 7 ngày
        cookie.setPath("/"); // Có tác dụng trên toàn bộ web
        cookie.setHttpOnly(true); // Bảo mật chống XSS

        response.addCookie(cookie);

        return "redirect:/home";
    }
}