package com.bookmyshow.controllers;


import com.bookmyshow.models.Movie;
import com.bookmyshow.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.AllArgsConstructor;

import java.util.List;

@Controller
public class MovieController {
    private MovieService movieService;
    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public List<Movie> requestMethodName() {
        return movieService.listMovies();
    }

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public List<Movie> requestMethodName(@RequestParam(value = "movieName", required = true) String movieName) {
        return movieService.searchMovies(movieName);
    }

}