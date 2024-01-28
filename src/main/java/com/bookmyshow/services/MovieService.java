package com.bookmyshow.services;

import java.util.List;

import com.bookmyshow.models.Movie;
import com.bookmyshow.repositories.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> searchMovies(String movieName) {
        return movieRepository.findByName(movieName);
    }

}