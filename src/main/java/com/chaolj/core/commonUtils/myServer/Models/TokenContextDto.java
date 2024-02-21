package com.chaolj.core.commonUtils.myServer.Models;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.chaolj.core.MyConst;

@Data
public class TokenContextDto implements Serializable {
    private String Id;
    private String ClientCode;
    private String Code;
    private String LoginName;
    private String DisplayName;
    private String Mobile;
    private String Email;
    private LocalDateTime EntryTime;
    private LocalDateTime FormalTime;
    private LocalDateTime LeaveTime;
    private LocalDateTime BirthdayTime;
    private Boolean IsEnabled;

    private TokenDeptDto Dept;
    private List<TokenDeptMemberDto> DeptJobs;
    private List<TokenDeptMemberDto> DeptRoles;
    private List<TokenAppRoleDto> AppRoles;

    public static TokenContextDto getDefault() {
        var context = new TokenContextDto();
        context.setId("-");
        context.setClientCode("-");
        context.setCode("-");
        context.setLoginName("-");
        context.setDisplayName("-");
        context.setMobile("-");
        context.setEmail("-");
        context.setEntryTime(MyConst.DATETIME_MAX);
        context.setFormalTime(MyConst.DATETIME_MAX);
        context.setLeaveTime(MyConst.DATETIME_MAX);
        context.setBirthdayTime(MyConst.DATETIME_MAX);
        context.setIsEnabled(true);

        context.Dept = new TokenDeptDto();
        context.Dept.setId("-");
        context.Dept.setAreaNo("-");
        context.Dept.setAreaName("-");
        context.Dept.setDeptNo("-");
        context.Dept.setDeptName("-");
        context.Dept.setDeptType("-");
        context.Dept.setDeptCityNo("-");
        context.Dept.setDeptCityName("-");
        context.Dept.setDeptProvinceNo("-");
        context.Dept.setDeptProvinceName("-");

        context.DeptJobs = new ArrayList<>();
        context.DeptRoles = new ArrayList<>();
        context.AppRoles = new ArrayList<>();

        return context;
    }
}
