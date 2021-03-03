package com.vcanus.vjvmqttmariadb;

import java.sql.Timestamp;

public class Values {

    private String table;
    private String valueType;
    private float value;
    private Timestamp datetime;

    public Values(String table, String valueType, float value, Timestamp datetime) {
        this.table = table;
        this.valueType = valueType;
        this.value = value;
        this.datetime = datetime;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp time) {
        this.datetime = time;
    }

    @Override
    public String toString() {
        return "Values{" +
                "table='" + table + '\'' +
                ", valueType='" + valueType + '\'' +
                ", value=" + value +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
