package stockapi;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import com.login.DataConnect;

@ManagedBean
@SessionScoped
public class StockApiBean {

    private static final long serialVersionUID = 1L;
    static final String API_KEY = "AF93E6L5I01EA39O";

    private String symbol;
    private double price;
    private int qty;
    private double amt;
    private String table1Markup;
    private String table2Markup;

	private String selectedSymbol;
    private List<SelectItem> availableSymbols;
    

    public String getPurchaseSymbol() {
        if (getRequestParameter("symbol") != null) {
            symbol = getRequestParameter("symbol");
        }
        return symbol;
    }
    
    public void setPurchaseSymbol(String purchaseSymbol) {
        System.out.println("func setPurchaseSymbol()");  //check
    }

    public double getPurchasePrice() {
        if (getRequestParameter("price") != null) {
            price = Double.parseDouble(getRequestParameter("price"));
            System.out.println("price: " + price);
        }
        return price;
    }

    public void setPurchasePrice(double purchasePrice) {
        System.out.println("func setPurchasePrice()");  //check
    }
    
    private String getRequestParameter(String name) {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter(name);
    }

    @PostConstruct
    public void init() {
        //initially populate stock list
        availableSymbols = new ArrayList<SelectItem>();
        availableSymbols.add(new SelectItem("AAPL", "Apple"));
        availableSymbols.add(new SelectItem("AMZN", "Amazon LLC"));
        availableSymbols.add(new SelectItem("AR", "Antero Resources"));
        availableSymbols.add(new SelectItem("EBAY", "Ebay"));
        availableSymbols.add(new SelectItem("FB", "Facebook, Inc."));
        availableSymbols.add(new SelectItem("GOLD", "Gold"));
        availableSymbols.add(new SelectItem("GOOGL", "Google"));
        availableSymbols.add(new SelectItem("MSFT", "Microsoft"));
        availableSymbols.add(new SelectItem("SLV", "Silver"));
        availableSymbols.add(new SelectItem("TWTR", "Twitter, Inc."));

        //initially populate intervals for stock api
        availableIntervals = new ArrayList<SelectItem>();
        availableIntervals.add(new SelectItem("1min", "1min"));
        availableIntervals.add(new SelectItem("5min", "5min"));
        availableIntervals.add(new SelectItem("15min", "15min"));
        availableIntervals.add(new SelectItem("30min", "30min"));
        availableIntervals.add(new SelectItem("60min", "60min"));
    }
    private String watchtable="";
    private String managertable="";
    
    public String getManagertable() {
		return managertable;
	}

	public void setManagertable(String managertable) {
		this.managertable = managertable;
	}

	public String getWatchtable() {
		return watchtable;
	}

	public void setWatchtable(String watchtable) {
		this.watchtable = watchtable;
	}


    private String selectedInterval;
    private List<SelectItem> availableIntervals;

    public String getSelectedInterval() {
        return selectedInterval;
    }

    public void setSelectedInterval(String selectedInterval) {
        this.selectedInterval = selectedInterval;
    }

    public List<SelectItem> getAvailableIntervals() {
        return availableIntervals;
    }

    public void setAvailableIntervals(List<SelectItem> availableIntervals) {
        this.availableIntervals = availableIntervals;
    }

    public String getSelectedSymbol() {
        return selectedSymbol;
    }

    public void setSelectedSymbol(String selectedSymbol) {
        this.selectedSymbol = selectedSymbol;
    }

    public List<SelectItem> getAvailableSymbols() {
        return availableSymbols;
    }

