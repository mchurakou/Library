package beans;
/**
 * Class contain information about book
 * 
 * @author Mikalai_Churakou
 */
public class BookDescription {
	private int id;
	private String name;
	private String author;
	private SimpleBean bookCategory;
	
	private String publicationPlace;
	private int publicationYear;
	private int size;
	private SimpleBean language;
	
	
	
	
	public BookDescription() {
		super();
		
	}
	public BookDescription(int id, String name, String author,
			SimpleBean bookCategory, String publicationPlace,
			int publicationYear, int size, SimpleBean language) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.bookCategory = bookCategory;
		this.publicationPlace = publicationPlace;
		this.publicationYear = publicationYear;
		this.size = size;
		this.language = language;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public SimpleBean getBookCategory() {
		return bookCategory;
	}
	public void setBookCategory(SimpleBean bookCategory) {
		this.bookCategory = bookCategory;
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
	public SimpleBean getLanguage() {
		return language;
	}
	public void setLanguage(SimpleBean language) {
		this.language = language;
	}
	

}
