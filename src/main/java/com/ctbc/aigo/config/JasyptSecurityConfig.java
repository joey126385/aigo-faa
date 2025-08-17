package com.ctbc.aigo.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Z00040866 黃勁超
 * @date 2023/04/10
 * @description Jasypt設定檔
 * 加密的參數像是iteration這類的要調整請放置於此處
 * 不要寫在properties中, 這個config支援各種等級的加密演算法
 **/
@Configuration
public class JasyptSecurityConfig {

    @Bean
    @ConfigurationProperties("jasypt.encryptor")
    public SimpleStringPBEConfig jasyptConfig() {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        // 加密的參數像是iteration這類的要調整請放置於此處
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
		config.setStringOutputType("base64");
        return config;
    }

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(jasyptConfig());
        return encryptor;
    }

}
