package com.vcanus.vjvmqttmariadb;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class MqttTest {
    private static final Logger logger =
            LoggerFactory.getLogger(MqttTest.class);

    ClientMqtt client;
    String subtopic = "data/cleanhouse/tplastic/+";
    String pubtopic = "data/cleanhouse/tplastic/sensor01";

    /* data samples */
    float fMsg = 30f;
    String sMsg = "30";
    String floatToString;
    byte[] floatToStringToByte;
    float[] fArrayMsg = new float[]{30.11111f, 50.12345f, 123.34f};
    byte[] byteData = String.format(Locale.US, "%f/%f", 30.12345f, 50.12345f).getBytes();


    @Before
    public void setUp() {
        client = new ClientMqtt();
        client.connect("tcp://192.168.50.19:1883", "client");
        //Assert.assertTrue(client.isConnected());
        logger.info("setUp");
    }

    //@After
    public void disconnect() {
        client.disconnect();
        logger.info("mqttClient disconnected");
    }

    @Test
    public void connectionTest() {
//        client = new ClientMqtt();
//        client.setClientInfo("tcp://192.168.50.19:1883", "client");
//        client.connect();

        Assert.assertFalse(!client.isConnected());
    }


    @Test
    public void subTest() throws InterruptedException {
        setUp();
        try {
            client.setSubscription(subtopic, (topic, msg) -> {
                byte[] payload = msg.getPayload();
                logger.debug("Message received: topic={}, payload={}", topic, new String(payload));
                String[] recievedSrings = new String(payload).split("/");
                Float[] recievedFloats = new Float[recievedSrings.length];
                for (int i = 0; i < recievedFloats.length; i++) {
                    recievedFloats[i] = Float.parseFloat(recievedSrings[i]);
                    logger.debug("recievedSrings[" + i + "] : " + recievedSrings[i]);
                    logger.debug("recievedFloats[" + i + "] : " + recievedFloats[i]);
                }
            });
        } catch (MqttException e) {
            logger.error(e.getMessage());
        }
        while(true){

        }
        //Thread.sleep(6000);
    }

    @Test
    public void pubTest() throws InterruptedException {
//        setUp();
        client.publish(pubtopic, byteData);
        logger.info("client.deliverArrived - log message ");
    }

    @Test
    public void pubsubTest() {
//        setUp();
        try {
            client.setSubscription(subtopic, (topic, msg) -> {
                /* option 1 */
                byte[] payload = msg.getPayload();
                logger.debug("Message received: topic={}, payload={}", topic, new String(payload));
                String[] recievedSrings = new String(payload).split("/");
                Float[] recievedFloats = new Float[recievedSrings.length];
                for (int i = 0; i < recievedFloats.length; i++) {
                    recievedFloats[i] = Float.parseFloat(recievedSrings[i]);
                    logger.debug("recievedSrings[" + i + "] : " + recievedSrings[i] + ", recievedFloats[" + i + "] : " + recievedFloats[i]);
                }


            });
        } catch (MqttException e) {
            logger.error(e.getMessage());
        }

        floatToStringToByte = convertFloatArrayToString(fArrayMsg);
        client.publish(pubtopic, floatToStringToByte);
        logger.info("client.deliverArrived - log message ");
        //while(true){}
    }


    public byte[] convertFloatArrayToString(float[] fArrayMsg) {
        String result = "";
        for(int i=0; i<fArrayMsg.length; i++){
            result+=String.valueOf(fArrayMsg[i]);
            result+="/";
        }

        return result.getBytes();
    }
//    public void floatArrayToBytes() {
//        floatToBytes = new byte[fArrayMsg.length * 4];
//        for (int i = 0; i < fArrayMsg.length; i++) {
//            byte[] s = intToBytes(Float.floatToRawIntBits(fArrayMsg[i]));
//            for (int j = 0; j < 4; j++)
//                floatToBytes[4 * i + j] = s[j];/* w  w  w .ja v  a  2 s  .c om*/
//        }
//    }
//    public byte[] intToBytes(int v) {
//        byte[] r = new byte[4];
//        for (int i = 0; i < 4; i++) {
//            r[i] = (byte) ((v >>> (i * 8)) & 0xFF);
//        }
//        return r;
//    }
}
