package org.zhire.service;


import org.zhire.dao.WbPhone;


public interface WbPhoneService {
    WbPhone findByQq(String qq);

    void handle();
}
