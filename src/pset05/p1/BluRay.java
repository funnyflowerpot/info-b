package pset05.p1;

public class BluRay extends LibraryItem {

	
	// data fields
	private String title;
	private String author;
	
	// constructor
	public BluRay(String title, String author){
		this.title = title;
		this.author= author;
	}
	
	// Methods
	/**
	 * Getter method for the title of the BluRay
	 * @return title of the BluRay
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Getter method for the director of the BluRay
	 * @return author of the item
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * This method delivers a description of the item in String format
	 * @return <code>String</code> of description
	 */
	public String getDescription() {
		return "Title:"+getTitle()+"\nDirector:"+getAuthor()+"\nType:"+getClass().getSimpleName()+"\nStatus:"+(super.isBorrowed() ? "borrowed" : " available");
	}
}
