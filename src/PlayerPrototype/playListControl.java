/*
 * Prototype musicPlayer by John Haney - 2019
 */
package PlayerPrototype;

import java.awt.HeadlessException;
import java.io.File;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
/**
 *
 * @author John
 */
public class playListControl 
{
    //instance varribales
    private final String playListFileName = "user playlist";                        //the directory that will hold the MP3 files
    
    private final DoublyLinkedList<File> playListTemp = new DoublyLinkedList<>();   //tempararilty holds removed stongs from goBack and skip methods
 
    private DoublyLinkedList<File> playList = new DoublyLinkedList<>();             //the DoublyLinkedList that will serve as a playlist
    
    private final DoublyLinkedList<File> shuffledList = playList;                   //DLL is set to playList so it may be shuffled when needed
   
    private boolean EXIT;                                                           //holds state of application 
        
    private final String playListPath;                                              //String of the path that holds the playlist
    
    private Music music = new Music();
    
    private boolean REPEAT;                                                         //boolean used to check if repeat is turned on or off
    
//---------------------------nested Music class--------------------------------    
    
    /**
     * the Music class is used to created Music objects that allow the stopping of the 
     * Player class play method while the MP3 continues to be played
     */
    class Music extends Thread
    {
        
        /**
         * allows the Player to play and for the program to continue without
         * getting tied up in the Player code loop
         */
        @Override
        public void run()
        {   
            try
            {
                FileInputStream fileInputStream = new FileInputStream(playList.first());        //FileInputSteam class object with first song path from DLL
                Player player = new Player(fileInputStream);                                    //Player class objects with fileInputStream as parameters
                System.out.println("Now Playing: " + playList.first().getName());
                player.play();
            }
            catch(FileNotFoundException | JavaLayerException e) 
            {
                System.out.println("file not found! ");
            }
   
        } 
 
    }
  
//-----------------------end of nested Music class------------------------------ 
    
    //default constructor
    public playListControl()
    {
        playListPath = makeDirectory();
        File playListFolderFile = new File(playListPath);
        fillList(playListFolderFile);
    }
    
//---------------------------protected run method-------------------------------
    
    /**
    * calls mainMenu and performs loop waiting for input. Input is provided by
    * the call to getInput()
    */
    protected void runMenu()
    {
        while(!EXIT)
        {
            mainMenu();                 //calls mainMenu
            int choice = getInput();    //calls getInput method and sets return value to choice
            performAction(choice);      //calls calls performAction and takes in choice for parameters
            choice = -1;                //resets choice to negitive integer
        }
    }
    
//--------------------------private update methods------------------------------
    
    /**
    * displays menu and options with print statements
    */
    private void mainMenu()
    {
        System.out.println("\n Welcome to prototype MP3 player! Please enter one of the following\n");
        System.out.println("Enter 1 to play");
        System.out.println("Enter 2 to stop");
        System.out.println("Enter 3 to pause");
        System.out.println("Enter 4 to go back");
        System.out.println("Enter 5 to skip");
        System.out.println("Enter 6 to turn on reapeat");
        System.out.println("Enter 7 to shuffle");
        System.out.println("Enter 8 to add song to the queue");
        System.out.println("Enter 9 to exit");
        System.out.println("Enter your choice: ");
    }
    
    
    /**
     * creates a directory folder named playListFileNamethat will store and hold 
     * all the MP3 files.
     * @return the path as a string
     */
    private String makeDirectory()
    {
        File playListFile = new File(playListFileName);               //gives file name of instace variable "user playlist"
        
        if(!playListFile.exists())                                   //checks to see if directory does not exsist
        {
            playListFile.mkdir();                                   //creates new directory out of playListFile
            String path = playListFile.getAbsolutePath();           //creates a string with path name of playListFile
            System.out.println("directory created " + "the file \"" + playListFileName +    
                    "'s\" path is: \n" + path + "\n");              //prints statement to let use know that firectory is created and location
            return path;                                            
        }
        else                                                        //if directory exsits
        {
            String path = playListFile.getAbsolutePath();           //create String of path
            System.out.println("directory already exists " + "the file \"" + 
                    playListFileName + "'s\" path is: \n" + path + "\n");   //print statment to inform user that it is already created and directory location
            return path;
        }
    }
    
