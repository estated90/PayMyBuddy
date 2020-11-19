package com.application.paymybuddy.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

import com.application.paymybuddy.dao.HolderDao;
import com.application.paymybuddy.dao.TransactionDao;
import com.application.paymybuddy.dto.CreateTransaction;
import com.application.paymybuddy.model.Holder;
import com.application.paymybuddy.model.Movement;
import com.application.paymybuddy.model.Transactions;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc // need this in Spring Boot test
@TestMethodOrder(OrderAnnotation.class)
class TransactionsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private HolderDao holderDao;
	@Autowired
	private TransactionDao transactionsDao;

	@AfterEach
	void tearDown() throws Exception {
	}


	@Test
	@Order(1)
	@DisplayName("Integration tests of transaction")
	void test_full_process_transaction_no_error() throws Exception {
		String email = "test1@test.com";
		mockMvc.perform(get("/Transaction/all").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].user", is(email)))
				.andExpect(jsonPath("$[0].toUser", is("test4@test.com"))).andExpect(jsonPath("$[0].amount", is(500.0)))
				.andExpect(jsonPath("$[0].fees", is(25.0))).andExpect(jsonPath("$[1].amount", is(300.0)))
				.andExpect(jsonPath("$[1].fees", is(15.0)));
		mockMvc.perform(get("/Transaction/to").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].user", is(email)))
				.andExpect(jsonPath("$[0].toUser", is("test4@test.com")));
		mockMvc.perform(get("/Transaction/from").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].user", is("test3@test.com")))
				.andExpect(jsonPath("$[0].toUser", is(email)));
		CreateTransaction createTransaction = new CreateTransaction("test2@test.com",
				"reimbursment for restaurant monday noon", 122.0);
		mockMvc.perform(post("/Transaction/create").param("email", email).content(asJsonString(createTransaction))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
		mockMvc.perform(get("/Transaction/all").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(3)));
		mockMvc.perform(
				get("/Transaction/from").param("email", "test2@test.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].user", is(email)))
				.andExpect(jsonPath("$[0].toUser", is("test2@test.com"))).andExpect(jsonPath("$[0].amount", is(122.0)))
				.andExpect(jsonPath("$[0].fees", is(6.10)));
		mockMvc.perform(get("/Movement").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(7))).andExpect(jsonPath("$[2].bankName").value(IsNull.nullValue()))
				.andExpect(jsonPath("$[2].transactionDescription", is(createTransaction.getDescription())))
				.andExpect(jsonPath("$[2].amount", is(-128.10)));
		mockMvc.perform(get("/Movement/solde").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.solde", is(46.9))).andExpect(jsonPath("$.account", is(email)));
		mockMvc.perform(get("/Movement").param("email", createTransaction.getFriendEmail()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].bankName").value(IsNull.nullValue()))
				.andExpect(jsonPath("$[0].transactionDescription", is(createTransaction.getDescription())))
				.andExpect(jsonPath("$[0].amount", is(122.0)));
		mockMvc.perform(get("/Movement/solde").param("email", createTransaction.getFriendEmail()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.solde", is(1622.0))).andExpect(jsonPath("$.account", is(createTransaction.getFriendEmail())));
		Holder holder = holderDao.findByEmail("test1@test.com");
		List<Movement> movements = holder.getMovement();
		Transactions transaction = null;
		for (Movement movement:movements) {
			if (movement.getTransaction().getDescription().equals("reimbursment for restaurant monday noon")){
				transaction = movement.getTransaction();
				transactionsDao.delete(transaction);
				break;
			}
		}
	}

	@Test
	@Order(2)
	@DisplayName("create a transaction when connection do not exist")
	void test_creation_without_connection() throws Exception {
		String email = "test2@test.com";
		CreateTransaction createTransaction = new CreateTransaction("test1@test.com",
				"reimbursment for restaurant monday noon", 122.0);
		mockMvc.perform(post("/Transaction/create").param("email", email).content(asJsonString(createTransaction))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andExpect(result -> assertEquals("No connection with user found",
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
