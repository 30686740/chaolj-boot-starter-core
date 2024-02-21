package com.chaolj.core.commonUtils.myServer.Interface;

import java.util.HashMap;
import java.util.List;
import com.chaolj.core.commonUtils.myDto.*;
import com.chaolj.core.commonUtils.myServer.Models.MailMessageDto;
import com.chaolj.core.commonUtils.myServer.Models.QueueMessageDto;
import com.chaolj.core.commonUtils.myServer.Models.WeChatTextCardMessageDto;
import com.chaolj.core.commonUtils.myServer.Models.WeChatTextMessageDto;

/*
 * 消息队列服务接口
 * */
public interface IQueueServer {
    DataResultDto<String> PushMessage(QueueMessageDto message);

    DataResultDto<String> PushMessageList(List<QueueMessageDto> messages);

    DataResultDto<String> PushMailMessage(MailMessageDto message);

    DataResultDto<String> PushWeChatTextMessage(WeChatTextMessageDto message);

    DataResultDto<String> PushWeChatTextCardMessage(WeChatTextCardMessageDto message);

    DataResultDto<String> PushBpmMessage(String packageName, String workflowName, String startUserLoginName, String startDeptCode, Boolean finishFirst, HashMap<String, Object> dataitems);

    DataResultDto<String> PushWxRobotMessage(String receivers, String message);
}
