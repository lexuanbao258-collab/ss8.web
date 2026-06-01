package com.example.library.validation;

import com.example.library.repository.BookRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BookIdValidator implements ConstraintValidator<ExistingBookId, Long> {

    private final BookRepository bookRepository;

    public BookIdValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean isValid(Long bookId, ConstraintValidatorContext context) {
        // bookId null được xử lý riêng bởi @NotNull trong DTO.
        if (bookId == null) {
            return true;
        }

        return bookRepository.existsById(bookId);
    }
}
