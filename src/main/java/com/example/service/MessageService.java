package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountService accountService;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService){
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    public Optional<Message> createMessage(Message message){
        if (message.getMessage_text().isBlank() == false && message.getMessage_text().length() <= 255){
            if (accountService.findById(message.getPosted_by())){
                messageRepository.save(message);
                Optional<Message> messageOptional = messageRepository.findByMessage_text(message.getMessage_text());
                return messageOptional;
            }
        }
        return null;
    }

    public List<Message> getAllMessages(){
        List<Message> messages = messageRepository.findAll();
        return messages;
    }

    public Message findMessageById(Integer id){
        Message message = messageRepository.findById(id).get();
        return message;
    }

    public Integer deleteMessageById(Integer id){
        Optional<Message> messageOptional = messageRepository.findById(id);
        messageRepository.deleteById(id);
        if (messageOptional.isPresent()){
            return 1;
        }
        return null;
    }

    public Integer updateMessage(Integer id, String message_text){
        if (message_text.isBlank() != false && message_text.length() <= 255){
            Optional<Message> messageOptional = messageRepository.findById(id);
            if (messageOptional.isPresent()){
                Message message = messageOptional.get();
                message.setMessage_text(message_text);
                messageRepository.save(message);
                return 1;
            }
        }
        return null;
    }

    public List<Message> getMessagesById(Integer posted_by){
        List<Message> messages = messageRepository.findAllByPosted_by(posted_by);
        return messages;
    }
}
