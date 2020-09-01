package com.rahadyan.lelanguser.service;

import com.rahadyan.lelanguser.dto.AddUserRequest;
import com.rahadyan.lelanguser.dto.TopupRequest;
import com.rahadyan.lelanguser.model.User;
import com.rahadyan.lelanguser.model.Wallet;
import com.rahadyan.lelanguser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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

    @Transactional
    public User topup(TopupRequest topupRequest){
        Optional<User> user = userRepository.findByEmail(topupRequest.getEmail());
        user.get().getWallet().setAmount(topupRequest.getAmount() + user.get().getWallet().getAmount());
        return userRepository.save(user.get());
    }

    @Transactional
    public void delete(String email){
        Optional<User> user = userRepository.findByEmail(email);
        userRepository.delete(user.get());
    }

}
