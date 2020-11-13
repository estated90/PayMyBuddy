/**
 * 
 */
package com.payMyBudy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

	@Test
	@Order(1)
	@DisplayName("Create a new connection - both user exist")
	void create_connection_without_specific() throws Exception {
		String email = "test4@test.com";
		String friendEmail = "test2@test.com";
		mockMvc.perform(post("/Connection/create").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
		Holder holder = holderDao.findByEmail(email);
		Connections listConnections = holder.getMainHolder().stream()
				.filter(str -> str.getHolderId().getEmail().equals(email) && str.getHolderId().getEmail().equals(friendEmail)).
				findAny().orElse(null);
		assertEquals(email, listConnections.getHolderId().getEmail());

	}

}
