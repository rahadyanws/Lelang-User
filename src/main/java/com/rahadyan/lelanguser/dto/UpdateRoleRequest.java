package com.rahadyan.lelanguser.dto;

import com.rahadyan.lelanguser.enums.UserRole;
import lombok.Data;

@Data
public class UpdateRoleRequest {
    public String email;
    public UserRole role;
}
