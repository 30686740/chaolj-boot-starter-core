package com.chaolj.core.commonUtils.myBuilder;

import com.alibaba.fastjson.parser.ParserConfig;
import com.chaolj.core.MyApp;
import com.chaolj.core.commonUtils.myDto.DataColumnDto;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DBConnectionBuilder {
    private DataSource myDataSource;
    private List<List<DataColumnDto>> myColumns;
    private List<List<Map<String, Object>>> myRows;

    private DBConnectionBuilder() {}

    public static DBConnectionBuilder Builder(@NotNull DataSource dataSource) {
        var self = new DBConnectionBuilder();
        self.myDataSource = dataSource;
        self.myColumns = new ArrayList<>();
        self.myRows = new ArrayList<>();
        return self;
    }

    public static DBConnectionBuilder Builder(@NotNull String dataSourceName) {
        var self = new DBConnectionBuilder();
        self.myDataSource = MyApp.Context().getBean(dataSourceName, DataSource.class);
        self.myColumns = new ArrayList<>();
        self.myRows = new ArrayList<>();
        return self;
    }

    public static DBConnectionBuilder Builder() {
        var self = new DBConnectionBuilder();
        self.myDataSource = MyApp.Context().getBean(DataSource.class);
        self.myColumns = new ArrayList<>();
        self.myRows = new ArrayList<>();
        return self;
    }

    public DBConnectionBuilder execute(String sql) {
        try (var con = this.myDataSource.getConnection();
             var cmd = con.createStatement();)
        {
            this.myColumns.clear();
            this.myRows.clear();

            if (cmd.execute(sql)){
                var resultSet =  cmd.getResultSet();

                this.myColumns.add(this.ToColumns(resultSet));
                this.myRows.add(this.ToRows(resultSet));

                while (cmd.getMoreResults()){
                    var nextResult = cmd.getResultSet();

                    this.myColumns.add(this.ToColumns(nextResult));
                    this.myRows.add(this.ToRows(nextResult));
                }


            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage());
        }

        return this;
    }

    public DBConnectionBuilder executeQuery(String sql) {
        try (var con = this.myDataSource.getConnection();
             var cmd = con.createStatement();)
        {
            this.myColumns.clear();
            this.myRows.clear();

            var resultSet = cmd.executeQuery(sql);

            this.myColumns.add(this.ToColumns(resultSet));
            this.myRows.add(this.ToRows(resultSet));
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage());
        }

        return this;
    }

    public List<DataColumnDto> getColumns(int index) {
        return this.myColumns == null || this.myColumns.stream().count() < index + 1 ? new ArrayList<>() : this.myColumns.get(index);
    }

    public List<DataColumnDto> getColumns() {
        return getColumns(0);
    }

    public List<Map<String, Object>> getRows(int index) {
        return this.myRows == null || this.myRows.stream().count() < index + 1 ? new ArrayList<>() : this.myRows.get(index);
    }

    public List<Map<String, Object>> getRows() {
        return this.getRows(0);
    }

    public <T> List<T> getList(Class<T> clazz, ParserConfig config, int index) {
        if (this.myRows == null) return new ArrayList<>();
        if (this.myRows.stream().count() < index + 1) return new ArrayList<>();

        var rows = this.myRows.get(index);
        var jsonString = MyApp.Helper().JsonHelper().toJSONString(rows);
        return MyApp.Helper().JsonHelper().parseArray(jsonString, clazz, config);
    }

    public <T> List<T> getList(Class<T> clazz, int index) {
        if (this.myRows == null) return new ArrayList<>();
        if (this.myRows.stream().count() < index + 1) return new ArrayList<>();

        var rows = this.myRows.get(index);
        var jsonString = MyApp.Helper().JsonHelper().toJSONString(rows);
        return MyApp.Helper().JsonHelper().parseArray(jsonString, clazz);
    }

    private List<DataColumnDto> ToColumns(ResultSet resultSet) {
        var columns = new ArrayList<DataColumnDto>();
        if (resultSet == null) return columns;

        try {
            var columnMeta = resultSet.getMetaData();
            var columnCount = columnMeta.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                var column = new DataColumnDto();
                column.setColumnClassName(columnMeta.getColumnClassName(i));
                column.setColumnTypeName(columnMeta.getColumnTypeName(i));
                column.setColumnType(columnMeta.getColumnType(i));
                column.setColumnName(columnMeta.getColumnName(i));
                column.setColumnLabel(columnMeta.getColumnLabel(i));
                column.setColumnDisplaySize(columnMeta.getColumnDisplaySize(i));
                column.setNullable(columnMeta.isNullable(i));
                column.setAutoIncrement(columnMeta.isAutoIncrement(i));
                column.setPrecision(columnMeta.getPrecision(i));
                column.setScale(columnMeta.getScale(i));

                if (column.getColumnClassName().equalsIgnoreCase("java.sql.Timestamp")) {
                    column.setColumnClassName("java.time.LocalDateTime");
                }

                columns.add(column);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }

        return columns;
    }

    private List<Map<String, Object>> ToRows(ResultSet resultSet) {
        var mapList = new ArrayList<Map<String, Object>>();
        if (resultSet == null) return mapList;

        try {
            var columnMeta = resultSet.getMetaData();
            var columnCount = columnMeta.getColumnCount();

            while (resultSet.next()){
                var map = new LinkedHashMap<String, Object>();

                for (int i = 1; i <= columnCount; i++) {
                    var k = columnMeta.getColumnLabel(i);
                    var v = resultSet.getObject(i);

                    if (v != null) {
                        // java.sql.Timestamp -> LocalDateTime
                        if (columnMeta.getColumnClassName(i).equalsIgnoreCase("java.sql.Timestamp")) {
                            v = ((java.sql.Timestamp)v).toLocalDateTime();
                        }
                    }

                    map.put(k, v);
                }

                mapList.add(map);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }

        return mapList;
    }
}
