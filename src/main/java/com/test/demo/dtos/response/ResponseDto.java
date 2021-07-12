package com.test.demo.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseDto {
	
	public ResponseDto(String message, String statusCode, Object data) {
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
	}
	private String message;
    private String statusCode;
    private Object data;
}
