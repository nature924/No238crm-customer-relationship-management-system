package com.wuhunyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * crm项目启动类
 * @author wuhunyu
 * @date 20201208
 */
@SpringBootApplication
@EnableScheduling
public class crmApp {
    public static void main( String[] args ) {
        SpringApplication.run(crmApp.class, args);
    }
}
