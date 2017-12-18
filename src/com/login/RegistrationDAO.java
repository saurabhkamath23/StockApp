package com.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.context.FacesContext;

public class RegistrationDAO {

    public static boolean register(String firstname, String lastname, String address, String phonenumber, String email,String fee,String username, String password,String role,String approval) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("INSERT INTO users (firstname,lastname,address,phonenumber,email,fee,username,password,role,approval)"+" VALUES(?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, address);
            ps.setString(4, phonenumber);
            ps.setString(5, email);
            ps.setString(6, fee);
            ps.setString(7, username);
            ps.setString(8, password);
            ps.setString(9, role);
            ps.setString(10, approval);

            boolean rs = ps.execute();
            
        //    System.out.println("Now"+rs);

            if (rs==false) {
                DataConnect.close(con);
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("Registration error -->" + ex.getMessage());
            return false;
        } finally {

        }
        return false;
    }

    
public static boolean appman(String username) {
	
	System.out.println("approve1"+username);
	Connection con = null;
    PreparedStatement ps = null;

    try {
        con = DataConnect.getConnection();
        ps = con.prepareStatement("UPDATE users SET approval=1 WHERE username=username");
      
        
        boolean rs = ps.execute();
        
    //    System.out.println("Now"+rs);

        if (rs==false) {
        	
            DataConnect.close(con);
            return true;
        }

    } catch (SQLException ex) {
        System.out.println("Registration error -->" + ex.getMessage());
        return false;
    } finally {

    }
    return false;

	
	
	

}
}
