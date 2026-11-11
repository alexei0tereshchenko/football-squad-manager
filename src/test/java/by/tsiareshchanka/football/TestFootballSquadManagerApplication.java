package by.tsiareshchanka.football;

import org.springframework.boot.SpringApplication;

public class TestFootballSquadManagerApplication {

	public static void main(String[] args) {
		SpringApplication.from(FootballSquadManagerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
