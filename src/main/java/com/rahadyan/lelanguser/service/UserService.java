package com.rahadyan.lelanguser.service;

import com.rahadyan.lelanguser.dto.AddUserRequest;
import com.rahadyan.lelanguser.dto.TopupRequest;
import com.rahadyan.lelanguser.dto.UpdateRoleRequest;
import com.rahadyan.lelanguser.enums.UserRole;
import com.rahadyan.lelanguser.model.User;
import com.rahadyan.lelanguser.model.Wallet;
import com.rahadyan.lelanguser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        this.userRepository.findAll().forEach(auctionItem -> users.add(auctionItem));
        return users;
    }

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
                .role(UserRole.ROLE_BIDDER)
                .wallet(new Wallet(null, 0))
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public User topup(TopupRequest topupRequest){
        Optional<User> user = userRepository.findByEmail(topupRequest.getEmail());
        user.get().getWallet().setAmount(topupRequest.getAmount() + user.get().getWallet().getAmount());
        userRepository.insertWalletHistory(UUID.randomUUID().toString(), topupRequest.getAmount(), "TopUp-" + user.get().getId(), "TOPUP",
                LocalDateTime.now(), user.get().getId(),user.get().getWallet().getId());
        return userRepository.save(user.get());
    }

    @Transactional
    public void delete(String email){
        Optional<User> user = userRepository.findByEmail(email);
        userRepository.delete(user.get());
    }

    @Transactional
    public User updateRole(UpdateRoleRequest updateRoleRequest){
        Optional<User> user = userRepository.findByEmail(updateRoleRequest.getEmail());
        user.get().setRole(updateRoleRequest.getRole());
        return userRepository.save(user.get());
    }

}
