package org.zhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.zhire.dao.ShortUrl;
import org.zhire.dao.WbPhone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Iterator;
import java.util.Optional;

public interface WbPhoneRepository extends JpaRepository<WbPhone, Long> {

    Optional<WbPhone> findFirstByQq(String qq);

}

