/**
 * This class implements the Library of exercise 5.1
 * It is the Library class in a simple library  organisation system.
 * @author Wicke
 * @date 23/05/14
 */
//package due to: library::Library
package pset05.p1;

public class Library {
	
	// the UML specifies 1 Library to 0...* LibraryItems
	List inverntory;
	
	// the constructor instantiate a new library with a default item
	public Library (){
		this.inverntory = new List();
	}
	
	// methods
	/**
	 * This method is the setter method for the LibraryItem
	 * @return void
	 * @param item of type <code>LibraryItem</code>
	 */
	public void addItem(LibraryItem item){
		// use add method of list.class
		this.inverntory.add(item);
	}
	
	/**
	 * This method deletes an item in the list.
	 * Successful deletion will return a statement
	 * (NOT mentioned in UML, still a useful extension)
	 * @param item
	 * @throws RuntimeException if inventory is empty
	 */
	public void deleteItem(LibraryItem item){
		if(this.inverntory.empty())
			throw new RuntimeException("Inventory is empty.");
		// as long as the list is not empty
		while(!this.inverntory.endpos()){
			// check if current head of list is item you want deleted
			if(this.inverntory.elem() == item){
				// if it is, then delete item and report deletion
				this.inverntory.delete();
				System.out.println(item+" removed from inventory.");
			}
			// if it is not head of list, advance in list
			else{
				this.inverntory.advance();
			}
		// do so until you reach end of list/inventory
		}
	}
	/**
	 * This method enables to search through all library items
	 * You provide a string with a descriptive string of the item 
	 * All matching items will be returned in a list
	 * @param text of type <code>String</code> that you want to search for
	 * @return matchings list of type <code>list</code>
	 */
	public List search(String text){
		if(this.inverntory.empty())
			throw new RuntimeException("Inventory is empty.");
		List matchings = new List();
		// as long as inventory is not empty
		
		while(!this.inverntory.empty()){
		  /* Note:
		   * public boolean contains(CharSequence s) Returns true if and only if
		   * this string contains the specified sequence of char values. 
		   * contains(CharSequence s):boolean expects a CharSequence s as parameter.
		   * CharSequence is an interface implemented by String
		   */
			
		  // check if entered string matches the description of the item
		  if( ((LibraryItem) inverntory.elem()).getDescription().contains(text) ) {
				// add matchings to our list
				matchings.add(this.inverntory.elem());
			}
			// if head of list is not target string, advance in list
			else {
				this.inverntory.advance();
			}
		}
		// if you reach the end of the list, return the matchings			
		return matchings;
	}
}
