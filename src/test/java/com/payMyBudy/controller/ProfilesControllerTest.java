/**
 * 
 */
package com.payMyBudy.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.payMyBudy.dto.EditProfile;
import com.payMyBudy.model.Holder;
import com.payMyBudy.model.Profiles;

/**
 * @author nicolas
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc // need this in Spring Boot test
@TestMethodOrder(OrderAnnotation.class)
class ProfilesControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private HolderDao holderDao;
	@Autowired
	private ProfileDao profileDao;
	
	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	@Order(1)
	@DisplayName("Update a profile successfully")
	void update_profile_successfully() throws Exception {
		EditProfile profile = new EditProfile("test5@test.com", "AZERTY", "Leo", "Dupassy",
				"7 route du test, une ville", "06161616418");
		mockMvc.perform(post("/Holder/create").param("email", "test5@test.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(put("/Profile/update").param("email", "test5@test.com").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(profile))).andExpect(status().isNoContent());
		Holder holder = holderDao.findByEmail("test5@test.com");
		Profiles profileUpdated = profileDao.findByFk(holder);
		assertEquals(profile.getAddress(), profileUpdated.getAddress());
		assertEquals(profile.getFirstName(), profileUpdated.getFirstName());
		assertEquals(profile.getLastName(), profileUpdated.getLastName());
		assertEquals(profile.getPhone(), profileUpdated.getPhone());
		assertNotNull(profileUpdated.getUpdate());
		assertNotNull(profileUpdated.getHolderId().getUpdatedAt());
		profileDao.delete(profileUpdated);
		holderDao.delete(holder);
	}

	@Test
	@Order(2)
	@DisplayName("Update a profile partially successfully")
	void update_profile_partially_successfully() throws Exception {
		EditProfile profile = new EditProfile("Leo", "Dupassy", "7 route du test, une ville", "06161616418");
		mockMvc.perform(post("/Holder/create").param("email", "test6@test.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(put("/Profile/update").param("email", "test6@test.com").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(profile))).andExpect(status().isNoContent());
		Holder holder = holderDao.findByEmail("test6@test.com");
		Profiles profileUpdated = profileDao.findByFk(holder);
		assertEquals(profile.getAddress(), profileUpdated.getAddress());
		assertEquals(profile.getFirstName(), profileUpdated.getFirstName());
		assertEquals(profile.getLastName(), profileUpdated.getLastName());
		assertNotNull(profileUpdated.getUpdate());
		assertNull(profileUpdated.getHolderId().getUpdatedAt());
		EditProfile profile2 = new EditProfile("test6@test.com", "AZERTY");
		mockMvc.perform(put("/Profile/update").param("email", "test6@test.com").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(profile2))).andExpect(status().isNoContent());
		holder = holderDao.findByEmail("test6@test.com");
		profileUpdated = profileDao.findByFk(holder);
		assertEquals(profile.getAddress(), profileUpdated.getAddress());
		assertEquals(profile.getFirstName(), profileUpdated.getFirstName());
		assertEquals(profile.getLastName(), profileUpdated.getLastName());
		assertEquals(profileUpdated.getHolderId().getEmail(), profile2.getEmail());
		assertNotNull(profileUpdated.getHolderId().getUpdatedAt());
		profileDao.delete(profileUpdated);
		holderDao.delete(holder);
	}

	@Test
	@Order(3)
	@DisplayName("Update a profile by deleting info successfully")
	void update_profile_delete_info_successfully() throws Exception {
		EditProfile profile = new EditProfile("Leo", "Dupassy", "7 route du test, une ville", "06161616418");
		mockMvc.perform(post("/Holder/create").param("email", "test7@test.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(put("/Profile/update").param("email", "test7@test.com").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(profile))).andExpect(status().isNoContent());
		profile = new EditProfile("Leo", "Dupassy", "7 route du test, une ville", "");
		mockMvc.perform(put("/Profile/update").param("email", "test7@test.com").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(profile))).andExpect(status().isNoContent());
		Holder holder = holderDao.findByEmail("test7@test.com");
		Profiles profileUpdated = profileDao.findByFk(holder);
		assertTrue(profileUpdated.getPhone().isEmpty());
		profileDao.delete(profileUpdated);
		holderDao.delete(holder);
	}

	@Test
	@Order(4)
	@DisplayName("Sent a string that is not an email")
	void update_profile_string_not_email() throws Exception {
		EditProfile profile = new EditProfile("Leo", "Dupassy", "7 route du test, une ville", "06161616418");
		mockMvc.perform(put("/Profile/update").param("email", "test7test.com").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(profile))).andExpect(status().isBadRequest());
	}

	@Test
	@Order(5)
	@DisplayName("Update account with email not in DB")
	void update_profile_email_not_exist() throws Exception {
		EditProfile profile = new EditProfile("Leo", "Dupassy", "7 route du test, une ville", "06161616418");
		mockMvc.perform(put("/Profile/update").param("email", "test20@test.com").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(profile))).andExpect(status().isBadRequest());
	}

	@Test
	@Order(6)
	@DisplayName("Update partially just password, update on holder")
	void update_profile_email_password() throws Exception {
		EditProfile profile = new EditProfile(null, "azerty", null, null, null, null);
		mockMvc.perform(post("/Holder/create").param("email", "test8@test.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(put("/Profile/update").param("email", "test8@test.com").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(profile))).andExpect(status().isNoContent());
		Holder holder = holderDao.findByEmail("test8@test.com");
		Profiles profileUpdated = profileDao.findByFk(holder);
		assertNotNull(profileUpdated.getHolderId().getUpdatedAt());
		assertNull(profileUpdated.getUpdate());
		profileDao.delete(profileUpdated);
		holderDao.delete(holder);
	}
	
	@Test
	@Order(7)
	@DisplayName("Update partially just first name, update on profile")
	void update_profile_email_first_name() throws Exception {
		EditProfile profile = new EditProfile(null, null, "Thierry", null, null, null);
		mockMvc.perform(post("/Holder/create").param("email", "test9@test.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(put("/Profile/update").param("email", "test9@test.com").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(profile))).andExpect(status().isNoContent());
		Holder holder = holderDao.findByEmail("test9@test.com");
		Profiles profileUpdated = profileDao.findByFk(holder);
		assertNull(profileUpdated.getHolderId().getUpdatedAt());
		assertNotNull(profileUpdated.getUpdate());
		profileDao.delete(profileUpdated);
		holderDao.delete(holder);
	}
	
	@Test
	@Order(8)
	@DisplayName("Update partially just last name, update on profile")
	void update_profile_email_last_name() throws Exception {
		EditProfile profile = new EditProfile(null, null, null, "Bruet", null, null);
		mockMvc.perform(post("/Holder/create").param("email", "test9@test.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(put("/Profile/update").param("email", "test9@test.com").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(profile))).andExpect(status().isNoContent());
		Holder holder = holderDao.findByEmail("test9@test.com");
		Profiles profileUpdated = profileDao.findByFk(holder);
		assertNull(profileUpdated.getHolderId().getUpdatedAt());
		assertNotNull(profileUpdated.getUpdate());
		profileDao.delete(profileUpdated);
		holderDao.delete(holder);
	}
	
	@Test
	@Order(9)
	@DisplayName("Update partially just phone, update on profile")
	void update_profile_email_phone() throws Exception {
		EditProfile profile = new EditProfile(null, null, null, null, null, "06126163161");
		mockMvc.perform(post("/Holder/create").param("email", "test9@test.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(put("/Profile/update").param("email", "test9@test.com").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(profile))).andExpect(status().isNoContent());
		Holder holder = holderDao.findByEmail("test9@test.com");
		Profiles profileUpdated = profileDao.findByFk(holder);
		assertNull(profileUpdated.getHolderId().getUpdatedAt());
		assertNotNull(profileUpdated.getUpdate());
		profileDao.delete(profileUpdated);
		holderDao.delete(holder);
	}
	
	@Test
	@Order(9)
	@DisplayName("get a profile")
	void get_profile() throws Exception {
		mockMvc.perform(get("/Profile").param("email", "test4@test.com")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.email", is("test4@test.com")))
				.andExpect(jsonPath("$.password", is("azerty")))
				.andExpect(jsonPath("$.phone", is("+4154798321")));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
