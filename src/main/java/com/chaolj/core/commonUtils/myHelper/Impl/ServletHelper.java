package com.chaolj.core.commonUtils.myHelper.Impl;

import com.chaolj.core.MyApp;
import com.chaolj.core.bootUtils.bootObject.AppRequestWrapper;
import com.chaolj.core.commonUtils.myHelper.MyHelperProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ServletHelper {
    private ApplicationContext applicationContext;
    private MyHelperProperties properties;

    public ServletHelper(ApplicationContext applicationContext, MyHelperProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    public String GetRequestIp(HttpServletRequest request) {
        var ipAddress = "";

        try {
            ipAddress = request.getHeader("x-forwarded-for");

            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }

            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }

            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }

            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }

        return ipAddress;
    }

    public String GetRequestBody(HttpServletRequest request) {
        var bodyContent = "";
        if (request == null) return bodyContent;

        if (request instanceof AppRequestWrapper) {
            var appRequestWrapper = (AppRequestWrapper)request;

            try {
                var bodyStream = appRequestWrapper.getInputStream();
                if (bodyStream == null) return bodyContent;

                var bodyBytes = bodyStream.readAllBytes();
                if (bodyBytes == null || bodyBytes.length <= 0) return bodyContent;

                bodyContent = new String(bodyBytes);
                bodyContent = bodyContent.replace("\n", "");
            } catch (Exception e) {
                bodyContent = "";
            }
        }

        return bodyContent;
    }

    public void SetResponseHeaderByDownload(HttpServletResponse response, String filename, String contentType) {
        try {
            response.reset();
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "utf-8"));
            response.setHeader("filename",  URLEncoder.encode(filename, "utf-8"));
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetResponseHeaderByDownload(HttpServletResponse response, String filename) {
        this.SetResponseHeaderByDownload(response, filename, "application/octet-stream;charset=utf-8");
    }

    public void WriteResponseOutputStream(HttpServletResponse response, String content) {
        try {
            response.getOutputStream().write(content.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void WriteResponseOutputStream(String content) {
        this.WriteResponseOutputStream(MyApp.WebContext().getResponse(), content);
    }

    public void WriteResponseOutputStream(HttpServletResponse response, byte[] content) {
        try {
            response.getOutputStream().write(content);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void WriteResponseOutputStream(byte[] content) {
        this.WriteResponseOutputStream(MyApp.WebContext().getResponse(), content);
    }

    public void WriteResponseOutputAttachment(HttpServletResponse response, byte[] content, String filename, String contentType) {
        try {
            response.reset();
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "utf-8"));
            response.setHeader("filename",  URLEncoder.encode(filename, "utf-8"));
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");

            response.getOutputStream().write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void WriteResponseOutputAttachment(byte[] content, String filename, String contentType) {
        this.WriteResponseOutputAttachment(MyApp.WebContext().getResponse(), content, filename, contentType);
    }

    public void WriteResponseOutputAttachment(HttpServletResponse response, byte[] content, String filename) {
        this.WriteResponseOutputAttachment(response, content, filename, "application/octet-stream;charset=utf-8");
    }

    public void WriteResponseOutputAttachment(byte[] content, String filename) {
        this.WriteResponseOutputAttachment(MyApp.WebContext().getResponse(), content, filename, "application/octet-stream;charset=utf-8");
    }

    public void WriteResponseOutputImg(HttpServletResponse response, byte[] content) {
        try {
            response.reset();
            response.setContentType("image/jpeg");
            response.getOutputStream().write(content);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void WriteResponseOutputImg(byte[] content) {
        this.WriteResponseOutputImg(MyApp.WebContext().getResponse(), content);
    }
}
