package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
public class ChannelController {



        @Autowired
        private ChannelService channelService;


        @PutMapping("/channels")
        public ResponseEntity<ChannelDTO> saveChannel(@RequestBody Channel channel){
                channelService.saveChannel(channel);
                return ResponseEntity.status(HttpStatus.CREATED).body(new ChannelDTO(channel));
        }

        @GetMapping("/channels/{channelId}")
        public ResponseEntity findChannel(@PathVariable("channelId") String id){
               Channel channel = channelService.getChannel(id);
               if(channel == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                return ResponseEntity.status(HttpStatus.OK).body(new ChannelDTO(channel));
        }

        @DeleteMapping("/channels/{channelId}")
        public ResponseEntity<?> deleteChannel(@PathVariable("channelId") String id){
                channelService.deleteChannel(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @PutMapping("/channels/{channelId}/messages")
        public ResponseEntity addMessage(@PathVariable("channelId") String id, @RequestBody Message message){
                Message savedMessage = channelService.addMessage(id, message);
                return new ResponseEntity<>("{ \n" + "text: "+ savedMessage.getText() + " \n" + "author: " + savedMessage.getAuthor() + "\n}", HttpStatus.CREATED);
        }

        @GetMapping("/channels/{channelId}/messages")
        public ResponseEntity<List<Message>> GetMessages(@PathVariable("channelId") String id, @RequestParam(required = false) String author, @RequestParam(required = false) Integer startAt){
                return ResponseEntity.status(HttpStatus.OK).body(channelService.getMessages(id, author, startAt));
        }

        @PutMapping("channels/{channelId}/members")
        public ResponseEntity<String> addMember(@PathVariable("channelId") String id, @RequestBody Map<String, String> member){
                channelService.addMembers(id, member.get("member"));
                return ResponseEntity.status(HttpStatus.CREATED).body(member.get("member"));
        }

        @GetMapping("channels/{channelId}/members")
        public List<String> getMembers(@PathVariable("channelId") String id){

                return channelService.getMembers(id);
        }

        @DeleteMapping("channels/{channelId}/members/{memberId}")
        public ResponseEntity<?> deleteMember(@PathVariable String channelId, @PathVariable String memberId){
                channelService.deleteMember(channelId, memberId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
