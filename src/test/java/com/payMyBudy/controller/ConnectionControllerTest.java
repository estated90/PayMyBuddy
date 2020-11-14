/**
 * 
 */
package com.payMyBudy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.payMyBudy.dao.ConnectionsDao;
import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.dao.ProfileDao;
import com.payMyBudy.model.Connections;
import com.payMyBudy.model.Holder;

/**
 * @author nicolas
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc // need this in Spring Boot test
@TestMethodOrder(OrderAnnotation.class)
class ConnectionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private HolderDao holderDao;
	@Autowired
	private ProfileDao profileDao;
	@Autowired
	private ConnectionsDao connectionDao;

	private String email;
	private String friendEmail;
	Holder holder;
	private Connections connection;

	@AfterEach
	void tearDown() throws Exception {
		if (connection != null)
			connectionDao.delete(connection);
		connection = null;
	}

	@Test
	@Order(1)
	@DisplayName("Create a new connection - both user exist")
	void create_connection_without_specific() throws Exception {
		email = "test4@test.com";
		friendEmail = "test2@test.com";
		mockMvc.perform(post("/Connection/create").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
		holder = holderDao.findByEmail(email);
		connection = holder.getMainHolder().stream().filter(
				str -> str.getHolderId().getEmail().equals(email) && str.getFriendId().getEmail().equals(friendEmail))
				.findAny().orElse(null);
		assertEquals(email, connection.getHolderId().getEmail());
	}

	@Test
	@Order(2)
	@DisplayName("Create a new connection - both mail are the same")
	void create_connection_with_two_same_mail() throws Exception {
		email = "test4@test.com";
		friendEmail = "test4@test.com";
		mockMvc.perform(post("/Connection/create").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("Emails provided are the same",
						result.getResolvedException().getMessage()));

	}
	
	@Test
	@Order(3)
	@DisplayName("Create a new connection - friend mail not in DB")
	void create_connection_miising_friend_mail() throws Exception {
		email = "test4@test.com";
		friendEmail = "test19@test.com";
		mockMvc.perform(post("/Connection/create").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("Email not found",
						result.getResolvedException().getMessage()));

	}

}
