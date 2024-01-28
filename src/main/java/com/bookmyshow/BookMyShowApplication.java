package com.bookmyshow;

import com.bookmyshow.controllers.UserController;
import com.bookmyshow.dtos.SignUpRequestDto;
import com.bookmyshow.dtos.SignUpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookMyShowApplication implements CommandLineRunner {
    @Autowired
    private UserController userController;
    public static void main(String[] args) {
        SpringApplication.run(BookMyShowApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        SignUpRequestDto requestDto = new SignUpRequestDto();
        requestDto.setEmailId("testuser@gmail.com");
        requestDto.setPassword("test123");
        SignUpResponseDto responseDto = userController.singUp(requestDto);
    }
}
