package com.vcanus.vjvmqttmariadb.Mappers.second;

import com.vcanus.vjvmqttmariadb.Values;

@org.apache.ibatis.annotations.Mapper
public interface Mapper2 {

    String test();
    int insertData(Values values);
}
