package org.zhire.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import org.zhire.dao.ShortUrl;
import org.zhire.repository.ShortUrlRepository;
import org.zhire.service.ShortUrlService;
import org.zhire.utils.NumberChangeUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${url.start}")
    private String startUrl;

    // 根据旧rul获取新url
    private static final String hashKey = "urlKey";

    // 根据生成短字符串获取旧链接
    private static final String getOldUrlKey = "oldUrlKey";

    // 获取自增值key
    private static final String shortUrlKey = "shorturl";


    /**
     * 保存
     *
     * @param oldUrl
     * @return
     */
    @Override
    public String save(String oldUrl, HttpServletRequest request) {
        if (StringUtils.isEmpty(getInRedis(oldUrl))) {
            // 获取Redis存储的自增值
            String shortUrlIndex = getShortUrlIndex();
            String result = startUrl + shortUrlIndex;
            // 入redis
            supplyAsync(() -> saveInRedis(oldUrl, result, shortUrlIndex));
            // 入mysql
            ShortUrl save = saveInMysql(result, oldUrl, shortUrlIndex, request.getHeader("X-real-ip"));
            log.info("保存结果:{}", JSON.toJSONString(save));
            return save.getNewUrl();
        } else {
            return getInRedis(oldUrl);
        }
    }

    /**
     * 获取Redis存储的自增值
     *
     * @return
     */
    private String getShortUrlIndex() {
        String shortUrlIndex = stringRedisTemplate.opsForValue().get(shortUrlKey);
        log.info("获取Redis存储的自增值:{}", shortUrlIndex);
        if (StringUtils.isEmpty(shortUrlIndex)) {
            shortUrlIndex = "1";
            stringRedisTemplate.opsForValue().set(shortUrlKey, shortUrlIndex);
        } else {
            shortUrlIndex = NumberChangeUtils.getNextNum(shortUrlIndex);
            stringRedisTemplate.opsForValue().set(shortUrlKey, shortUrlIndex);
        }
        return shortUrlIndex;
    }


    /**
     * 保存mysql
     *
     * @param result
     * @param oldUrl
     * @param shortUrlIndex
     * @param ip
     * @return
     */
    private ShortUrl saveInMysql(String result, String oldUrl, String shortUrlIndex, String ip) {
        ShortUrl u = new ShortUrl();
        u.setOldUrl(oldUrl);
        u.setNewUrl(result);
        u.setIp(ip);
        u.setRandomStr(shortUrlIndex);
        log.info("保存到MySQL入参:{}", JSON.toJSONString(u));
        return shortUrlRepository.save(u);
    }


    /**
     * 保存Redis
     *
     * @param oldUrl
     * @param result
     * @param shortUrlIndex
     * @return
     */
    private boolean saveInRedis(String oldUrl, String result, String shortUrlIndex) {
        String hex = SecureUtil.md5().digestHex(oldUrl);
        // String digestHex = SecureUtil.md5().digestHex(shortUrlIndex);
        redisTemplate.boundHashOps(hashKey).put(hex, result);
        // redisTemplate.boundHashOps(getOldUrlKey).put(digestHex, oldUrl);
        return true;
    }


    /**
     * 从Redis获取
     *
     * @param oldUrl
     * @return
     */
    private String getInRedis(String oldUrl) {
        String hex = SecureUtil.md5().digestHex(oldUrl);
        Object obj = redisTemplate.boundHashOps(hashKey).get(hex);
        if (Objects.isNull(obj)) {
            return null;
        }
        return obj.toString();
    }


    @Override
    public String findFirstByRandomStr(String s) {
        // 从Redis获取
        // String obj = (String) redisTemplate.boundHashOps(getOldUrlKey).get(SecureUtil.md5().digestHex(s));
        // 从mysql获取
        Optional<ShortUrl> u = shortUrlRepository.findFirstByRandomStr(s);
        return u.map(ShortUrl::getOldUrl).orElse(null);
    }

}
