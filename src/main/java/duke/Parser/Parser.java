package duke.Parser;
import duke.Ui.Ui;
import duke.task.*;
import duke.exception.DukeException;

import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    private Ui ui;
    public static final String SEPARATOR = "-----------------------------------";

    /**
     * Constructor for Parser with ui
     * @param ui The ui used to set Parser.
     */
    public Parser(Ui ui) {
        this.ui = ui;
    }

    /**
     * Process the command retrieved from user input.
     * @param userInput The full user input.
     * @param tasks The list of tasks.
     */
    public void processCommand(String userInput, TaskList tasks){
        //while (!userInput.equals("bye")) {
            if (userInput.equals("echo")) {

                //tasks.echo(userInput);

            } else if (userInput.length() >= 3 && userInput.substring(0, 3).equals("add")) {  // Add the task into the list

                //addTask(userInput);
                //tasks.

            } else if (userInput.equals("list")) {      /* Show the list to user which contains indicators to indicate that whether this task is done or have a deadline or is an event with given time */

                ui.printTasks(tasks);

            }else if (userInput.contains("find")){

                findTask(userInput.substring(5),tasks);

            } else if (userInput.length() >= 4 && userInput.substring(0, 4).equals("done")) {   // Mark the task that needs to be done as done in list

                try {
                    tasks.markAsDone(Integer.parseInt(userInput.substring(5)) - 1);
                } catch (DukeException e){
                    ui.printErrorMessage(e.getMessage());
                }

            } else if (userInput.length() >= 4 && userInput.substring(0, 4).equals("todo")) { // Create a Todo object and add it into the task list

                try {
                    processTodo(userInput,tasks);
                } catch(DukeException e){
                    ui.printErrorMessage(e.getMessage());
                }

            } else if (userInput.length() >= 8 && userInput.substring(0, 8).equals("deadline")) {  // Create a deadline object and add it into the task list

                try{
                    processDeadline(userInput,tasks);
                } catch(DukeException e){
                    ui.printErrorMessage(e.getMessage());
                }
            } else if (userInput.length() >= 5 && userInput.substring(0, 5).equals("event")) {  // Create an event object and add it into the task list

                try{
                    processEvent(userInput,tasks);
                } catch (DukeException e){
                    ui.printErrorMessage(e.getMessage());
                }
            }else if (userInput.length() >= 6 && userInput.substring(0,6).equals("delete")){

                int taskIndex = Integer.parseInt(String.valueOf(userInput.charAt(7)));
                tasks.deleteTask(taskIndex - 1);
            }  else {
                try {
                    notValidCommand();
                } catch (DukeException e){
                    ui.printErrorMessage(e.getMessage());
                }

            }
       }






    public void notValidCommand()throws DukeException{
        throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    /**
     * Process the deadline command to create a new deadline task in task list
     * @param userInput The full user input.
     * @param tasks The list of tasks.
     * @throws DukeException If user input does not contain "/by" which means the command is invalid
     */
    public void processDeadline(String userInput,TaskList tasks) throws DukeException{
        if(!userInput.contains("/by") || userInput.length()<= 8){
            throw new DukeException("☹ OOPS!!! The description or the date of a deadline cannot be empty.");
        }
            int slashIndexDeadline = userInput.indexOf("/");
            int slashByIndex = userInput.indexOf("/by");
            tasks.createDeadline(userInput.substring(9,slashIndexDeadline),userInput.substring(slashByIndex + 4));
    }

    /**
     * Process the event command to create a new event task in the task list.
     * @param userInput The full user input.
     * @param tasks The list of tasks.
     * @throws DukeException If user input does not contain "/by" which means the command is invalid
     */
    public void processEvent(String userInput,TaskList tasks) throws DukeException{
        if(!userInput.contains("/at") || userInput.length()<= 5){
            throw new DukeException("☹ OOPS!!! The description or the date of an event cannot be empty.");
        }
        int slashIndexEvent = userInput.indexOf("/");
        int slashAtIndex = userInput.indexOf("/at");
        tasks.createEvent(userInput.substring(6,slashIndexEvent),userInput.substring(slashAtIndex+4));
    }

    /**
     * Process the todo command to create a new todo task in the task list.
     * @param userInput The full user input
     * @param tasks The task list.
     * @throws DukeException If user input only contains "todo" or even shorter which are invalid conditions
     */
    public void processTodo(String userInput, TaskList tasks) throws DukeException{
        if(userInput.length()<= 4){
            throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
        }
        tasks.createTodo(userInput.substring(5));
    }

    /**
     * Find the task containing user's keyword.
     * @param keyword The keyword that user type in.
     * @param tasks The list of tasks.
     */
    public void findTask(String keyword,TaskList tasks){

        if(tasks.searchTask(keyword,tasks).isEmpty()){
            System.out.println(SEPARATOR);
            System.out.println("Sorry, it seems that you don't have task contains this keyword in your task list.");
            System.out.println(SEPARATOR);
        } else {
            ui.printSelectedTasks(tasks.searchTask(keyword,tasks),keyword);

        }



    }

}
