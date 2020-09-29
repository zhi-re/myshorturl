package org.zhire.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zhire.service.ShortUrlService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ShortUrlGetController {

    @Autowired
    private ShortUrlService shortUrlService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/{value}")
    public void get(@PathVariable String value, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect(shortUrlService.findFirstByRandomStr(value));
    }


}
