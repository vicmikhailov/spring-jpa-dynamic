package com.plcware.demo;

import com.plcware.demo.model.Book;
import com.plcware.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class SpringJpaDynamicApplication {

  @Autowired
  BookService bookService;

  public static void main(String[] args) {
    SpringApplication.run(SpringJpaDynamicApplication.class, args);
  }

  @Bean
  CommandLineRunner runner() {
    return args -> {

      bookService.deleteAll();

      bookService.save(new Book(null, "The Lord of the Rings", "J. R. R. Tolkien"));
      bookService.save(new Book(null, "The Hobbit", "J. R. R. Tolkien"));
      bookService.save(new Book(null, "The Fellowship of the Ring", "J. R. R. Tolkien"));
      bookService.save(new Book(null, "The Two Towers", "J. R. R. Tolkien"));

      bookService.findAll().forEach(System.out::println);

      System.out.println("------------------------------------");
      Book book = new Book();
      book.setId(4);
      book.setAuthor("Unknown");

      Book merged = bookService.merge(book);
      bookService.findAll().forEach(System.out::println);
    };
  }
}
