package com.rahadyan.lelanguser.dto;

import lombok.Data;

@Data
public class AddUserRequest {
    public String fullname;
    public String email;
    public String ktpNumber;
    public String phoneNumber;
    public String address;
}
