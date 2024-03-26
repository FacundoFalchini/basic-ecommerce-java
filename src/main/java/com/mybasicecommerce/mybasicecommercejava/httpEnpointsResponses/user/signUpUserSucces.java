package com.mybasicecommerce.mybasicecommercejava.httpEnpointsResponses.user;

import com.mybasicecommerce.mybasicecommercejava.models.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class signUpUserSucces {
    private String token;
    private int expiresIn;
    private User newUser;
    private String message;
}