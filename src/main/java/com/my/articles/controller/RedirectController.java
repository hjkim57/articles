package com.my.articles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/mapping")
public class RedirectController {
    @GetMapping("")
    public String testMain() {
        return "/test/testMain";
    }

    @GetMapping("page")
    public String view(Model model) {
        return "/test/page";
    }

    @RequestMapping(value = "requestMapping", method = RequestMethod.GET)
    public String requestMapping(Model model) {
        String msg = "requestMapping";
        model.addAttribute("msg", msg);
//        return "redirect:page"; // 메시지 전달X
        return "/test/page"; // 메시지 전달O
    }

    @GetMapping("modelAndView")
    public ModelAndView modelAndView(Model model) {
        String msg = "ModelAndView";
        model.addAttribute("msg", msg);
//        return new ModelAndView("/test/page"); // 메시지 전달O
        return new ModelAndView("redirect:page"); // 메시지 전달X
    }

    @GetMapping("redirectView")
    public RedirectView redirectView(RedirectAttributes redirectAttributes) {
        String msg = "redirectView";
//        redirectAttributes.addAttribute("msg", msg); // 메시지 전닱X
        redirectAttributes.addFlashAttribute("msg", msg); // 메시지 전달O
        return new RedirectView("page");
    }

    @GetMapping("redirectView2")
    public String redirectView2(RedirectAttributes redirectAttributes) {
        String msg = "redirectView2";
        redirectAttributes.addFlashAttribute("msg", msg); // 메시지 전달O
        return "redirect:page"; // 메시지 전달O
    }

    @GetMapping("redirect_1")
    public String redirect_1() {
        return "redirect:http://naver.com";
    }

    @GetMapping("redirect_2")
    public ModelAndView redirect_2() {
//        String url = "redirect:http://www.daum.net";
        String url = "redirect:http://localhost:8081/main";
        return new ModelAndView(url);
    }
}
