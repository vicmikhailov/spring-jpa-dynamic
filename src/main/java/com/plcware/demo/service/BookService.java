package com.plcware.demo.service;

import com.plcware.demo.model.Book;
import com.plcware.demo.repository.BookRepository;
import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  @Autowired
  BookRepository bookRepository;

  public Book save(Book book) {
    return bookRepository.save(book);
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Optional<Book> findById(Integer id) {
    return bookRepository.findById(id);
  }

  public void deleteAll() {
    bookRepository.deleteAll();
  }

  public Book merge(Book book) {
    Book oldBook = bookRepository.findById(book.getId()).orElseThrow();
    BeanUtils.copyProperties(book, oldBook, getNullPropertyNames(book));
    return bookRepository.save(oldBook);
  }

  private <T> String[] getNullPropertyNames(T bean) {
    BeanWrapper ws = new BeanWrapperImpl(bean);
    return Stream.of(ws.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(propertyName -> ws.getPropertyValue(propertyName) == null)
        .toArray(String[]::new);
  }
}
