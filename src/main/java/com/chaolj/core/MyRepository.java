package com.chaolj.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaolj.core.commonUtils.myDelegate.ActionDelegate2;
import com.chaolj.core.commonUtils.myDelegate.FunctionDelegate1;
import com.chaolj.core.commonUtils.myDto.DataStatusDto;
import com.chaolj.core.commonUtils.myDto.PageResultDto;
import com.chaolj.core.commonUtils.myDto.UIException;

public class MyRepository<TPO, TMapper extends BaseMapper<TPO>> {
    public TMapper _Mapper;
    public Class<TPO> _TPOClazz;
    public DbType _DbType;

    // region 关键字段映射

    // 设置主键
    public ActionDelegate2<TPO, String> _DBPolicy_SetId = (m, value) -> { };

    // 获取主键
    public FunctionDelegate1<String, TPO> _DBPolicy_GetId = m -> "";

    // 设置状态
    public ActionDelegate2<TPO, DataStatusDto> _DBPolicy_SetStatus = (m, value) -> { };

    // 获取状态
    public FunctionDelegate1<DataStatusDto, TPO> _DBPolicy_GetStatus = m -> DataStatusDto._0_Apply;

    // 设置创建时间
    public ActionDelegate2<TPO, LocalDateTime> _DBPolicy_SetCreateTime = (m, value) -> { };

    // 获取创建时间
    public FunctionDelegate1<LocalDateTime, TPO> _DBPolicy_GetCreateTime = m -> MyConst.DATETIME_MAX;

    // 设置创建人
    public ActionDelegate2<TPO, String> _DBPolicy_SetCreateUser = (m, value) -> { };

    // 获取创建人
    public FunctionDelegate1<String, TPO> _DBPolicy_GetCreateUser = m -> "";

    // 设置修改时间
    public ActionDelegate2<TPO, LocalDateTime> _DBPolicy_SetUpdateTime = (m, value) -> { };

    // 获取修改时间
    public FunctionDelegate1<LocalDateTime, TPO> _DBPolicy_GetUpdateTime = m -> MyConst.DATETIME_MAX;

    // 设置修改人
    public ActionDelegate2<TPO, String> _DBPolicy_SetUpdateUser = (m, value) -> { };

    // 获取修改人
    public FunctionDelegate1<String, TPO> _DBPolicy_GetUpdateUser = m -> "";

    // endregion

    // region 数据操作映射

    /// <summary>
    /// 检查状态
    /// </summary>
    public void _DBCheckStatus(TPO model, DataStatusDto... statusArray) {
        if (model == null) throw new UIException(this._TPOClazz.getName() + "，对象不能为空！");

        if (statusArray == null) return;
        if (statusArray.length <= 0) return;
        if (Arrays.stream(statusArray).noneMatch(s -> s.getValue().equals(this._DBPolicy_GetStatus.invoke(model).getValue()))) {
            throw new UIException(this._TPOClazz.getName() + "(" + this._DBPolicy_GetId.invoke(model) + ")，当前状态(" + this._DBPolicy_GetStatus.invoke(model).getName() + ")不支持此操作！");
        }
    }

    /// <summary>
    /// 检查主键
    /// </summary>
    public void _DBCheckId(TPO model) {
        if (model == null) throw new UIException(this._TPOClazz.getName() + "，对象不能为空！");

        var id = this._DBPolicy_GetId.invoke(model);
        if (StrUtil.isBlank(id)) throw new UIException(this._TPOClazz.getName() + "，主键不能为空！");
        if (this._DBExists(id)) throw new UIException(this._TPOClazz.getName() + "(" + id + ")，主键已存在！");
    }

    /// <summary>
    /// 重置主键
    /// </summary>
    public void _DBResetId(TPO model, String id) {
        // 设置为指定Id
        if (!StrUtil.isBlank(id)) {
            this._DBPolicy_SetId.invoke(model, id);
            return;
        }

        // 未指定Id且当前Id为空，则初始化
        id = this._DBPolicy_GetId.invoke(model);
        if (StrUtil.isBlank(id)) {
            id = MyApp.Helper().GuidHelper().NewID();
            this._DBPolicy_SetId.invoke(model, id);
        }
    }

