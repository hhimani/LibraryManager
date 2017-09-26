package org.library.manager.dao;

import java.util.List;

import org.library.manager.model.Book;

public interface BookDAO {

	void save(Book book);
	Book searchByTitle(String title);
	Book searchByAuthor(String author);
	void addNewBook(Book book);//add a new row, same as saving a book
	void increaseCopies(String title);//increase copies count of a given book
	List<Book> getBooks(); //inventory display
	void updateCopies(String title,int copies);
	void deleteBook(String title);
	void updateBook(Book book);
}
