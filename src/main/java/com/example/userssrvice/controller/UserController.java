package com.example.userssrvice.controller;

import com.example.userssrvice.helper.JwtUtil;
import com.example.userssrvice.model.JwtRequest;
import com.example.userssrvice.model.JwtResponse;
import com.example.userssrvice.model.Users;
import com.example.userssrvice.service.CustomUserDetails;
import com.example.userssrvice.service.userServices;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private userServices userServices;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetails customUserDetails;

    private final org.slf4j.Logger logger= LoggerFactory.getLogger(UserController.class);


    @RequestMapping(name = "/token",method = RequestMethod.POST)
    public ResponseEntity<?> generateTokenUsername(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
        }catch (Exception exception){
            exception.printStackTrace();
            throw  new Exception("Bad Credential");
        }
        UserDetails userDetails =this.customUserDetails.loadUserByUsername(jwtRequest.getUsername());
        String token=this.jwtUtil.generateToken(userDetails);
        return  ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/save")
    public ResponseEntity<Users> create(@RequestBody Users u){
        Users users1=userServices.saveUser(u);
        return  ResponseEntity.status( HttpStatus.CREATED).body(users1);
    }

int retryCount=1;
    @GetMapping("/{id}")
    @CircuitBreaker(name = "ratingHotel",fallbackMethod = "ratinghotelfallback")
    @Retry(name = "ratingHotelServices",fallbackMethod = "ratinghotelfallback")
    @RateLimiter(name = "ratelimiter",fallbackMethod = "ratinghotelfallback")
    public  ResponseEntity<Users> getUsers(@PathVariable String id) {
        Users id1 = userServices.getUsers(id);
        logger.info("Retry {} "+retryCount);
        retryCount++;
        return ResponseEntity.ok(id1);
    }
    public  ResponseEntity<Users> ratinghotelfallback(String id,Exception exception){
        logger.info("fallback is excuted  services is down "+exception.getMessage());
         Users users=Users.builder().email("dummy@gmail.com").name("dummy").build();
         return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/all_users")
    public ResponseEntity<List<Users>> getAllUsers(){

        List  users=userServices.getAllUsers();
        return ResponseEntity.ok(users);

    }

}
