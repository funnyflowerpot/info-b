package pset05.p1;

public class Book extends LibraryItem {
	
	// data fields
	private String title;
	private String author;
	
	// constructor
	public Book(String title, String author){
		this.title = title;
		this.author= author;
	}
	
	// Methods
	/**
	 * Getter method for the title of the book
	 * @return title of the book
	 */
	public String getTitle() {
		return this.title;
	}
	/**
	 * Getter method for the author of the book
	 * @return author of the book
	 */
	public String getAuthor() {
		return this.author;
	}
	/**
	 * This method delivers a description of the item in String format
	 * @return <code>String</code> of description
	 */
	public String getDescription() {
		return "Title:"+getTitle()+"\nAuthor:"+getAuthor()+"\nType:"+getClass().getSimpleName()+"\nStatus:"+(super.isBorrowed() ? "borrowed" : " available");
	}

}

