package com.chaolj.core.commonUtils.myBuilder;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import com.chaolj.core.MyApp;
import com.chaolj.core.commonUtils.myDelegate.FunctionDelegate1;
import com.chaolj.core.commonUtils.myDto.QueryDataDto;
import com.chaolj.core.commonUtils.myDto.QueryDataWhereDto;
import com.chaolj.core.commonUtils.myDto.QueryDataWhereOpDto;
import com.chaolj.core.commonUtils.myDto.UIException;

@Slf4j
public class XmlQueryBuilder {

    private DbType dbType;

    private String alias;

    private int page;

    private int rows;

    private List<String> sorts;

    private List<QueryDataWhereDto> whereAnd;

    private List<QueryDataWhereDto> whereOr;

    private XmlQueryBuilder() {}

    public static XmlQueryBuilder Builder(){
        var qry = new XmlQueryBuilder();
        qry.dbType = DbType.MYSQL;
        qry.alias = "t1";
        qry.page = 1;
        qry.rows = 20;
        qry.sorts = new ArrayList<>();
        qry.whereAnd = new ArrayList<>();
        qry.whereOr = new ArrayList<>();
        return qry;
    }

    public static XmlQueryBuilder Builder(QueryDataDto data){
        var qry = Builder();

        qry.page = data.getPage();
        qry.rows = data.getRows();

        qry.sorts.addAll(qry.BuildSortConvert(data.getSort()));

        if (data.getWhereAnd() != null && !data.getWhereAnd().isEmpty()) {
            qry.whereAnd.addAll(data.getWhereAnd());
        }

        if (data.getWhereOr() != null && !data.getWhereOr().isEmpty()) {
            qry.whereOr.addAll(data.getWhereOr());
        }

        return qry;
    }

    public XmlQueryBuilder DbType(DbType dbType){
        this.dbType = dbType;
        return this;
    }

    public XmlQueryBuilder Alias(String alias){
        this.alias = alias;
        return this;
    }

    public XmlQueryBuilder Page(Integer page){
        this.page = page;
        return this;
    }

    public XmlQueryBuilder Rows(Integer rows){
        this.rows = rows;
        return this;
    }

    public XmlQueryBuilder Sort(String field){
        this.sorts.add(field);
        return this;
    }

    public XmlQueryBuilder SortDesc(String field){
        this.sorts.add(field + " desc");
        return this;
    }

    public XmlQueryBuilder Orderby(String orderbyString){
        var orderbyList = this.BuildSortConvert(orderbyString);
        this.sorts.addAll(orderbyList);
        return this;
    }

    public XmlQueryBuilder WhereAnd(String field, QueryDataWhereOpDto operation, String value){
        var where = new QueryDataWhereDto();
        where.setField(field);
        where.setOperation(operation.getName());
        where.setValue(value);
        this.whereAnd.add(where);
        return this;
    }

    public XmlQueryBuilder WhereOr(String field, QueryDataWhereOpDto operation, String value){
        var where = new QueryDataWhereDto();
        where.setField(field);
        where.setOperation(operation.getName());
        where.setValue(value);
        this.whereOr.add(where);
        return this;
    }

    public <T> Page<T> BuildPage(){
        var _rows = this.rows <= 0 ? 20 : this.rows;
        var _page = this.page <= 0 ? 1 : this.page;

        var page = new Page<T>(_page, _rows);
        if(this.sorts.isEmpty()) return page;

        for (var sort: this.sorts) {
            if(StrUtil.isBlank(sort)) continue;

            var _sort = StrUtil.trim(sort);
            if(StrUtil.endWithIgnoreCase(_sort, " desc")){
                _sort = StrUtil.removeSuffixIgnoreCase(_sort, " desc").trim();
                if(StrUtil.isBlank(_sort)) continue;

                _sort = MyApp.Helper().CommonHelper().QuoteFieldName(_sort, this.dbType, this.alias);
                page.addOrder(OrderItem.desc(_sort));
            }
            else {
                _sort = MyApp.Helper().CommonHelper().QuoteFieldName(_sort, this.dbType, this.alias);
                page.addOrder(OrderItem.asc(_sort));
            }
        }

        page.setOptimizeCountSql(false);
        return page;
    }

    public String BuildSQL(){
        var sql = new StringBuilder(50);
        sql.append("where 1 = 1");

        if(!this.whereAnd.isEmpty()){
            var andJoiner = new StringJoiner(" and ", " and (", ")");
            for (var where : this.whereAnd){
                andJoiner.add(this.BuildSQLConvert(where));
            }

            sql.append(andJoiner);
        }

        if(!this.whereOr.isEmpty()){
            var oroiner = new StringJoiner(" or ", " and (", ")");
            for (var where : this.whereOr){
                oroiner.add(this.BuildSQLConvert(where));
            }

            sql.append(oroiner);
        }

        return sql.toString();
    }

