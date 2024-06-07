package com.fooddelivery.finalprojectfredy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


//@MapperScan("com.fooddelivery.finalprojectfredy.Mappers")
@SpringBootApplication
public class FinalProjectFredyApplication {

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = FinalProjectFredyApplication.class.getClassLoader();

        File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());
        FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
        
        FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build();

        FirebaseApp.initializeApp(options);
        
        SpringApplication.run(FinalProjectFredyApplication.class, args);
    }

}