    /// <summary>
    /// 查询对象，分页
    /// </summary>
    public PageResultDto<TPO> _DBQueryPage(LambdaQueryWrapper<TPO> wrapper, int pagerows, int pageindex, String orderby) {
        if (pageindex <= 0) pageindex = 1;
        if (pagerows <= 0) pagerows = 50;

        var page = MyApp.Query().DbType(this._DbType).Alias("").Page(pageindex).Rows(pagerows).Orderby(orderby).<TPO>BuildPage();
        var qry = this._Mapper.selectPage(page, wrapper);

        var count = qry.getTotal();
        var models = qry.getRecords() == null ? new ArrayList<TPO>() : qry.getRecords();

        var pageResult = new PageResultDto<TPO>();
        pageResult.setCount(count);
        pageResult.setRows(models);

        return pageResult;
    }

    /// <summary>
    /// 查询对象，总数量
    /// </summary>
    public Long _DBQueryCount(LambdaQueryWrapper<TPO> wrapper) {
        return this._Mapper.selectCount(wrapper);
    }

    /// <summary>
    /// 查询对象，对象
    /// </summary>
    public TPO _DBQueryOne(LambdaQueryWrapper<TPO> wrapper) {
        var page = new Page<TPO>(1, 1, false);
        var records =  this._Mapper.selectPage(page, wrapper).getRecords();
        if (records == null) return null;
        if (records.isEmpty()) return null;
        return records.get(0);
    }

    /// <summary>
    /// 查询对象，集合
    /// </summary>
    public List<TPO> _DBQueryList(LambdaQueryWrapper<TPO> wrapper) {
        return this._Mapper.selectList(wrapper);
    }

    /// <summary>
    /// 存在对象
    /// </summary>
    public boolean _DBExists(String id) {
        if (StrUtil.isBlank(id)) return false;
        return this._Mapper.selectById(id) != null;
    }

    /// <summary>
    /// 获取对象
    /// </summary>
    public TPO _DBGet(String id) {
        if (StrUtil.isBlank(id)) return null;
        return this._Mapper.selectById(id);
    }

    /// <summary>
    /// 获取对象
    /// </summary>
    public List<TPO> _DBGet(List<String> ids) {
        if (ids == null) return new ArrayList<>();
        if (ids.isEmpty()) return new ArrayList<>();

        var models = this._Mapper.selectBatchIds(ids);
        return models == null ? new ArrayList<>() : models;
    }

    /// <summary>
    /// 新增对象
    /// </summary>
    public void _DBInsert(TPO model) {
        if (model == null) return;

        var id = this._DBPolicy_GetId.invoke(model);
        if (StrUtil.isBlank(id)) return;

        this._Mapper.insert(model);
    }

    /// <summary>
    /// 修改对象
    /// </summary>
    public void _DBUpdate(TPO model) {
        if (model == null) return;

        var id = this._DBPolicy_GetId.invoke(model);
        if (StrUtil.isBlank(id)) return;

        this._Mapper.updateById(model);
    }

    /// <summary>
    /// 修改对象
    /// </summary>
    public void _DBUpdate(LambdaUpdateWrapper<TPO> wrapper) {
        this._Mapper.update(null, wrapper);
    }

    /// <summary>
    /// 删除对象
    /// </summary>
    public void _DBDelete(TPO model) {
        if (model == null) return;

        var id = this._DBPolicy_GetId.invoke(model);
        if (StrUtil.isBlank(id)) return;

        this._Mapper.deleteById(id);
    }

    /// <summary>
    /// 删除对象
    /// </summary>
    public void _DBDelete(LambdaQueryWrapper<TPO> wrapper) {
        this._Mapper.delete(wrapper);
    }

    // endregion
}
