package com.rahadyan.lelanguser.repository;

import com.rahadyan.lelanguser.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, String> {}