package com.laptrinhjavaweb.oauth2.google;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.dto.GoogleUserDTO;

@Service
public class GoogleService {

	@Value("${google.link.get.token}")
	private String GoogleLinkGetToken;

	@Value("${google.link.get.user_info}")
	private String GoogleLinkGetUserInfo;

	@Value("${google.app.id}")
	private String GoogleAppID;

	@Value("${google.app.secret}")
	private String GoogleAppSecret;

	@Value("${google.redirect.uri}")
	private String GoogleRedirectUri;

	public String getToken(String code) throws ClientProtocolException, IOException {

		String response = Request.Post(GoogleLinkGetToken)
				.bodyForm(Form.form().add("client_id", GoogleAppID).add("client_secret", GoogleAppSecret)
						.add("redirect_uri", GoogleRedirectUri).add("code", code)
						.add("grant_type", "authorization_code").build())
				.execute().returnContent().asString();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(response).get("access_token");
		return node.textValue();
	}

	public GoogleUserDTO getUserInfo(String accessToken) throws ClientProtocolException, IOException {
		String response = Request.Get(GoogleLinkGetUserInfo + accessToken).execute().returnContent().asString();
		ObjectMapper mapper = new ObjectMapper();
		GoogleUserDTO googleUserDTO = mapper.readValue(response, GoogleUserDTO.class);
		return googleUserDTO;
	}
}