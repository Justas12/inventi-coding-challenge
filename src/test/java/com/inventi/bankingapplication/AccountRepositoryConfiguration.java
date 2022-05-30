package com.inventi.bankingapplication;

import com.inventi.bankingapplication.repository.AccountRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AccountRepositoryConfiguration {

	@Bean
	@Primary
	public AccountRepository accountRepository() {
		return Mockito.mock(AccountRepository.class);
	}

}
