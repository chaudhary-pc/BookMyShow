package com.bookmyshow.controllers;

import com.bookmyshow.dtos.BookMovieRequestDto;
import com.bookmyshow.dtos.BookMovieResponseDto;
import com.bookmyshow.models.Booking;
import com.bookmyshow.models.ResponseStatus;
import com.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private BookingService bookingService;
    @Autowired
    BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    BookMovieResponseDto bookTicket(BookMovieRequestDto requestDto) {
        BookMovieResponseDto responseDto = new BookMovieResponseDto();

        try {
            Booking booking = bookingService.bookMovie(
                    requestDto.getUserId(),
                    requestDto.getShowId(),
                    requestDto.getShowSeatId()
            );
            responseDto.setBookingId(booking.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setAmount(booking.getPrice());
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}