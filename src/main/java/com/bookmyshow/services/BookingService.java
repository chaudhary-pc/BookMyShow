package com.bookmyshow.services;

import com.bookmyshow.exceptions.InvalidShowException;
import com.bookmyshow.exceptions.InvalidUserException;
import com.bookmyshow.exceptions.ShowSeatNotAvailableException;
import com.bookmyshow.models.*;
import com.bookmyshow.repositories.ShowRepository;
import com.bookmyshow.repositories.ShowSeatRepository;
import com.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculator priceCalculator;

    @Autowired
    BookingService(UserRepository userRepository, ShowRepository showRepository,
                   ShowSeatRepository showSeatRepository, PriceCalculator priceCalculator) {
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculator = priceCalculator;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE) // Performs all transactions in a sequence
    public Booking bookMovie(Long userId, Long showId, List<Long> showSeatIds) throws InvalidUserException, InvalidShowException, ShowSeatNotAvailableException {
        //STEPS:

        //1. Get user with the userId.
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new InvalidUserException("Invalid User");
        }
        User user = optionalUser.get();

        //2. Get show with the showId.
        Optional<Show> optionalShow = showRepository.findById(showId);
        if (optionalShow.isEmpty()) {
            throw new InvalidShowException("Invalid Show");
        }
        Show show = optionalShow.get();

        //3. Get showSeats with showSeatIds.
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        //4. Check if seats are available or not.
        for (ShowSeat showSeat : showSeats) {
            if (!showSeat.getSeatStatus().equals(SeatStatus.AVAILABLE)) {
                //5. if no, throw an exception.
                throw new ShowSeatNotAvailableException("ShowSeat not available");
            }
        }

        List<ShowSeat> finalShowSeats = new ArrayList<>();

        //6. If yes, Mark the seat status as BLOCKED.
        for (ShowSeat showSeat : showSeats) {
            showSeat.setSeatStatus(SeatStatus.BLOCKED);
            //7. Save the updated status in DB.
            finalShowSeats.add(showSeatRepository.save(showSeat));
        }

        //8. Create the Booking object.
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setTimeOfBooking(new Date());
        booking.setShow(show);
        booking.setUser(user);
        booking.setSeats(finalShowSeats);
        booking.setPayments(new ArrayList<>());
        booking.setPrice(priceCalculator.calculatePrice(show, finalShowSeats));

        return booking;
    }
}
