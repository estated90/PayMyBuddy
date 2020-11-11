package com.payMyBudy.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.model.Holder;
import com.payMyBudy.service.ControllerServices;
import com.payMyBudy.service.MemberService;

import io.swagger.annotations.Api;

@RestController
@Api(description = "API pour for the CRUD operation on users (holders).")
public class HolderController {

	private final Logger logger = LoggerFactory.getLogger(HolderController.class);
	
	@Autowired
	private HolderDao holderDao;
	
	@Autowired
	private ControllerServices controllerServices;
	
	@Autowired
	private MemberService memberService; 

	// Récupérer la liste des produits
	@RequestMapping(value = "/Holder", method = RequestMethod.GET)
	public List<Holder> getHolders() {

		return holderDao.findAll();
	}
	
    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("member", new Holder());
        return "views/registerForm";
    }    
    
    @PostMapping("/register")
    public String registerMember(@Valid Holder holder){
        memberService.createMember(holder);
        return "views/loginForm";
    }

	@PostMapping(value = "/Holder/create", params="email")
	public ResponseEntity<Holder> createUser(@RequestParam String email) throws ServiceEmailException {
		logger.info("create a user with : {}", email);
		Holder createHolder = controllerServices.createHolder(email);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(createHolder.getEmail()).toUri();
		logger.info("{} was created", createHolder);
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(value="/Connection", params= {"email", "password"})
	public ResponseEntity<Holder> connection(@RequestParam String email, @RequestParam String password) throws ServiceEmailException {
		logger.info("User with email {} is trying to connect", email);
		Holder connectedHolder = controllerServices.connection(email, password);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(connectedHolder.getEmail()).toUri();
		logger.info("{} is connected", connectedHolder);
		return ResponseEntity.created(location).build();
	}
	

}
