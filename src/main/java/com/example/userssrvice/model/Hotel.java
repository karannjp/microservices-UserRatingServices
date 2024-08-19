package com.example.userssrvice.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    private String hotel_id;
    private String hotel_name;
    private String location;
    private String about;

    public Hotel(String hotel_id, String hotel_name) {
    }
}
