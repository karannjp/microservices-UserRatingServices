package com.example.userssrvice.external;

import com.example.userssrvice.model.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelServices {

    @GetMapping("/hotel/{id}")
    public Hotel getHoltes(@PathVariable  String id);

}
