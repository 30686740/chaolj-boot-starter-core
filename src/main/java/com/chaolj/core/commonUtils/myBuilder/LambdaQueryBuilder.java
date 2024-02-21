package com.chaolj.core.commonUtils.myBuilder;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.*;
import java.util.function.Consumer;
import com.chaolj.core.MyApp;

public class LambdaQueryBuilder<TPO> {
    private BaseMapper<TPO> mapper;
    private LambdaQueryWrapper<TPO> conditions;

    public static <TPO> LambdaQueryBuilder<TPO> Builder(Class<? extends BaseMapper<TPO>> mapperClazz) {
        var query = new LambdaQueryBuilder<TPO>();
        query.conditions = new LambdaQueryWrapper<TPO>();
        query.mapper = MyApp.MyBean(mapperClazz);
        return query;
    }

    public LambdaQueryBuilder<TPO> clone() {
        var query = new LambdaQueryBuilder<TPO>();
        query.conditions = this.conditions.clone();
        query.mapper = this.mapper;
        return query;
    }

    // 逻辑与
    public LambdaQueryBuilder<TPO> and(Consumer<LambdaQueryWrapper<TPO>> consumer) {
        this.conditions.and(true, consumer);
        return this;
    }

    // 逻辑与
    public LambdaQueryBuilder<TPO> and(boolean condition, Consumer<LambdaQueryWrapper<TPO>> consumer) {
        this.conditions.and(condition, consumer);
        return this;
    }

    // 逻辑或
    public LambdaQueryBuilder<TPO> or(Consumer<LambdaQueryWrapper<TPO>> consumer) {
        this.conditions.or(true, consumer);
        return this;
    }

    // 逻辑或
    public LambdaQueryBuilder<TPO> or(boolean condition, Consumer<LambdaQueryWrapper<TPO>> consumer) {
        this.conditions.or(condition, consumer);
        return this;
    }

    // 是空
    public LambdaQueryBuilder<TPO> isNull(boolean condition, SFunction<TPO, ?> column) {
        this.conditions.isNull(condition, column);
        return this;
    }

    // 是空
    public LambdaQueryBuilder<TPO> isNull(SFunction<TPO, ?> column) {
        this.conditions.isNull(column);
        return this;
    }

    // 是空
    public LambdaQueryBuilder<TPO> isNotNull(boolean condition, SFunction<TPO, ?> column) {
        this.conditions.isNotNull(condition, column);
        return this;
    }

    // 是空
    public LambdaQueryBuilder<TPO> isNotNull(SFunction<TPO, ?> column) {
        this.conditions.isNotNull(column);
        return this;
    }

    // 等于
    public LambdaQueryBuilder<TPO> eq(boolean condition, SFunction<TPO, ?> column, Object val) {
        this.conditions.eq(condition, column, val);
        return this;
    }

    // 等于
    public LambdaQueryBuilder<TPO> eq(SFunction<TPO, ?> column, Object val) {
        this.conditions.eq(column, val);
        return this;
    }

    // 不等于
    public LambdaQueryBuilder<TPO> ne(boolean condition, SFunction<TPO, ?> column, Object val) {
        this.conditions.ne(condition, column, val);
        return this;
    }

    // 不等于
    public LambdaQueryBuilder<TPO> ne(SFunction<TPO, ?> column, Object val) {
        this.conditions.ne(column, val);
        return this;
    }

    // 大于
    public LambdaQueryBuilder<TPO> gt(boolean condition, SFunction<TPO, ?> column, Object val) {
        this.conditions.gt(condition, column, val);
        return this;
    }

    // 大于
    public LambdaQueryBuilder<TPO> gt(SFunction<TPO, ?> column, Object val) {
        this.conditions.gt(column, val);
        return this;
    }

    // 大于等于
    public LambdaQueryBuilder<TPO> ge(boolean condition, SFunction<TPO, ?> column, Object val) {
        this.conditions.ge(condition, column, val);
        return this;
    }

    // 大于等于
    public LambdaQueryBuilder<TPO> ge(SFunction<TPO, ?> column, Object val) {
        this.conditions.ge(column, val);
        return this;
    }

    // 小于
    public LambdaQueryBuilder<TPO> lt(boolean condition, SFunction<TPO, ?> column, Object val) {
        this.conditions.lt(condition, column, val);
        return this;
    }

