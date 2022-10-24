package com.example.hikaricpbasicsample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class HikaricpBasicSampleApplication implements CommandLineRunner {

	@Value("${spring.datasource.url}")
	private String jdbcUrl;

	public static void main(String[] args) {
		SpringApplication.run(HikaricpBasicSampleApplication.class, args);
	}

	@Override
	public void run(String... args) {
		final HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcUrl);

		log.info("Start");
		try (final HikariDataSource ds = new HikariDataSource(config);
		     final Connection conn = ds.getConnection();
			 final PreparedStatement statement = conn.prepareStatement("SELECT 1")) {
			statement.executeQuery();
			conn.close();
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getSQLState());
		}
		log.info("Done");
	}
}
