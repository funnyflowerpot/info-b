package pset07.p5;

import java.io.File;
import java.util.Arrays;


/**
 * @author sriegl
 *
 * A thin wrapper for class File to allow instances get visited, according to
 * The visitor pattern.
 */
public class VisitableFile extends File implements Visitable<VisitableFile> {
 
  /**
   * Wrapper method for constructor File(String).
   * 
   * @param pathname the pathname specifying this file
   */
  public VisitableFile(String pathname) {
    super(pathname);
  }

  /**
   * SerialVersionUID as necessity of file extension
   */
  private static final long serialVersionUID = 1667979193997729448L;

  
  @Override
  public void accept(Visitor<VisitableFile> v) {

    FileVisitor visitor = (FileVisitor) v;
    VisitableFile file;
    String[] fileNames;
    
    // this will only be called from main
    if(!isDirectory())
      throw new IllegalArgumentException("can only handle directories");
   
    // guarantee alphabetical order for directory listings
    fileNames = list();
    Arrays.sort(fileNames);
    
    // list files, since we are a directory and want our content
    for(String fileName : fileNames) {

      file = new VisitableFile(getPath() + File.separatorChar + fileName);
      
      // Handling return value is a requirement of the visitor pattern, 
      // although FileVisitor always returns true.
      if(!visitor.visit(file))
        return;
      
      if(file.isDirectory() && visitor.isRecursing()) {
        file.accept(visitor);
        
      } // end of "if" (file is directory and recursion is requested)
    
    } // end of "for" (file iteration, directory traversion)
    
  }

}
