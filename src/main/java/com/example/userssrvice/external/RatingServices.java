package com.example.userssrvice.external;

import com.example.userssrvice.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Service
@FeignClient(name = "RATING-SERVICES")
public interface RatingServices {


    @PostMapping("/rating/save_rating")
    public Rating saveData(@RequestBody  Rating rating);

}
