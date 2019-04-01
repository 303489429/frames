package com.frame.example.frames.redisson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

/**
 * @author wangzhilong
 * @date 2019-04-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@REntity
public class LedgerLiveObject {

    @RId
    private String name;

    private String address ;



}
