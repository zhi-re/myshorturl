package org.zhire.service;

import javax.servlet.http.HttpServletRequest;

public interface ShortUrlService {
    String save(String oldUrl, HttpServletRequest request);

    String findFirstByRandomStr(String s);
}
