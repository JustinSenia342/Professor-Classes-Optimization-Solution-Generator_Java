/*
 * Name: Justin Senia
 * E-Number: E00851822
 * Date: 10/6/2017
 * Class: COSC 461
 * Project: #1
 */
import java.util.LinkedList;
import java.io.*;
import java.util.*;

//This program solves Professors problem
public class Professors
{
  //board class (inner class)
  private class Board{
    private int[][] array;  //array
    private int rows;
    private int cols;

    //constructor of board class
    private Board(int profRows, int classCols)
    {
      array = new int[profRows][classCols]; //create array
      for (int i = 0; i < profRows; i++)
      {
        for (int j = 0; j < classCols; j++)
        {
          array[i][j] = 0;
        }
      }
      rows = 0; //keeps track of rows
      cols = 0; //keeps track of columns
    }
}

  //declaring private variables
  private int numOfProfs; //# of professors
  private int numOfProfPrefs; //number of professor preferred classes
  private int classNumRequired; //number of classes required for each prof to teach
  private int maxNumCourses; //total number of courses provided by the shool
  private PrintWriter pW; //printwriter
  private Board profClass = new Board(numOfProfs, numOfProfPrefs); //prof class

  //constructor of Furniture class
  public Professors(int numProfs, int numProfPrefs, int classNumReq,
  int maxCourses, int[][] profArray, PrintWriter PWOut)
  {
    this.numOfProfs = numProfs;
    this.numOfProfPrefs = numProfPrefs;
    this.classNumRequired = classNumReq;
    this.maxNumCourses = maxCourses;
    this.pW = PWOut;
    this.profClass.array = profArray;
  }

  //method solves Professors problem
  public void solve()
  {

    LinkedList<Board> openList = new LinkedList<Board>();
    LinkedList<Board> closedList = new LinkedList<Board>();

    //creating initial board from dimensions specified by external file
    Board initialBoard = new Board(numOfProfs, maxNumCourses);

    //adding initial board to open list
    openList.addFirst(initialBoard);

    while (!openList.isEmpty()) //continues until all states are searched
    {
      Board board = openList.removeFirst(); //remove initial from openList

      closedList.addLast(board); //add initial to closed list

      if (complete(board) ) //if board is complete..
      {
      if    ( calcValue(board)) //make sure that all teachers have proper # of classes
              {
                  display(board); //display board
                  return; //return
              }
      }
      else //board is not complete
      {
        LinkedList<Board> children = generate(board); //generate children

        //pop children off of list
        for (int i = 0; i < children.size(); i++)
        {
          Board child = children.get(i);

          //if child does not exist in open or closed list: add to open list
          if (!exists(child, openList) && !exists(child, closedList))
            openList.addLast(child);
        }
      }
    }

    // prints out "No solution if there are conflicts that cannot be reconciled"
    System.out.println("******************************************");
    System.out.println("*                No Solution             *");
    System.out.println("******************************************");

    pW.println("\n");
    pW.println("******************************************");
    pW.println("*                No Solution             *");
    pW.println("******************************************");

    System.out.println("");
    pW.println("");
  }

  //Method generates children of a board
  private LinkedList<Board> generate(Board board)
  {
    LinkedList<Board> children = new LinkedList<Board>();

    for (int i = 0; i < numOfProfPrefs; i++)
    {
        Board child = copy(board);

        //tries to add a 1 (as a flag) to any classes being taught by that teacher
        child.array[child.rows][profClass.array[child.rows][i]] = 1;

          child.rows += 1; //increase rows

        //if child has no conflicts, it is added to a linked list and passed back
        //to the method that called it. (there is a problem here, ran out of time
        //to figure it out, but i believe the issue with this program is in the
        // criteria for figuring out conflicts)
        if (check(child, child.rows-1, i))
          children.addLast(child);
    }
      return children;
  }

  //Method s whether teacher teaching a specific class causes conflict
  private boolean check(Board board, int x, int y)
  {
    for (int i = 0; i < numOfProfs; i++)
      for (int j = 0; j < maxNumCourses; j++)
      {
        if (x == i && y == j)
          ; //if teacher teaches a subject and someone else has "1" flag in same row, false
        else if (board.array[x][y] == 1 && board.array[x][y] == board.array[i][y])
          return false;
      }

      return true;
  }

  //method s whether board is complete
  private boolean complete(Board board)
  {
    // number filled rows equals board size
    return (board.rows == numOfProfs);
  }

  //calculates board final correctness based on how many "1" flags can be seen in each row
  private boolean calcValue(Board board)
  {
      int totalValue = 0;
      for (int i = 0; i < numOfProfs; i++)
      {
        for (int j = 0; j < maxNumCourses; j++)
        {
          totalValue = totalValue + board.array[i][j];
        }
                  System.out.println(totalValue);
        if (totalValue != classNumRequired)
          return false;

        totalValue = 0;
      }

      return true;
  }

  //Method makes copy of board
  private Board copy(Board board)
  {
    Board result = new Board(numOfProfs, maxNumCourses);

    for (int i = 0; i < numOfProfs; i++)
      for (int j = 0; j < maxNumCourses; j++)
        result.array[i][j] = board.array[i][j];

    result.rows = board.rows;

    return result;
  }

  //Method decides whether a board exists in a list
  private boolean exists(Board board, LinkedList<Board> list)
  {
    for (int i = 0; i < list.size(); i++)
      if (identical(board, list.get(i)))
        return true;

    return false;
  }

  //Method decides whether two boards are identical
  private boolean identical(Board p, Board q)
  {
    for (int i = 0; i < numOfProfs; i++)
      for (int j = 0; j < maxNumCourses; j++)
        if (p.array[i][j] != q.array[i][j])
          return false;

    return true;
  }

  //Method displays board
  private void display(Board board)
  {
    System.out.println("A Working Solution:");
    pW.println("A Working Solution:");

    System.out.println("******************************************");
    System.out.println("*     Professor #     *     Classes      *");
    System.out.println("******************************************");

    pW.println("******************************************");
    pW.println("*     Professor #     *     Classes      *");
    pW.println("******************************************");

    for (int i = 0; i < numOfProfs; i++)
    {
      System.out.printf("*        %3d          :", (i+1));
      pW.printf("*        %3d          :", (i+1));
      for (int j = 0; j < maxNumCourses; j++)
      {
        if (board.array[i][j] > -1)
        {
          System.out.printf("%4d", board.array[i][j]);
          pW.printf("%4d", board.array[i][j]);
        }
      }
      System.out.println("            *");
      pW.println("            *");
    }

    System.out.println("");

    System.out.println("");
    pW.println("");
  }
}
