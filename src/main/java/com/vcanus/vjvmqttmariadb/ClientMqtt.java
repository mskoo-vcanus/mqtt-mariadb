package com.vcanus.vjvmqttmariadb;

import java.util.List;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientMqtt implements MqttCallback{
    private static final Logger logger =
            LoggerFactory.getLogger(ServiceController.class);
    private static MqttClient client;
    private static MqttConnectOptions options;
    private MqttMessage message;
    private IMqttMessageListener listener;
    String url;
    String clientId;
    String topic;

    public boolean connect(String url, String clientId) {
        // Connect to MQTT server
        if(client==null){
            this.url=url;
            this.clientId = clientId;
        }
        options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setKeepAliveInterval(15);
        options.setConnectionTimeout(30);

        try {
            client = new org.eclipse.paho.client.mqttv3.MqttClient(url, clientId);
            client.setCallback(this);
            client.connect(options);
            logger.info("Connection success");
            this.message = new MqttMessage();
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

//    public MqttClient connect(String url, String clientId){
//        try {
//            client = new MqttClient(url,clientId);
//            logger.debug("Connected-client : " + url + ", id : " + clientId);
//        } catch (MqttException e) {
//            logger.error(e.getMessage() + " Code: " + e.getReasonCode());
//        }
//        MqttConnectOptions options = new MqttConnectOptions();
//        options.setAutomaticReconnect(true);
//        options.setCleanSession(true);
//        options.setConnectionTimeout(10);
//        try {
//            Objects.requireNonNull(client).connect(options);
//        } catch (MqttException e) {
//            logger.error("Cannot connect to MQTT Broker");
//        }
//        return client;
//    }
    public void setTopic(String topic){
        this.topic = topic;
    }
    public void setListener(IMqttMessageListener listener){
        this.listener = listener;
    }

    public boolean isConnected(){
        if(client==null){
            return false;
        }
        return client.isConnected();
    }

    public void publish(String topic, byte[] msg) {
        message.setQos(0);
        message.setPayload(msg);
        try {
            client.publish(topic, message);
        }catch (MqttException e) {
            logger.error("publish failed : " + e.getMessage());
        }

    }
    //    public void publish(String topic, float[] msg) {
//        message.setQos(0);
//        message.setPayload(msg);
//
//        try {
//            client.publish(topic, message);
//        }catch (MqttException e) {
//            logger.error("publish failed : " + e.getMessage());
//        }
//    }
    public void publish(String topic, String msg) {

    }
    public void publish(String topic, float msg) {

    }
    public void publish(String topic, List<float[]> msg) {

    }

    public void setSubscription(String channel, IMqttMessageListener listener) throws MqttException {
        this.client.subscribe(channel, listener);
        logger.info("subscribe ready : " + this.getClass().getName());
    }

    public void disconnect() {
        try {
            client.disconnect();
            client.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.info("Lost connection." + cause.getCause());
        try {
            client.connect(options);
            setSubscription(topic, listener);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        if(this.isConnected()){
            logger.info("reconnection." + this.getClass().getName());
        }

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("Message with " + token + " delivered.");
    }
}
