package com.example.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class BookCreateDTO {

    @NotBlank(message = "Tên sách không được để trống")
    private String title;

    @NotBlank(message = "Tên tác giả không được để trống")
    private String author;

    @NotNull(message = "Số lượng sách không được để trống")
    @Min(value = 0, message = "Stock phải là số nguyên không âm")
    private Integer stock;

    @NotNull(message = "Ảnh bìa không được để trống")
    private MultipartFile coverImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public MultipartFile getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(MultipartFile coverImage) {
        this.coverImage = coverImage;
    }
}
