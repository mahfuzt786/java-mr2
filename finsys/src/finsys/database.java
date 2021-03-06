package finsys;

import static java.lang.String.format;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

public class database {

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
//DB CONNECTION

    database() {
        try {
            Class.forName("org.postgresql.Driver");
            //conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/finsys", "finsys", "finsys");
            conn = DriverManager.getConnection("jdbc:postgresql://pg-f5e9854.mahfuzt786-393a.aivencloud.com:23795/finsys?user=avnadmin&password=rf7r1qr059ur3fw3&sslmode=require");
           } catch (Exception e) {
            System.out.println(e);
        }
    }
//CHECKING LOGIN CREDENTIALS

    public Boolean checkLogin(String uname, String pwd) {
        try {
             pst = conn.prepareStatement("select * from finsys.mt_userlogin where userid=? and userpassword=?");
        
            pst.setString(1, uname);
            pst.setString(2, pwd);
            rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error while validating in checklogin" + e);
            return false;
        }
    }

    public int getUsercode(String uname, String pwd) {
        try {
            pst = conn.prepareStatement("select * from finsys.mt_userlogin where userid=? and userpassword=? and enabled=1");

            pst.setString(1, uname);
            pst.setString(2, pwd);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("usercode");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error while validating: in getusercode" + e);
            return 0;
        }
    }

    public int logdetails(String logname, String dt) {
        try {
            String sql = "SELECT MAX(logid) as max FROM finsys.logdetails";
            int id = getmax(sql);
            System.out.println("logid: " + id);
            pst = conn.prepareStatement("INSERT INTO finsys.logdetails(logid,logname) values(?,?)");

            pst.setInt(1, id);
            pst.setString(2, logname);

            System.out.println("logid: " + id);
            pst.executeUpdate();
            pst = conn.prepareStatement("INSERT INTO active_log.logdetails(logid,logname) values(?,?)");

            pst.setInt(1, id);
            pst.setString(2, logname);

            System.out.println("logid: " + id);
            pst.executeUpdate();
            
            return id;
        } catch (Exception e) {
            System.out.println("Error ::" + e);
            return 0;
        }
    }

    public int logout(int id) {
        try {

            System.out.println("logid: " + id);
            pst = conn.prepareStatement("update finsys.logdetails set logouttime=? where logid=?");

            java.util.Date t = new java.util.Date();

            pst.setTimestamp(1, new java.sql.Timestamp(t.getTime()));
            pst.setInt(2, id);

            System.out.println("logid: " + id);
            pst.executeUpdate();
            pst = conn.prepareStatement("update active_log.logdetails set logouttime=? where logid=?");

           
            pst.setTimestamp(1, new java.sql.Timestamp(t.getTime()));
            pst.setInt(2, id);

            System.out.println("logid: " + id);
            pst.executeUpdate();
            
            return id;
        } catch (Exception e) {
            System.out.println("Error ::" + e);
            return 0;
        }
    }

