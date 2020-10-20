package TodoList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dom
{
    private static final String SHOW_ALL="List";
    private static final String HELP="Help";
    private static final String DELETE="Delete";
    private static final String NEW="New";
    private static final String EXIT="Exit";
    private static final String EDIT="Edit";
    private static final String COMPLETE="Complete";
    private static final String PROCESSING="Processing";

    public static int extractParameter(String command, int startlength)
    {
        StringBuilder parameter= new StringBuilder();

        for (int i = startlength; i < command.length(); i++)
        {
            parameter.append(command.charAt(i));
        }
        return Integer.parseInt(parameter.toString());
    }

    /** Выводим в консоль текс и получаем строку
     * @param message Сообщение которое выводим message("Введите новый заголовок")
     * @return Возврат введеной строки
     */
    public static String getInput(String message, Scanner scanner)
    {
        System.out.println(message);
        return scanner.nextLine();
    }
    public static void main(String[] args) throws Exception
    {
        DocumentBuilder documentBuilder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document Document=documentBuilder.parse("src/main/java/TodoList/Task.xml");
        Node Root = Document.getDocumentElement();
        System.out.println("List of tasks:");
        System.out.println();

        Task task=null;
        List<Task> taskList = new ArrayList<Task>();


        NodeList Tasks = Root.getChildNodes();
        for (int i = 0; i <Tasks.getLength() ; i++)
        {
            Node Task = Tasks.item(i);
            if (Task.getNodeName().equals("Task"))
            {
                task = new Task();
                String test= Task.getAttributes().getNamedItem("id").getTextContent();
                task.setID(Integer.parseInt(Task.getAttributes().getNamedItem("id").getTextContent()));
                task.setHeader(Task.getAttributes().getNamedItem("caption").getTextContent());
            }

            if (Task.getNodeType() != Node.TEXT_NODE)
            {
                NodeList TaskProps = Task.getChildNodes();
                for (int j = 0; j <TaskProps.getLength() ; j++)
                {
                    Node TaskProp= TaskProps.item(j);
                    if (TaskProp.getNodeType() != Node.TEXT_NODE)
                    {
                        if (TaskProp.getNodeName().equals("Description"))
                        {
                            task.setDescription(TaskProp.getChildNodes().item(0).getTextContent());
                        }
                        if (TaskProp.getNodeName().equals("Priority"))
                        {
                            task.setPriority(Integer.parseInt(TaskProp.getChildNodes().item(0).getTextContent()));
                        }
                        if (TaskProp.getNodeName().equals("Deadline"))
                        {
                            task.setTime(LocalDate.parse(TaskProp.getChildNodes().item(0).getTextContent()));
                        }
                        if (TaskProp.getNodeName().equals("Status"))
                        {
                            task.setStatus(Status.valueOf(TaskProp.getChildNodes().item(0).getTextContent()));
                        }
                        if (TaskProp.getNodeName().equals("Complete"))
                        {
                            task.setEndTime(LocalDate.parse(TaskProp.getChildNodes().item(0).getTextContent()));
                        }

                    }
                }
                taskList.add(task);
            }
        }
        Scanner scanner=new Scanner(System.in);
        String text;
        do {
            System.out.println("Введите комаду: ");
            text = scanner.nextLine();
            if (text.contains(SHOW_ALL))
            {

                if (text.length()==SHOW_ALL.length())
                {
                    taskList.forEach(System.out::println);
                }
                else
                {
                    String textdel="";
                    for (int i = 8; i < text.length(); i++)
                    {
                        textdel+=text.charAt(i);
                    }
                    Status status1 = Status.valueOf(textdel);
                    taskList.stream().filter(task1 -> task1.getStatus()==status1)
                        .forEach(System.out::println);
                }
            }
            if (text.equals(HELP))
            {
                System.out.println("List: показать все задачи"+"\n"+"List -s Done: показать все задачи со статусом Done"+"\n"
                        +"List -s New: показать все задачи со статусом New"+"\n"+"List -s In_process: показать все задачи со статусом In_process"+"\n"
                        +"Delete и ID задачи: удолить задачу с указанным ID"+"\n"+"New: Создать новую задачу."+"\n"+"Edit и ID: Редактировать задачу"+"\n"
                        +"Processing и Id: Изменить статус задачи на In_process"+"\n"+"Complete и ID: Изменить статус задачи на Done"+"\n"+"Exit: Выход из программы");

            }
            if (text.contains(DELETE))
            {
                for (int i = 0; i < taskList.size(); i++)
                {
                    if (extractParameter(text,DELETE.length())==taskList.get(i).getID())
                    {
                        taskList.remove(i);
                        break;
                    }
                }
                XMLWriter.File(taskList);
            }
            if (text.contains(EDIT))
            {
                for (int i = 0; i < taskList.size(); i++)
                {
                    if (extractParameter(text,EDIT.length())==taskList.get(i).getID())
                    {
                        Task EditTask=taskList.get(i);

                        String input=getInput("Введите новый заголовок: ",scanner);
                        EditTask.setHeader(input.equals("") ? EditTask.getHeader() : input);

                        input=getInput("Введите новое описание: ",scanner);
                        EditTask.setDescription(input.equals("") ? EditTask.getDescription() : input);

                        input=getInput("Введите новую важность задачи: ",scanner);
                        EditTask.setPriority(input.equals("") ? EditTask.getPriority() : Integer.parseInt(input));

                        input=getInput("Введите новое колличество дней для выполнения задания: ",scanner);
                        EditTask.setEndTime(input.equals("") ? EditTask.getEndTime()
                                : EditTask.getEndTime().plusDays(Integer.parseInt(input)));

                        break;
                    }
                }
                XMLWriter.File(taskList);
            }
            if (text.equals(NEW))
            {
                Task NewTask = new Task();
                System.out.println("Введите заголовок: ");
                NewTask.setHeader(scanner.nextLine());
                System.out.println("Введите описание: ");
                NewTask.setDescription(scanner.nextLine());
                System.out.println("Введите важность задачи: ");
                NewTask.setPriority(scanner.nextInt());
                System.out.println("Введите колличество дней для выполнения задания: ");
                NewTask.setEndTime(LocalDate.now().plusDays(scanner.nextLong()));
                NewTask.setStatus(Status.New);
                NewTask.setTime(LocalDate.now());
                if (taskList.size()<1){
                    NewTask.setID(1);
                }
                else {
                    NewTask.setID(taskList.get(taskList.size() - 1).getID() + 1);
                }
                taskList.add(NewTask);
                XMLWriter.File(taskList);

            }
            if (text.contains(COMPLETE))
            {
                for (int i = 0; i < taskList.size(); i++)
                {
                    if (extractParameter(text,COMPLETE.length())==taskList.get(i).getID())
                    {
                        Task CompleteTask = taskList.get(i);
                        CompleteTask.setStatus(Status.Done);
                    }
                }
            }
            if (text.contains(PROCESSING)){
                for (int i = 0; i < taskList.size(); i++)
                {
                    if (extractParameter(text,PROCESSING.length())==taskList.get(i).getID())
                    {
                        Task CompleteTask = taskList.get(i);
                        CompleteTask.setStatus(Status.In_process);
                    }
                }
            }
        }
        while (!text.equals(EXIT));

    }
}
