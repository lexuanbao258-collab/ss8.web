package com.example.library.service;

import com.example.library.dto.BookCreateDTO;
import com.example.library.dto.BookUpdateStockDTO;
import com.example.library.entity.Book;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final Path uploadDirectory = Paths.get("uploads");

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(BookCreateDTO dto) {
        MultipartFile coverImage = dto.getCoverImage();

        if (coverImage == null || coverImage.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn ảnh bìa");
        }

        String contentType = coverImage.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File tải lên phải là ảnh");
        }

        try {
            Files.createDirectories(uploadDirectory);

            String originalFilename = coverImage.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                throw new IllegalArgumentException("Tên file ảnh không hợp lệ");
            }

            String cleanFilename = Paths.get(originalFilename)
                    .getFileName()
                    .toString();

            String storedFilename = UUID.randomUUID() + "_" + cleanFilename;
            Path filePath = uploadDirectory.resolve(storedFilename);

            Files.copy(
                    coverImage.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            Book book = new Book();
            book.setTitle(dto.getTitle());
            book.setAuthor(dto.getAuthor());
            book.setStock(dto.getStock());
            book.setCoverUrl("/uploads/" + storedFilename);

            return bookRepository.save(book);
        } catch (IOException exception) {
            throw new IllegalArgumentException("Không thể lưu file ảnh", exception);
        }
    }

    public Book updateBook(Long id, BookUpdateStockDTO dto) {
        Book book = getBookById(id);
        book.setStock(dto.getStock());
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book with id " + id + " not found"
                ));
    }
}
