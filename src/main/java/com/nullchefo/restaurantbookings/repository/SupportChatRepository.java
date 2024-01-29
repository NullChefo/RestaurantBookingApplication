package com.nullchefo.restaurantbookings.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nullchefo.restaurantbookings.entity.SentEmail;
import com.nullchefo.restaurantbookings.entity.SupportChat;

@Repository
public interface SupportChatRepository extends JpaRepository<SupportChat, UUID>, JpaSpecificationExecutor<SupportChat> {


}
