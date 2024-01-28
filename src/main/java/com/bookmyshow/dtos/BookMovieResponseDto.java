package com.bookmyshow.dtos;

import com.bookmyshow.models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMovieResponseDto {
    private int amount;
    private Long bookingId;
    private ResponseStatus responseStatus;
}
