package com.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.context.FacesContext;

public class LoginDAO {

	public static int validateuser(String username, String password) {
		Connection con = null;
		PreparedStatement ps = null;
		// PreparedStatement ps1 = null;
		// String userid=null;
		System.out.println("Username2  " + username);
		System.out.println("password2  " + password);
		
		

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from users where username = ? and password = ?");
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstname", rs.getString("firstname"));
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastname", rs.getString("lastname"));
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("address", rs.getString("address"));
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("phonenumber", rs.getString("phonenumber"));
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("email", rs.getString("email"));
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fee", rs.getString("fee"));
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs.getString("username"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uid", rs.getString("uid"));
                if(rs.getInt("role")==0 && rs.getInt("approval")==1) {
        			return 0;
        		}
        		else if(rs.getInt("role")==1 && rs.getInt("approval")==1)
                {
                	DataConnect.close(con);
    				return 1;
                }else if(rs.getInt("role")==2 && rs.getInt("approval")==1){
                	DataConnect.close(con);
    				return 2;
                
                	
                }
				
			}

		}

		catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());

		} finally {

		}
		
		return -1;
	}
	
		

}
