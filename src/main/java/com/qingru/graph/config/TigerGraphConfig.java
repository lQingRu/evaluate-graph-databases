package com.qingru.graph.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TigerGraphConfig {

    private static HikariConfig config = new HikariConfig();

    @Bean
    public DataSource dataSource() {
        config.setDriverClassName("com.tigergraph.jdbc.Driver");
        config.setJdbcUrl("jdbc:tg:http://localhost:14240");
        config.setUsername("tigergraph");
        config.setPassword("tigergraph");
        config.addDataSourceProperty("debug", "1");
        config.addDataSourceProperty("graph", "social");
        return new HikariDataSource(config);
    }
}
