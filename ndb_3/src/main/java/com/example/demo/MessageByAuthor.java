package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "MessagesByAuthor")
public class MessageByAuthor {

        @PrimaryKeyColumn(name = "channel_id", type = PrimaryKeyType.PARTITIONED)
        private String channelId;

        private String text;

        @PrimaryKeyColumn(name = "author", type = PrimaryKeyType.PARTITIONED)
        private String author;

        @PrimaryKeyColumn(name = "timestamp", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
        private Integer timestamp;

}
