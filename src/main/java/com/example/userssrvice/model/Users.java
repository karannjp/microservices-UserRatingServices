package com.example.userssrvice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class Users {

    @Id
    private  String user_id;
    private  String name;
    private String email;
    @Transient
    private List<Rating> ratings=new ArrayList<>();

    public Users(String user_id, String name, String email) {
        this.user_id=user_id;
        this.name=name;
        this.email=email;
    }
}
