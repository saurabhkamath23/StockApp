package stockapi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.context.FacesContext;

import com.login.DataConnect;

public class Watchlist {

    public static boolean viewwatchlist() {
        Connection con = null;
        PreparedStatement ps = null;
        String uid =(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid");
        //PreparedStatement ps1 = null;
        //String userid=null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select * from watchlist where uid= ? ");
            ps.setString(1, uid);
            int i=0;

            ResultSet rs = ps.executeQuery();
            
                        //ps1 = con.prepareStatement("select uid from users where username = ? and password = ?");
            //ResultSet rs1=ps1.executeQuery();
            
            while (rs.next()) {
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs.getString("username"));
            	System.out.println("Stock"+i+rs.getString("stock_symbol"));
                i++;
            }
            
            DataConnect.close(con);
            return true;

        } catch (SQLException ex) {
            System.out.println("Error -->" + ex.getMessage());
            return false;
        } finally {

        }
        
    }
    


}
