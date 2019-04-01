package com.frame.example.frames.redisson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wangzhilong
 * @date 2019-03-30
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

    private String name ;
    private int age ;
    private String address ;

}
