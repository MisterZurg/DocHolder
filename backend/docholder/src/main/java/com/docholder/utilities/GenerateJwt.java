package com.docholder.utilities;

import com.docholder.model.User;
import com.docholder.model.UserDto;

public interface GenerateJwt {
    String generateTokenByUser(UserDto userDto);
}
