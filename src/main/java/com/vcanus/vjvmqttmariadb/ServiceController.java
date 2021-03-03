package com.vcanus.vjvmqttmariadb;


import org.apache.ibatis.jdbc.Null;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
public class ServiceController {
    private static final Logger logger =
            LoggerFactory.getLogger(ServiceController.class);
    private CleanhouseService cleanhouseService;
    public ServiceController(CleanhouseService cleanhouseService){
        this.cleanhouseService = cleanhouseService;
    }

    private ClientMqtt clientMqtt;

    private final String topic = "data/#";
    private final String url = "tcp://125.132.149.3:1883";

    private final String _WEIGHT = "_weight";
    private final String _HEIGHT = "_height";

    private final String WEIGHT = "weight";
    private final String HEIGHT = "height";

    private final String TPLASTIC = "Tplastic";
    private final String tPLASTIC = "tplastic";

    private final String NUMBER1 = "1";
    private final String NUMBER2 = "2";
    private final String NUMBER3 = "3";
    private final String NUMBER4 = "4";
    private final String NUMBER5 = "5";

    public void createNqtt(){
        if(clientMqtt!=null){
            logger.debug("clientMqtt already exist");
            return;
        }
        clientMqtt = new ClientMqtt();
    }
    private IMqttMessageListener listener = (topic, msg) -> {
        /* msg process */
        byte[] payload = msg.getPayload();
        String datas = bArr2String(payload);
        logger.debug("Message received: topic={}, payload={}", topic, datas);
        String[] values = new String(datas).split(",");
        float[] fValues = new float[values.length];
        if(fValues.length!=2){
            return;
        }
        for(int i=0; i<fValues.length; i++){
            fValues[i] = Float.parseFloat(values[i]);
        }

        /* topic process */
        String[] topics = new String(topic).split("/");
        String dbParam = topics[1]; //db명
        String recycleType = topics[2];
        Timestamp ts = localDateTimeToTimeStamp(LocalDateTime.now());
        recycleType = (recycleType.equals(tPLASTIC)) ? TPLASTIC : recycleType;

        /* db process */
        Values heightValues = new Values(recycleType + _HEIGHT, HEIGHT, fValues[0], ts);
        Values weightValues = new Values(recycleType + _WEIGHT, WEIGHT, fValues[1], ts);
        logger.debug(heightValues.toString());
        logger.debug(weightValues.toString());

        boolean result = InsertDB(dbParam, heightValues, weightValues);
//                if (dbParam.contains("1")) {
//                    result = cleanhouseService.insertData1(heightValues, weightValues);
//                } else if (dbParam.contains("2")) {
//                    result = cleanhouseService.insertData2(heightValues, weightValues);
//                } else if (dbParam.contains("3")) {
//                    result = cleanhouseService.insertData3(heightValues, weightValues);
//                } else if (dbParam.contains("4")) {
//                    result = cleanhouseService.insertData4(heightValues, weightValues);
//                } else if (dbParam.contains("5")) {
//                    result = cleanhouseService.insertData5(heightValues, weightValues);
//                }
        logger.info("input result : " + result);

    };
    public boolean connectMqttt() {
        createNqtt();
        if(clientMqtt==null){
            throw new NullPointerException("");
        }
        if(clientMqtt.isConnected()){
            logger.debug("already connection");
            return true;
        }
        boolean conn =  clientMqtt.connect(url, "sub");
        setPubParam();
        return conn;
    }
    public void setPubParam(){
        clientMqtt.setTopic(topic);
        clientMqtt.setListener(listener);
    }

