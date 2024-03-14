package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository <Message, Integer>{

    @Query("SELECT m FROM Message m WHERE m.message_text = :messageText")
    public Optional<Message> findByMessageText(@Param("messageText") String messageText);

    @Query("SELECT m FROM Message m WHERE m.posted_by = :postedBy")
    public List<Message> findAllByPostedBy(@Param("postedBy") Integer postedBy);

}
