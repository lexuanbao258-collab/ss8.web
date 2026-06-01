package com.example.library.controller;

import com.example.library.dto.BookCreateDTO;
import com.example.library.dto.BookUpdateStockDTO;
import com.example.library.entity.Book;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Bài 1: Thêm sách kèm ảnh bìa bằng multipart/form-data.
    @PostMapping
    public ResponseEntity<Book> createBook(
            @Valid @ModelAttribute BookCreateDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.createBook(dto));
    }

    // Bài 2: Chỉ cập nhật stock bằng JSON.
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateBookStock(
            @PathVariable Long id,
            @Valid @RequestBody BookUpdateStockDTO dto
    ) {
        bookService.updateBook(id, dto);
        return ResponseEntity.ok("Book stock updated successfully");
    }

    // Bài 3: Tìm chi tiết sách theo ID.
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}
