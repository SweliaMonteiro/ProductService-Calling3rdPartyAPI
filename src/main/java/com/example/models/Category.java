package com.example.models;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

// Implement Serializable interface to make the object serializable else it will throw an error while storing in Redis

@Getter
@Setter
public class Category implements Serializable {

    private long id;

    private String description;

}
