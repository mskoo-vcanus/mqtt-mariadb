package com.vcanus.vjvmqttmariadb.Mappers.third;

import com.vcanus.vjvmqttmariadb.Values;

@org.apache.ibatis.annotations.Mapper
public interface Mapper3 {

    String test();
    int insertData(Values values);
}
