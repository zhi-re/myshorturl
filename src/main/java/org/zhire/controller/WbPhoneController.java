package org.zhire.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zhire.dao.WbPhone;
import org.zhire.service.WbPhoneService;
import org.zhire.utils.R;

import java.util.concurrent.CompletableFuture;


@Slf4j
@RestController
@RequestMapping("/wb")
public class WbPhoneController {

    @Autowired
    private WbPhoneService wbPhoneService;

    @RequestMapping("/findByQq")
    public R findByQq(@RequestParam String qq) {
        log.info("请求参数为：{}", qq);
        WbPhone wbPhone = wbPhoneService.findByQq(qq);
        return R.ok().put("wb", wbPhone);
    }


    @RequestMapping("/handle")
    public R handle() {
        // new Thread(() -> wbPhoneService.handle()).start();
        return R.ok();
    }


}
