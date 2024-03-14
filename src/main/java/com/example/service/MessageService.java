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
                Optional<Message> messageOptional = messageRepository.findByMessageText(message.getMessage_text());
                return messageOptional;
            }
        }
        return Optional.empty();
    }

    public List<Message> getAllMessages(){
        List<Message> messages = messageRepository.findAll();
        return messages;
    }

    public Optional<Message> findMessageById(Integer id){
        Optional<Message> message = messageRepository.findById(id);
        return message;
    }

    public Optional<Integer> deleteMessageById(Integer id){
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()){
            messageRepository.deleteById(id);
            Optional<Integer> rowsAffected = Optional.ofNullable(1);
            return rowsAffected;
        }
        Optional<Integer> rowsAffected = Optional.ofNullable(null);
        return rowsAffected;
    }

    public Integer updateMessage(Integer id, String message_text){
        if (message_text.isBlank() == false && message_text.length() <= 255){
            Optional<Message> messageOptional = messageRepository.findById(id);
            if (messageOptional.isPresent()){
                Message message = messageOptional.get();
                message.setMessage_text(message_text);
                messageRepository.save(message);
                return 1;
            }
        }
        return 0;
    }

    public List<Message> getMessagesById(Integer posted_by){
        List<Message> messages = messageRepository.findAllByPostedBy(posted_by);
        return messages;
    }
}
