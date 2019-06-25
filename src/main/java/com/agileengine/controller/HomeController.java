package com.agileengine.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@CrossOrigin
public class HomeController {

    @RequestMapping("/")
    @ResponseBody
    String root() {
        return "Visit http://localhost:8080/swagger-ui.html/";
    }

}
