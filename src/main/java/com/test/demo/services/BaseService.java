package com.test.demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {
	@Autowired
	private ModelMapper mapper;
	protected <T> T mapTo(Object source,  Class<T> dest)
	{
		return mapper.map(source, dest);		
	}
}
