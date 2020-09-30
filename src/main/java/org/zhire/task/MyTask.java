package org.zhire.task;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zhire.Interceptor.MyInterceptor;

/**
 * 定时任务，每天零点清除黑名单IP
 */
@Component
@Slf4j
public class MyTask {

    @Scheduled(cron = "0 0 0 * * ?")
    //@Scheduled(cron = "0 19 15 ? * *")
    public void clearMap() {
        log.info("清空之前的map:{}", JSON.toJSONString(MyInterceptor.map));
        MyInterceptor.map.clear();
        log.info("清空之后的map:{}", JSON.toJSONString(MyInterceptor.map));
    }

}
