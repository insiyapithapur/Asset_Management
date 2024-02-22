package com.elecon.asset_mgt.DTO;

import lombok.Data;

@Data
public class LoginRequest {
  private String employeeCode;
  private String password;
}