    // 小于
    public LambdaQueryBuilder<TPO> lt(SFunction<TPO, ?> column, Object val) {
        this.conditions.lt(column, val);
        return this;
    }

    // 小于等于
    public LambdaQueryBuilder<TPO> le(boolean condition, SFunction<TPO, ?> column, Object val) {
        this.conditions.le(condition, column, val);
        return this;
    }

    // 小于等于
    public LambdaQueryBuilder<TPO> le(SFunction<TPO, ?> column, Object val) {
        this.conditions.le(column, val);
        return this;
    }

    // 之间
    public LambdaQueryBuilder<TPO> between(boolean condition, SFunction<TPO, ?> column, Object val1, Object val2) {
        this.conditions.between(condition, column, val1, val2);
        return this;
    }

    // 之间
    public LambdaQueryBuilder<TPO> between(SFunction<TPO, ?> column, Object val1, Object val2) {
        this.conditions.between(column, val1, val2);
        return this;
    }

    // 非之间
    public LambdaQueryBuilder<TPO> notBetween(boolean condition, SFunction<TPO, ?> column, Object val1, Object val2) {
        this.conditions.notBetween(condition, column, val1, val2);
        return this;
    }

    // 非之间
    public LambdaQueryBuilder<TPO> notBetween(SFunction<TPO, ?> column, Object val1, Object val2) {
        this.conditions.notBetween(column, val1, val2);
        return this;
    }

    // 相似，%xxx%
    public LambdaQueryBuilder<TPO> like(boolean condition, SFunction<TPO, ?> column, Object val) {
        this.conditions.like(condition, column, val);
        return this;
    }

    // 相似，%xxx%
    public LambdaQueryBuilder<TPO> like(SFunction<TPO, ?> column, Object val) {
        this.conditions.like(column, val);
        return this;
    }

    // 相似，%xxx
    public LambdaQueryBuilder<TPO> likeLeft(boolean condition, SFunction<TPO, ?> column, Object val) {
        this.conditions.like(condition, column, val);
        return this;
    }

    // 相似，%xxx
    public LambdaQueryBuilder<TPO> likeLeft(SFunction<TPO, ?> column, Object val) {
        this.conditions.like(column, val);
        return this;
    }

    // 相似，xxx%
    public LambdaQueryBuilder<TPO> likeRight(boolean condition, SFunction<TPO, ?> column, Object val) {
        this.conditions.likeRight(condition, column, val);
        return this;
    }

    // 相似，xxx%
    public LambdaQueryBuilder<TPO> likeRight(SFunction<TPO, ?> column, Object val) {
        this.conditions.likeRight(column, val);
        return this;
    }

    // 非相似
    public LambdaQueryBuilder<TPO> notLike(boolean condition, SFunction<TPO, ?> column, Object val) {
        this.conditions.notLike(condition, column, val);
        return this;
    }

    // 非相似
    public LambdaQueryBuilder<TPO> notLike(SFunction<TPO, ?> column, Object val) {
        this.conditions.notLike(column, val);
        return this;
    }

    // 包含在列表
    public LambdaQueryBuilder<TPO> in(boolean condition, SFunction<TPO, ?> column, Object... vals) {
        this.conditions.in(condition, column, vals);
        return this;
    }

    // 包含在列表
    public LambdaQueryBuilder<TPO> in(SFunction<TPO, ?> column, Object... vals) {
        this.conditions.in(column, vals);
        return this;
    }

    // 包含在列表
    public LambdaQueryBuilder<TPO> in(boolean condition, SFunction<TPO, ?> column, Collection<?> coll) {
        this.conditions.in(condition, column, coll);
        return this;
    }

    // 包含在列表
    public LambdaQueryBuilder<TPO> in(SFunction<TPO, ?> column, Collection<?> coll) {
        this.conditions.in(column, coll);
        return this;
    }

    // 不包含在列表
    public LambdaQueryBuilder<TPO> notIn(boolean condition, SFunction<TPO, ?> column, Object... vals) {
        this.conditions.notIn(condition, column, vals);
        return this;
    }

    // 不包含在列表
    public LambdaQueryBuilder<TPO> notIn(SFunction<TPO, ?> column, Object... vals) {
        this.conditions.notIn(column, vals);
        return this;
    }

