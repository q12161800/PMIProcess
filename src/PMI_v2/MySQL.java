package PMI_v2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class MySQL {
	private Connection con = null; // Database objects
	// 連接object
	private Statement stat = null;
	// 執行,傳入之sql為完整字串
	private ResultSet rs = null;
	// 結果集
	private PreparedStatement pst = null;

	// 執行,傳入之sql為預儲之字申,需要傳入變數之位置
	// 先利用?來做標示
	public MySQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");// com.mysql.jdbc.Driver
			// 註冊driver 140.125.84.46:3306
			con = DriverManager.getConnection(
					"jdbc:mysql://140.125.84.60:3306/penguintest?useUnicode=true&characterEncoding=utf-8", "root",
					"root");// tmqcas , ntuh_yun
			// 取得connection
			// jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
			// localhost是主機名,test是database名
			// useUnicode=true&characterEncoding=Big5使用的編碼
			// System.out.println("成功連結");
		} catch (ClassNotFoundException e) {
			System.out.println("DriverClassNotFound :" + e.toString());
		} // 有可能會產生sqlexception
		catch (SQLException x) {
			System.out.println("Exception :" + x.toString());
		}
	}
//, String table2, String table3
	public ArrayList<String> getNews(String table1,String table2,String table3,String cloumename) {
		try {
			String sql = "select id,"+cloumename +" from 201406_08_random_yahoo_news_" + table1+"_6000 union " +
						"select id,"+cloumename +" from 201406_08_random_yahoo_news_" + table2+"_6000 union " + 
						"select id,"+cloumename +" from 201406_08_random_yahoo_news_" + table3+"_6000";
			//System.out.println(sql);
			
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
			ArrayList<String> news = new ArrayList<String>();

			while (rs.next()) {
				// New new1 = new
				// New(rs.getString("id"),rs.getString("content"),rs.getString("CKIPSegmentContent"));
				news.add(rs.getString(cloumename));
			}
			return news;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Close();
		}
		return null;
	}
	
	public void Close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stat != null) {
				stat.close();
				stat = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		} catch (SQLException e) {
			System.out.println("Close Exception :" + e.toString());
		}
	}
}

