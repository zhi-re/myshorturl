package org.zhire.utils;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.zhire.dao.WbPhone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Date 2020/11/27 14:05
 * @Author by chenqi
 */
@Repository
@Transactional(readOnly = true)
public class MySaveAll {

    @PersistenceContext()
    protected EntityManager em;

    @Transactional
    public List<WbPhone> saveAll(Iterable<WbPhone> entities) {
        List<WbPhone> result = new ArrayList<WbPhone>();
        for (WbPhone entity : entities) {
            em.persist(entity);
            result.add(entity);
        }
        return result;
    }



}
