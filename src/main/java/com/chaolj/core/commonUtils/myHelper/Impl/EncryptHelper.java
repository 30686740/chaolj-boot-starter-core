package com.chaolj.core.commonUtils.myHelper.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.chaolj.core.commonUtils.myDto.TokenException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.ApplicationContext;
import com.chaolj.core.commonUtils.myHelper.MyHelperProperties;

public class EncryptHelper {
    private ApplicationContext applicationContext;
    private MyHelperProperties properties;

    public EncryptHelper(ApplicationContext applicationContext, MyHelperProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    private final String KEY_ALGORITHM = "DESede";
    private final String DEFAULT_CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";// 默认的加密算法

    private byte[] makeMD5(String key) {
        byte[] temp = SecureUtil.md5().digest(key.getBytes(StandardCharsets.UTF_8));
        byte[] keyByteMd5 = new byte[24];
        System.arraycopy(temp, 0, keyByteMd5, 0, 16);
        System.arraycopy(temp, 0, keyByteMd5, 16, 8);
        return keyByteMd5;
    }

    public String encrypt(String content, String customerKey) {
        try {
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(this.DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(this.makeMD5(customerKey), this.KEY_ALGORITHM));
            // 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(byteContent);// 加密
            return Base64.encodeBase64String(result);// 通过Base64转码返回
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String decrypt(String content, String customerKey) {
        try {
            // 实例化
            Cipher cipher = Cipher.getInstance(this.DEFAULT_CIPHER_ALGORITHM); // 使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(this.makeMD5(customerKey), this.KEY_ALGORITHM)); // 执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            // do nothing
        }
        return null;
    }

    public String EncryptToken(String client, String uname, Integer days, String key) {
        if (StrUtil.isBlank(client)) throw new RuntimeException("创建凭据失败, 租户编号为空！");
        if (StrUtil.isBlank(uname)) throw new RuntimeException("创建凭据失败, 用户名为空！");

        var value = "";
        var tokenTime = LocalDateTime.now().plusSeconds(30);

        if (days > 0) {
            var now = LocalDateTime.now();
            var t1 = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 5, 0, 0);

            if (t1.isBefore(now)) t1 = t1.plusDays(1);
            tokenTime = t1;

            if (days > 1) tokenTime = t1.plusDays(days - 1);
        }

        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        value = dateTimeFormatter.format(tokenTime) + ";;" + uname + "@" + client;
        value = cn.hutool.core.codec.Base64.encode(value);
        value = this.encrypt(value, key);

        return value;
    }

    public List<String> DecryptToken(String tokenValue, boolean oneday, String key) {
        if (StrUtil.isBlank(tokenValue)) throw new TokenException("用户凭据为空！");

        var value = "";

        try {
            value = this.decrypt(tokenValue, key);
            value = cn.hutool.core.codec.Base64.decodeStr(value);
        } catch (Exception e) {
            throw new TokenException("用户凭据无效！");
        }

        if (StrUtil.isBlank(value)) throw new TokenException("用户凭据无效！");
        if (!value.contains(";;")) throw new TokenException("用户凭据格式无效！");
        if (!value.contains("@")) throw new TokenException("用户凭据格式不合法！");

        var tokenTimeString = StrUtil.subBefore(value, ";;", false);
        var tokenTime = DateUtil.parse(tokenTimeString).toLocalDateTime();
        if (oneday) {
            var t1 = LocalDateTime.of(tokenTime.getYear(), tokenTime.getMonth(), tokenTime.getDayOfMonth(), 5, 0, 0);
            if (t1.isBefore(tokenTime)) t1 = t1.plusDays(1);

            tokenTime = t1;
        }

        if (tokenTime.isBefore(LocalDateTime.now())) throw new TokenException("用户凭据已过期！");

        var valueString = StrUtil.subAfter(value, ";;", false);
        return StrUtil.split(valueString, "@");
    }
}
