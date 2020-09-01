package com.rahadyan.lelanguser.dto;

import lombok.Data;

@Data
public class TopupRequest {
    public String email;
    public double amount;
}
