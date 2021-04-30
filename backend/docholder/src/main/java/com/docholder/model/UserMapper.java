package com.docholder.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserDto entityToDto(User user);

    User dtoToEntity(UserDto userDto);

    default List<UserDto> entityToDto(List<User> users){
        return users.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
