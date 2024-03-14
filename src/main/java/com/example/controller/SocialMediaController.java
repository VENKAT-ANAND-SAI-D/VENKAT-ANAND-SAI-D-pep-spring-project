package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@Controller
@RequestMapping
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> registerAccount(@RequestBody Account account){
        Optional<Account> accountOptional = accountService.registerAccount(account);
        if (accountOptional.isPresent()){
            return ResponseEntity.status(200).body(accountOptional.get());
        }
        else {
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Optional<Account> accountOptional = accountService.loginAccount(account);
        if (accountOptional.isPresent()){
            return ResponseEntity.status(200).body(accountOptional.get());
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message){
        Optional<Message> messageOptional = messageService.createMessage(message);
        if (messageOptional.isPresent()){
            return ResponseEntity.status(200).body(messageOptional.get());
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable Integer message_id){
        Optional<Message> message = messageService.findMessageById(message_id);
        return ResponseEntity.status(200).body(message.get());
    }

    @DeleteMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> deleteMessageById(@PathVariable Integer message_id){
        Optional<Integer> rowsAffected = messageService.deleteMessageById(message_id);
        return ResponseEntity.status(200).body(rowsAffected.get());
    }

    @PatchMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> updateMessageById(@PathVariable Integer message_id, @RequestParam String message_text){
        Integer rowsAffected = messageService.updateMessage(message_id, message_text);
        if (rowsAffected != 0){
            return ResponseEntity.status(200).body(rowsAffected);
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{account_id}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getMessagesByPostedBy(@PathVariable Integer account_id){
        List<Message> messages = messageService.getMessagesById(account_id);
        return ResponseEntity.status(200).body(messages);
    }


}
