package com.sidenow.freshgreenish.domain.user.controller;

import com.sidenow.freshgreenish.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired private UserRepository userRepository;


}