    //INSERT FUNCTION FOR MASTER TABLE
    public int insertemp(Issue_item i) {
        int flag = 0;
        String sql;
        int uomcode;
        System.out.println("Values: " + i.getItemcode() + " " + i.getItemname());
        try {
            sql = "SELECT MAX(uomcode) as max FROM finsys.t_uom";
            uomcode = getmax(sql);
            System.out.println("uomcode: " + uomcode);
            pst = conn.prepareStatement("INSERT INTO finsys.t_uom(uomcode,uomname,uomabbr) values(?,?,?)");

            pst.setInt(1, uomcode);
            pst.setString(2, i.getItemcode());
            pst.setString(3, i.getItemname());
            System.out.println("uomcode1: " + uomcode);
            flag = pst.executeUpdate();
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }

    public int insertms(ms_item i) {
        int flag = 0;
        String sql;
        int msid;
        System.out.println("Values: " + i.getMsname() + " " + i.getMsaddress() + " " + i.getMsphone());
        try {
            sql = "SELECT MAX(companyid) as max FROM finsys.m_fromcompany";
            msid = getmax(sql);
            System.out.println("MS id: " + msid);
            pst = conn.prepareStatement("INSERT INTO finsys.m_fromcompany(companyid,companyname,companyaddress,companyphone) values(?,?,?,?)");

            pst.setInt(1, msid);
            pst.setString(2, i.getMsname());
            pst.setString(3, i.getMsaddress());
            pst.setString(4, i.getMsphone());
            System.out.println("Ms id: " + msid);
            flag = pst.executeUpdate();
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }

    public int insertCostcenter(Costcentertable i) {
        int flag = 0;
        String sql;
        int centerid;
        System.out.println("Values: " + i.getCentercode() + " " + i.getCentername());
        try {
            sql = "SELECT MAX(centerid) as max FROM finsys.m_costcenter";
            centerid = getmax(sql);
            System.out.println("centercode: " + centerid);
            pst = conn.prepareStatement("INSERT INTO finsys.m_costcenter(centerid,centercode,centername) values(?,?,?)");

            pst.setInt(1, centerid);
            pst.setString(2, i.getCentercode());
            pst.setString(3, i.getCentername());
            System.out.println("centercode1: " + centerid);
            flag = pst.executeUpdate();
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }

    public int insertCategory(Categorytable i) {
        int flag = 0;
        String sql;
        int catid;
        System.out.println("Values: " + i.getCategoryname());
        try {
            sql = "SELECT MAX(categoryid) as max FROM finsys.m_itemcategory";
            catid = getmax(sql);
            String categorycode = "CAT" + catid;
            System.out.println("centercode: " + catid);
            pst = conn.prepareStatement("INSERT INTO finsys.m_itemcategory(categoryid,categorycode,categoryname) values(?,?,?)");

            pst.setInt(1, catid);
            pst.setString(2, categorycode);
            pst.setString(3, i.getCategoryname());
            // System.out.println("centercode1: " + centerid);
            flag = pst.executeUpdate();
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }

    public int getmax(String sql) {
        int maxcode = 0;
        try {
            pst = conn.prepareStatement(sql);

            System.out.println("in getmax" + sql);
            rs = pst.executeQuery();
            if (rs.next()) {

                maxcode = rs.getInt("max");
                maxcode += 1;

            } else {
                maxcode = 1;
            }
            return maxcode;
        } catch (Exception e) {
            System.out.println("Error in max uom" + e);
            return maxcode;
        }

    }

    public int insertSoemaingroup(Soemaingrouptable i) {
        int flag = 0;
        String sql;
        int id;
        System.out.println("Values: " + i.getSoemaingroupcode());
        try {
            sql = "SELECT MAX(soemaingroupid) as max FROM finsys.m_soemaingroup";
            id = getmax(sql);

            pst = conn.prepareStatement("INSERT INTO finsys.m_soemaingroup(soemaingroupid,soemaingroupcode,soemaingroupname) values(?,?,?)");

            pst.setInt(1, id);
            pst.setString(2, i.getSoemaingroupcode());
            pst.setString(3, i.getSoemaingroupname());
            // System.out.println("centercode1: " + centerid);
            flag = pst.executeUpdate();
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }

    public int insertItem(Itemtable i) {
        int flag = 0;
        String sql;
        String itemcode = "";
        int itemid = 0;
        int uomcd = Integer.valueOf(i.getUomcode());
        System.out.println("Values: " + i.getItemname());
        try {
            sql = "SELECT MAX(itemid) as max FROM finsys.m_item";
            itemid = getmax(sql);
            itemcode = "ITEM" + itemid;
            pst = conn.prepareStatement("INSERT INTO finsys.m_item(categoryid,itemtypeid,uomcode,itemid,itemcode,itemname) values(?,?,?,?,?,?)");

            pst.setInt(1, i.getCategoryid());

            pst.setInt(2, i.getItemtypeid());
            pst.setInt(3, uomcd);
            pst.setInt(4, itemid);
            pst.setString(5, itemcode);
            pst.setString(6, i.getItemname());

            // System.out.println("centercode1: " + centerid);
            flag = pst.executeUpdate();
            if (flag == 1) {
                pst = conn.prepareStatement("INSERT INTO finsys.t_stock(itemid,quantity,amount) values(?,?,?)");
                pst.setInt(1, itemid);

                pst.setDouble(2, 0);
                pst.setDouble(3, 0);
                flag = pst.executeUpdate();
            }
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating in insert :" + e);
            return flag;
        }
    }

    public int insertStockIn(Stockintable i) {
        int flag = 0;
        String sql;
        int slno;
        System.out.println("Values: ");
        try {
            sql = "SELECT MAX(slno) as max FROM finsys.t_stockin";
            slno = getmax(sql);

            pst = conn.prepareStatement("INSERT INTO finsys.t_stockin(slno,invoiceno"
                    + ",transportation_amt,less_per,from_company_id,tax_invoice_no ,tax_invoice_date ,challan_no"
                    + " ,challan_date,purchase_order_no,purchase_order_date,vat_per,invoiceid) values("
                    + "?,?,"
                    + "?,?,?,?,?,?,"
                    + "?,?,?,?,?)");

            pst.setInt(1, slno);

            pst.setString(2, i.getInvoiceid());
            pst.setDouble(3, Double.valueOf(i.getTransportation_amt()));
            pst.setDouble(4, Double.valueOf(i.getLess_per()));
            pst.setInt(5, Integer.valueOf(i.getFrom_company_id()));
            pst.setString(6, i.getTax_invoice_no());
            pst.setDate(7, UtilDate.convertStringToSqlDate("dd-MM-yyyy", i.getTax_invoice_date()));
            pst.setString(8, i.getChallan_no());
            pst.setDate(9, UtilDate.convertStringToSqlDate("dd-MM-yyyy", i.getChallan_date()));
            pst.setString(10, i.getPurchase_order_no());
            pst.setDate(11, UtilDate.convertStringToSqlDate("dd-MM-yyyy", i.getPurchase_order_date()));
            pst.setDouble(12, Double.valueOf(i.getVat_per()));
            pst.setString(13, i.getInvoiceid());

            flag = pst.executeUpdate();

            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating in insert :" + e);
            return flag;
        }
    }

    public int insertSoe(Soegrouptable i) {
        int flag = 0;
        String sql;
        int catid;
        System.out.println("Values: " + i.getSoegroupname());
        try {
            sql = "SELECT MAX(soegroupid) as max FROM finsys.m_soegroup";
            catid = getmax(sql);
            String categorycode = "SOE" + catid;
            System.out.println("sub catcode: " + catid);
            pst = conn.prepareStatement("INSERT INTO finsys.m_soegroup(soemaingroupid,soegroupid,soegroupcode,soegroupname) values(?,?,?,?)");
            pst.setInt(1, i.getSoemaingroupid());
            pst.setInt(2, catid);
            pst.setString(3, categorycode);
            pst.setString(4, i.getSoegroupname());
            // System.out.println("centercode1: " + centerid);
            flag = pst.executeUpdate();
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }

    public int insertLedger(Ledgertable i) {
        int flag = 0;
        String sql;
        String lcode = "";
        int lid = 0;

        System.out.println("Values: " + i.getLedgername());
        try {
            sql = "SELECT MAX(ledgerid) as max FROM finsys.m_ledger";
            lid = getmax(sql);
            lcode = "LEDGER" + lid;
            pst = conn.prepareStatement("INSERT INTO finsys.m_ledger(soemaingroupid,soegroupid,ledgerid,ledgercode,ledgername) values(?,?,?,?,?)");

            pst.setInt(1, i.getSoemaingroupid());
            pst.setInt(2, i.getSoegroupid());
            pst.setInt(3, lid);
            pst.setString(4, lcode);
            pst.setString(5, i.getLedgername());

            flag = pst.executeUpdate();
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating in insert :" + e);
            return flag;
        }
    }

    public int insertItemtype(Itemtypetable i) {
        int flag = 0;
        String sql;
        int iid;
        System.out.println("Values: " + i.getItemtypename());
        try {
            sql = "SELECT MAX(itemtypeid) as max FROM finsys.m_itemtype";
            iid = getmax(sql);

            System.out.println("centercode: " + iid);
            pst = conn.prepareStatement("INSERT INTO finsys.m_itemtype(itemtypeid,itemtypename) values(?,?)");

            pst.setInt(1, iid);
            pst.setString(2, i.getItemtypename());

            // System.out.println("centercode1: " + centerid);
            flag = pst.executeUpdate();
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }

    public int insertStockinitem(Stockinitemtable i) {
        int flag = 0;
        String sql;
        Double totalstockamount = 0.0, totalstockquantity = 0.0;
        System.out.println("Values: " + i.getItemid());
        try {

            pst = conn.prepareStatement("INSERT INTO finsys.t_stockin_items(invoiceid,itemid,item_rate,quantity) values(?,?,?,?)");

            pst.setString(1, i.getInvoiceid());

            pst.setInt(2, i.getItemid());

            pst.setDouble(3, Double.valueOf(i.getItem_rate()));
            pst.setInt(4, Integer.valueOf(i.getQuantity()));

            // System.out.println("centercode1: " + centerid);
            flag = pst.executeUpdate();
            if (flag == 1) {
                ArrayList<Stocktable> d = getStock(Integer.valueOf(i.getItemid()));
                for (Stocktable c : d) {
                    totalstockamount = Double.valueOf(c.getAmount());
                    totalstockquantity = Double.valueOf(c.getQuantity());
                }
                String query = "update finsys.t_stock set quantity='" + (totalstockquantity + Double.valueOf(i.getQuantity())) + "',amount='" + (totalstockamount + Double.valueOf(i.getItem_rate()) * Double.valueOf(i.getQuantity())) + "' where itemid='" + i.getItemid() + "'";
                pst = conn.prepareStatement(query);
                flag = pst.executeUpdate();
            }
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating in insert :" + e);
            return flag;
        }
    }

    public int insertStockout(Stockouttable i) {
        int flag = 0;
        String sql;
        int slno;
        String issue_return_code = "";
        System.out.println("Values: ");
        try {
            sql = "SELECT MAX(slno) as max FROM finsys.t_issue_return";
            slno = getmax(sql);
            issue_return_code = "BO" + i.getCostcenterid() + slno;
            pst = conn.prepareStatement("INSERT INTO finsys.t_issue_return(slno,issue_returncode"
                    + ",acc_post,receiptno,issue_or_return,costcenterid ,"
                    + "issueamt_value ,transportation_amt"
                    + " ) values(?,?,"
                    + "?,?,?,?,"
                    + "?,?)");

            pst.setInt(1, slno);

            pst.setString(2, issue_return_code);

            pst.setString(3, i.getAcc_post());
            pst.setString(4, issue_return_code);
            pst.setString(5, i.getIssue_or_return());
            pst.setInt(6, i.getCostcenterid());
            pst.setDouble(7, i.getIssueamt_value());
            pst.setDouble(8, 0);

            flag = pst.executeUpdate();

            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating in insert :" + e);
            return flag;
        }
    }

    public int insertStockoutitem(Stockoutitemtable i) {
        int flag = 0;
        String sql;
        Double totalstockamount = 0.0, totalstockquantity = 0.0;
        System.out.println("Values: " + i.getItemid());
        try {

            pst = conn.prepareStatement("INSERT INTO finsys.t_issue_items(itemid,reqquantity,issuequantity,issue_returncode,itemvalue,ledgerid) values(?,?,?,?,?,?)");

            pst.setInt(1, i.getItemid());

            pst.setDouble(2, i.getReqquantity());

            pst.setDouble(3, Math.abs(i.getIssuequantity()));
            pst.setString(4, i.getIssue_returncode());
            pst.setDouble(5, i.getItemvalue());
            pst.setInt(6, i.getLedgerid());

            // System.out.println("centercode1: " + centerid);
            flag = pst.executeUpdate();
            if (flag == 1) {
                ArrayList<Stocktable> d = getStock(i.getItemid());
                for (Stocktable c : d) {
                    totalstockamount = Double.valueOf(c.getAmount());
                    totalstockquantity = Double.valueOf(c.getQuantity());
                }

                String query = "update finsys.t_stock set quantity='" + (totalstockquantity - i.getIssuequantity()) + "',amount='" + (totalstockamount - (i.getItemvalue() * i.getIssuequantity())) + "' where itemid='" + i.getItemid() + "'";
                pst = conn.prepareStatement(query);
                flag = pst.executeUpdate();
            }
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating in insert :" + e);
            return flag;
        }
    }

    public int insertOpening(openingtable i) {
        int flag = 0;
        String sql;
        int slno;

        System.out.println("Values: ");
        try {
            sql = "SELECT MAX(slno) as max FROM finsys.t_openingstock";
            slno = getmax(sql);

            pst = conn.prepareStatement("INSERT INTO finsys.t_openingstock(slno,itemid"
                    + ",mnth,yr,ostockqty,ostockvalue"
                    + ") values("
                    + "?,?,"
                    + "?,?,?,?)");
            pst.setInt(1, slno);

            pst.setInt(2, i.getId());
            pst.setInt(3, i.getMonth());
            pst.setInt(4, i.getYear());
            pst.setDouble(5, Double.valueOf(i.getQuantity()));
            pst.setDouble(6, Double.valueOf(i.getValue()));

            flag = pst.executeUpdate();

            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating in insert :" + e);
            return flag;
        }
    }

    //GET FUNCTION FOR MASTER TABLE
    public ArrayList<Categorytable> getCategory() {
        ArrayList<Categorytable> catTable = new ArrayList<Categorytable>();
        String query = "select * from finsys.m_itemcategory";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Categorytable centerTab;
            while (rs.next()) {
                centerTab = new Categorytable(rs.getInt("categoryid"), rs.getString("categorycode"), rs.getString("categoryname"));
                catTable.add(centerTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return catTable;
    }

    public ArrayList<uomtable> getUom() {
        ArrayList<uomtable> catTable = new ArrayList<uomtable>();
        String query = "select * from finsys.t_uom";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            uomtable uomTab;
            while (rs.next()) {
                uomTab = new uomtable(rs.getInt("uomcode"), rs.getString("uomname"), rs.getString("uomabbr"));
                catTable.add(uomTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return catTable;
    }

    public ArrayList<Ledgertable> getLedger() {
        ArrayList<Ledgertable> lTable = new ArrayList<Ledgertable>();
        String query = "select * from finsys.m_ledger,finsys.m_soegroup,finsys.m_soemaingroup where m_ledger.soegroupid = m_soegroup.soegroupid AND m_ledger.soemaingroupid = m_soemaingroup.soemaingroupid";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Ledgertable lTab;
            while (rs.next()) {
                //lTab = new Ledgertable(rs.getInt("soemaingroupid"),rs.getInt("soegroupid"),rs.getInt("ledgerid"), rs.getString("ledgercode"), rs.getString("ledgername"));
                lTab = new Ledgertable(rs.getInt("soemaingroupid"), rs.getString("soemaingroupname"), rs.getInt("soegroupid"), rs.getString("soegroupname"), rs.getInt("ledgerid"), rs.getString("ledgercode"), rs.getString("ledgername"));
                lTable.add(lTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lTable;
    }

    public ArrayList<Soemaingrouptable> getSoemain() {
        ArrayList<Soemaingrouptable> catTable = new ArrayList<Soemaingrouptable>();
        String query = "select * from finsys.m_soemaingroup";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Soemaingrouptable centerTab;
            while (rs.next()) {
                centerTab = new Soemaingrouptable(rs.getInt("soemaingroupid"), rs.getString("soemaingroupcode"), rs.getString("soemaingroupname"));
                catTable.add(centerTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return catTable;
    }

    public ArrayList<Soegrouptable> getSoe() {
        ArrayList<Soegrouptable> catTable = new ArrayList<Soegrouptable>();
        String query = "select * from finsys.m_soegroup,finsys.m_soemaingroup WHERE m_soegroup.soemaingroupid = m_soemaingroup.soemaingroupid";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Soegrouptable centerTab;
            while (rs.next()) {
                centerTab = new Soegrouptable(rs.getInt("soemaingroupid"), rs.getString("soemaingroupname"), rs.getInt("soegroupid"), rs.getString("soegroupcode"), rs.getString("soegroupname"));
                catTable.add(centerTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return catTable;
    }

    //get soe group based on soemaingroupid
    public ArrayList<Soegrouptable> getSoe1(int soemaingroupid) {
        ArrayList<Soegrouptable> catTable = new ArrayList<Soegrouptable>();
        String query = "select * from finsys.m_soegroup,finsys.m_soemaingroup WHERE m_soegroup.soemaingroupid = m_soemaingroup.soemaingroupid AND soemaingroupid='" + soemaingroupid + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Soegrouptable centerTab;
            while (rs.next()) {
                centerTab = new Soegrouptable(rs.getInt("soemaingroupid"), rs.getString("soemaingroupname"), rs.getInt("soegroupid"), rs.getString("soegroupcode"), rs.getString("soegroupname"));
                catTable.add(centerTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return catTable;
    }

    public ArrayList<Itemtypetable> getItemtype() {
        ArrayList<Itemtypetable> iTable = new ArrayList<Itemtypetable>();
        String query = "select * from finsys.m_itemtype";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Itemtypetable iTab;
            while (rs.next()) {
                iTab = new Itemtypetable(rs.getInt("itemtypeid"), rs.getString("itemtypename"));
                iTable.add(iTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iTable;
    }

    public ArrayList<Itemtable> getItem() {
        ArrayList<Itemtable> iTable = new ArrayList<Itemtable>();
        //String query = "select * from finsys.m_item";
        String query = "select * from finsys.m_item,finsys.m_itemtype,finsys.m_itemcategory,finsys.t_uom where m_item.itemtypeid = m_itemtype.itemtypeid AND m_item.categoryid = m_itemcategory.categoryid AND m_item.uomcode = t_uom.uomcode ORDER BY itemid DESC";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Itemtable iTab;
            while (rs.next()) {
                //iTab = new Itemtable(rs.getInt("categoryid"),rs.getInt("itemid"),rs.getInt("itemtypeid"),
                //        rs.getString("itemcode"),rs.getString("itemname"),rs.getString("uomcode"));
                iTab = new Itemtable(rs.getInt("categoryid"), rs.getString("categoryname"), rs.getString("itemtypename"), rs.getString("uomabbr"), rs.getInt("itemid"), rs.getInt("itemtypeid"), rs.getString("itemcode"), rs.getString("itemname"), rs.getString("uomcode"));
                iTable.add(iTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iTable;
    }

    public ArrayList<Companytable> getCompany() {
        ArrayList<Companytable> cTable = new ArrayList<Companytable>();
        String query = "select * from finsys.m_fromcompany";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Companytable cTab;
            while (rs.next()) {
                cTab = new Companytable(rs.getInt("companyid"), rs.getString("companyname"));
                cTable.add(cTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cTable;
    }

    public ArrayList<Stockintable> getStockIn(String inv) {
        ArrayList<Stockintable> cTable = new ArrayList<Stockintable>();
        String query = "select t.slno,t.invoiceno,t.total_amt_value,to_char(t.entrydate,'dd-MM-yyyy') AS entrydate ,t.transportation_amt,t.less_per,t.from_company_id "
                + ",t.tax_invoice_no ,to_char(t.tax_invoice_date,'dd-MM-yyyy')AS tax_invoice_date ,t.challan_no ,to_char(t.challan_date,'dd-MM-yyyy') AS challan_date,t.purchase_order_no,to_char(t.purchase_order_date,'dd-MM-yyyy') AS purchase_order_date,"
                + "t.vat_per,invoiceid,t.total_gross_amt ,m.companyname"
                + " from finsys.t_stockin t inner join finsys.m_fromcompany m on m.companyid=t.from_company_id "
                + "where t.invoiceid='" + inv + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Stockintable sTab;
            while (rs.next()) {
                sTab = new Stockintable(rs.getString("slno"), rs.getString("invoiceno"), rs.getString("total_amt_value"),
                        rs.getString("entrydate"), rs.getString("transportation_amt"), rs.getString("less_per"),
                        rs.getString("from_company_id"), rs.getString("tax_invoice_no"), rs.getString("tax_invoice_date"), rs.getString("challan_no"),
                        rs.getString("challan_date"), rs.getString("purchase_order_no"), rs.getString("purchase_order_date"),
                        rs.getString("vat_per"), rs.getString("invoiceid"), rs.getString("total_gross_amt"), rs.getString("companyname"));

                cTable.add(sTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cTable;
    }

    public ArrayList<Stockinitemtable> getStockinitemtable(String inv, String iid) {
        ArrayList<Stockinitemtable> sTable = new ArrayList<Stockinitemtable>();
        String query = "select t.invoiceid,t.itemid,t.item_rate,t.quantity ,m.itemcode,m.itemname,(t.item_rate*t.quantity) as grossvalue"
                + " from finsys.t_stockin_items t inner join finsys.m_item m on m.itemid=t.itemid where t.invoiceid='" + inv + "' and t.itemid='" + iid + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Stockinitemtable siTab;

            while (rs.next()) {
                siTab = new Stockinitemtable(rs.getInt("itemid"), rs.getString("itemcode"), rs.getString("itemname"), rs.getString("invoiceid"),
                        rs.getString("item_rate"), rs.getString("quantity"), rs.getString("grossvalue"));

                sTable.add(siTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sTable;
    }

    public ArrayList<Stocktable> getStock(int itemid) {
        ArrayList<Stocktable> cTable = new ArrayList<Stocktable>();
        String query = "select * from finsys.t_stock where itemid='" + itemid + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Stocktable cTab;
            while (rs.next()) {
                cTab = new Stocktable(rs.getInt("itemid"), rs.getString("amount"), rs.getString("quantity"));
                cTable.add(cTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cTable;
    }

    public ArrayList<Costcentertable> getCC() {
        ArrayList<Costcentertable> cTable = new ArrayList<Costcentertable>();
        String query = "select * from finsys.m_costcenter ";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Costcentertable cTab;
            while (rs.next()) {
                cTab = new Costcentertable(rs.getInt("centerid"), rs.getString("centercode"), rs.getString("centername"));
                cTable.add(cTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cTable;
    }

    public ArrayList<Stockouttable> getStockout(String issue_returncode) {
        ArrayList<Stockouttable> cTable = new ArrayList<Stockouttable>();
        //String query = "select * from finsys.t_issue_return where issue_returncode='"+issue_returncode+"'";
        String query = "SELECT t_issue_return.issue_returncode,t_issue_return.acc_post,t_issue_return.issueamt_value,t_issue_return.receiptno,t_issue_return.transportation_amt,t_issue_return.issue_or_return,t_issue_return.costcenterid,t_issue_return.slno,to_char(t_issue_return.issuedate,'dd-MM-yyyy') as issuedate, m_costcenter.centerid, m_costcenter.centername from finsys.t_issue_return,finsys.m_costcenter where t_issue_return.costcenterid = m_costcenter.centerid and t_issue_return.issue_returncode='" + issue_returncode + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Stockouttable cTab;
            while (rs.next()) {
                /*cTab = new Stockouttable(rs.getString("issue_returncode"), rs.getString("acc_post"), rs.getString("issuedate"),
               rs.getString("receiptno"), rs.getString("issue_or_return"), rs.getInt("costcenterid"),rs.getDouble("issueamt_value"),rs.getDouble("transportation_amt") );
               cTable.add(cTab);*/
                cTab = new Stockouttable(rs.getString("issue_returncode"), rs.getString("acc_post"), rs.getString("issuedate"),
                        rs.getString("receiptno"), rs.getString("issue_or_return"), rs.getInt("costcenterid"), rs.getString("centername"), rs.getDouble("issueamt_value"), rs.getDouble("transportation_amt"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cTable;
    }

    public ArrayList<Stockoutitemtable> getStockoutitem(String issue_returncode, int itemid) {
        ArrayList<Stockoutitemtable> cTable = new ArrayList<Stockoutitemtable>();
        //String query = "select * from finsys.t_issue_items t inner join finsys.m_item m on m.itemid=m.itemid where issue_returncode='" + issue_returncode + "' and t.itemid='" + itemid + "'";
        String query = "select * from finsys.t_issue_items,finsys.m_itemcategory,finsys.m_ledger,finsys.m_item where m_itemcategory.categoryid = m_item.categoryid AND m_ledger.ledgerid = t_issue_items.ledgerid AND m_item.itemid = t_issue_items.itemid AND t_issue_items.issue_returncode='" + issue_returncode + "' and m_item.itemid='" + itemid + "'";
        
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Stockoutitemtable cTab;
            while (rs.next()) {
                /*cTab = new Stockoutitemtable(rs.getString("issue_returncode"), rs.getString("itemcode"), rs.getString("itemcode"), rs.getInt("itemid"), rs.getInt("ledgerid"),
                        rs.getDouble("reqquantity"), rs.getDouble("issuequantity"), rs.getDouble("itemvalue"), rs.getInt("categoryid"));*/
                cTab = new Stockoutitemtable(rs.getString("categoryname"), rs.getString("ledgername"), rs.getString("issue_returncode"), rs.getString("itemcode"), rs.getString("itemname"), rs.getInt("itemid"), rs.getInt("ledgerid"), rs.getDouble("reqquantity"), rs.getDouble("issuequantity"), rs.getDouble("itemvalue"), rs.getInt("categoryid"));

                cTable.add(cTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cTable;
    }

    public ArrayList<Itemtable> getItem1(int catid) {
        ArrayList<Itemtable> iTable = new ArrayList<Itemtable>();
        //String query = "select * from finsys.m_item where categoryid='"+catid+"'";
        String query = "select * from finsys.m_item,finsys.m_itemtype,finsys.m_itemcategory,finsys.t_uom where m_item.itemtypeid = m_itemtype.itemtypeid AND m_item.categoryid = m_itemcategory.categoryid AND m_item.uomcode = t_uom.uomcode AND m_item.categoryid='" + catid + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Itemtable iTab;
            while (rs.next()) {
                //iTab = new Itemtable(rs.getInt("categoryid"),rs.getInt("itemid"),rs.getInt("itemtypeid"),
                //        rs.getString("itemcode"),rs.getString("itemname"),rs.getString("uomcode"));
                iTab = new Itemtable(rs.getInt("categoryid"), rs.getString("categoryname"), rs.getString("itemtypename"), rs.getString("uomabbr"), rs.getInt("itemid"), rs.getInt("itemtypeid"), rs.getString("itemcode"), rs.getString("itemname"), rs.getString("uomcode"));

                iTable.add(iTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iTable;
    }

    public ArrayList<Menu> getMenu(int usercode) {
        ArrayList<Menu> m = new ArrayList<Menu>();
        //Integer usercode, Integer menucode, Integer add_p, Integer edit_p, Integer delete_p, Integer tabid, String menuname;
        String query = "select * "
                + " from finsys.mt_usermenu t inner join finsys.m_menu m on m.menucode=t.menucode "
                + "where t.usercode='" + usercode + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Menu mm;
            while (rs.next()) {
                mm = new Menu(rs.getInt("usercode"), rs.getInt("menucode"), rs.getInt("add_p"), rs.getInt("edit_p"), rs.getInt("delete_p"), rs.getInt("tabid"), rs.getString("menuname"));
                m.add(mm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public Menu getPrivilege(int usercode, int menucode) {
        Menu mm = new Menu();
        String query = "select * "
                + " from finsys.mt_usermenu t inner join finsys.m_menu m on m.menucode=t.menucode "
                + "where t.usercode='" + usercode + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                mm = new Menu(rs.getInt("usercode"), rs.getInt("menucode"), rs.getInt("add_p"), rs.getInt("edit_p"), rs.getInt("delete_p"), rs.getInt("tabid"), rs.getString("menuname"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mm;
    }

    public ArrayList<Usertable> getUser() {
        ArrayList<Usertable> iTable = new ArrayList<Usertable>();
        String query = "select * from finsys.mt_userlogin ";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Usertable iTab;
            while (rs.next()) {
                iTab = new Usertable(rs.getInt("usercode"), rs.getInt("enabled"), rs.getString("username"), rs.getString("userdescription"),
                        rs.getString("userid"), rs.getString("userpassword"), rs.getString("entrydate"), rs.getString("userrole"));
                iTable.add(iTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iTable;
    }

    public ArrayList<Usertable> getUser1(int usercode) {
        ArrayList<Usertable> iTable = new ArrayList<Usertable>();
        String query = "select * from finsys.mt_userlogin where usercode='" + usercode + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Usertable iTab;
            while (rs.next()) {
                iTab = new Usertable(rs.getInt("usercode"), rs.getInt("enabled"), rs.getString("username"), rs.getString("userdescription"),
                        rs.getString("userid"), rs.getString("userpassword"), rs.getString("entrydate"), rs.getString("userrole"));
                iTable.add(iTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iTable;
    }

    public ArrayList<Menu> getPrivilege1(int usercode) {
        ArrayList<Menu> mm = new ArrayList<Menu>();
        String query = "select * "
                + " from finsys.mt_usermenu t inner join finsys.m_menu m on m.menucode=t.menucode "
                + "where t.usercode='" + usercode + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Menu m;
            while (rs.next()) {
                m = new Menu(rs.getInt("usercode"), rs.getInt("menucode"), rs.getInt("add_p"), rs.getInt("edit_p"), rs.getInt("delete_p"), rs.getInt("tabid"), rs.getString("menuname"));
                mm.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mm;
    }

    public ArrayList<Menu> getPrivilege2(int usercode) {
        ArrayList<Menu> mm = new ArrayList<Menu>();
        String query = "select * from finsys.m_menu t left outer join("
                + " select * from finsys.mt_usermenu n "
                + " left outer join finsys.mt_userlogin mm on mm.usercode=n.usercode "
                + "where n.usercode='" + usercode + "'"
                + ")r1 on r1.menucode=t.menucode order by t.menucode";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Menu m;
            while (rs.next()) {
                m = new Menu(rs.getInt("usercode"), rs.getInt("menucode"), rs.getInt("add_p"), rs.getInt("edit_p"), rs.getInt("delete_p"), rs.getInt("tabid"), rs.getString("menuname"));
                mm.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mm;

    }

    //assign menu
    public int assignMenu(ArrayList<Menu> newmenu, int usercd) {
        int flag = 0;
        String sql;
        int iid, i;

        try {
            sql = "delete FROM finsys.mt_usermenu where usercode='" + usercd + "'";
            pst = conn.prepareStatement(sql);
            if ((i = pst.executeUpdate()) == 1) {
                System.out.println("User menu deleted!!" + usercd + "ii" + i);
            } else {
                System.out.println("User menu not deleted!!" + usercd + "ii" + i);

            }

            pst = conn.prepareStatement("INSERT INTO finsys.mt_usermenu(usercode,menucode"
                    + ",add_p,edit_p,delete_p"
                    + " ) values(?,?,"
                    + "?,?,?"
                    + ")");
            for (Menu m : newmenu) {
                pst.setInt(1, m.getUsercode());

                pst.setInt(2, m.getMenucode());
                pst.setInt(3, m.getAdd_p());
                pst.setInt(4, m.getEdit_p());
                pst.setInt(5, m.getDelete_p());
                flag = pst.executeUpdate();
            }

            return flag;

        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }

    //enable
    public int Enableuser(int usercode, int t) {
        int flag = 0;
        String sql;
        int iid;

        try {

            pst = conn.prepareStatement("update finsys.mt_userlogin set enabled=? where usercode=?");

            pst.setInt(1, t);
            pst.setInt(2, usercode);

            flag = pst.executeUpdate();
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }

    public int insertUser(Usertable i) {
        int flag = 0;
        String sql;
        int uomcode;
        try {
            sql = "SELECT MAX(usercode) as max FROM finsys.mt_userlogin";
            uomcode = getmax(sql);
            System.out.println("uomcode: " + uomcode);
            pst = conn.prepareStatement("INSERT INTO finsys.mt_userlogin(usercode,username,userdescription,userid,userpassword,userrole) values(?,?,?,?,?,?)");

            pst.setInt(1, uomcode);
            pst.setString(2, i.getUsername());
            pst.setString(3, i.getUserdescription());
            pst.setString(4, i.getUserid());
            pst.setString(5, i.getUserpassword());
            pst.setString(6, i.getUserrole());

            System.out.println("uomcode1: " + uomcode);
            flag = pst.executeUpdate();
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }

    public int insertMasterlog(Masterlog i) {
        int flag = 0;
        String sql;
        int slno;

        try {
            sql = "SELECT MAX(slno) as max FROM finsys.t_tablelogdetails";
            slno = getmax(sql);
            System.out.println("slno: " + slno);
            pst = conn.prepareStatement("INSERT INTO finsys.t_tablelogdetails(slno,tablename,uniquetableid,operation,usercode) values(?,?,?,?,?)");

            pst.setInt(1, slno);
            pst.setString(2, i.getTablename());
            pst.setInt(3, i.getUniquetableid());
            pst.setString(4, i.getOperation());
            pst.setInt(5, i.getUsercode());

            flag = pst.executeUpdate();
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }

    public int insertStocklog(Stocklog i) {
        int flag = 0;
        String sql;
        int slno;

        try {
            sql = "SELECT MAX(slno) as max FROM finsys.t_stocktablelogdetails";
            slno = getmax(sql);
            System.out.println("slno: " + slno);
            pst = conn.prepareStatement("INSERT INTO finsys.t_stocktablelogdetails(slno,tablename,uniquetableid,operation,usercode,itemid) values(?,?,?,?,?,?)");

            pst.setInt(1, slno);
            pst.setString(2, i.getTablename());
            pst.setString(3, i.getUniquetableid());
            pst.setString(4, i.getOperation());
            pst.setInt(5, i.getUsercode());
            pst.setInt(6, i.getItemcode());

            flag = pst.executeUpdate();
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }

    public int getStoutslno(String d) {
        int s = 0;
        String query = "select * from finsys.t_issue_return where issue_returncode='" + d + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Stockouttable cTab;
            while (rs.next()) {
                s = rs.getInt("slno");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    
    
     public int insertActivationdate() {
        int flag = 0;
        String sql;
        int slno,count;
        try {
            sql="SELECT COUNT(slno) as active FROM active_log.first_active";
            count=getactive(sql);
            if(count==0){
            sql = "SELECT MAX(slno) as max FROM active_log.first_active";
            slno = getmax(sql);
            pst = conn.prepareStatement("INSERT INTO active_log.first_active(slno) values(?)");

            pst.setInt(1, slno);
                   flag = pst.executeUpdate();
            
            }
            return flag;
        } catch (Exception e) {
            System.out.println("Error while validating :" + e);
            return flag;
        }
    }
     
     public int getactive(String sql) {
        int count = 0;
        try {
            pst = conn.prepareStatement(sql);

            System.out.println("in getmax" + sql);
            rs = pst.executeQuery();
            if (rs.next()) {

                count = rs.getInt("active");
                

            } 
            return count;
        } catch (Exception e) {
            System.out.println("Error in max uom" + e);
            return count;
        }

    }
     
      public ArrayList<active_log> getActive() {
        ArrayList<active_log> cTable = new ArrayList<active_log>();
        String query = "select * from active_log.first_active";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            active_log cTab;
            while (rs.next()) {
                cTab = new active_log(rs.getInt("slno"), rs.getString("initial_date"));
                cTable.add(cTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cTable;
    }
     
      public ArrayList<logtable> getLogTableA() {
        ArrayList<logtable> logTable = new ArrayList<logtable>();
        String query = "select logid,logname,to_char(logdate,'dd-MM-yyyy hh:mm:ss AM')AS logdate,to_char(logouttime,'dd-MM-yyyy hh:mm:ss AM')AS logouttime from active_log.logdetails order by logid desc";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            logtable log;
            while (rs.next()) {
                log = new logtable(rs.getInt("logid"), rs.getString("logname"), rs.getString("logdate"), rs.getString("logouttime"));
                logTable.add(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logTable;
    }
//select  now()<=max(logdate) as g from finsys.logdetails
     
       public boolean getDateTamper() {
        boolean st=false;
        String query = "select  now()<=max(logdate) as g  from active_log.logdetails ";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            logtable log;
            while (rs.next()) {
                st=rs.getBoolean("g");
               }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }
       
    public boolean getDateTamperlog() {
        boolean st=false;
        String query = "select  now()<=max(initial_date) as g  from active_log.first_active ";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            logtable log;
            while (rs.next()) {
                st=rs.getBoolean("g");
               }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }
    
    //select  now()-initial_date as g from active_log.first_active
    
     public int getDays() {
        int st=0;
        String query = "select  DATE_PART('day', now() - initial_date) as g  from active_log.first_active ";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            logtable log;
            while (rs.next()) {
                st=rs.getInt("g");
               }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }
   
}
