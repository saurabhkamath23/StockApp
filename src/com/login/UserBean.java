package com.login;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable{

    private String username;
    private String password;
	private String firstname;
    private String lastname;
    private String address;
    private String phonenumber;
    private String email;
    private String fee;
    
    
    public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void clear() {
    	this.firstname=null;
    	this.lastname=null;
    	this.address=null;
    	this.phonenumber=null;
    	this.email=null;
    	this.username=null;
    	this.fee=null;
    	
    }
    public void ud() {
    	this.firstname=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("firstname");
//    	System.out.println("Email"+this.lastname);
    	this.lastname=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lastname");
    	this.address=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("address");
    	this.phonenumber=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("phonenumber");
    	this.email=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("email");
    	this.fee=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fee");
    	this.username=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
    	
    } 
    public String validateUser() {
//    	System.out.println("Username  "+username);
//    	System.out.println("password  "+password);
       int valid = LoginDAO.validateuser(username, password);
        System.out.println("Valid  "+valid);
        ud();
        
        if(valid==0) {
        	
        	return "adminhome?faces-redirect=true";
        }
        
        
        
        else if (valid==1) {
        	
        	return "userhome?faces-redirect=true";           
        }
        
        else if(valid==2){
        	return "managerhome?faces-redirect=true";
        	
        }
        else{
        	FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username and Passowrd. Please enter correct username and Password",""));
        	return "login";
        }
        
    }
    
    public String registerUser() {
    	
        boolean reg = RegistrationDAO.register(firstname,lastname,address,phonenumber,email,"0",username, password,"1","1");
        clear();
        //System.out.println(reg);
        
        if (reg == true) {
        	        	
            return "index";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Details",""));
            
        	
            return "registeruser";
        }
    }
    public String registerManager() {
    	
        boolean reg = RegistrationDAO.register(firstname,lastname,address,phonenumber,email,fee,username, password,"2","0");
        clear();
        //System.out.println(reg);
        
        if (reg == true) {
        	        	
            return "index";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Details",""));
            
        	
            return "register";
        }
    }
  
    public String updateUser() {
    	
        boolean user = ProfileDAO.updateuser(firstname, lastname, address, phonenumber, email,username, password);
//      clear();
                
        if (user == true) {
        	
            return "userhome?faces-redirect=true";
            
           
        } else {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username and Passowrd. Please enter correct username and Password",""));
        	
            return null;
        }

    
}
        
 public String updateManager() {
    	
        boolean user = ProfileDAO.updatemanager(firstname, lastname, address, phonenumber, email,fee,username, password);
//      clear();
                
        if (user == true) {
        	
            return "userhome?faces-redirect=true";
            
           
        } else {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username and Passowrd. Please enter correct username and Password",""));
        	
            return null;
        }

    
}
     
public String appman(String username) {
	
	System.out.println("approve"+username);
	
	boolean x=RegistrationDAO.appman(username);
	
	if (x==true)
	{
	return "adminhome";
	}
	return"-1";
}

    // logout event, invalidate session
    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }
}
