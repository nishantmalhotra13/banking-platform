package com.banking.platform.auth.ccsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.banking.platform.auth.ccsl",
        "com.banking.platform.common"
})
public class CcslAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcslAuthApplication.class, args);
    }
}

