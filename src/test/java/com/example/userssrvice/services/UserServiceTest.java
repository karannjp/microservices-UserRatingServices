package com.example.userssrvice.services;

import com.example.userssrvice.external.HotelServices;
import com.example.userssrvice.model.Hotel;
import com.example.userssrvice.model.Rating;
import com.example.userssrvice.model.Users;
import com.example.userssrvice.repo.userRepoitory;
import com.example.userssrvice.service.userServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private userServices userServices;

    @MockBean
    private userRepoitory userRepoitory;

    private Users users;
    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private HotelServices hotelServices;



    @Test
    public void getAllUsersTest(){
        when(userRepoitory.findAll()).thenReturn(Stream.of(new Users("1","karan","karan@gmail.com"),new Users("2","kp","kp@gmail.com"))
                .collect(Collectors.toList()));
        assertEquals(2,userServices.getAllUsers().size());
    }


    @Test
  public void saveTest(){
        Users users1=new Users("111","karan","karan@gmail.com");
        when(userRepoitory.save(users1)).thenReturn(users1);
      assertEquals(users1,userServices.saveUser(users1));


  }
    @Test
    public void testGetUsersSuccess() {
        String userId = "123";
        Users user = new Users();
        user.setUser_id(userId);

        Rating[] ratings = {
                new Rating("1", "Hotel1", 4),
                new Rating("2", "Hotel2", 5)
        };

        when(userRepoitory.findById(userId)).thenReturn(Optional.of(user));
        when(restTemplate.getForObject("http://RATING-SERVICES/rating/users/" + userId, Rating[].class))
                .thenReturn(ratings);

        Hotel hotel1 = new Hotel("1","khusboo hotel","pune","good hotel");
        Hotel hotel2 = new Hotel("2","khusboo hotel","pune","good hotel");

        when(hotelServices.getHoltes("1")).thenReturn(hotel1);
        when(hotelServices.getHoltes("2")).thenReturn(hotel2);

        Users result = userServices.getUsers(userId);

        assertNotNull(result);
        assertEquals(userId, result.getUser_id());
        assertEquals(2, result.getRatings().size());
        //assertEquals(hotel1, result.getRatings().get(0).getHotel());
       // assertEquals(hotel2, result.getRatings().get(1).getHotel());
        verify(userRepoitory, times(1)).findById(userId);
        verify(restTemplate, times(1)).getForObject("http://RATING-SERVICES/rating/users/" + userId, Rating[].class);

//        verifyNoMoreInteractions(hotelServices);

    }



}
