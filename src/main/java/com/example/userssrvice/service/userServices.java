package com.example.userssrvice.service;

import com.example.userssrvice.exception.ResourceNotFound;
import com.example.userssrvice.external.HotelServices;
import com.example.userssrvice.model.Hotel;
import com.example.userssrvice.model.Rating;
import com.example.userssrvice.model.Users;
import com.example.userssrvice.repo.userRepoitory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class userServices {

@Autowired
  private userRepoitory userRepoitory;

@Autowired
private RestTemplate restTemplate;

@Autowired
private HotelServices hotelServices;

@Autowired
public  userServices(userRepoitory userRepoitory,RestTemplate restTemplate){
    this.userRepoitory=userRepoitory;
    this.restTemplate=restTemplate;

}


private final org.slf4j.Logger logger= LoggerFactory.getLogger(userServices.class);



    public Users saveUser(Users users) {
        String random_id= UUID.randomUUID().toString();
        users.setUser_id(random_id);
        return userRepoitory.save(users);
    }


    public List<Users> getAllUsers() {
    return userRepoitory.findAll();


    }

//http://localhost:8003/rating/users/6be5a1b6-3664-4f64-9494-889733a24837
    public Users getUsers(String user_id) {
        Users users= userRepoitory.findById(user_id).orElseThrow(()-> new ResourceNotFound("users id is not found "+user_id));
        Rating[] usersrating=restTemplate.getForObject("http://RATING-SERVICES/rating/users/"+users.getUser_id(), Rating[].class);
        logger.info("{}",usersrating);
       List<Rating> list=Arrays.stream(usersrating).toList();

 List<Rating> ratingLsit= list.stream().map(ratings_list ->{
   // ResponseEntity<Hotel> hotelResponseEntity =restTemplate.getForEntity("http://HOTEL-SERVICE/hotel/"+ratings_list.getHotelId(), Hotel.class);
    Hotel hotel= hotelServices.getHoltes(ratings_list.getHotelId());
    ratings_list.setHotel(hotel);

    return  ratings_list;
}).collect(Collectors.toList());
users.setRatings(ratingLsit);
        return  users;
    }
}
