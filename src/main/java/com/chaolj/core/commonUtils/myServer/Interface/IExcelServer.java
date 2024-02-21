package com.chaolj.core.commonUtils.myServer.Interface;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*
 * Excel操作服务接口
 * */
public interface IExcelServer {
    <T> byte[] transfer(List<T> data, Class<T> clazz, String sheetName);

    default <T> byte[] transfer(List<T> data, Class<T> clazz) {
        return this.transfer(data, clazz, "Sheet1");
    }

    <T> void download(HttpServletResponse response, List<T> data, Class<T> clazz, String fileName, String sheetName);

    default <T> void download(HttpServletResponse response, List<T> data, Class<T> clazz, String fileName) {
        this.download(response, data, clazz, fileName, "Sheet1");
    }

    default <T> void download(HttpServletResponse response, List<T> data, Class<T> clazz) {
        this.download(response, data, clazz, clazz.getSimpleName(), "Sheet1");
    }
}
