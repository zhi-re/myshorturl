package org.zhire.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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

    @RequestMapping("/{value}/{v2}")
    public String get(@PathVariable String value,
                      @PathVariable String v2,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        String key = request.getHeader("X-real-ip");
        log.info("请求IP：{}", key);
        log.info("请求路径：{}", request.getServletPath());
        System.out.println(value + " " + v2);
        String url = shortUrlService.findFirstByRandomStr(value);
        if (StringUtils.isEmpty(url)) {
            return "404";
        }
        return "redirect:" + url;

    }


}
