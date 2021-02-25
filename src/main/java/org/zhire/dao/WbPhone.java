package org.zhire.dao;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "wb", indexes = {
        @Index(name = "my_qq", columnList = "qq"),
        @Index(name = "my_phone", columnList = "phone")})
@Entity
@Data
@DynamicInsert(true)
@DynamicUpdate(true)
public class WbPhone implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id")
    private Long id;

    @Column(name = "qq", nullable = true, columnDefinition = "varchar(255) default ''" + " COMMENT 'qq'")
    private String qq;

    @Column(name = "phone", nullable = true, columnDefinition = "varchar(255) default ''" + " COMMENT 'phone'")
    private String phone;


}
