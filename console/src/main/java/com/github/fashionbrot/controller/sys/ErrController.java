package com.github.fashionbrot.controller.sys;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrController implements ErrorController {



    @RequestMapping("/401")
    public String unauthorized(HttpServletRequest request, String requestUrl){
        request.setAttribute("requestUrl",requestUrl);
        return "error/unauth";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 500){
            return "/error/500";
        }else if(statusCode == 404){
            return "/error/404";
        }else{
            return "/500";
        }

    }


    @Override
    public String getErrorPath() {
        System.out.println("--------------------------------------------");
        return null;
    }
}
