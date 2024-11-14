package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;

    @Autowired
    ChannelService(ChannelRepository channelRepository){
        this.channelRepository = channelRepository;
    }


    public Channel saveChannel(Channel channel){
        channel.getMembers().add(channel.getOwner());
        channelRepository.save(channel);
        return channel;
    }


    public Channel getChannel(String primaryKey){

        return channelRepository.findById(primaryKey).orElse(null);
    }

    public void deleteChannel(String channelId){
         channelRepository.deleteById(channelId);
    }

//    public Message addMessage(String channelId, Message message){
//        Channel channel = channelRepository.findById(channelId).orElseThrow();
//        if (channel.getMessages() == null) {
//            channel.setMessages(new ArrayList<>());
//        }
//        channel.getMessages().add(message);
//
//        channelRepository.save(channel);
//        return message;
//    }
//
//    public List<Message> getMessages(String channelId){
//        Channel channel = channelRepository.findById(channelId).orElseThrow();
//        return channel.getMessages();
//    }
}
