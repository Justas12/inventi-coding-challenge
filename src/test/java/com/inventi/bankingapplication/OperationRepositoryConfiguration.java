package com.inventi.bankingapplication;

import com.inventi.bankingapplication.repository.OperationRepository;
import com.inventi.bankingapplication.service.OperationService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class OperationRepositoryConfiguration {

	@Bean
	@Primary
	public OperationRepository operationRepository() {
		return Mockito.mock(OperationRepository.class);
	}

}
