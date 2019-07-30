package com.theorydance.myspringboot.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.theorydance.myspringboot.model.User;

public class JwtUtils {
	private final static String SECRET = "Ranfusheng123";
	public static void main(String[] args) {
		User user = new User();
		user.setAge(30);
		user.setName("ranfusheng");
		user.setPass("Ranfusheng656");
		Map<String,Object> header = new HashMap<>();
		header.put("alg", "HS256");
		header.put("typ", "JWT");
		Algorithm algorithm = Algorithm.HMAC256(SECRET);
		String token = JWT.create()
				.withHeader(header)
				.withClaim("user",user.toString())
				.withIssuer("SERVICE") // 签名由谁生成
				.withSubject("this is test token") // 签名的主题
				.withAudience("APP")
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(new Date().getTime() + 1000*60*60))
				.sign(algorithm);
		System.out.println(token);
		System.out.println("---------------------------");
		JWTVerifier verifier = JWT.require(algorithm)
				.build();
		DecodedJWT jwt = verifier.verify(token);
		String subject = jwt.getSubject();
		System.out.println(subject);
		Claim claim = jwt.getClaim("user");
		User user2 = User.parseStr(claim.asString());
		System.out.println(user2);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
