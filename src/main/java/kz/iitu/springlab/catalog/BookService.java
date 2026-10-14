package kz.iitu.springlab.catalog;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll(String author) {
        return repository.findAll().stream()
                .filter(b -> author == null
                        || b.author().toLowerCase().contains(author.toLowerCase()))
                .sorted(Comparator.comparing(Book::id))
                .toList();
    }

    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    public Book create(Book book) {
        return repository.save(new Book(null, book.title(), book.author(), book.year()));
    }

    public Optional<Book> replace(long id, Book book) {
        if (repository.findById(id).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(repository.save(new Book(id, book.title(), book.author(), book.year())));
    }

    public boolean delete(long id) {
        return repository.deleteById(id);
    }

    public BookStats stats() {
        List<Book> all = repository.findAll();
        return new BookStats(
                all.size(),
                all.stream().map(Book::year).min(Integer::compare).orElse(null),
                all.stream().map(Book::year).max(Integer::compare).orElse(null)
        );
    }
}