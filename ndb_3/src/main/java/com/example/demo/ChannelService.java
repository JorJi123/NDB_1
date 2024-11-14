package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.data.cassandra.core.query.Columns;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.cassandra.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private CassandraTemplate cassandraTemplate;

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

    public Message addMessage(String channelId, Message message){
        Channel channel = channelRepository.findById(channelId).orElseThrow();
        if (channel.getMessages() == null) {
            channel.setMessages(new ArrayList<>());
        }
        channel.getMessages().add(message);

        channelRepository.save(channel);
        return message;
    }

    public List<Message> getMessages(String channelId){
        Channel channel = channelRepository.findById(channelId).orElseThrow();
        return channel.getMessages();
    }

    public void addMembers(String channelId, String member){
        Channel channel = cassandraTemplate.selectOneById(channelId, Channel.class);
        if(channel == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        channel.addMember(member);
        cassandraTemplate.update(channel);
    }

    public List<String> getMembers(String id){
        Query query = Query.query(Criteria.where("id").is(id)).columns(Columns.from("members"));
        return cassandraTemplate.selectOne(query, Channel.class).getMembers();
    }

    public void deleteMember(String id, String member){
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = Update.empty().remove("members", member);
        cassandraTemplate.update(query, update, Channel.class);
    }
}
