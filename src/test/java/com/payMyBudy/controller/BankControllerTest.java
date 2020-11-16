/**
 * 
 */
package com.payMyBudy.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.dao.ProfileDao;
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
	@Autowired
	private Bank bank;
	private Holder holder;

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() throws Exception {
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
		//bank = holder.getBankId().stream().filter(
		//		str -> str.getIban().equals(creationBank.getIban()) & str.getRib().equals(creationBank.getRib()))
		//		.findAny().orElse(null);
		assertNotNull(bank.getCreated());
		assertNull(bank.getUpdate());
		assertTrue(bank.isActive());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
