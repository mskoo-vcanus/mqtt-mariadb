package com.vcanus.vjvmqttmariadb;

import com.vcanus.vjvmqttmariadb.Mappers.fifth.Mapper5;
import com.vcanus.vjvmqttmariadb.Mappers.first.Mapper1;
import com.vcanus.vjvmqttmariadb.Mappers.fourth.Mapper4;
import com.vcanus.vjvmqttmariadb.Mappers.second.Mapper2;
import com.vcanus.vjvmqttmariadb.Mappers.third.Mapper3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CleanhouseService  {
    @Autowired
    Mapper1 mapper1;
    @Autowired
    Mapper2 mapper2;
    @Autowired
    Mapper3 mapper3;
    @Autowired
    Mapper4 mapper4;
    @Autowired
    Mapper5 mapper5;

    public boolean insertData1(Values heightValues, Values weightValues){
        int result1 = mapper1.insertData(heightValues);
        int result2 = mapper1.insertData(weightValues);
        if(result1==1 && result2==1) return true;
        else return false;
    }
    public boolean insertData2(Values heightValues, Values weightValues){
        int result1 = mapper2.insertData(heightValues);
        int result2 = mapper2.insertData(weightValues);
        if(result1==1 && result2==1) return true;
        else return false;
    }
    public boolean insertData3(Values heightValues, Values weightValues){
        int result1 = mapper3.insertData(heightValues);
        int result2 = mapper3.insertData(weightValues);
        if(result1==1 && result2==1) return true;
        else return false;
    }
    public boolean insertData4(Values heightValues, Values weightValues){
        int result1 = mapper4.insertData(heightValues);
        int result2 = mapper4.insertData(weightValues);
        if(result1==1 && result2==1) return true;
        else return false;
    }
    public boolean insertData5(Values heightValues, Values weightValues){
        int result1 = mapper5.insertData(heightValues);
        int result2 = mapper5.insertData(weightValues);
        if(result1==1 && result2==1) return true;
        else return false;
    }
}
