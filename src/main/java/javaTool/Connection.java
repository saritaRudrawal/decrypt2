package javaTool;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jasypt.hibernate4.encryptor.HibernatePBEStringEncryptor;
import org.jasypt.hibernate4.type.EncryptedStringType;
import org.jasypt.salt.FixedStringSaltGenerator;
import org.jasypt.salt.SaltGenerator;


public class Connection implements interfaces.Connection {
	static HibernatePBEStringEncryptor hibernatePBEStringEncryptor;
	java.sql.Connection connect;
	public void doConnection() {

		try {
			Class.forName(Connection.driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connect = DriverManager
					.getConnection(Connection.url);
			Statement statement = connect.createStatement();
			ResultSet phoneListOfUser = statement
					.executeQuery("select phone,user_id from care4today.User");
			
			//phoneListOfUser.next();
			String decryptedValue=hibernatePBEStringEncryptor
					.decrypt("+EuTqdQ5Phic2S9g/P/Hdg==");
			System.out.println("decrypted value"+decryptedValue);
			//doDecrypt(phoneListOfUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void doDecrypt(ResultSet phoneListOfUser) throws SQLException {
		//PreparedStatement pst=connect.prepareStatement("insert into care4today.phone values(?,?)");
		PreparedStatement pst=connect.prepareStatement("UPDATE care4today.user SET phone = ? WHERE user_id = ?");
		
		
		try {
			while (phoneListOfUser.next()) {
								
				if (phoneListOfUser.getString(1) != null)
					{
					
					
					String decryptedValue=hibernatePBEStringEncryptor
							.decrypt(phoneListOfUser.getString(1));
					System.out.println("Decrypted String Value "+
							decryptedValue);
					pst.setLong(2, phoneListOfUser.getLong(2));
					pst.setString(1,decryptedValue);
					pst.executeUpdate();
					
					}
				else
				{
					pst.setLong(2, phoneListOfUser.getLong(2));
					pst.setString(1,null);
					pst.executeUpdate();
					
				}
					

			  
			
			
			}
		} catch (SQLException e) {
			
			System.out.println("Exception exist");
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		hibernatePBEStringEncryptor = new HibernatePBEStringEncryptor();
		hibernatePBEStringEncryptor
				.setRegisteredName("hibernateStringEncryptor");
		hibernatePBEStringEncryptor.setPassword("yourPasswordGoesHere");
		FixedStringSaltGenerator fixedStringSaltGenerator = new FixedStringSaltGenerator();
		fixedStringSaltGenerator
				.setSalt("DFSAAFPDSAFSDFSDREWRWERWVSDF075670569075697065760595097sdffsdfsdfddafsdfsdf3534532453543v879780563456451321defweqfweqwerwerwevdfqweweqrwevwfsbsbdfgeyeryrwynsdfg34535343543543454534");
		hibernatePBEStringEncryptor.setSaltGenerator(fixedStringSaltGenerator);
		new Connection().doConnection();

	}

}
