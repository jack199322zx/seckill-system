package com.seckill.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ss
 * @Date 2018/7/6 17:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeckillSuccess {

    private String seckill_id;
    private String user_id;
    private int state;

}
