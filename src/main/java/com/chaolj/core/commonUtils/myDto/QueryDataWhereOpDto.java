package com.chaolj.core.commonUtils.myDto;

import java.io.Serializable;

public enum QueryDataWhereOpDto implements Serializable {
    _EQ("=", "= {}"),
    _NE("<>", "<> {}"),
    _GT(">", "> {}"),
    _GE(">=", ">= {}"),
    _LT("<", "< {}"),
    _LE("<=", "<= {}"),
    _Like("Like", "like '%{}%'"),
    _StartsWith("StartsWith", "like '{}%'"),
    _EndsWith("EndsWith", "like '%{}'"),
    _In("In", "in [{}, {}, {}]"),
    _ES_match("match", "{ match: { field: { query: value, operator: and } } }"),
    _ES_match_phrase("match_phrase", "{ match_phrase: { field: value } }"),
    _ES_multi_match("multi_match", "{ multi_match: { query: value, type: most_fields, fields: [{field}, {field}, {field}] } }");

    private String name;
    private String value;

    QueryDataWhereOpDto(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
