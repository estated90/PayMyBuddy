/**
 * 
 */
package com.application.paymybuddy.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.application.paymybuddy.ApplicationPayMyBuddy;
import com.application.paymybuddy.dao.ConnectionsDao;
import com.application.paymybuddy.dao.HolderDao;
import com.application.paymybuddy.model.Connections;
import com.application.paymybuddy.model.Holder;

/**
 * @author nicolas
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ApplicationPayMyBuddy.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class ConnectionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private HolderDao holderDao;
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
		email = "test2@test.com";
		friendEmail = "test3@test.com";
		mockMvc.perform(post("/Connection/create").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
		holder = holderDao.findByEmail(email);
		connection = holder.getHolderFriendship().stream().filter(
				str -> str.getHolderId().getEmail().equals(email) && str.getFriendId().getEmail().equals(friendEmail))
				.findAny().orElse(null);
		assertTrue(connection.isActive());
		assertEquals(email, connection.getHolderId().getEmail());
		mockMvc.perform(get("/Connection").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].email", is("test3@test.com")))
				.andExpect(jsonPath("$[1].lastName", is("Alvarez")));
		mockMvc.perform(delete("/Connection").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		holder = holderDao.findByEmail(email);
		connection = holder.getHolderFriendship().stream().filter(
				str -> str.getHolderId().getEmail().equals(email) & str.getFriendId().getEmail().equals(friendEmail))
				.findAny().orElse(null);
		assertFalse(connection.isActive());
		
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
				.andExpect(result -> assertEquals("Email not found", result.getResolvedException().getMessage()));
	}

	@Test
	@Order(4)
	@DisplayName("Create a new connection - main mail not in DB")
	void create_connection_miising_main_mail() throws Exception {
		email = "test40@test.com";
		friendEmail = "test@test.com";
		mockMvc.perform(post("/Connection/create").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("Email not found", result.getResolvedException().getMessage()));
	}

	@Test
	@Order(5)
	@DisplayName("Create a new connection - connection exist")
	void create_connection_already_in_db() throws Exception {
		email = "test4@test.com";
		friendEmail = "test2@test.com";
		mockMvc.perform(post("/Connection/create").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("Connections already exists",
						result.getResolvedException().getMessage()));
	}

	@Test
	@Order(6)
	@DisplayName("Create a new connection - connection exist but inactive")
	void create_connection_already_in_db_inactive() throws Exception {
		email = "test4@test.com";
		friendEmail = "test3@test.com";
		mockMvc.perform(post("/Connection/create").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}


	@Test
	@Order(9)
	@DisplayName("Delete a connection - friend mail not in DB")
	void delete_connection_missing_friend_mail() throws Exception {
		email = "test4@test.com";
		friendEmail = "test19@test.com";
		mockMvc.perform(delete("/Connection").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("Email not found", result.getResolvedException().getMessage()));
	}

	@Test
	@Order(10)
	@DisplayName("Delete a connection - main mail not in DB")
	void delete_connection_miising_main_mail() throws Exception {
		email = "test40@test.com";
		friendEmail = "test@test.com";
		mockMvc.perform(delete("/Connection").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("Email not found", result.getResolvedException().getMessage()));
	}

	@Test
	@Order(11)
	@DisplayName("Delete a connection - not in DB")
	void delete_connection_not_in_db() throws Exception {
		email = "test2@test.com";
		friendEmail = "test3@test.com";
		mockMvc.perform(delete("/Connection").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andExpect(
						result -> assertEquals("No connection to delete", result.getResolvedException().getMessage()));
	}

}
