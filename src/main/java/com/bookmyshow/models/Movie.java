package com.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Movie extends BaseModel{
    private String name;

}
