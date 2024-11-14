package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChannelDTO {
    private String id;
    private String owner;
    private String topic;

    ChannelDTO(Channel channel){
        this.id = channel.getId();
        this.owner = channel.getOwner();
        this.topic = channel.getTopic();
    }
}
