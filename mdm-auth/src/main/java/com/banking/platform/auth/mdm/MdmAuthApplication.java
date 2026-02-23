package com.banking.platform.auth.mdm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** MDM Auth Server — machine-to-machine client credentials. */
@SpringBootApplication(scanBasePackages = {
        "com.banking.platform.auth.mdm",
        "com.banking.platform.common"
})
public class MdmAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MdmAuthApplication.class, args);
    }
}

