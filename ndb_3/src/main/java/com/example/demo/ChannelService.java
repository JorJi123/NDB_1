package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Columns;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.cassandra.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Channel channel = cassandraTemplate.selectOneById(channelId, Channel.class);
        if (channel.getMessages() == null) {
            channel.setMessages(new ArrayList<>());
        }
        message.setTimestamp(channel.getMessages().size());
        channel.getMessages().add(message);
        cassandraTemplate.update(channel);
        return message;
    }

    public List<Message> getMessages(String channelId, String author, Integer startAt){
        Query query = Query.query(Criteria.where("id").is(channelId)).columns(Columns.from("messages"));;
    if(startAt != null) {
        return cassandraTemplate.selectOne(query, Channel.class).getMessages().stream()
                .filter(message -> (author == null || author.equals(message.getAuthor())) &&
                        (message.getTimestamp() >= startAt))
                .collect(Collectors.toList());
    }
        return cassandraTemplate.selectOne(query, Channel.class).getMessages().stream()
                .filter(message -> (author == null || author.equals(message.getAuthor())))
                .collect(Collectors.toList());
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
