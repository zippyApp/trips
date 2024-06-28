package com.zippy.trips.clients;

import com.zippy.trips.dto.PersonalInformationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "usersFeign", url = "http://localhost:8081/api/v1/users")
public interface UsersClient {

    @GetMapping("/")
    List<PersonalInformationDTO> getAllUsers();

    @GetMapping("/{id}")
    PersonalInformationDTO getUserById(@PathVariable Long id);

    @PostMapping("/new")
    PersonalInformationDTO addUser(@RequestBody PersonalInformationDTO personalInformationDTO);

    @DeleteMapping("/{id}")
    Void deletePersonalInformation(@PathVariable Long id);



}
