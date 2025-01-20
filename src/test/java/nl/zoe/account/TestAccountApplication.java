package nl.zoe.account;

import org.springframework.boot.SpringApplication;

public class TestAccountApplication {

	public static void main(String[] args) {
		SpringApplication.from(AccountApplication::main).run(args);
	}
}
