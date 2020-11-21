package com.application.paymybuddy.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
import com.application.paymybuddy.dto.BankList;
import com.application.paymybuddy.dto.CreateTransaction;
import com.application.paymybuddy.dto.CreationBank;
import com.application.paymybuddy.dto.EditProfile;
import com.application.paymybuddy.interfaces.PasswordManager;
import com.application.paymybuddy.model.Holder;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc // need this in Spring Boot test
@TestMethodOrder(OrderAnnotation.class)
class integrationTestsUser {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private HolderDao holderDao;
	@Autowired
	private PasswordManager passwordManager;

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@DisplayName("Integration test of full process user")
	void test() throws Exception {
		String email = "test15@test.com";
		String friendEmail = "test2@test.com";
		String password = "AZERTY";
		EditProfile profile = new EditProfile(email, password, "Leo", "Dupassy", "7 route du test, une ville",
				"06161616418");
		mockMvc.perform(post("/Holder/create").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(put("/Profile/update").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(profile))).andExpect(status().isNoContent());
		Holder holder = holderDao.findByEmail(email);
		assertTrue(passwordManager.passwordDecoder(password, holder.getPassword()));
		mockMvc.perform(get("/Holder/connection").param("email", email).param("password", password)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		mockMvc.perform(post("/Connection/create").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
		mockMvc.perform(post("/Connection/create").param("email", friendEmail).param("emailFriend", email)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
		holder = holderDao.findByEmail(email);
		mockMvc.perform(get("/Connection").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
		assertEquals(1, holder.getHolderFriendship().size());
		assertEquals(1, holder.getHolderAsFriend().size());
		mockMvc.perform(delete("/Connection").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		holder = holderDao.findByEmail(email);
		assertFalse(holder.getHolderFriendship().get(0).isActive());
		mockMvc.perform(get("/Connection").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
		mockMvc.perform(post("/Connection/create").param("email", email).param("emailFriend", friendEmail)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
		holder = holderDao.findByEmail(email);
		assertTrue(holder.getHolderFriendship().get(0).isActive());
		mockMvc.perform(get("/Connection").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
		CreationBank creationBank = new CreationBank("test", "Somewhere", "FR7656612313131", "CIC321321");
		mockMvc.perform(post("/Bank/create").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isCreated());
		mockMvc.perform(get("/Bank").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1)));
		creationBank.setDomiciliation("Somewhere else");
		mockMvc.perform(put("/Bank/update").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isNoContent());
		mockMvc.perform(get("/Bank").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].name", is(creationBank.getName())))
				.andExpect(jsonPath("$[0].domiciliation", is(creationBank.getDomiciliation())))
				.andExpect(jsonPath("$[0].iban", is(creationBank.getIban())));
		BankList bankList = new BankList("FR7656612313131", "CIC321321");
		mockMvc.perform(post("/Movement/create").param("email", email).param("amount", "150.67")
				.content(asJsonString(bankList)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(get("/Movement/solde").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.solde", is(150.67)));
		mockMvc.perform(delete("/Bank/delete").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isOk());
		mockMvc.perform(post("/Movement/create").param("email", email).param("amount", "150.67")
				.content(asJsonString(bankList)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(post("/Movement/create").param("email", email).param("amount", "-150.67")
				.content(asJsonString(bankList)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertEquals("This bank has been disabled, please enable it",
						result.getResolvedException().getMessage()));
		holder = holderDao.findByEmail(email);
		assertFalse(holder.getBankId().get(0).isActive());
		mockMvc.perform(get("/Bank").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(0)));
		mockMvc.perform(post("/Bank/create").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isCreated());
		holder = holderDao.findByEmail(email);
		mockMvc.perform(post("/Movement/create").param("email", email).param("amount", "-150.67")
				.content(asJsonString(bankList)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		assertTrue(holder.getBankId().get(0).isActive());
		mockMvc.perform(get("/Bank").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1)));
		mockMvc.perform(get("/Movement").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(3)));
		CreateTransaction createTransaction = new CreateTransaction(friendEmail,
				"reimbursment for restaurant monday noon", 122.0);
		CreateTransaction createTransaction2 = new CreateTransaction(email, "reimbursment for restaurant monday noon",
				150.0);
		mockMvc.perform(post("/Transaction/create").param("email", email).content(asJsonString(createTransaction))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
		mockMvc.perform(post("/Transaction/create").param("email", friendEmail)
				.content(asJsonString(createTransaction2)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(get("/Transaction/all").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(2)));
		mockMvc.perform(get("/Transaction/from").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1)));
		mockMvc.perform(get("/Movement").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(5)));
		mockMvc.perform(delete("/Holder/delete").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		mockMvc.perform(get("/Holder/connection").param("email", email).param("password", password)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
				.andExpect(result -> assertEquals("This account do not exist",
						result.getResolvedException().getMessage()));
		mockMvc.perform(get("/Profile").param("email", email)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.email").value(IsNull.nullValue()));
		mockMvc.perform(get("/Connection").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
		holder = holderDao.findByEmail(email);
		assertFalse(holder.getHolderAsFriend().get(0).isActive());
		mockMvc.perform(get("/Bank").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(0)));
		mockMvc.perform(get("/Movement").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(5)));
		mockMvc.perform(get("/Transaction/all").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(2)));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
