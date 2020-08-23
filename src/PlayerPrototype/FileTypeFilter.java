/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlayerPrototype;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author John
 */
public class FileTypeFilter extends FileFilter
{
   
    //instance varriables    
    private final char DotIndex = '.';
    
    private final String MP3Format = "MP3";
    
    private final String NameFilter = " user playlist";
    
//--------------------------filter methods--------------------------------------

    /**
     * verifies if file is correct type for use. Will return true if file type is directory
     * @param file target file
     * @return the file name and extension
     */
    @Override
    public boolean accept(File file) 
    {
        if(file.isDirectory())                      //checks if file is a directory
        {
            return true;
        }
        if(extension(file).equalsIgnoreCase(MP3Format))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @return display file type and description
     */
    @Override
    public String getDescription() 
    {
        return "MP3 format only";
    }
    
    /**
     * Trims the path name of file to display only the "." ie .MP3, .WAV .JPEG etc. of file.
     * method is a helper method to help filter only MP3 files.
     * @param file any file type 
     * @return the . extension of file.
     */
    public String extension(File file)
    {
        String FileName = file.getName();
        int IndexFile = FileName.lastIndexOf(DotIndex);
        
        if(IndexFile > 0 && IndexFile < FileName.length()-1)
        {
            return FileName.substring(IndexFile + 1);
        }
        else
        {
            return "";
        }
    }
}
