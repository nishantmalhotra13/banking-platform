package com.banking.platform.mdm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MDM (Master Data Management) Service.
 * <p>
 * Owns customer account master data.  Provides search APIs for phone, name, and email
 * lookups.  Each account has a product code that downstream services use to determine
 * whether it is a credit card, debit card, savings account, etc.
 * </p>
 */
@SpringBootApplication(scanBasePackages = {
        "com.banking.platform.mdm",
        "com.banking.platform.common"
})
public class MdmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MdmServiceApplication.class, args);
    }
}

