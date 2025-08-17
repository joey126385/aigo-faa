package com.ctbc.aigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Z00040866 黃勁超
 * @date 2023/04/10 * @description 主執行檔案
 * 要加入@ServletComponentScan() WebFilter才會生效
 **/
@EnableJpaRepositories
@SpringBootApplication() //disable security settings
@ServletComponentScan()
public class CustomerKycApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerKycApplication.class, args);
    }

}
