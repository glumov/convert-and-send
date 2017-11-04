package com.glumov.convertandsend;

import com.glumov.convertandsend.client.PostJSON;
import com.glumov.convertandsend.util.ConverterCSVtoJSON;
import org.apache.http.auth.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	@Value("${file}")
	private String file;

	@Value("${url}")
	private String url;

	@Autowired
	private ConverterCSVtoJSON converter;

	@Autowired
	private PostJSON postJSON;

	@Override
	public void run(String... strings) throws Exception {
		String json = converter.convert(file);
		postJSON.send(url,json);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
