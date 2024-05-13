package com.example.userServiceUtils;

import lombok.Getter;
import lombok.Setter;
import java.util.List;


// Class for getting data from UserService

@Getter
@Setter
public class UserDto {

    private String name;

    private String email;

    private List<Role> roles;

    private boolean isEmailVerified;

}
