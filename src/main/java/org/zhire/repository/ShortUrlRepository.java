package org.zhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zhire.dao.ShortUrl;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findFirstByOldUrl(String oldUrl);

    Optional<ShortUrl> findFirstByRandomStr(String s);
}

