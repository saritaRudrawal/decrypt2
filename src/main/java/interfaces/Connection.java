package interfaces;

public interface  Connection
{
	public static String url="jdbc:mysql://localhost/care4today?"
              + "user=root&password=diaspark";
	public static String driverName="com.mysql.jdbc.Driver";
	
         public void doConnection();
}
