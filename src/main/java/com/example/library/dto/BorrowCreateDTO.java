package com.example.library.dto;

import com.example.library.validation.ExistingBookId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BorrowCreateDTO {

    @NotBlank(message = "Tên người dùng không được để trống")
    private String username;

    @NotNull(message = "Mã sách không được để trống")
    @ExistingBookId
    private Long bookId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
