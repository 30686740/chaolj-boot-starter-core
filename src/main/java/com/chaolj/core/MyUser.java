package com.chaolj.core;

import cn.hutool.core.util.StrUtil;
import com.chaolj.core.commonUtils.myServer.Models.TokenContextDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyUser {
    // region HttpTrack

    private static ThreadLocal<String> _currentHttpTrackId = ThreadLocal.withInitial(() -> "");

    public static String getCurrentHttpTrackId() {
        return _currentHttpTrackId.get();
    }

    public static void setCurrentHttpTrackId(String xid) {
        _currentHttpTrackId.set(xid);
    }

    public static void removeCurrentHttpTrackId() { _currentHttpTrackId.remove(); }

    // endregion

    // region GTTrack

    private static ThreadLocal<String> _currentGTTrackId = ThreadLocal.withInitial(() -> "");
    private static ThreadLocal<Boolean> _currentGTTrackFirst = ThreadLocal.withInitial(() -> false);

    public static String getCurrentGTTrackId() {
        return _currentGTTrackId.get();
    }

    public static void setCurrentGTTrackId(String xid) {
        _currentGTTrackId.set(xid);
    }

    public static Boolean getCurrentGTTrackFirst() {
        return _currentGTTrackFirst.get();
    }

    public static void setCurrentGTTrackFirst(Boolean value) {
        _currentGTTrackFirst.set(value);
    }

    // endregion

    // region Datalog

    private static ThreadLocal<com.chaolj.core.MyDatalog> _currentDatalog = ThreadLocal.withInitial(() -> null);

    public static com.chaolj.core.MyDatalog getCurrentDatalog() {
        if (_currentDatalog.get() != null) return _currentDatalog.get();

        initCurrentDatalog();
        return _currentDatalog.get();
    }

    public static void setCurrentDatalog(com.chaolj.core.MyDatalog log){
        _currentDatalog.set(log);
    }

    private static void initCurrentDatalog(){
        _currentDatalog.set(com.chaolj.core.MyDatalog.Create());
    }

    // endregion

    // region ClientToken

    private static ThreadLocal<String> _currentUserToken = ThreadLocal.withInitial(() -> "");
    private static ThreadLocal<String> _currentUserClient = ThreadLocal.withInitial(() -> "");
    private static ThreadLocal<String> _currentUserName = ThreadLocal.withInitial(() -> "");

    /*
     * 当前用户的ssotoken
     * */
    public static String getCurrentUserToken() {
        return _currentUserToken.get();
    }

    public static void setCurrentUserToken(String token) {
        _currentUserToken.set(token);
    }

    /*
     * 当前用户的租户编号
     * */
    public static String getCurrentUserClient() {
        return _currentUserClient.get();
    }

    public static void setCurrentUserClient(String userClient) {
        _currentUserClient.set(userClient);
    }

    /*
     * 当前用户的登录名
     * */
    public static String getCurrentUserName() {
        return _currentUserName.get();
    }

    public static void setCurrentUserName(String userName) {
        _currentUserName.set(userName);
    }

    // endregion

    // region ClientTokenContext

    private static ThreadLocal<TokenContextDto> _currentUser = ThreadLocal.withInitial(() -> null);

    /*
     * 当前用户的上下文
     * */
    public static TokenContextDto getCurrentUser(boolean init) {
        if (init && _currentUser.get() == null) {
            initCurrentUser();
        }

        return _currentUser.get();
    }

    public static TokenContextDto getCurrentUser() {
        return getCurrentUser(true);
    }

    public static void setCurrentUser(TokenContextDto user){
        _currentUser.set(user);
    }

    private static void initCurrentUser(){
        // 如果租户编号为空，返回默认上下文对象
        var client = _currentUserClient.get();
        if (StrUtil.isBlank(client)) {
            _currentUser.set(TokenContextDto.getDefault());
            return;
        }

        // 如果用户名为空，返回默认上下文对象
        var uname = _currentUserName.get();
        if (StrUtil.isBlank(uname)) {
            _currentUser.set(TokenContextDto.getDefault());
            return;
        }

        // 没有找到服务[TokenServer]的提供者，返回默认上下文对象
        var tokenServer = com.chaolj.core.MyApp.Server().TokenServer(true);
        if (tokenServer == null) {
            _currentUser.set(TokenContextDto.getDefault());
            log.info("MyUser.initCurrentUser，没有找到服务[TokenServer]的提供者，返回默认上下文对象！");
            return;
        }

        // 远程获取上下文对象
        var appno = com.chaolj.core.MyApp.Helper().ConfigHelper().getApplicationName();
        var myresult = tokenServer.GetTokenContextByUsername(client, uname, appno);
        if (myresult.isResult()) {
            _currentUser.set(myresult.getData());
            return;
        }

        // 获取失败，返回默认上下文对象，设置用户名
        var user = TokenContextDto.getDefault();
        user.setClientCode(client);
        user.setLoginName(uname);
        _currentUser.set(user);
    }

    // endregion

    public static void Remove(){
        _currentHttpTrackId.remove();
        _currentGTTrackId.remove();
        _currentGTTrackFirst.remove();
        _currentDatalog.remove();
        _currentUserToken.remove();
        _currentUserClient.remove();
        _currentUserName.remove();
        _currentUser.remove();
    }
}
