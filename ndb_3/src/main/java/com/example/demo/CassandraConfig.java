package com.example.demo;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.cql.CqlTemplate;

@Configuration
public class CassandraConfig {
    @Autowired
    private CqlSession cqlSession;

    @Bean
    public CqlTemplate cqlTemplate(){
        return new CqlTemplate(cqlSession);
    }
}
