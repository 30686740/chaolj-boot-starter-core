package com.chaolj.core.bootUtils.bootInterceptor;

import cn.hutool.core.util.StrUtil;
import com.chaolj.core.MyApp;
import com.chaolj.core.MyConst;
import com.chaolj.core.MyUser;
import com.chaolj.core.bootUtils.bootAnnotation.HttpTrackIgnore;
import com.chaolj.core.bootUtils.bootAnnotation.HttpTrackLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 控制器拦截
// Http请求跟踪
@Slf4j
public class HttpTrackInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        this.preHandle_HttpTrack(request, response, handler);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        this.afterCompletion_HttpTrack(request, response, handler, ex);
    }

    // region HttpTrack

    // 检查是否具有注解
    private Boolean IsHttpTrackIgnore(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) return false;

        var handlerMethod = (HandlerMethod) handler;
        var annotation = handlerMethod.getMethodAnnotation(HttpTrackIgnore.class);
        return annotation != null;
    }

    // 检查是否具有注解
    private Boolean IsHttpTrackLog(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) return false;

        var handlerMethod = (HandlerMethod) handler;
        var annotation = handlerMethod.getMethodAnnotation(HttpTrackLog.class);
        return annotation != null;
    }

    // 请求前处理
    private void preHandle_HttpTrack(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 有注解，@HttpTrackIgnore
        // 忽略，不做跟踪
        if (this.IsHttpTrackIgnore(request, response, handler)) return;

        // 初始化httpTrackId
        var httpTrackId = request.getHeader(MyConst.HEADERKEY_HttpTrackID);
        if (StrUtil.isBlank(httpTrackId)) httpTrackId = MyApp.Helper().GuidHelper().NewID();
        MyUser.setCurrentHttpTrackId(httpTrackId);

        // 记录访问日志（请求开始）
        if (this.IsHttpTrackLog(request, response, handler)) {
            MyApp.LogAccess().From("HttpTrackInterceptor").Body(request).Push();
        }
    }

    // 请求后处理
    private void afterCompletion_HttpTrack(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        // 有注解，@HttpTrackIgnore
        // 忽略，不做跟踪
        //if (this.IsHttpTrackIgnore(request, response, handler)) return;

        // httpTrackId为空，直接退出
        //var httpTrackId = MyUser.getCurrentHttpTrackId();
        //if (StrUtil.isBlank(httpTrackId)) return;

        // 记录访问日志（请求结束）

        // 移除httpTrackId
        MyUser.removeCurrentHttpTrackId();
    }

    // endregion
}