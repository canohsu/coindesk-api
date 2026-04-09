package com.example.coindesk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "CURRENCY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE", nullable = false, unique = true, length = 10)
    private String code;

    @Column(name = "CHINESE_NAME", nullable = false, length = 100)
    private String chineseName;

    @Column(name = "CREATE_TIME")
    private String createTime;

    @Column(name = "UPDATE_TIME")
    private String updateTime;

}
