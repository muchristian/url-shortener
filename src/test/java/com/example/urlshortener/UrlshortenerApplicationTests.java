package com.example.urlshortener;

import com.example.urlshortener.urlShortenerModule.UrlShortenerEntity;
import com.example.urlshortener.urlShortenerModule.UrlShortenerRepository;
import com.example.urlshortener.urlShortenerModule.shared.GenerateShortenUrlDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class UrlshortenerApplicationTests {


	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UrlShortenerRepository urlShortenerRepository;

	@BeforeEach
	public void before() {
		urlShortenerRepository.deleteAll();
	}

	@Test
	void getLongUrl_200() throws Exception {
		UrlShortenerEntity urlShortenerEntity = new UrlShortenerEntity(
				"tur38g2", "https://github.com/flectra-hq/flectra/tree/3.0/flectra", null
		);
		urlShortenerRepository.save(urlShortenerEntity);
		mockMvc.perform(MockMvcRequestBuilders.get("/{shortenedUrlId}", urlShortenerEntity.getShortenedUrlId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void getLongUrl_shortUrl_notFound_404() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/{shortenedUrlId}", "000000")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void generateShortenUrl_200() throws Exception {
		GenerateShortenUrlDTO.input shortenUrlInput = new GenerateShortenUrlDTO.input(null, "https://github.com/flectra-hq/flectra/tree/3.0/flectra", null);

		mockMvc.perform(MockMvcRequestBuilders.post("/shortener")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(shortenUrlInput)))
				.andExpect(status().isOk());
	}

	@Test
	void generateShortenUrl_shortenUrl_already_exist_409() throws Exception {
		UrlShortenerEntity urlShortenerEntity = new UrlShortenerEntity(
				"tur38g2", "https://github.com/flectra-hq/flectra/tree/3.0/flectra", null
		);
		urlShortenerRepository.save(urlShortenerEntity);
		GenerateShortenUrlDTO.input shortenUrlInput = new GenerateShortenUrlDTO.input("tur38g2", "https://github.com/flectra-hq/flectra/tree/3.0/flectra", null);

		mockMvc.perform(MockMvcRequestBuilders.post("/shortener")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(shortenUrlInput)))
				.andExpect(status().isConflict());
	}

	@Test
	void deleteShortenUrl_200() throws Exception {
		UrlShortenerEntity urlShortenerEntity = new UrlShortenerEntity(
				"tur38g2", "https://github.com/flectra-hq/flectra/tree/3.0/flectra", null
		);
		urlShortenerRepository.save(urlShortenerEntity);
		mockMvc.perform(MockMvcRequestBuilders.delete("/{shortenedUrlId}", urlShortenerEntity.getShortenedUrlId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}


}
