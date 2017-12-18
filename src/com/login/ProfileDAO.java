package com.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.context.FacesContext;

public class ProfileDAO {

    public static boolean updateuser(String firstname,String lastname,String address,String phonenumber,String email,String username,String password) {
        Connection con = null;
        PreparedStatement ps = null;
        //PreparedStatement ps1 = null;
        //String userid=null;
    String uid =(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid");
    
    
    
        try {
        	
        //	System.out.println("FN"+firstname);
            
            while(firstname.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET firstname=? WHERE uid=?");
            ps.setString(1, firstname);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                
                break;
                      
              }
            
            }
            
//            System.out.println("LN"+lastname);
            while(lastname.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET lastname=? WHERE uid=?");
            ps.setString(1, lastname);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                
                break;
                            
              }
            
            }          

//            System.out.println("AD"+address);
            while(address.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET address=? WHERE uid=?");
            ps.setString(1, address);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                
                break;
                            
              }
            
            }
            
         //   System.out.println("PN"+phonenumber.length());
            while(phonenumber.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET phonenumber=? WHERE uid=?");
            ps.setString(1, phonenumber);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                
                break;
                            
              }
            
            }
            
//            System.out.println("EN"+email);
            while(email.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET email=? WHERE uid=?");
            ps.setString(1, email);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                
                break;
                            
              }
            
            }
            
//            System.out.println("UN"+username);
            while(username.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET username=? WHERE uid=?");
            ps.setString(1, username);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                
                break;
                            
              }
            
            }
            
//            System.out.println("PS"+password);
            while(password.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET password=? WHERE uid=?");
            ps.setString(1, password);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                break;               
              }
            
            }
            
            
            
        } catch (SQLException ex) {
            System.out.println("Error -->" + ex.getMessage());
            return false;
        } finally {

        }
		return true;
   }
    
    public static boolean updatemanager(String firstname,String lastname,String address,String phonenumber,String email,String fee,String username,String password) {
        Connection con = null;
        PreparedStatement ps = null;
        //PreparedStatement ps1 = null;
        //String userid=null;
    String uid =(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid");
    
    
    
        try {
        	
        //	System.out.println("FN"+firstname);
            
            while(firstname.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET firstname=? WHERE uid=?");
            ps.setString(1, firstname);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                
                break;
                      
              }
            
            }
            
//            System.out.println("LN"+lastname);
            while(lastname.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET lastname=? WHERE uid=?");
            ps.setString(1, lastname);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                
                break;
                            
              }
            
            }          

//            System.out.println("AD"+address);
            while(address.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET address=? WHERE uid=?");
            ps.setString(1, address);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                
                break;
                            
              }
            
            }
            
         //   System.out.println("PN"+phonenumber.length());
            while(phonenumber.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET phonenumber=? WHERE uid=?");
            ps.setString(1, phonenumber);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                
                break;
                            
              }
            
            }
            
//            System.out.println("EN"+email);
            while(email.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET email=? WHERE uid=?");
            ps.setString(1, email);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                
                break;
                            
              }
            
            }
            
//            System.out.println("UN"+username);
            while(username.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET username=? WHERE uid=?");
            ps.setString(1, username);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                
                break;
                            
              }
            
            }
            
//            System.out.println("PS"+password);
            while(password.length()!=0) {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE users SET password=? WHERE uid=?");
            ps.setString(1, password);
            ps.setString(2, uid);
            boolean rs = ps.execute();
            if (rs==false) {  
                DataConnect.close(con);
                break;               
              }
            
            }
            
            while(fee.length()!=0) {
                con = DataConnect.getConnection();
                ps = con.prepareStatement("UPDATE users SET fee=? WHERE uid=?");
                ps.setString(1,fee);
                ps.setString(2, uid);
                boolean rs = ps.execute();
                if (rs==false) {  
                    DataConnect.close(con);
                    break;               
                  }
                
                }
                
            
        } catch (SQLException ex) {
            System.out.println("Error -->" + ex.getMessage());
            return false;
        } finally {

        }
		return true;
   }


}
