package pset10.p2;

/**
 * An {@code Ant} is created at a specific position of an {@link AntField} with
 * an initial {@code stepCount}. When running an Ant, it will lookup the values
 * on the current and all surrounding {@link AntField.Field}
 * (Moore-neighborhood) instances and test if the position is free, i.e. has a
 * value of {@code 0}, or if the value is greater than the {@code stepCount} of
 * this Ant. For both cases, the Ant will set the value of the {@code Field} at
 * the visited position to its own {@code stepCount+1}. After an {@code Ant} has
 * successfully visited one field, it will create new {@code Ant} instances with
 * an incremented {@code stepCount} to visit the other available {@code Field}
 * elements. The Ant will run until it finds no more {@code Field} elements in
 * its neighborhood to be altered.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class Ant implements Runnable {

	private int xPos,yPos, stepCount;
	
	private AntField field;
	
   /**
    * 
    * @param fields
    *           the {@code AntField} on which this {@code Ant} operates
    * @param x
    *           x-axis value of the starting position
    * @param y
    *           y-axis value of the starting position
    * @param stepCount
    *           initial stepCount of this {@code Ant}.
    * 
    * @throws IllegalArgumentException
    *            If the {@code Field} at position {@code x,y} does not exist, or
    *            if its value is < 0
    */
   public Ant(AntField fields, int x, int y, int stepCount) {
	   this.xPos = x;
	   this.yPos = y;
	   this.stepCount = stepCount;
	   this.field	= fields;
   }

   public void run() {
	  /*
	   * The checking of an ant must be synchronised 
	   */
	  synchronized (field){
		  // the entering ant will check if its field is free and the stepCount of the field lower
		  if( (this.field.getField(xPos, yPos).getValue() == 0) 
				  || (this.field.getField(xPos, yPos).getValue() > stepCount)){
			  // if this holds it will assign its stepCount to the value the field 
			   this.field.getField(xPos, yPos).setValue(stepCount);
		   }
	   }
	   // now the ant will check its moore environment
	   for(int i=-1; i < 2 ; i++){
		   // iterate through columns
		   for(int j= -1; j < 2 ; j++){
			   // iterate through lines, but its own field
			   if(i != 0 && j != 0){

				   // if it is another field, call it the neighbouring field
				   AntField.Field neighbourfield = field.getField(this.xPos+i, this.yPos+j);
				   // check if the field is not blocked already
				   if(neighbourfield != null){
						   // set a new ant on the neighbouring field
						   Ant newAnt = new Ant(field,this.xPos+i, this.yPos+j, stepCount+1);    
						   // start the new ant
						   
						   // TESTING PURPOSE
						   try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						   System.out.println("Ant is at:("+this.xPos+"|"+this.yPos+")");
						   // END TESTING DEVICE
						   new Thread(newAnt).start();
				   }
			   }
		   }
	   }
	   // thread ends here
   }
}
