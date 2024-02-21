package com.chaolj.core.commonUtils.myServer.Interface;

import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.chaolj.core.MyApp;
import com.chaolj.core.commonUtils.myDto.QueryDataDto;

/*
 * ES服务接口
 * */
public interface IElasticsearchServer {
    boolean checkIndex (String indexName);

    void createIndex (String indexName, Map<String, Object> columnMap);

    default void createIndex (String indexName) {
        this.createIndex(indexName, null);
    }

    void putIndexMapping(String indexName, Map<String, Object> indexMap);

    void deleteIndex(String indexName);

    void saveDoc(String indexName, Map<String,Object> dataMap);

    void saveDoc(String indexName, Map<String,Object> dataMap, String idFieldName);

    default <T> void saveDoc(String indexName, String dataString) {
        var map = MyApp.Helper().JsonHelper().parseObject(dataString, new TypeReference<Map<String,Object>>(){});
        this.saveDoc(indexName, map);
    }

    default <T> void saveDoc(String indexName, String dataString, String idFieldName) {
        var map = MyApp.Helper().JsonHelper().parseObject(dataString, new TypeReference<Map<String,Object>>(){});
        this.saveDoc(indexName, map, idFieldName);
    }

    default <T> void saveDocObject(String indexName, T dataObject) {
        var mapString = MyApp.Helper().JsonHelper().toJSONString(dataObject);
        this.saveDoc(indexName, mapString);
    }

    default <T> void saveDocObject(String indexName, T dataObject, String idFieldName) {
        var mapString = MyApp.Helper().JsonHelper().toJSONString(dataObject);
        this.saveDoc(indexName, mapString, idFieldName);
    }

    void batchSaveDoc (String indexName, List<Map<String,Object>> dataMaps);

    void batchSaveDoc (String indexName, List<Map<String,Object>> dataMaps, String idFieldName);

    default void batchSaveDoc(String indexName, String dataString) {
        var maps = MyApp.Helper().JsonHelper().parseObject(dataString, new TypeReference<List<Map<String, Object>>>(){});
        this.batchSaveDoc(indexName, maps);
    }

    default void batchSaveDoc(String indexName, String dataString, String idFieldName) {
        var maps = MyApp.Helper().JsonHelper().parseObject(dataString, new TypeReference<List<Map<String, Object>>>(){});
        this.batchSaveDoc(indexName, maps, idFieldName);
    }

    default <T> void batchSaveDocObject(String indexName, List<T> dataObject) {
        var mapString = MyApp.Helper().JsonHelper().toJSONString(dataObject);
        this.batchSaveDoc(indexName, mapString);
    }

    default <T> void batchSaveDocObject(String indexName, List<T> dataObject, String idFieldName) {
        var mapString = MyApp.Helper().JsonHelper().toJSONString(dataObject);
        this.batchSaveDoc(indexName, mapString, idFieldName);
    }

    void updateDoc(String indexName, String id, Map<String, Object> dataMap);

    void deleteDoc (String indexName, String id);

    void batchDeleteDoc (String indexName, List<String> ids);

    void batchDeleteDoc(String indexName, QueryDataDto query);

    Map<String,Object> getDoc (String indexName, String id);

    default <T> T getDoc(String indexName, String id, Class<T> clazz) {
        var map = this.getDoc(indexName, id);
        var mapString = MyApp.Helper().JsonHelper().toJSONString(map);
        return MyApp.Helper().JsonHelper().parseObject(mapString, clazz);
    }

    default <T> T getDoc(String indexName, String id, Class<T> clazz, ParserConfig config) {
        var map = this.getDoc(indexName, id);
        var mapString = MyApp.Helper().JsonHelper().toJSONString(map);
        return MyApp.Helper().JsonHelper().parseObject(mapString, clazz, config);
    }

    List<Map<String,Object>> getDoc (String indexName, List<String> ids);

    default <T> List<T> getDoc(String indexName, List<String> ids, Class<T> clazz) {
        var mapList = this.getDoc(indexName, ids);
        var mapListString = MyApp.Helper().JsonHelper().toJSONString(mapList);
        return MyApp.Helper().JsonHelper().parseArray(mapListString, clazz);
    }

    default <T> List<T> getDoc(String indexName, List<String> ids, Class<T> clazz, ParserConfig config) {
        var mapList = this.getDoc(indexName, ids);
        var mapListString = MyApp.Helper().JsonHelper().toJSONString(mapList);
        return MyApp.Helper().JsonHelper().parseArray(mapListString, clazz, config);
    }

    Long queryCount (String[] indices, QueryDataDto query);

    default Long queryCount(String indexName, QueryDataDto query) {
        var indices = new String[] { indexName };
        return this.queryCount(indices, query);
    }

    List<Map<String,Object>> queryList (String[] indices, QueryDataDto query, String source);

    default List<Map<String,Object>> queryList (String[] indices, QueryDataDto query) {
        return this.queryList(indices, query, "");
    }

    default List<Map<String,Object>> queryList (String indexName, QueryDataDto query, String source) {
        var indices = new String[] { indexName };
        return this.queryList(indices, query, source);
    }

    default List<Map<String,Object>> queryList (String indexName, QueryDataDto query) {
        return this.queryList(indexName, query, "");
    }

    default <T> List<T> queryList(String indexName, QueryDataDto query, Class<T> clazz, String source) {
        var mapList = this.queryList(indexName, query, source);
        var mapListString = MyApp.Helper().JsonHelper().toJSONString(mapList);
        return MyApp.Helper().JsonHelper().parseArray(mapListString, clazz);
    }

