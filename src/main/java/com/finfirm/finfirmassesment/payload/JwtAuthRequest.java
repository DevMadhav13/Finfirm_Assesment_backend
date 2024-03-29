package com.finfirm.finfirmassesment.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JwtAuthRequest {

    private String username;

    private String password;

}


