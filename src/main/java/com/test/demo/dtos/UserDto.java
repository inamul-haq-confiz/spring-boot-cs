package com.test.demo.dtos;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	public UserDto(String username, String password, String fullName) {
		this.username = username;
		this.password = password;
		this.fullName = fullName;
	}
	@NotNull(message = "Username can't be blank")
	@Email(message = "Email should be in correct format")
	private String username;
	@NotNull(message = "Password can't be blank")
	private String password;
	private String fullName;	
}
