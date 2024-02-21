package com.chaolj.core.commonUtils.myHelper.Impl;

import java.util.Date;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.context.ApplicationContext;
import com.chaolj.core.commonUtils.myHelper.MyHelperProperties;

public class GuidHelper {
    private ApplicationContext applicationContext;
    private MyHelperProperties properties;

    public GuidHelper(ApplicationContext applicationContext, MyHelperProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    private String timelast = "";
    private Integer count = 0;

    public String NewID(){
        var nowmask = "";
        var countmask = "";
        var randommask = "";

        synchronized (GuidHelper.class){
            nowmask = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");

            if (this.timelast.equals(nowmask))
            {
                this.count++;
            }
            else
            {
                this.count = 0;
            }

            this.timelast = nowmask;

            countmask = String.valueOf(this.count);
            countmask = StrUtil.padPre(countmask, 3, "000");

            randommask = IdUtil.getSnowflake(1,1).nextIdStr();
            randommask = StrUtil.padPre(randommask, 12, "000000000000");
        }

        var stringBuilder = new StringBuilder(nowmask + countmask + randommask);
        return stringBuilder.insert(8, "-").insert(13,"-").insert(18,"-").insert(23,"-").toString();
    }
}
