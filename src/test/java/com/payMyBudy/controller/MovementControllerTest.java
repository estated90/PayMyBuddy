package com.payMyBudy.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.IsNull;
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
import com.paymybuddy.dto.BankList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc // need this in Spring Boot test
@TestMethodOrder(OrderAnnotation.class)
class MovementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	@DisplayName("Integration tests of movements")
	void test_normal_cycle_of_movement() throws Exception {
		String email = "test1@test.com";
		mockMvc.perform(get("/Movement").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(4))).andExpect(jsonPath("$[0].bankName").value(IsNull.nullValue()))
				.andExpect(jsonPath("$[0].transactionDescription", is("a test description")))
				.andExpect(jsonPath("$[0].amount", is(-525.0))).andExpect(jsonPath("$[1].amount", is(300.0)))
				.andExpect(jsonPath("$[3].amount", is(-100.6)));
		mockMvc.perform(get("/Movement/solde").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.solde", is(175.0))).andExpect(jsonPath("$.account", is(email)));
		BankList bankList = new BankList("FR761354163213216543013354", "CIC23132ZZ");
		mockMvc.perform(post("/Movement/create").param("email", email).param("amount", "-150.67")
				.content(asJsonString(bankList)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(get("/Movement/solde").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.solde", is(24.33))).andExpect(jsonPath("$.account", is(email)));
		mockMvc.perform(post("/Movement/create").param("email", email).param("amount", "-50.00")
				.content(asJsonString(bankList)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("Amount to sent is higher than the solde of the account",
						result.getResolvedException().getMessage()));
		mockMvc.perform(post("/Movement/create").param("email", email).param("amount", "150.67")
				.content(asJsonString(bankList)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	@Order(2)
	@DisplayName("Unknomn bank account")
	void test_bank_account_not_in_db() throws Exception {
		String email = "test1@test.com";
		BankList bankList = new BankList("FR76135416321321654301", "CIC2313");
		mockMvc.perform(post("/Movement/create").param("email", email).param("amount", "-150.67")
				.content(asJsonString(bankList)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(result -> assertEquals("This bank do not exist",
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