    default <T> List<T> queryList(String indexName, QueryDataDto query, Class<T> clazz) {
        return this.queryList(indexName, query, clazz, "");
    }

    default <T> List<T> queryList(String indexName, QueryDataDto query, Class<T> clazz, ParserConfig config, String source) {
        var mapList = this.queryList(indexName, query, source);
        var mapListString = MyApp.Helper().JsonHelper().toJSONString(mapList);
        return MyApp.Helper().JsonHelper().parseArray(mapListString, clazz, config);
    }

    default <T> List<T> queryList(String indexName, QueryDataDto query, Class<T> clazz, ParserConfig config) {
        return this.queryList(indexName, query, clazz, config, "");
    }

    List<Map<String,Object>> queryPage (String[] indices, QueryDataDto query, String source);

    default List<Map<String,Object>> queryPage (String[] indices, QueryDataDto query) {
        return this.queryPage(indices, query, "");
    }

    default <T> List<T> queryPage(String[] indices, QueryDataDto query, Class<T> clazz) {
        var mapList = this.queryPage(indices, query);
        var mapListString = MyApp.Helper().JsonHelper().toJSONString(mapList);
        return MyApp.Helper().JsonHelper().parseArray(mapListString, clazz);
    }

    default <T> List<T> queryPage(String[] indices, QueryDataDto query, Class<T> clazz, ParserConfig config) {
        var mapList = this.queryPage(indices, query);
        var mapListString = MyApp.Helper().JsonHelper().toJSONString(mapList);
        return MyApp.Helper().JsonHelper().parseArray(mapListString, clazz, config);
    }

    default List<Map<String,Object>> queryPage (String indexName, QueryDataDto query, String source) {
        var indices = new String[] { indexName };
        return this.queryPage(indices, query, source);
    }

    default List<Map<String,Object>> queryPage (String indexName, QueryDataDto query) {
        var indices = new String[] { indexName };
        return this.queryPage(indices, query);
    }

    default <T> List<T> queryPage(String indexName, QueryDataDto query, Class<T> clazz, String source) {
        var mapList = this.queryPage(indexName, query, source);
        var mapListString = MyApp.Helper().JsonHelper().toJSONString(mapList);
        return MyApp.Helper().JsonHelper().parseArray(mapListString, clazz);
    }

    default <T> List<T> queryPage(String indexName, QueryDataDto query, Class<T> clazz) {
        return this.queryPage(indexName, query, clazz, "");
    }

    default <T> List<T> queryPage(String indexName, QueryDataDto query, Class<T> clazz, ParserConfig config, String source) {
        var mapList = this.queryPage(indexName, query, source);
        var mapListString = MyApp.Helper().JsonHelper().toJSONString(mapList);
        return MyApp.Helper().JsonHelper().parseArray(mapListString, clazz, config);
    }

    default <T> List<T> queryPage(String indexName, QueryDataDto query, Class<T> clazz, ParserConfig config) {
        return this.queryPage(indexName, query, clazz, config, "");
    }

    default Map<String,Object> queryOne (String[] indices, QueryDataDto query, String source) {
        query.setPage(1);
        query.setRows(1);
        var lst = this.queryPage(indices, query, source);
        if (lst == null) return null;
        if (lst.isEmpty()) return null;
        return lst.get(0);
    }

    default Map<String,Object> queryOne (String[] indices, QueryDataDto query) {
        return this.queryOne(indices, query, "");
    }

    default <T> T queryOne(String[] indices, QueryDataDto query, Class<T> clazz) {
        var map = this.queryOne(indices, query);
        var mapString = MyApp.Helper().JsonHelper().toJSONString(map);
        return MyApp.Helper().JsonHelper().parseObject(mapString, clazz);
    }

    default <T> T queryOne(String[] indices, QueryDataDto query, Class<T> clazz, ParserConfig config) {
        var map = this.queryOne(indices, query);
        var mapString = MyApp.Helper().JsonHelper().toJSONString(map);
        return MyApp.Helper().JsonHelper().parseObject(mapString, clazz, config);
    }

    default Map<String,Object> queryOne (String indexName, QueryDataDto query, String source) {
        query.setPage(1);
        query.setRows(1);
        var lst = this.queryPage(indexName, query, source);
        if (lst == null) return null;
        if (lst.isEmpty()) return null;
        return lst.get(0);
    }

    default Map<String,Object> queryOne (String indexName, QueryDataDto query) {
        return this.queryOne(indexName, query, "");
    }

    default <T> T queryOne(String indexName, QueryDataDto query, Class<T> clazz, String source) {
        var map = this.queryOne(indexName, query, source);
        var mapString = MyApp.Helper().JsonHelper().toJSONString(map);
        return MyApp.Helper().JsonHelper().parseObject(mapString, clazz);
    }

    default <T> T queryOne(String indexName, QueryDataDto query, Class<T> clazz) {
        return this.queryOne(indexName, query, clazz, "");
    }

    default <T> T queryOne(String indexName, QueryDataDto query, Class<T> clazz, ParserConfig config, String source) {
        var map = this.queryOne(indexName, query, source);
        var mapString = MyApp.Helper().JsonHelper().toJSONString(map);
        return MyApp.Helper().JsonHelper().parseObject(mapString, clazz, config);
    }

    default <T> T queryOne(String indexName, QueryDataDto query, Class<T> clazz, ParserConfig config) {
        return this.queryOne(indexName, query, clazz, config, "");
    }
}
