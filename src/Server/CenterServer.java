package Server;

public interface CenterServer {

    public boolean createTRecord (String managerID, String firstName, String lastName, String Address,
                                  String Phone, String Specialization, String Location);

    public boolean createSRecord (String managerID, String firstName, String lastName, String CoursesRegistered,
                                  String Status, String StatusDate);

    public boolean editRecord (String managerID, String recordID, String fieldName, String newValue);

    public boolean printRecord (String ManagerID);

    public boolean transferRecord (String managerID, String recordID, String remoteCenterServerName);

    public String getRecordCounts ();

}
