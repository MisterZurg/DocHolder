package com.docholder.utilities;

import com.docholder.model.User;

public interface GenerateJwt {
    String generateTokenByUser(User user);
}