    public void setAvailableSymbols(List<SelectItem> availableSymbols) {
        this.availableSymbols = availableSymbols;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getTable1Markup() {
        return table1Markup;
    }

    public void setTable1Markup(String table1Markup) {
        this.table1Markup = table1Markup;
    }

    public String getTable2Markup() {
        return table2Markup;
    }

    public void setTable2Markup(String table2Markup) {
        this.table2Markup = table2Markup;
    }

    public String createDbRecord(String symbol, double price, int qty, double amt) {
        try {
            //System.out.println("symbol: " + this.symbol + ", price: " + this.price + "\n");
            //System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");

            Connection conn = DataConnect.getConnection();
            Statement statement = conn.createStatement();
            
            //get userid
            Integer uid = Integer.parseInt((String) FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap().get("uid"));
            
            System.out.println(uid);
            System.out.println("symbol:" + symbol);
            System.out.println("price:" + price);
            System.out.println("qty:" + qty);
            System.out.println("amt:" + amt);
            statement.executeUpdate("INSERT INTO `purchase` (`uid`, `stock_symbol`, `qty`, `price`, `amt`) "
                    + "VALUES ('" + uid + "','" + symbol + "','" + qty + "','" + price + "','" + amt +"')");
            
            statement.close();
            conn.close();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully purchased stock",""));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "purchase";
    }

    public void installAllTrustingManager() {
        TrustManager[] trustAllCerts;
        trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }
        return;
    }

    public void timeseries() throws MalformedURLException, IOException {

        installAllTrustingManager();

//        System.out.println("selectedItem: " + this.selectedSymbol);
        //System.out.println("selectedInterval: " + this.selectedInterval);
        String symbol = this.selectedSymbol;
        System.out.println("Symbol  "+symbol);
        String interval = this.selectedInterval;
        System.out.println("Interval  "+interval);
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=" + interval + "&apikey=" + API_KEY;
        System.out.println("url1  "+url);

        this.table1Markup = "URL::: <a href='" + url + "'>Data Link</a><br>"+this.table1Markup;
        InputStream inputStream = new URL(url).openStream();

        // convert the json string back to object
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject mainJsonObj = jsonReader.readObject();
        System.out.println("mainJsonObj1  "+mainJsonObj);
        for (String key : mainJsonObj.keySet()) {
            if (key.equals("Meta Data")) {
//                System.out.println("TM"+this.table1Markup);
            	this.table1Markup=""; // reset table 1 markup before repopulating
            	
                JsonObject jsob = (JsonObject) mainJsonObj.get(key);
                this.table1Markup += "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
//                System.out.println("TM1"+this.table1Markup);
                this.table1Markup += "<table>";
                this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
                this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
                this.table1Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed") + "</td></tr>";
                this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
                this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
                this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
                this.table1Markup += "</table>";
                //this.table1Markup=null; // reset table 1 markup before repopulating
                
            } else {
                this.table2Markup = ""; // reset table 2 markup before repopulating
                JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
                this.table2Markup += "<table class='table table-hover'>";
                this.table2Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
                this.table2Markup += "<tbody>";
                int i = 0;
                System.out.println("subkey"+dataJsonObj.keySet());
                for (String subKey : dataJsonObj.keySet()) {
                    JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
                    this.table2Markup
                            += "<tr>"
                            + "<td>" + subKey + "</td>"
                            + "<td>" + subJsonObj.getString("1. open") + "</td>"
                            + "<td>" + subJsonObj.getString("2. high") + "</td>"
                            + "<td>" + subJsonObj.getString("3. low") + "</td>"
                            + "<td>" + subJsonObj.getString("4. close") + "</td>"
                            + "<td>" + subJsonObj.getString("5. volume") + "</td>";
                    if (i == 0) {
                        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                       // System.out.println("path"+path);
                        this.table2Markup += "<td><a class='btn btn-success' href='" + path + "/faces/purchase.xhtml?symbol=" + symbol + "&price=" + subJsonObj.getString("4. close") + "'>Buy Stock</a></td>";
                    }
                    this.table2Markup += "</tr>";
                    i++;
                }
                this.table2Markup += "</tbody></table>";
            }
        }
        return;
    }

    public void purchaseStock() {
        System.out.println("Calling function purchaseStock()");
        System.out.println("stockSymbol: " + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("stockSymbol"));
        System.out.println("stockPrice" + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("stockPrice"));
        return;
    }
    
    public String watchlist() {
    	   try {
               //System.out.println("symbol: " + this.symbol + ", price: " + this.price + "\n");
               //System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");

               Connection conn = DataConnect.getConnection();
               Statement statement = conn.createStatement();
               String symbol = this.selectedSymbol;
               //get userid
               Integer uid = Integer.parseInt((String) FacesContext.getCurrentInstance()
                       .getExternalContext()
                       .getSessionMap().get("uid"));
    
               System.out.println(uid);
               System.out.println("symbol:" + symbol);
               statement.executeUpdate("INSERT INTO `watchlist` (`id`, `uid`, `stock_symbol`) "
                       + "VALUES (NULL,'" + uid + "','" + symbol + "')");
               
               
               statement.close();
               conn.close();
              FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully added to watchlist",""));
           } catch (SQLException e) {
               e.printStackTrace();
           }
           return "";

    }
    
    public  String viewwatchlist() throws MalformedURLException, IOException {
    	installAllTrustingManager();
        Connection con = null;
        PreparedStatement ps = null;
        String uid =(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid");

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select * from watchlist where uid= ? ");
            ps.setString(1, uid);
            int i=1;

            ResultSet rs = ps.executeQuery();
            
            this.watchtable+="";
            this.watchtable += "<table class='table table-hover'>";
            this.watchtable += "<thead><tr><th>Stock Number</th><th>Stock Symbol</th><th>Most Recent Price</th></tr></thead>";
            this.watchtable += "<tbody>";
            
//            int j = 0;
            while (rs.next()) {
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs.getString("username"));
//            	System.out.println("Stock"+i+rs.getString("stock_symbol"));
            	 String rp="";
            	 String interval = "1min";
                 String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + rs.getString("stock_symbol") + "&interval=" + interval + "&apikey=" + API_KEY;
               
//               	 System.out.println("url  "+url);
            	 InputStream inputStream = new URL(url).openStream();
//            	 System.out.println("inputstream  "+inputStream);
                 JsonReader jsonReader = Json.createReader(inputStream);
//                 System.out.println("jsonread  "+jsonReader);
                 JsonObject mainJsonObj = jsonReader.readObject();
//                 System.out.println("mainJsonObj  "+mainJsonObj);
                 
                 // convert the json string back to object
                 for (String key : mainJsonObj.keySet()) {
                	 if (key.equals("Time Series (1min)")) {
                	 	 JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
//                	 	 System.out.println("dataJsonObj  "+dataJsonObj);
                         
//                         System.out.println("subkey"+dataJsonObj.keySet());
                         for (String subKey : dataJsonObj.keySet()) {
//                           System.out.println("subkey "+subKey);

                             JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
//                            
                                 rp= subJsonObj.getString("4. close") ;
                                 break;
                                 
//                            }
                             
                         }
                             
                	 }  
                       
                     }
                     
                    this.watchtable
                            += "<tr>"
                            + "<td>" + i + "</td>"
                            + "<td>" + rs.getString("stock_symbol") + "</td>"+"<td>"+rp+"</td>";
                    
                    System.out.println("Table  "+this.watchtable);
                    i++;
                }
            	
            	this.watchtable+= "</tr>";
                this.watchtable += "</tbody></table>";
          
            
            DataConnect.close(con);
            return "watchlist";

        
            
        }    
            catch (SQLException ex) {
            System.out.println("Error -->" + ex.getMessage());
            return null;
        } finally {

        }        
        
    
    
        
    }
    
