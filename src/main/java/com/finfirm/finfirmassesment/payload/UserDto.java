package com.finfirm.finfirmassesment.payload;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.finfirm.finfirmassesment.constants.ValidationConstants;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	@Id
	private Integer id;
	
	@NotEmpty
	@Size( min=4 , message = ValidationConstants.USERNAME_VALIDATION)
	private String username;


	@Email(message = ValidationConstants.EMAIL_VALIDATION)
	private String email;
	
	@NotEmpty
	@Size(min =3 , max =10 , message = ValidationConstants.PASSWORD_VALIDATION)
	private String password;

}
