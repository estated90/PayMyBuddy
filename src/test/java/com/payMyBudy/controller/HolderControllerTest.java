package com.payMyBudy.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.IsNull;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.dao.ProfileDao;
import com.payMyBudy.model.Holder;
import com.payMyBudy.model.Profiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc // need this in Spring Boot test
@TestMethodOrder(OrderAnnotation.class)
class HolderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private HolderDao holderDao;
	@Autowired
	private ProfileDao profileDao;
	private Holder holder;
	private Profiles profile;

	@AfterEach
	void tearDown() throws Exception {
		if (profile!=null) profileDao.delete(profile);
		if (holder!=null) holderDao.delete(holder);
		holder = null;
		profile = null;
	}

	@Test
	@Order(1)
	@DisplayName("Create a user successfully")
	void test_creation_success() throws Exception {
		mockMvc.perform(
				post("/Holder/create").param("email", "test10@test.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(get("/Holder")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(5))).andExpect(jsonPath("$[4].email", is("test10@test.com")))
				.andExpect(jsonPath("$[4].active", is(true)))
				.andExpect(jsonPath("$[4].updatedAt").value(IsNull.nullValue()));
		mockMvc.perform(
				post("/Holder/create").param("email", "test10@test.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("Email already used", result.getResolvedException().getMessage()));
		holder = holderDao.findByEmail("test10@test.com");
		profile = profileDao.findByFk(holder);
		assertEquals(holder.getCreatedAt(), profile.getCreated());
		assertNotNull(profile.getProfileId());
		assertEquals(holder.getHolderId(), profile.getHolderId().getHolderId());

	}

	@Test
	@Order(2)
	@DisplayName("Fail to create user, due to wrong string sent")
	void test_no_mail_failure() throws Exception {
		mockMvc.perform(post("/Holder/create").param("email", "test6test.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(result -> assertEquals("String provided is not an email",
						result.getResolvedException().getMessage()));
		mockMvc.perform(get("/Holder")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$[3].email", is("test4@test.com"))).andExpect(jsonPath("$[3].active", is(true)));
	}

	@Test
	@Order(3)
	@DisplayName("Connection is successful")
	void test_connection_success() throws Exception {
		mockMvc.perform(get("/Connection").param("email", "test@test.com").param("password", "xyzert")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@Order(4)
	@DisplayName("Connection is failure due to email")
	void test_connection_failure_due_email() throws Exception {
		mockMvc.perform(get("/Connection").param("email", "test9@test.com").param("password", "xyzert")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
				.andExpect(result -> assertEquals("Unknown email or/and password",
						result.getResolvedException().getMessage()));
	}

	@Test
	@Order(5)
	@DisplayName("Connection is failure due to password")
	void test_connection_failure_due_pmassword() throws Exception {
		mockMvc.perform(get("/Connection").param("email", "test@test.com").param("password", "xyzer")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
				.andExpect(result -> assertEquals("Unknown email or/and password",
						result.getResolvedException().getMessage()));
	}

	@Test
	@Order(6)
	@DisplayName("Connection is failure due to password")
	void test_connection_failure_due_pmassword_email() throws Exception {
		mockMvc.perform(get("/Connection").param("email", "tes@test.com").param("password", "xyzer")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
				.andExpect(result -> assertEquals("Unknown email or/and password",
						result.getResolvedException().getMessage()));
	}

	@Test
	@Order(7)
	@DisplayName("Connection is failure because no email was used")
	void test_connection_failure_due_not_email() throws Exception {
		mockMvc.perform(get("/Connection").param("email", "testtest.com").param("password", "xyzer")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("String provided is not an email",
						result.getResolvedException().getMessage()));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
