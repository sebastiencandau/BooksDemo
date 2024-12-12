package com.example.demo.domain.usecase

import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeSortedWith
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.*

class BookRepositoryTest : StringSpec({

    val bookRepository: BookRepository = mockk(relaxed = true)
    val bookUseCase = BookUseCase(bookRepository)

    "should add a book when title and author are not empty" {
        // Arrange
        val title = "Effective Java"
        val author = "Joshua Bloch"
        every { bookRepository.save(any()) } just Runs

        // Act
        bookUseCase.createBook(title, author)

        // Assert
        verify { bookRepository.save(Book(title, author)) }
    }

    "should throw an error when title is empty" {
        val exception = shouldThrow<IllegalArgumentException> {
            bookUseCase.createBook("", "Author")
        }
        exception.message shouldBe "Title must not be empty"
    }

    "should throw an error when author is empty" {
        val exception = shouldThrow<IllegalArgumentException> {
            bookUseCase.createBook("Title", "")
        }
        exception.message shouldBe "Author must not be empty"
    }

    "should return a sorted list of books by title" {
        // Arrange
        val books = listOf(
            Book("Z Programming", "Author Z"),
            Book("A Programming", "Author A"),
            Book("M Programming", "Author M")
        )
        every { bookRepository.findAll() } returns books

        // Act
        val result = bookUseCase.listBooks()

        // Assert
        result shouldBeSortedWith compareBy { it.title }
        result shouldHaveSize 3
    }
})
