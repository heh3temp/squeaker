package com.hamsterbusters.squeaker.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public ResponseEntity<UserDto> getUser(@PathVariable int userId) {
        return ResponseEntity.ok()
                .body(
                        new UserDto(
                                "jan",
                                "description",
                                null,
                                null,
                                0,
                                0
                        )
                );
    }
}
