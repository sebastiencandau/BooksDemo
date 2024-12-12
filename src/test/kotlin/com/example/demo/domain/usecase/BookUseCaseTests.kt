import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository
import com.example.demo.domain.usecase.BookUseCase

@WebMvcTest(BookUseCase::class)
class BookUseCaseTest : StringSpec({

    val bookRepository: BookRepository = mockk()
    val bookUseCase = BookUseCase(bookRepository)

    "The returned list of books should contain all the stored books" {
        // Given
        val book1 = Book(title = "Book 1", author = "Author 1")
        val book2 = Book(title = "Book 2", author = "Author 2")
        every { bookRepository.findAll() } returns listOf(book1, book2)

        // When
        val returnedBooks = bookUseCase.listBooks()

        // Then
        returnedBooks shouldContainAll listOf(book1, book2)

        verify { bookRepository.findAll() }
    }
})
