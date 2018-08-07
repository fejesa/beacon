package com.bcm.dao;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service("TokenService")
public class TokenService {

	private Map<String, String> cache = new ConcurrentHashMap<>();

	public String generate(String username) {
		String token = UUID.randomUUID().toString();
		cache.put(token, username);
		return token;
	}

	public boolean isValid(String token, String username) {
		String u = cache.get(token);
		return username.equals(u);
	}
}