    // 不包含在列表
    public LambdaQueryBuilder<TPO> notIn(boolean condition, SFunction<TPO, ?> column, Collection<?> coll) {
        this.conditions.notIn(condition, column, coll);
        return this;
    }

    // 不包含在列表
    public LambdaQueryBuilder<TPO> notIn(SFunction<TPO, ?> column, Collection<?> coll) {
        this.conditions.notIn(column, coll);
        return this;
    }

    // 拼接sql
    public LambdaQueryBuilder<TPO> apply(boolean condition, String applySql, Object... value) {
        this.conditions.apply(condition, applySql, value);
        return this;
    }

    // 拼接sql
    public LambdaQueryBuilder<TPO> apply(String applySql, Object... value) {
        this.conditions.apply(true, applySql, value);
        return this;
    }

    // 存在，子查询
    public LambdaQueryBuilder<TPO> exists(boolean condition, String existsSql) {
        this.conditions.exists(condition, existsSql);
        return this;
    }

    // 存在，子查询
    public LambdaQueryBuilder<TPO> exists(String existsSql) {
        this.conditions.exists(true, existsSql);
        return this;
    }

    // 不存在，子查询
    public LambdaQueryBuilder<TPO> notExists(boolean condition, String existsSql) {
        this.conditions.notExists(condition, existsSql);
        return this;
    }

    // 不存在，子查询
    public LambdaQueryBuilder<TPO> notExists(String existsSql) {
        this.conditions.notExists(true, existsSql);
        return this;
    }

    // 排序，正序
    public LambdaQueryBuilder<TPO> orderByAsc(SFunction<TPO, ?> column) {
        this.conditions.orderByAsc(column);
        return this;
    }

    // 排序，反序
    public LambdaQueryBuilder<TPO> orderByDesc(SFunction<TPO, ?> column) {
        this.conditions.orderByDesc(column);
        return this;
    }

    public LambdaQueryBuilder<TPO> select(SFunction<TPO, ?>... column) {
        this.conditions.select(column);
        return this;
    }

    public LambdaQueryBuilder<TPO> groupBy(boolean condition, SFunction<TPO, ?>... column) {
        this.conditions.groupBy(condition, Arrays.asList(column));
        return this;
    }

    public LambdaQueryBuilder<TPO> groupBy(SFunction<TPO, ?>... column) {
        this.conditions.groupBy(Arrays.asList(column));
        return this;
    }

    public List<TPO> ToList() {
        return this.mapper.selectList(this.conditions);
    }

    public List<Map<String, Object>> ToMaps() {
        return this.mapper.selectMaps(this.conditions);
    }

    public boolean Any() {
        return this.mapper.exists(this.conditions);
    }

    public TPO FirstOrDefault() {
        var page = new Page<TPO>(1, 1, false);
        var records =  this.mapper.selectPage(page, this.conditions).getRecords();
        if (records == null) return null;
        if (records.isEmpty()) return null;
        return records.get(0);
    }

    public long Count() {
        return this.mapper.selectCount(this.conditions);
    }

    public IPage<TPO> toPage (int rows, int page, OrderItem... orderItems) {
        int _rows = rows <= 0 ? 20 : rows;
        int _page = page <= 0 ? 1 : page;

        var pageinfo = new Page<TPO>((long)_page, (long)_rows);
        pageinfo.setOrders(Arrays.asList(orderItems));

        return this.mapper.selectPage(pageinfo, this.conditions);
    }

    public IPage<TPO> toPage (int rows, int page, String orderByString) {
        var sortItems = new ArrayList<OrderItem>();

        var orderBys = MyApp.Of(orderByString).ToList(",");
        for (var orderBy : orderBys) {
            if (StrUtil.isBlank(orderBy)) continue;

            var o = orderBy.trim();
            if(StrUtil.endWithIgnoreCase(o, " desc")) {
                o = StrUtil.removeSuffixIgnoreCase(o, " desc").trim();
                sortItems.add(OrderItem.desc(o));
            }
            else if(StrUtil.endWithIgnoreCase(o, " asc")) {
                o = StrUtil.removeSuffixIgnoreCase(o, " asc").trim();
                sortItems.add(OrderItem.asc(o));
            }
            else {
                sortItems.add(OrderItem.asc(o));
            }
        }

        return this.toPage(rows, page, sortItems.toArray(new OrderItem[0]));
    }
}
