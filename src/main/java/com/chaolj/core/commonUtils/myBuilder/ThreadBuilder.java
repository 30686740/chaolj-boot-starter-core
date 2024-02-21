package com.chaolj.core.commonUtils.myBuilder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import com.chaolj.core.MyDatalog;
import com.chaolj.core.MyUser;
import com.chaolj.core.commonUtils.myDelegate.ActionDelegate;
import com.chaolj.core.commonUtils.myDelegate.ActionDelegate1;
import com.chaolj.core.commonUtils.myServer.Models.TokenContextDto;

public class ThreadBuilder {
    private ArrayList<Exception> exceptions;
    private ArrayList<ActionDelegate> runnables;
    private ActionDelegate1<ArrayList<Exception>> callbackCommand;

    private String Local_currentHttpTrackId;
    private String Local_currentGTTrackId;
    private Boolean Local_currentGTTrackFirst;
    private String Local_currentSSOToken;
    private String Local_currentUserClient;
    private String Local_currentUserName;
    private TokenContextDto Local_currentUser;
    private MyDatalog Local_currentDatalog;

    public static ThreadBuilder Builder() {
        var myThread = new ThreadBuilder();
        myThread.exceptions = new ArrayList<>();
        myThread.runnables = new ArrayList<>();
        myThread.callbackCommand = null;
        return myThread;
    }

    private ThreadBuilder() {}

    public ThreadBuilder Join(@NotNull ActionDelegate command) {
        this.runnables.add(command);
        return this;
    }

    public ThreadBuilder Callback(@NotNull ActionDelegate1<ArrayList<Exception>> command){
        this.callbackCommand = command;
        return this;
    }

    public ThreadBuilder CloneThreadLocal() {
        this.Local_currentHttpTrackId = MyUser.getCurrentHttpTrackId();
        this.Local_currentGTTrackId = MyUser.getCurrentGTTrackId();
        this.Local_currentGTTrackFirst = MyUser.getCurrentGTTrackFirst();
        this.Local_currentSSOToken = MyUser.getCurrentUserToken();
        this.Local_currentUserClient = MyUser.getCurrentUserClient();
        this.Local_currentUserName = MyUser.getCurrentUserName();
        this.Local_currentUser = MyUser.getCurrentUser(false);
        this.Local_currentDatalog = MyUser.getCurrentDatalog();

        return this;
    }

    public void Start(int timeout, TimeUnit timeunit) {
        this.exceptions.clear();

        var count = (int)this.runnables.stream().count();
        if (count <= 0) {
            if (this.callbackCommand != null) this.callbackCommand.invoke(this.exceptions);
            return;
        }

        var countDownLatch = new CountDownLatch(count);
        var executorService = Executors.newFixedThreadPool(count);

        for(var runnable : this.runnables){
            executorService.execute(() -> {
                MyUser.setCurrentHttpTrackId(this.Local_currentHttpTrackId);
                MyUser.setCurrentGTTrackId(this.Local_currentGTTrackId);
                MyUser.setCurrentGTTrackFirst(this.Local_currentGTTrackFirst);
                MyUser.setCurrentUserToken(this.Local_currentSSOToken);
                MyUser.setCurrentUserClient(this.Local_currentUserClient);
                MyUser.setCurrentUserName(this.Local_currentUserName);
                MyUser.setCurrentUser(this.Local_currentUser);
                MyUser.setCurrentDatalog(this.Local_currentDatalog);

                try {
                    runnable.invoke();
                }
                catch (RuntimeException e) {
                    exceptions.add(new Exception("线程(" + Thread.currentThread().getId() + ")发生错误，" + e.getMessage()));
                } catch (Error e) {
                    exceptions.add(new Exception("线程(" + Thread.currentThread().getId() + ")发生错误，" + e.getMessage()));
                } catch (Throwable e) {
                    exceptions.add(new Exception("线程(" + Thread.currentThread().getId() + ")发生错误，" + e.getMessage()));
                }
                finally {
                    countDownLatch.countDown();
                }
            });
        }

        executorService.shutdown();

        var await = false;
        try {
            await = countDownLatch.await(timeout, timeunit);
        } catch (InterruptedException e) {
            exceptions.add(new Exception("线程(" + Thread.currentThread().getId() + ")计数错误，" + e.getMessage()));
        }

        if (!await) {
            exceptions.add(new Exception("线程(" + Thread.currentThread().getId() + ")超时错误，" + timeout + "秒!"));
            executorService.shutdownNow();
        }

        if (this.callbackCommand != null) this.callbackCommand.invoke(this.exceptions);
    }

    public void Start() {
        this.Start(60, TimeUnit.SECONDS);
    }
}
