package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChannelController {



        @Autowired
        private ChannelService channelService;


        @PutMapping("/channels")
        public ResponseEntity saveChannel(@RequestBody Channel channel){
                channelService.saveChannel(channel);
                return new ResponseEntity<>("{ \n" + "id: "+ channel.getId() + " \n}", HttpStatus.OK);
        }

        @GetMapping("/channels/{channelId}")
        public ResponseEntity findChannel(@PathVariable("channelId") String id){

               Channel channel = channelService.getChannel(id);
                return new ResponseEntity<>("{ \n" + "id: "+ channel.getId() + " \n"
                        + "owner: " + channel.getOwner() + "\n"
                        + "topic: " + channel.getTopic()+ "\n}", HttpStatus.OK);
        }

        @DeleteMapping("/channels/{channelId}")
        public void deleteChannel(@PathVariable("channelId") String id){
                channelService.deleteChannel(id);
        }

//        @PutMapping("/channels/{channelId}/messages")
//        public ResponseEntity addMessage(@PathVariable("channelId") String id, @RequestBody Message message){
//                Message savedMessage = channelService.addMessage(id, message);
//                return new ResponseEntity<>("{ \n" + "text: "+ savedMessage.getText() + " \n" + "author: " + savedMessage.getAuthor() + "\n}", HttpStatus.OK);
//        }
//
//        @GetMapping("/channels/{channelId}/messages")
//        public List<Message> GetMessages(@PathVariable("channelId") String id){
//
//                return channelService.getMessages(id);
//        }
}
