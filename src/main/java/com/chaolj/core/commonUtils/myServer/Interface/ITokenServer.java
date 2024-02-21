package com.chaolj.core.commonUtils.myServer.Interface;

import com.chaolj.core.commonUtils.myDto.DataResultDto;
import com.chaolj.core.commonUtils.myServer.Models.TokenContextDto;

import java.util.List;

/*
 * 用户认证服务接口
 * */
public interface ITokenServer {
    /*
     * 获取指定用户的token
     * days <= 0，有效期30s
     * days > 0，有效期为 从现在开始计数，往后数第{days}个凌晨5点
     * */
    String EncryptToken(String client, String uname, Integer days);

    /*
     * 获取指定用户的token
     * 有效期30s
     * */
    default String EncryptToken(String client, String uname) {
        return this.EncryptToken(client, uname, 0);
    }

    /*
     * 解密用户的token
     * oneday = false，超过有效期则报错
     * oneday = true，宽限到有效期后的第一个凌晨5点
     * */
    List<String> DecryptToken(String tokenValue, boolean oneday);

    /*
     * 解密用户的token
     * 超过有效期则报错
     * */
    default List<String> DecryptToken(String tokenValue) {
        return this.DecryptToken(tokenValue, false);
    }

    DataResultDto<String> GetToken(String client, String username, String password);

    /*
     * 通过token获取用户上下文
     * */
    DataResultDto<TokenContextDto> GetTokenContext(String ssotoken, String appno);

    /*
     * 通过用户名获取用户上下文
     * */
    DataResultDto<TokenContextDto> GetTokenContextByUsername(String client, String username, String appno);
}
