package gr.aueb.network_book.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Used as a Data Transfer Object (DTO) to validate
 * and transfer data when handling book-related requests in your application.
 * Provides meaningful error codes (100, 101, 102, 103)
 * @param id
 * @param title
 * @param authorName
 * @param isbn
 * @param synopsis
 * @param shareable
 */

public record BookRequest(
        Integer id,

        @NotNull(message = "100")
        @NotEmpty(message = "100")
        String title,

        @NotNull(message = "101")
        @NotEmpty(message = "101")
        String authorName,

        @NotNull(message = "102")
        @NotEmpty(message = "102")
        String isbn,

        @NotNull(message = "103")
        @NotEmpty(message = "103")
        String synopsis,
        boolean shareable
) {
}
