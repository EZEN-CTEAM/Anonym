package teamproject.util;
import java.sql.*;

public class DBConn
{
	public static final String URL = "jdbc:mysql://localhost:3306/Anonym?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
	public static final String USERID = "cteam";
	public static final String USERPW = "ezen";
//	public static final String USERID = "root";
//	public static final String USERPW = "1234";
	
	public static Connection conn() throws Exception 
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(URL, USERID, USERPW);
	}
	
	public static void close(ResultSet rs, PreparedStatement psmt, Connection conn) throws Exception 
	{
		if(rs != null) rs.close();
		if(psmt != null) psmt.close();
		if(conn != null) conn.close();	
	}

	public static void close(PreparedStatement psmt, Connection conn) throws Exception 
	{
		if(psmt != null) psmt.close();
		if(conn != null) conn.close();	
	}
}
