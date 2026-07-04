package br.dev.fornarilabs.voting_system;

import org.springframework.boot.SpringApplication;

public class TestVotingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(VotingSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