    public  String viewmanagerlist() throws MalformedURLException, IOException {
    	installAllTrustingManager();
        Connection con = null;
        PreparedStatement ps = null;
        String uid =(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid");

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select * from users where role= 2 and approval=0");
            
            int i=1;

            ResultSet rs = ps.executeQuery();
            
            this.managertable+="";
            this.managertable += "<table class='table table-hover'>";
            this.managertable+= "<thead><tr><th>Number</th><th>Manager Username</th><th>Email</th><th>UID</th></tr></thead>";
            this.managertable+= "<tbody>";
            String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
//            int j = 0;
            while (rs.next()) {
            	                     
                    this.managertable
                            += "<tr>"
                            + "<td>" + i + "</td>"
                            + "<td>" + rs.getString("username") + "</td>"+"<td>"+rs.getString("email")+"</td>"+"<td>"+rs.getString("uid")+"</td>";
                    this.managertable+="<td><a class='btn btn-success' href='" + path + "/faces/approval.xhtml?username=" + rs.getString("username") +"'>Manage Request</a></td>";
                     
//                    System.out.println("Table  "+this.managertable);
                    i++;
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs.getString("username"));
                }
            	
            	this.managertable+= "</tr>";
                this.managertable += "</tbody></table>";
          
            
            DataConnect.close(con);
            return "";

        
            
        }    
            catch (SQLException ex) {
            System.out.println("Error -->" + ex.getMessage());
            return null;
        } finally {

        }        
        
    
    
        
    }

    
}

