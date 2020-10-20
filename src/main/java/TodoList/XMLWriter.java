package TodoList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XMLWriter {
    public  static final String Filexml ="src/main/java/TodoList/Task.xml";
    public static void File(List<Task> taskList) throws TransformerException, ParserConfigurationException
    {

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("ToDoList");
        document.appendChild(root);
        for (int i = 0; i < taskList.size(); i++)
        {
            taskList.get(i);
            Task task = taskList.get(i);
            Element employee = document.createElement("Task");

            root.appendChild(employee);

            Attr attr = document.createAttribute("id");
            attr.setValue(task.getID().toString());
            employee.setAttributeNode(attr);
            Attr caption = document.createAttribute("caption");
            caption.setValue(task.getHeader());
            employee.setAttributeNode(caption);


            Element Description = document.createElement("Description");
            Description.appendChild(document.createTextNode(task.getDescription()));
            employee.appendChild(Description);

            Element Priority = document.createElement("Priority");
            Priority.appendChild(document.createTextNode(task.getPriority().toString()));
            employee.appendChild(Priority);

            Element Deadline = document.createElement("Deadline");
            Deadline.appendChild(document.createTextNode(task.getTime().toString()));
            employee.appendChild(Deadline);

            Element Status = document.createElement("Status");
            Status.appendChild(document.createTextNode(task.getStatus().toString()));
            employee.appendChild(Status);

            Element Complete = document.createElement("Complete");
            Complete.appendChild(document.createTextNode(task.getEndTime().toString()));
            employee.appendChild(Complete);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        String xmlFilePath="";
        StreamResult streamResult = new StreamResult(new File(Filexml));
        transformer.transform(domSource, streamResult);

        System.out.println("Done creating XML File");
    }
}
