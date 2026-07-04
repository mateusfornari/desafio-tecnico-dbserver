package br.dev.fornarilabs.voting_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class VotingSystemApplicationTests {

	@Test
	void contextLoads() {
	}

}
