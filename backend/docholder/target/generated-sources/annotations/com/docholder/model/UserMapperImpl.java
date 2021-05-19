package com.docholder.model;

import java.util.Arrays;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-17T22:42:37+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_282 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto entityToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setName( user.getName() );
        userDto.setSurname( user.getSurname() );
        userDto.setEmail( user.getEmail() );
        userDto.setPassword( user.getPassword() );
        userDto.setPhone( user.getPhone() );
        userDto.setGender( user.getGender() );
        userDto.setCompany_id( user.getCompany_id() );
        userDto.setRole( user.getRole() );
        byte[] avatar = user.getAvatar();
        if ( avatar != null ) {
            userDto.setAvatar( Arrays.copyOf( avatar, avatar.length ) );
        }

        return userDto;
    }

    @Override
    public User dtoToEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setName( userDto.getName() );
        user.setSurname( userDto.getSurname() );
        user.setEmail( userDto.getEmail() );
        user.setPassword( userDto.getPassword() );
        user.setPhone( userDto.getPhone() );
        user.setGender( userDto.getGender() );
        user.setCompany_id( userDto.getCompany_id() );
        user.setRole( userDto.getRole() );
        byte[] avatar = userDto.getAvatar();
        if ( avatar != null ) {
            user.setAvatar( Arrays.copyOf( avatar, avatar.length ) );
        }

        return user;
    }
}
