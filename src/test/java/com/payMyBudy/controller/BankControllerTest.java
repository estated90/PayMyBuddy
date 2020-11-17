/**
 * 
 */
package com.payMyBudy.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.payMyBudy.dto.CreationBank;
import com.payMyBudy.model.Bank;
import com.payMyBudy.model.Holder;

/**
 * @author nicol
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc // need this in Spring Boot test
@TestMethodOrder(OrderAnnotation.class)
class BankControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private HolderDao holderDao;
	private Holder holder;

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDownAfterClass() throws Exception {
	}

	@Test
	@Order(1)
	@DisplayName("All process life of bank")
	void creating_bank_modifying_deleting() throws Exception {
		String email = "test1@test.com";
		mockMvc.perform(get("/Bank").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].name", is("My CIC")))
				.andExpect(jsonPath("$[0].domiciliation", is("CIC somewhere")))
				.andExpect(jsonPath("$[0].iban", is("FR761354163213216543013354")));
		CreationBank creationBank = new CreationBank("test", "Somewhere", "FR7656612313131", "CIC321321");
		mockMvc.perform(post("/Bank/create").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isCreated());
		mockMvc.perform(get("/Bank").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[1].name", is(creationBank.getName())))
				.andExpect(jsonPath("$[1].domiciliation", is(creationBank.getDomiciliation())))
				.andExpect(jsonPath("$[1].iban", is(creationBank.getIban())));
		holder = holderDao.findByEmail(email);
		Bank bank = holder.getBankId().stream().filter(
				str -> str.getIban().equals(creationBank.getIban()) & str.getRib().equals(creationBank.getRib()))
				.findAny().orElse(null);
		assertNotNull(bank.getCreated());
		assertNull(bank.getUpdate());
		assertTrue(bank.isActive());
		creationBank.setDomiciliation("Somewhere else");
		mockMvc.perform(put("/Bank/update").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isNoContent());
		mockMvc.perform(get("/Bank").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[1].name", is(creationBank.getName())))
				.andExpect(jsonPath("$[1].domiciliation", is(creationBank.getDomiciliation())))
				.andExpect(jsonPath("$[1].iban", is(creationBank.getIban())));
		holder = holderDao.findByEmail(email);
		Bank bank2 = holder.getBankId().stream().filter(
				str -> str.getIban().equals(creationBank.getIban()) & str.getRib().equals(creationBank.getRib()))
				.findAny().orElse(null);
		assertEquals(bank.getCreated(), bank2.getCreated());
		assertNotNull(bank2.getUpdate());
		mockMvc.perform(delete("/Bank/delete").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isOk());
		mockMvc.perform(get("/Bank").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1)));
		holder = holderDao.findByEmail(email);
		Bank bank3 = holder.getBankId().stream().filter(
				str -> str.getIban().equals(creationBank.getIban()) & str.getRib().equals(creationBank.getRib()))
				.findAny().orElse(null);
		assertFalse(bank3.isActive());
		mockMvc.perform(post("/Bank/create").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isCreated());
		mockMvc.perform(get("/Bank").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[1].name", is(creationBank.getName())))
				.andExpect(jsonPath("$[1].domiciliation", is(creationBank.getDomiciliation())))
				.andExpect(jsonPath("$[1].iban", is(creationBank.getIban())));
		holder = holderDao.findByEmail(email);
		Bank bank4 = holder.getBankId().stream().filter(
				str -> str.getIban().equals(creationBank.getIban()) & str.getRib().equals(creationBank.getRib()))
				.findAny().orElse(null);
		assertTrue(bank4.isActive());
	}

	@Test
	@Order(2)
	@DisplayName("Mail is not in DB")
	void call_method_mail_not_in_db() throws Exception {
		String email = "test10@test.com";
		mockMvc.perform(get("/Bank").param("email", email).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(result -> assertEquals("Email of bank holder not found",
						result.getResolvedException().getMessage()));
		CreationBank creationBank = new CreationBank("test", "Somewhere", "FR7656612313131", "CIC321321");
		mockMvc.perform(post("/Bank/create").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("Email of bank holder not found",
						result.getResolvedException().getMessage()));
		mockMvc.perform(put("/Bank/update").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("Email of bank holder not found",
						result.getResolvedException().getMessage()));
		mockMvc.perform(delete("/Bank/delete").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("Email of bank holder not found",
						result.getResolvedException().getMessage()));
	}

	@Test
	@Order(3)
	@DisplayName("Creation of bank already active in db")
	void call_post_bank_already_in_db() throws Exception {
		String email = "test1@test.com";
		CreationBank creationBank = new CreationBank("My CIC", "CIC somewhere", "FR761354163213216543013354", "CIC23132ZZ");
		mockMvc.perform(post("/Bank/create").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isNotFound())
				.andExpect(result -> assertEquals("This bank is already attach to the user",
						result.getResolvedException().getMessage()));
	}
	
	@Test
	@Order(4)
	@DisplayName("Update of bank not in db")
	void call_put_bank_not_in_db() throws Exception {
		String email = "test1@test.com";
		CreationBank creationBank = new CreationBank("My CIC", "CIC somewhere", "FR7613541632132165430133", "CIC23132ZZ");
		mockMvc.perform(put("/Bank/update").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isNotFound())
				.andExpect(result -> assertEquals("This bank do not exist",
						result.getResolvedException().getMessage()));
	}
	
	@Test
	@Order(5)
	@DisplayName("Delete of bank not in db")
	void call_delete_bank_not_in_db() throws Exception {
		String email = "test1@test.com";
		CreationBank creationBank = new CreationBank("My CIC", "CIC somewhere", "FR7613541632132165430133", "CIC23132ZZ");
		mockMvc.perform(delete("/Bank/delete").param("email", email).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(creationBank))).andExpect(status().isNotFound())
				.andExpect(result -> assertEquals("This bank do not exist",
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
