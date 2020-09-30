package org.zhire.Interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zhire.dao.IpDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static cn.hutool.core.util.ObjectUtil.isNotNull;
import static java.util.Objects.isNull;

@Slf4j
public class MyInterceptor implements HandlerInterceptor {

    /**
     *  基于内存的黑名单map，
     *  同一个IP一分钟内请求接口超过60次拉黑，
     *  第二天可以正常访问，
     *  明天零点定时清除map。
     *  （暂时这么搞，先不做持久化。）
     */
    public static final Map<Object, IpDto> map = new ConcurrentHashMap<>();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // String key = request.getHeader("X-real-ip");
        String key = request.getRemoteAddr();
        log.info("请求IP：{}", key);
        log.info("请求路径：{}", request.getServletPath());
        IpDto ipDto = map.get(key);
        if (isNotNull(ipDto) &&
                isNotNull(ipDto.getLtime()) &&
                ipDto.getLtime() - ipDto.getFtime() < 60000 &&
                ipDto.getCount() > 60) {
            System.out.println("毫秒：" + (ipDto.getLtime() - ipDto.getFtime()));
            return false;
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // String key = request.getHeader("X-real-ip");
        String key = request.getRemoteAddr();
        IpDto ipDto = map.get(key);
        IpDto dto = new IpDto();
        dto.setCount(isNull(ipDto) ? 1 : ipDto.getCount() + 1);
        if (isNull(ipDto)) {
            dto.setFtime(System.currentTimeMillis());
        } else {
            dto.setLtime(System.currentTimeMillis());
            dto.setFtime(ipDto.getFtime());
        }
        map.put(key, dto);
    }


}
