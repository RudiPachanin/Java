package TodoList;

public enum Status
{
    New("New"),
    In_process("In_process"),
    Done("Done");

    String value;

    Status(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
