package com.mikalai.library.beans;

import com.mikalai.library.beans.base.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class contain information about book
 * 
 * @author Mikalai_Churakou
 */

@Entity
@Table(name="book_descriptions")
public class BookDescription extends NamedEntity {
	private String author;


	@ManyToOne
	@JoinColumn(name="bookCategoryId")
	private BookCategory bookCategory;


	@ManyToOne
	@JoinColumn(name="languageId")
	private Language language;

	
	private String publicationPlace;
	private int publicationYear;
	private int size;


	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublicationPlace() {
		return publicationPlace;
	}
	public void setPublicationPlace(String publicationPlace) {
		this.publicationPlace = publicationPlace;
	}
	public int getPublicationYear() {
		return publicationYear;
	}
	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	public BookCategory getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(BookCategory bookCategory) {
		this.bookCategory = bookCategory;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	

}
