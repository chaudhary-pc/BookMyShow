package com.bookmyshow.controllers;

import com.bookmyshow.dtos.SignUpRequestDto;
import com.bookmyshow.dtos.SignUpResponseDto;
import com.bookmyshow.models.ResponseStatus;
import com.bookmyshow.models.User;
import com.bookmyshow.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    public SignUpResponseDto singUp(SignUpRequestDto requestDto) {
        User user;
        SignUpResponseDto responseDto = new SignUpResponseDto();

        try {
            user = userService.signUp(requestDto.getEmailId(), requestDto.getPassword());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setUserId(user.getId());
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            responseDto.setUserId(-1L);
        }

        return responseDto;
    }
}
