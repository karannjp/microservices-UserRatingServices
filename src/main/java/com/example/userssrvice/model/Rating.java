package com.example.userssrvice.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Rating {
    private String rating_id;
    private String userId;
    private String hotelId;
    private int rating ;
    private String feedback;
    private  Hotel hotel;

    public Rating(String userId, String hotelId, int rating) {
    }


}
