# Library Book Borrow Project — PostgreSQL

Project Spring Boot gộp 4 bài tập:

1. Thêm mới sách kèm ảnh bìa bằng `MultipartFile` và `@ModelAttribute`.
2. Cập nhật stock bằng `PATCH`, `@RequestBody` và validation.
3. Tìm sách theo ID và bắt lỗi tập trung bằng `@RestControllerAdvice`.
4. Tạo phiếu mượn với custom annotation `@ExistingBookId` truy vấn database.

## 1. Yêu cầu

- JDK 17 trở lên.
- PostgreSQL đang chạy trên máy.
- Maven hoặc Maven được tích hợp trong IntelliJ IDEA.
- Postman để kiểm tra API.

## 2. Tạo database PostgreSQL

Cách 1: dùng pgAdmin.

- Mở pgAdmin.
- Chọn server PostgreSQL của bạn.
- Mở `Query Tool`.
- Chạy nội dung file `postgresql-setup.sql`.

Cách 2: dùng terminal:

```bash
psql -U postgres -d postgres -f postgresql-setup.sql
```

Database cần có tên:

```text
library_db
```

Bạn không cần tự tạo bảng. Khi project chạy, Hibernate sẽ tự tạo hoặc cập nhật hai bảng:

```text
books
borrows
```

## 3. Cấu hình kết nối PostgreSQL

Mở file:

```text
src/main/resources/application.properties
```

Cấu hình mặc định:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
spring.datasource.username=postgres
spring.datasource.password=123456
```

Hãy sửa `username` và `password` nếu tài khoản PostgreSQL trên máy bạn khác cấu hình mặc định.

Trong project, cấu hình được viết theo dạng biến môi trường có giá trị dự phòng:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/library_db}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:123456}
```

Vì vậy, bạn có thể sửa trực tiếp giá trị sau dấu `:` hoặc đặt biến môi trường.

## 4. Chạy project

Mở terminal tại thư mục project và chạy:

```bash
mvn spring-boot:run
```

Hoặc mở IntelliJ IDEA và chạy file:

```text
src/main/java/com/example/library/LibraryApplication.java
```

Ứng dụng chạy tại:

```text
http://localhost:8080
```

## 5. Kiểm tra API bằng Postman

### Bài 1: Thêm sách kèm ảnh bìa

```text
POST http://localhost:8080/api/books
```

Chọn `Body -> form-data`:

| Key | Type | Ví dụ |
|---|---|---|
| `title` | Text | `Lão Hạc` |
| `author` | Text | `Nam Cao` |
| `stock` | Text | `50` |
| `coverImage` | File | Chọn ảnh từ máy |

Sau khi tạo thành công, response có `coverUrl`. Mở đường dẫn sau để xem ảnh:

```text
http://localhost:8080/uploads/<ten-file-anh>
```

### Bài 2: Cập nhật stock

```text
PATCH http://localhost:8080/api/books/update/1
```

Chọn `Body -> raw -> JSON`:

```json
{
  "stock": 10
}
```

### Bài 3: Tìm sách theo ID

```text
GET http://localhost:8080/api/books/1
```

Thử ID không tồn tại:

```text
GET http://localhost:8080/api/books/999
```

Response lỗi có dạng:

```json
{
  "status": 404,
  "message": "Book with id 999 not found",
  "timestamp": "2026-06-01T09:30:25.123456"
}
```

### Bài 4: Tạo phiếu mượn

```text
POST http://localhost:8080/api/borrows
```

Body JSON:

```json
{
  "username": "huongcaoha",
  "bookId": 1
}
```

Nếu gửi ID sách không tồn tại:

```json
{
  "username": "huongcaoha",
  "bookId": 999
}
```

API trả về `400 Bad Request` trước khi chạy code trong controller:

```json
{
  "bookId": "Sách không tồn tại trong hệ thống"
}
```

## 6. File quan trọng

```text
src/main/java/com/example/library
├── controller
│   ├── BookController.java
│   └── BorrowController.java
├── dto
│   ├── BookCreateDTO.java
│   ├── BookUpdateStockDTO.java
│   ├── BorrowCreateDTO.java
│   └── ErrorResponse.java
├── entity
│   ├── Book.java
│   └── Borrow.java
├── exception
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── repository
│   ├── BookRepository.java
│   └── BorrowRepository.java
├── service
│   ├── BookService.java
│   └── BorrowService.java
└── validation
    ├── ExistingBookId.java
    └── BookIdValidator.java
```
