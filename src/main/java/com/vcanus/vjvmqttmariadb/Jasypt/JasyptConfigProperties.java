//package com.vcanus.vjvmqttmariadb.Jasypt;
//
//
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.NestedConfigurationProperty;
//
//@ConfigurationProperties(prefix = "jasypt.encryptor", ignoreUnknownFields = true)
//@Data
//class JasyptConfigProperties {
//    private Boolean proxyPropertySources = false;
//    private String bean = "jasyptStringEncryptor";
//    private String password;
//    private String algorithm = "PBEWithMD5AndDES";
//    private String keyObtentionIterations = "1000";
//    private String poolSize = "1";
//    private String providerName = null;
//    private String saltGeneratorClassname = "org.jasypt.salt.RandomSaltGenerator";
//    private String stringOutputType = "base64";
//    @NestedConfigurationProperty
//    private PropertyConfigurationProperties property = new PropertyConfigurationProperties();
//    @Data
//    public static class PropertyConfigurationProperties {
//        private String detectorBean = "encryptablePropertyDetector";
//        private String resolverBean = "encryptablePropertyResolver";
//        private String prefix = "ENC(";
//        private String suffix = ")";
//    }
//}
