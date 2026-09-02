package com.savarachynskyi.vault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EphemeralSecretVaultApplication {

    public static void main(String[] args) {
        SpringApplication.run(EphemeralSecretVaultApplication.class, args);
    }
}