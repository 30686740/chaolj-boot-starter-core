package com.chaolj.core.commonUtils.myServer.Interface;

import java.util.List;
import com.chaolj.core.commonUtils.myDto.DataResultDto;
import com.chaolj.core.commonUtils.myServer.Models.GlobalOptionDto;

/*
 * 全局服务接口
 * */
public interface IGlobalServer {
    DataResultDto<String> GlobalParameter(String name);

    DataResultDto<List<GlobalOptionDto>> GlobalOptions(String catalog);

    DataResultDto<String> GlobalSN(String name);
}