    /**
     * takes in input from scanner System.in reads data as String. String is then parsed
     * and wrapped as an integer which is then passed to choice
     * @return integer choice
     */
    private int getInput()
    {
        int choice = -1;                        
        Scanner scanner = new Scanner(System.in);    //new scanner object to with keyboard for parameters
        while(choice < 1 || choice > 9)            //checks to make sure choice (user input) is valid
        {
            try                                     //try catch block to ensure inut is a valid integer
            {
                System.out.println("\n enter number: ");        
                choice = Integer.parseInt(scanner.nextLine());      //parses scanner input for an integer
            }
            catch(NumberFormatException e)                          //exception caught if input is not integer format
            {
                System.out.println("invalid selection try agian");
            }
        }
        return choice;                                              
    }
    
    /**
     * creates a playlist of songs by taking file location and storing them in a
     * DoublyLinkedList.
     * @param file location of the file
     */
    private void addSong(File file)
    {
        playList.addLast(file);         //where playList is a DoublyLinkedList
    }
    
    /**
     * adds song to the second to first place in DLL
     * @param file 
     */
    private void addToQueue(File file)
    {
        playList.addNext(file);
    }
    
    /**
     * reads the files in a given folder and turns the contents into a file array.
     * then takes the data from the file array and Stores them into the class DoublyLinkedList
     * variable.
     * @param folder the folder directory
     */
    private void fillList(File folder)
    {
        FileTypeFilter filter = new FileTypeFilter();          //instanciates a FileTypeFilter Object that filter for .MP3 files
        File[] allFiles = folder.listFiles();                  //turns all files in directory into File array allFiles
        
        for(int i = 0; i <= allFiles.length -1; i++)            //goes through File array converts paths to elemets in list
        {
            if(filter.accept(allFiles[i]))                      //filters files in  File array and uses filter varrible
            {
                addSong(allFiles[i]);                           //adds file path as string to end of DoulbyLinkedList
            }
        }
    }
    
    /**
     * takes in the user input and does switch comparison to see which methods to make 
     * a call to. 
     * @param choice as an integer 
     */
    private void performAction(int choice)
    {        
        switch(choice)
        {
            case 1:
                long startTime = System.nanoTime();
                startMusic();
                long endtime = System.nanoTime();
                long elapsed = endtime - startTime;
                System.out.println("the total running time of the startMusic method in "
                        + "nano seconds is: " + elapsed);
                break; 
            case 2:
                long startTime1 = System.nanoTime();
                stopMusic();
                long endtime1 = System.nanoTime();
                long elapsed1 = endtime1 - startTime1;
                System.out.println("the total running time of the stopMusic Method in "
                        + "nano seconds is: " + elapsed1);
                break;
            case 3:
                pauseMusic();
                break;
            case 4:
                goBack();
                break;
            case 5:
                long startTime2 = System.nanoTime();
                skipSong();
                long endtime2 = System.nanoTime();
                long elapsed2 = endtime2 - startTime2;
                System.out.println("the total running time of the skipSong method in "
                        + "nano seconds is: " + elapsed2);
                break;
            case 6:
                repeat();
                break;
            case 7:
                long startTime3 = System.nanoTime();
                shuffle();
                long endtime3 = System.nanoTime();
                long elapsed3 = endtime3 - startTime3;
                System.out.println("the total running time of the shuffle in "
                        + "nano seconds is: " + elapsed3);
                break;
            case 8:
                addNewSong();
                break;
            case 9:
                exit();
                stopMusic();                
                break;
            default:
                System.out.println("An unknown error has occurred");
        }
    }
    
//---------------------------user options methods-------------------------------
    
    /**
    * terminates program by setting EXIT variable to true
    */
    private void exit()
    {
        System.out.println("Goodbye...");
        EXIT = true;
        
    }
    
    /**
     * plays the first song in the DoulbyLinkedList uses a try catch block to see
     * if song is already currently being played
     */
    private void startMusic()
    {
        try
                {
                    if(!music.isInterrupted())
                    {
                    music.start();  
                    }
                    else
                    {
                        music.resume();
                    }
                }
                catch(IllegalThreadStateException e)
                {
                    System.out.println("Song is already playing!");
                }
    }
    
    /**
     * Instanciates a new Music object reset.
     * invokes stop method on the music variable to stop the thread in the object
     * then resets global music method by setting variable music equal to reset
     */
    private void stopMusic()
    {
        Music reset = new Music();    //new instance of music object to reset dangerous thread stop method
        music.stop();
        music = reset;
    }
    
