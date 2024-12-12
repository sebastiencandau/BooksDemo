package com.example.demo.domain.usecase

import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository

class BookUseCase(private val bookRepository: BookRepository) {

    fun createBook(title: String, author: String) {
        require(title.isNotBlank()) { "Title must not be empty" }
        require(author.isNotBlank()) { "Author must not be empty" }

        val book = Book(title, author)
        bookRepository.save(book)
    }

    fun listBooks(): List<Book> {
        return bookRepository.findAll().sortedBy { it.title }
    }
}
