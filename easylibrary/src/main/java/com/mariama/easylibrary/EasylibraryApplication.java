package com.mariama.easylibrary;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.mariama.easylibrary.service.IBookService;

@SpringBootApplication
public class EasylibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasylibraryApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner runManualTest(IBookService bookService) {
//		return args -> {
//			System.out.println("--- CALLING SERVICE MANUALLY ---");
//			var books = bookService.getBooks();
//			System.out.println("Results: " + books);
//		};
//	}

}
