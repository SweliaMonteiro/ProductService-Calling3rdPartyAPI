package com.example.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {

    private long id;

    private String title;

    private String description;

    private double price;

    private String image;

    private String category;

}
