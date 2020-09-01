package com.rahadyan.lelanguser.service;

import com.rahadyan.lelanguser.dto.AddUserRequest;
import com.rahadyan.lelanguser.model.User;
import com.rahadyan.lelanguser.model.Wallet;
import com.rahadyan.lelanguser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User findByEmail(String email) throws Exception {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new Exception("User not found"));
    }

    @Transactional
    public User createUser(AddUserRequest addUserRequest) {
        User user = User.builder()
                .fullname(addUserRequest.fullname)
                .email(addUserRequest.email)
                .ktpNumber(addUserRequest.ktpNumber)
                .phoneNumber(addUserRequest.phoneNumber)
                .address(addUserRequest.address)
                .wallet(new Wallet(null, 0))
                .build();
        return userRepository.save(user);
    }


}
