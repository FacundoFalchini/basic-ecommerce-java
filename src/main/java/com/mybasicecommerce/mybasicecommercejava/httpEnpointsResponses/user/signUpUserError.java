package com.mybasicecommerce.mybasicecommercejava.httpEnpointsResponses.user;

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
public class signUpUserError {
    private String message;
}

