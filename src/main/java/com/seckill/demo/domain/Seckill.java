package com.seckill.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author ss
 * @Date 2018/7/6 17:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seckill {

    private String seckill_id;
    private String name;
    private int number;
    private Date start_time;
    private Date end_time;
    private Timestamp create_time;
    private int version;

}
