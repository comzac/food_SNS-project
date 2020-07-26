package com.ssafy.sub.dto;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@RedisHash
@NoArgsConstructor
@AllArgsConstructor	
public class Token implements Serializable {

    private static final long serialVersionUID = -7353484588260422449L;
    private String username;
    private String refreshToken;

}