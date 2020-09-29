package org.zhire.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;
import org.zhire.service.ShortUrlService;
import org.zhire.utils.R;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

@Slf4j
@RestController
@RequestMapping("/url")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @RequestMapping("/save")
    public Map save(@RequestParam String oldUrl, HttpServletRequest request) {
        log.info("请求参数为：{}", oldUrl);
        if (StringUtils.isEmpty(oldUrl.trim())) {
            throw new RuntimeException("地址不能为空！");
        } else {
            Pattern pattern = compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
            if (!pattern.matcher(oldUrl).matches()) {
                throw new RuntimeException("请输入正确的网址！");
            }
        }
        return R.ok(shortUrlService.save(oldUrl, request));
    }


}
