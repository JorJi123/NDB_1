package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "Channel")
public class Channel {
    @PrimaryKey
    private String id;
    private String owner;
    private String topic;
    private List<String> members = new ArrayList<>();
  //  private List<Message> messages = new ArrayList<>();

}
