
/*HW2 第六組 劉弘偉 黃柏瑜
 * input:command line OR Scanner
 * output:a table contains text,user_name,user_id sorted by user_id
 * 
 * environment:eclipse in windows
 * console font:Courier New 標準  12
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;


public class IKDDhw2 {
	public static class sqldata{
		public String text;
		public String name;
		public long id;
		public sqldata(String s,String n,long i)
		{
			text=s;
			name=n;
			id=i;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String instr;
		if(args.length==0)
		{
			Scanner sc=new Scanner(System.in);
			System.out.println("input query string:");
			instr=sc.nextLine();
			sc.close();
		}
		else
		{
			instr=args[0];
		}
	      Connection c = null;
	      try {
	         int i;
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://iservdb.cloudopenlab.org.tw:5432/sydang.ncku_db_7291","sydang.ncku_user_7291","DzdbUjBy");

	         Statement st = c.createStatement();
	         String sql = " select * from twitter WHERE q = '"+instr+"' ORDER BY cast(user_id as bigint) ASC" ;
	         ResultSet rs = st.executeQuery(sql);
	         Vector<sqldata> temp=new Vector<sqldata>();
             while (rs.next())
             {
            	 sqldata tsd=new sqldata(rs.getString( "text" ),rs.getString( "user_name" ),rs.getLong( "user_id" ));
            	 tsd.text=tsd.text.replace("\n","");
            	 temp.add(tsd);
             }  
             System.out.println("－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－");
             System.out.println("|text　　　　　　　　　　　　　　　　　　　　　　　|user_name　　　　　　　　　　　|user_id　　　　　　　|");
             System.out.println("－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－");
             int d1=25;
             for(i=0;i<temp.size();++i)
             {
            	 if(temp.get(i).text.length()>d1)
            	 {
            		 int l=temp.get(i).text.length()/d1;
            		 System.out.println("|"+temp.get(i).text.substring(0,d1-1)+"\t|"+(temp.get(i).name+"　　　　　　　　　　　　　　　").substring(0,15)+"\t|"+temp.get(i).id+"\t|");
            		 for(int i1=1;i1<l;++i1)
            		 {
            			 System.out.println("|"+temp.get(i).text.substring(i1*d1,(i1+1)*d1-1)+"\t|\t\t\t|\t\t|");
            		 }
            		 System.out.println("|"+(temp.get(i).text.substring(l*d1)+"　　　　　　　　　　　　　　　　　　　　　　　　　").substring(0,d1-1)+"\t|\t\t\t|\t\t|");
            		 
            	 }
            	 else
            	 {
            		 System.out.println("|"+(temp.get(i).text+"　　　　　　　　　　　　　　　　　　　　　　　　　").substring(0,d1-1)+"\t|"+(temp.get(i).name+"　　　　　　　　　　　　　　　").substring(0,15)+"\t|"+temp.get(i).id+"\t|");
            	 }
            	 System.out.println("－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－");
             }
	         c.close();
	      } 
	      catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	}
}
