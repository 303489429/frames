package com.frame.example.frames.redisson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangzhilong
 * @date 2019-04-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomMessage {

    private Integer id ;
    private String name ;
    private Integer age ;

}
