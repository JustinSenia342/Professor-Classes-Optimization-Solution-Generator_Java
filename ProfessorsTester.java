/*
 * Name: Justin Senia
 * E-Number: E00851822
 * Date: 10/6/2017
 * Class: COSC 461
 * Project: #1
 */
import java.io.*;
import java.util.*;

public class ProfessorsTester
{

    //Main method for testing
    public static void main(String[] args) throws IOException
    {

      //creating buffered reader for getting user input
      java.io.BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));

      //message welcoming to the program/giving instructions
      System.out.println("******************************************");
      System.out.println("*    Welcome to the Professors program   *");
      System.out.println("******************************************");
      System.out.println("*   Please enter in a filename to start  *");
      System.out.println("*  or type quit to terminate the program *");
      System.out.println("******************************************");

      //start a loop that continues querying for input as long as user
      //does not enter "quit" (without the quotes)
      while (true)
      {
        System.out.print("Please make your entry now: ");
        String userIn = ""; //used for file entry and/or quitting

        userIn = keyIn.readLine(); //reading user input

        if (userIn.equalsIgnoreCase("quit")) //if user typed quit, stops program
          break;
        else{
              try
              {
                //establishing working directory for file I/O
                String currentDir = System.getProperty("user.dir");
                File fIn = new File(currentDir + '\\' + userIn);

                //using scanner with new input file based on user input
                Scanner scanIn = new Scanner(fIn);

                //creating printwriter for file output
                File fOut = new File("output_" + userIn);
                PrintWriter PWOut = new PrintWriter(fOut, "UTF-8");

                //scanning information from file to populate variables
                int numProfs = scanIn.nextInt();
                int numProfPrefs = scanIn.nextInt();
                int classNumReq = scanIn.nextInt();
                int maxCourses = numProfs * numProfPrefs;

                //creating array based on size read by filein
                int[][] profArray = new int[numProfs][numProfPrefs];

                //initializing all values to zero
                for (int i = 0; i < numProfs; i++)
                {
                  for (int j = 0; j < numProfPrefs; j++)
                  {
                    profArray[i][j] = 0;
                  }
                }

                //populating the array with external file data
                for (int i = 0; i < numProfs; i++)
                {
                  scanIn.nextInt();
                  scanIn.next().charAt(0);
                  int courseTempVar = 0;
                  for (int j = 0; j < numProfPrefs; j++)
                  {
                    profArray[i][j] = scanIn.nextInt();
                  }
                }

                //Creating object of professor class with file supplied information
                Professors p = new Professors(numProfs, numProfPrefs, classNumReq,
                maxCourses, profArray, PWOut);

                //calling professor solve method
                p.solve();

                //closing I/O
                scanIn.close();
                PWOut.close();
              }
              catch (IOException e)
              {
                ;
              }
            }
      }
    }
}
