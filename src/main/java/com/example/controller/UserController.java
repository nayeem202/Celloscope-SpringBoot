package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public UserRepository userRepository;

	@PostMapping("/save_user")
	public ResponseEntity<Map> save(@RequestBody User user) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {

			Optional<User> dbuser = userRepository.findById(user.getUserId());

			if (!dbuser.isEmpty()) {
				map.put("message", "User Already exists");
				map.put("status", "failed to save");
				return ResponseEntity.status(403).body(map);

			} else {
				user = userRepository.save(user);
				map.put("data", user);
				map.put("status", 200);
				map.put("message", "data successfully saved");

				logger.info("New User Save");
				return ResponseEntity.ok(map);
			}

		} catch (Exception e) {
			map.put("message", "data failed to save");
			map.put("error", e.getLocalizedMessage());
			logger.error("data saved failed");
			return ResponseEntity.status(500).body(map);

		}

	}

	@PutMapping("/update_password")
	public ResponseEntity<Map> update(@RequestBody User user) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Optional<User> dbuser = userRepository.findById(user.getUserId());

			if (dbuser.isEmpty()) {
				map.put("message", "User not found");
				map.put("status", "failed to save");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			} else {
				user = userRepository.save(user);
				map.put("data", user);
				map.put("status", 200);
				map.put("message", "data successfully updated");
				logger.info("Upadated user password");
				return ResponseEntity.ok(map);
			}

		} catch (Exception e) {
			map.put("message", "data failed to update");
			map.put("error", e.getLocalizedMessage());
			logger.info("password failed to update");
			return ResponseEntity.status(500).body(map);

		}

	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {

		List<User> users = (List<User>) userRepository.findAll();
		Map<String, Object> map = new HashMap<String, Object>();

		for (User other : users) {
			if (other.getUserId() == (user.getUserId()) && other.getPassword().equals(user.getPassword())) {
				map.put("message", "Login Successful");
				map.put("status", "Success");
				map.put("data", other);
				logger.info("user login successfully");
				return ResponseEntity.ok(map);

			}
		}

		map.put("message", "Login fail!");
		map.put("status", "Failed");
		map.put("data", null);
		logger.info("failed to login");
		return ResponseEntity.status(409).body(map);
	}

	@PostMapping("/reset_password")
	public ResponseEntity<Map<String, Object>> resetpass(@RequestBody User user) {

		Map<String, Object> map = new HashMap<String, Object>();
		User dbuser = userRepository.findById(user.getUserId()).get();

		if (dbuser.getUserId() == (user.getUserId()) && dbuser.getMobile().equals(user.getMobile())) {
			map.put("message", "Provided  Information Matched");
			map.put("status", "Success");
			map.put("data", dbuser);
			logger.info("user information matched");
			return ResponseEntity.ok(map);

		}

		map.put("message", "Not Matched");
		map.put("status", "Failed");
		map.put("data", null);
		logger.error("user information not matched");
		return ResponseEntity.status(409).body(map);
	}

}