    /**
     * pauses music by suspending the thread it then interrupts the thread. This
     * interrupt is used by playMusic method test boolean value if it needs to be
     * reset.
     */
    private void pauseMusic()
    {
        music.suspend();
        music.interrupt();
        System.out.println("music is paused");
    }
    
    /**
     * skips the song currently being played by getting and removing the first element in the
     * list and storing the element in last position in the playListTemp. The Stop method is called to stop song
     * then the start method is called to begin new song in list. If the end of the DDL is reached, the song skipped is 
     * added back to the playList.
     */
    private void skipSong()
    {   
        if(REPEAT == true)
        {
            if(playList.size() != 1)
            {
                playListTemp.addLast(playList.removeFirst());
                stopMusic();
                startMusic();
            }
            else
            {
                playListTemp.addLast(playList.removeFirst());
                
                while(!playListTemp.isEmpty())
                {
                    playList.addLast(playListTemp.removeFirst());
                }
                stopMusic();
                startMusic();
            }
        }
        else
        {
            if(playList.size() != 1)
            {
                playListTemp.addLast(playList.removeFirst());
                stopMusic();
                startMusic();
            }
            else
            {
                playListTemp.addLast(playList.removeFirst());
                
                while(!playListTemp.isEmpty())
                {
                    playList.addLast(playListTemp.removeFirst());
                }
                stopMusic();
            }
        }  
    }
    
    /**
     * the songs are removed from the playListTemp and added back to the playList.
     * they the music is first stopped then started to initialize the new song that is
     * first in the DDL. If there are no songs currently in the playListTemp, the song
     * being played is started over.
     */
    private void goBack()
    {
        if(playListTemp.isEmpty())
        {
            stopMusic();
            startMusic();
        }
        else
        {
            playList.addFirst(playListTemp.removeLast());          
            stopMusic();
            startMusic();
        }
    
    }
    
    /**
     * switches the global boolean value to the opposite of its current state,
     * which creates conditions in skip method to either create playlist loop or not
     */
    private void repeat()
    {
        if(REPEAT == true)
        {
            REPEAT = false;
            System.out.println("Repeat is turned off \n");
        }
        else
        {
            REPEAT = true;
            System.out.println("Repeat is turned on \n");
        }
    }
    
    /**
     * randomizes or shuffles the order of the songs in the playList. This is done by
     * utilizing the seperate shuffledList which is initialized when class is called
     * to be equal to initial playList. A files array is then created to hold the data
     * removed from shuffledList. Then the files array is shuffled with swaps and a random
     * object. The elements are then reloaded to the shuffledList. The playList is then
     * set to the new list.
     */
    private void shuffle()
    {        
        File[] files = new File[shuffledList.size()];
        int k = shuffledList.size();
        Random random = new Random();
            
        for(int i = 0; i < k; i++)                      //loop to take all files from the DLL and store in array
        {
            files[i] = shuffledList.removeFirst();
        }
        for(int i = files.length - 1; i > 0; i--)       //loop to randomize the indicies of the files
        {
            int j = random.nextInt(i);
            
            File temp = files[i];
            files[i] = files[j];
            files[i] = temp;
        }
        for(int i = 0; i < k; i++)                      //loop to readd the newly randomized files back to the list
        {
            shuffledList.addFirst(files[i]);
        }
        playList = shuffledList;                        //playList now equals shuffledList
        System.out.println("playlist is shuffled");
    }
    
    /**
     * Utilizes a JFileChooser to allow user to queue up songs to be played next in
     * playList. openFileChooser is set to only make file type visible and uses
     * FileTypeFilter to filer only MP3 files. Prints message of song name to 
     * notify user that their selection has been queued up to play.
     */
    private void addNewSong()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//used to give JFile window updated look
            JButton open = new JButton();                                       //Button object required to open

            JFileChooser openFileChooser = new JFileChooser();                  //JFileChooser object
            openFileChooser.setFileFilter(new FileTypeFilter());                //sets chooser to use filtertype class

            openFileChooser.setCurrentDirectory(new File(playListFileName));    //sets choosers opening window location

            openFileChooser.setDialogTitle("Add new song");                     //chooser title
            openFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);      //chooser only will show file type

            if(openFileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION)
            {
                File newSong;
                newSong = openFileChooser.getSelectedFile();
                addToQueue(newSong);
                System.out.println("song : " + newSong.getName() + "has been added to the queue");
            }
        }
        catch(HeadlessException | ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e)
        {
            
        }
    }
}
