package org.zhire.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zhire.dao.WbPhone;
import org.zhire.repository.WbPhoneRepository;
import org.zhire.service.WbPhoneService;
import org.zhire.utils.MySaveAll;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WbPhoneServiceImpl implements WbPhoneService {

    @Autowired
    private WbPhoneRepository wbPhoneRepository;

    @Override
    public WbPhone findByQq(String qq) {
        Optional<WbPhone> wb = wbPhoneRepository.findFirstByQq(qq);
        if (wb.isPresent()) {
            WbPhone phone = wb.get();
            String s = phone.getPhone();
            String phone2 = s.substring(0, 3) + "****" + s.substring(7);
            phone.setPhone(phone2);
            return phone;
        }
        return null;
    }

    @Autowired
    private MySaveAll mySaveAll;

    private static Snowflake snowflake = IdUtil.getSnowflake(1, 1);


    @Override
    public void handle() {
        try {
            int i = 0;
            BufferedReader br = new BufferedReader(new FileReader("/users/admin/downloads/xx/xx.txt"));
            String line;
            Map<String, String> map = new HashMap<>(10000);
            for (int j = 0; j < 24178000; j++) {
                br.readLine();
            }
            log.info("读取完成");
            while ((line = br.readLine()) != null) {
                String s = line.replace("----", ",");
                List<String> list = Arrays.stream(s.split(",")).collect(Collectors.toList());
                map.put(list.get(0), list.get(1));
                if (map.size() == 10000) {
                    ArrayList<WbPhone> arrayList = new ArrayList<>();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        String k = entry.getKey();
                        String v = entry.getValue();
                        WbPhone phone = new WbPhone();
                        phone.setId(snowflake.nextId());
                        phone.setQq(k);
                        phone.setPhone(v);
                        arrayList.add(phone);
                        //wbPhoneRepository.save(phone);
                    }
                    mySaveAll.saveAll(arrayList);
                    //wbPhoneRepository.saveAll(arrayList);
                    map.clear();
                    arrayList.clear();
                    i++;
                    log.info("已经导入数据：{}", i * 10000);
                }
            }//100000000  100000
            log.info("全部导入完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
