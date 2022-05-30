package com.inventi.bankingapplication;

import com.inventi.bankingapplication.service.FileService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class FileServiceConfiguration {

	@Bean
	@Primary
	public FileService fileService() {
		return Mockito.mock(FileService.class);
	}

}
