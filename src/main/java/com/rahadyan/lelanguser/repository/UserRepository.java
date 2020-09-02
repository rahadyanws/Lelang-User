package com.rahadyan.lelanguser.repository;

import com.rahadyan.lelanguser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query(
            value =
                    "insert into wallet_history (id,amount,note,payment,transaction_time,user_id,wallet_id) values (:id,:amount,:note,:payment,:transaction_time,:user_id,:wallet_id)",
            nativeQuery = true)
    void insertWalletHistory(@Param("id") String id, @Param("amount") double amount, @Param("note") String note, @Param("payment") String payment,
                             @Param("transaction_time") LocalDateTime transaction_time, @Param("user_id") String user_id, @Param("wallet_id") String wallet_id);
}
