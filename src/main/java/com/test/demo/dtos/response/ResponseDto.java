package com.test.demo.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseDto {
	
	public ResponseDto(String message, String statusCode, String data) {
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
	}
	private String message;
    private String statusCode;
    private String data;
}
