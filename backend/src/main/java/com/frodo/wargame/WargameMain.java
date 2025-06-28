package com.frodo.wargame;

import com.frodo.wargame.domain.Battle;
import com.frodo.wargame.repository.BattleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@SpringBootApplication
public class WargameMain {
	public static void main(String[] args) {
		SpringApplication.run(WargameMain.class, args);
	}

	@Bean
	public CommandLineRunner verifyDatabaseConnection(DataSource dataSource, BattleRepository battleRepository) {
		return args -> {
			try (Connection connection = dataSource.getConnection()) {
				System.out.println(String.format("✅✅✅ Connected to %s ✅✅✅", connection.getMetaData().getURL()));

			} catch (Exception e) {
				System.err.println("❌ Failed to connect to PostgreSQL: " + e.getMessage() + " ❌");
			}
			List<Battle> all = battleRepository.findAll();
			System.out.println(String.format("***************  All battles all=%s",all.size()));
		};
	}
}
