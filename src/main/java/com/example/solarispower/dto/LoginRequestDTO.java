package com.example.solarispower.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String emailPessoa;
    private String senhaPessoa;
}