    @GetMapping("/mqtt")
    public void startSubscribe() throws MqttException, InterruptedException {
        long startTime = System.currentTimeMillis();
        while(!connectMqttt()){
            Thread.sleep(1000);
            long endTime = System.currentTimeMillis();
            long waitTime = endTime-startTime;
            if(waitTime>5000) break;
        }
        try {
            clientMqtt.setSubscription(topic, listener);
//            clientMqtt.setSubscription(topic, (topic, msg) -> {
//                /* msg process */
//                byte[] payload = msg.getPayload();
//                String datas = bArr2String(payload);
//                logger.debug("Message received: topic={}, payload={}", topic, datas);
//                String[] values = new String(datas).split(",");
//                float[] fValues = new float[values.length];
//                for(int i=0; i<fValues.length; i++){
//                    fValues[i] = Float.parseFloat(values[i]);
//                }
//
//                /* topic process */
//                String[] topics = new String(topic).split("/");
//                String dbParam = topics[1]; //db명
//                String recycleType = topics[2];
//                Timestamp ts = localDateTimeToTimeStamp(LocalDateTime.now());
//                recycleType = (recycleType.equals(tPLASTIC)) ? TPLASTIC : recycleType;
//
//                /* db process */
//                Values heightValues = new Values(recycleType + _HEIGHT, HEIGHT, fValues[0], ts);
//                Values weightValues = new Values(recycleType + _WEIGHT, WEIGHT, fValues[1], ts);
//                logger.debug(heightValues.toString());
//                logger.debug(weightValues.toString());
//
//                boolean result = InsertDB(dbParam, heightValues, weightValues);
////                if (dbParam.contains("1")) {
////                    result = cleanhouseService.insertData1(heightValues, weightValues);
////                } else if (dbParam.contains("2")) {
////                    result = cleanhouseService.insertData2(heightValues, weightValues);
////                } else if (dbParam.contains("3")) {
////                    result = cleanhouseService.insertData3(heightValues, weightValues);
////                } else if (dbParam.contains("4")) {
////                    result = cleanhouseService.insertData4(heightValues, weightValues);
////                } else if (dbParam.contains("5")) {
////                    result = cleanhouseService.insertData5(heightValues, weightValues);
////                }
//                logger.info("input result : " + result);
//
//            });
//            String datetime = new SimpleDateFormat("yyyyMMddhhmmss").format(new java.sql.Date(System.currentTimeMillis()));
//            byte[] byteData1 = String.format(Locale.US, "%f/%f/%s", 221.12345f, 221.12345f, datetime).getBytes();
//            byte[] byteData2 = String.format(Locale.US, "%f/%f/%s", 222.12345f, 222.12345f, datetime).getBytes();
//            clientMqtt.publish("data/device3/can", byteData1);
//            clientMqtt.publish("data/device4/can", byteData2);


        } catch (Exception e) {
            logger.error("/subscrive error : " + e.getMessage());
        }

//        try {
//            Thread.sleep(5000);
//        } catch(InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }
    }

    public boolean InsertDB(String dbParam, Values height, Values weight){
        boolean result = false;
        if (dbParam.contains(NUMBER1)) {
            result = cleanhouseService.insertData1(height, weight);
        } else if (dbParam.contains(NUMBER2)) {
            result = cleanhouseService.insertData2(height, weight);
        } else if (dbParam.contains(NUMBER3)) {
            result = cleanhouseService.insertData3(height, weight);
        } else if (dbParam.contains(NUMBER4)) {
            result = cleanhouseService.insertData4(height, weight);
        } else if (dbParam.contains(NUMBER5)) {
            result = cleanhouseService.insertData5(height, weight);
        }
        return result;
    }
    public static String getCurrentTime(String timeFormat){
        return new SimpleDateFormat(timeFormat).format(System.currentTimeMillis());
    }

    public static String bArr2String(byte[] bytes){
        String result = "";
        for(int i=0; i<bytes.length; i++){
            result +=(char)bytes[i];
        }
        return result;
    }

    public static Timestamp localDateTimeToTimeStamp(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt); // 2021-02-18 01:06:55.323
    }

    public static float[] bArr2fArr(byte[] buffer) throws IOException {
        ByteArrayInputStream bas = new ByteArrayInputStream(buffer);
        DataInputStream ds = new DataInputStream(bas);
        float[] fArr = new float[buffer.length / 4];  // 4 bytes per float
        for (int i = 0; i < fArr.length; i++)
        {
            fArr[i] = ds.readFloat();
        }
        return fArr;
    }

}
