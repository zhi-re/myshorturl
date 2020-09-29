package org.zhire.dao;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "url",indexes = {
        @Index(name = "u_random_str", columnList = "random_str")})
@Entity
@Data
@DynamicInsert(true)
@DynamicUpdate(true)
public class ShortUrl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "old_url", nullable = true, columnDefinition = "varchar(255) default ''" + " COMMENT '旧网址'")
    private String oldUrl;

    @Column(name = "new_url", nullable = true, columnDefinition = "varchar(255) default ''" + " COMMENT '新网址'")
    private String newUrl;

    @Column(name = "random_str", nullable = true, columnDefinition = "varchar(255) default ''" + " COMMENT '生成的随机串'")
    private String randomStr;

    @Column(name = "ip", nullable = true, columnDefinition = "varchar(255) default ''" + " COMMENT 'ip'")
    private String ip;

    @CreatedDate
    @Column(name = "ctime", nullable = true, columnDefinition = "bigint default 0")
    private Long ctime;






}
