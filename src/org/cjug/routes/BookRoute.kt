package org.cjug.routes

import org.cjug.data.Book
import org.cjug.services.BookService
import io.ktor.application.call
import io.ktor.features.NotFoundException
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Route.books() {

    val bookService by di().instance<BookService>()

    get("books") {
        val allBooks = bookService.getAllBooks()
        call.respond(allBooks)
    }

    get("books/{id}") {
        val bookId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        val foundBook = bookService.findBook(bookId)
        val title = foundBook?.title
        call.respond("Title: $title")
    }

    get("autoadd") {
        val book = Book(id=null,title="Book A", author="Author Two")
        bookService.addBook(book)
        call.respond(HttpStatusCode.Accepted)
    }

    post("book") {
        val bookRequest = call.receive<Book>()
        bookService.addBook(bookRequest)
        call.respond(HttpStatusCode.Accepted)
    }

    delete("book/{id}") {
        val bookId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        bookService.deleteBook(bookId)
        call.respond(HttpStatusCode.OK)
    }
}