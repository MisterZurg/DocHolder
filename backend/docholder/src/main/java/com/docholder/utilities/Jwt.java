package com.docholder.utilities;

import com.docholder.model.User;
import com.docholder.model.UserDto;

import java.util.Map;

public interface Jwt {
    String generateTokenByUser(UserDto userDto);
    Map<String, Object> getData(String token);
    boolean isValid(String token);
}