    public QueryDataDto BuildDto() {
        var dto = new QueryDataDto();
        dto.setPage(this.page);
        dto.setRows(this.rows);
        dto.setSort(this.BuildSortConvert(this.sorts));
        dto.setWhereAnd(this.whereAnd);
        dto.setWhereOr(this.whereOr);

        return dto;
    }

    public String BuildDtoJson() {
        var dto = this.BuildDto();
        return JSONObject.toJSONString(dto);
    }

    private String BuildSQLConvert(QueryDataWhereDto where){
        var fieldName = where.getField();
        fieldName = MyApp.Helper().CommonHelper().QuoteFieldName(fieldName, this.dbType, this.alias);
        if (StrUtil.isBlank(fieldName)) throw new UIException("查询字段名不能为空: " + where);

        var operation = where.getOperation();
        if (StrUtil.isBlank(operation)) throw new UIException("查询操作符不能为空: " + where);

        operation = operation.trim();
        if (operation.equals("=") || operation.equals("==")) {
            var value = MyApp.Helper().CommonHelper().QuoteFieldValue(where.getValue());
            if (value.equalsIgnoreCase("null")) return fieldName + " is " + value;
            return fieldName + " = " + value;
        }
        else if (operation.equals("<>") || operation.equals("!=")) {
            var value = MyApp.Helper().CommonHelper().QuoteFieldValue(where.getValue());
            if (value.equalsIgnoreCase("null")) return fieldName + " is not " + value;
            return fieldName + " <> " + value;
        }
        else if (operation.equalsIgnoreCase("Like")) {
            var value = MyApp.Helper().CommonHelper().QuoteFieldValueLike(where.getValue());
            return fieldName + " Like " + value;
        }
        else if (operation.equalsIgnoreCase("StartsWith") || operation.equalsIgnoreCase("LikeRight")) {
            var value = MyApp.Helper().CommonHelper().QuoteFieldValueStartsWith(where.getValue());
            return fieldName + " Like " + value;
        }
        else if (operation.equalsIgnoreCase("EndsWith") || operation.equalsIgnoreCase("LikeLeft")) {
            var value = MyApp.Helper().CommonHelper().QuoteFieldValueEndsWith(where.getValue());
            return fieldName + " Like " + value;
        }
        else if (operation.equalsIgnoreCase("In")) {
            var value = where.getValue();
            if (StrUtil.isBlank(value)) return fieldName + " in ()";

            var vals = StrUtil.splitToArray(value, ",");
            var joiner = new StringJoiner(",", "(", ")");
            for (var val : vals) {
                var _val = MyApp.Helper().CommonHelper().QuoteFieldValue(val);
                joiner.add(_val);
            }
            value = joiner.toString();
            return fieldName + " in " + value;
        }
        else if (operation.equals(">") || operation.equals(">=") || operation.equals("<") || operation.equals("<=")) {
            var value = MyApp.Helper().CommonHelper().QuoteFieldValue(where.getValue());
            return fieldName + " " + operation + " " + value;
        }
        else {
            throw new UIException("查询操作符不合法: " + where);
        }
    }

    private String BuildSortConvert(List<String> sorts) {
        if (sorts == null) return "";
        if (sorts.isEmpty()) return "";

        var joiner = new StringJoiner(",");
        for (var sort: this.sorts) {
            if(StrUtil.isBlank(sort)) continue;
            joiner.add(sort.trim());
        }

        return joiner.toString();
    }

    private List<String> BuildSortConvert(String sortString) {
        var result = new ArrayList<String>();
        if (StrUtil.isBlank(sortString)) return result;

        var sorts = StrUtil.splitToArray(sortString, ',');
        for (var sort: sorts) {
            if(StrUtil.isBlank(sort)) continue;

            var _sort = StrUtil.trim(sort);
            if(StrUtil.endWithIgnoreCase(_sort, " asc")){
                _sort = StrUtil.removeSuffixIgnoreCase(_sort, " asc").trim();
                if(StrUtil.isBlank(_sort)) continue;

                result.add(_sort);
            }
            else if(StrUtil.endWithIgnoreCase(_sort, " desc")){
                _sort = StrUtil.removeSuffixIgnoreCase(_sort, " desc").trim();
                if(StrUtil.isBlank(_sort)) continue;

                result.add(_sort + " desc");
            }
            else {
                result.add(_sort);
            }
        }

        return result;
    }

    public <TPO, TMapper> IPage<TPO> ToList(Class<TPO> poClazz, Class<TMapper> mapperClazz, FunctionDelegate1<IPage<TPO>, TMapper> query) {
        var mapper = MyApp.MyBean(mapperClazz);
        return query.invoke(mapper);
    }
}
