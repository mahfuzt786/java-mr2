/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finsys;

/**
 *
 * @author rupa
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Ledger extends javax.swing.JInternalFrame {

    String soemaingroup = "";
    String soegroup = "";
    String ledgername = "";
    database db;
    Ledgertable i = new Ledgertable();
    String dialogmessage;
    String dialogs;
    int dialogtype = JOptionPane.PLAIN_MESSAGE;
    database data = new database();
    public String ID;
    ArrayList<Soemaingrouptable> soemain;
    ArrayList<Soegrouptable> soe;
    DefaultTableModel model;
    Logdetails m;
    int ucode;

    /**
     * Creates new form cost center
     *
     * @param usercode
     */
    public Ledger(int usercode) {
        initComponents();
        ReloadTable();
        btnadd.setVisible(false);
        btnupdate.setVisible(false);
        btndelete.setVisible(false);
        ucode = usercode;
        db = new database();

        Menu m = db.getPrivilege(usercode, 14);
        if (m.getAdd_p() == 1) {
            btnadd.setVisible(true);

        }
        if (m.getEdit_p() == 1) {
            btnupdate.setVisible(true);

        }
        if (m.getDelete_p() == 1) {
            btndelete.setVisible(true);

        }
        soemain = db.getSoemain();
        soe = db.getSoe();
        jComboBox_soemain.addItem(new Comboitem(0, "Select SOE Main Group"));
        jComboBox_soe.addItem(new Comboitem(0, "Select SOE Group"));
        for (Soemaingrouptable c : soemain) {
            // Comboitem combo =new Comboitem(c.getCategorycode(),c.getCategoryname());
            jComboBox_soemain.addItem(new Comboitem(c.getSoemaingroupid(), c.getSoemaingroupname()));
        }

        //checking
        jComboBox_soemain.addItemListener(new ItemListener() {
            //
            // Listening if a new items of the combo box has been selected.
            //
            public void itemStateChanged(ItemEvent event) {
                JComboBox comboBox = (JComboBox) event.getSource();

                // The item affected by the event.
                Object item = event.getItem();
                jComboBox_soe.removeAllItems();
                jComboBox_soe.addItem(new Comboitem(0, "Select SOE Group"));
                System.out.println("Affected items: " + item.toString());
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    Comboitem g = (Comboitem) jComboBox_soemain.getSelectedItem();
                    int catid = g.getKey();
                    db = new database();
                    soe = db.getSoe1(catid);

                    for (Soegrouptable c : soe) {

                        jComboBox_soe.addItem(new Comboitem(c.getSoegroupid(), c.getSoegroupname()));
                    }

                }

                if (event.getStateChange() == ItemEvent.DESELECTED) {
                    jComboBox_soe.setSelectedIndex(0);
                }
            }
        });

    }

    /**
     *
     * @return
     */
    public ArrayList<Ledgertable> getLedgerTable() {
        ArrayList<Ledgertable> costTable = new ArrayList<Ledgertable>();
        String query = "select * from finsys.m_ledger,finsys.m_soegroup,finsys.m_soemaingroup where m_ledger.soegroupid = m_soegroup.soegroupid AND m_ledger.soemaingroupid = m_soemaingroup.soemaingroupid order by m_ledger.ledgerid desc";
        try {
            PreparedStatement pst = data.conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Ledgertable subitemTab;
            while (rs.next()) {
                subitemTab = new Ledgertable(rs.getInt("soemaingroupid"), rs.getString("soemaingroupname"), rs.getInt("soegroupid"), rs.getString("soegroupname"), rs.getInt("ledgerid"), rs.getString("ledgercode"), rs.getString("ledgername"));

                costTable.add(subitemTab);
            }
        } catch (Exception e) {
            System.out.println("Exception" + e);
            e.printStackTrace();
        }
        return costTable;
    }

    private void ReloadTable() {
        ArrayList<Ledgertable> subcatitemlist = getLedgerTable();
        model = (DefaultTableModel) jtable_ledger.getModel();
        model.setRowCount(0);
        Object[] row = new Object[4];
        for (int i = 0; i < subcatitemlist.size(); i++) {
            row[0] = subcatitemlist.get(i).getSoemaingroupid() + " - " + subcatitemlist.get(i).getSoe();
            row[1] = subcatitemlist.get(i).getSoegroupid() + " - " + subcatitemlist.get(i).getSoemain();
            row[2] = subcatitemlist.get(i).getLedgerid() + " - " + subcatitemlist.get(i).getLedgercode();
            row[3] = subcatitemlist.get(i).getLedgername();
            model.addRow(row);
        }
    }

    public void executeSqlQuery(String query, String message) {
        try {
            PreparedStatement pst = data.conn.prepareStatement(query);
            if ((pst.executeUpdate()) == 1) {
                ReloadTable();
                JOptionPane.showMessageDialog(null, "Data " + message + " Successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Data not " + message);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void ResetRecord() {
        jComboBox_soemain.setSelectedIndex(0);
        jComboBox_soe.setSelectedIndex(0);
        txtledger.setText("");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtledger = new javax.swing.JTextField();
        btnadd = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btnclear = new javax.swing.JButton();
        jComboBox_soemain = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox_soe = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable_ledger = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        btndelete = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setForeground(java.awt.Color.white);
        setTitle("Ledger");
        setToolTipText("");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/finsys/shivbari-23x23.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("LEDGER"));
        jPanel2.setOpaque(false);

        jLabel2.setText("SOE Main Group :");

        jLabel3.setText("Ledger Name :");

        btnadd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/finsys/icons/Add_16x16.png"))); // NOI18N
        btnadd.setText("add");
        btnadd.setOpaque(false);
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/finsys/icons/Edit_16x16.png"))); // NOI18N
        btnupdate.setText("update");
        btnupdate.setOpaque(false);
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btnclear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/finsys/icons/Cancel_16x16.png"))); // NOI18N
        btnclear.setText("clear");
        btnclear.setOpaque(false);
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jComboBox_soemain, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), jComboBox_soemain, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jLabel4.setText("SOE  Group :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnclear, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                    .addComponent(txtledger)
                    .addComponent(jComboBox_soemain, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_soe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox_soemain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox_soe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtledger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnadd)
                    .addComponent(btnupdate)
                    .addComponent(btnclear)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ledger Table"));
        jPanel3.setOpaque(false);

        jtable_ledger.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SOE Main Group (ID & NAME)", "SOE Group (ID & NAME)", "Ledger ID & CODE", "Ledger Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtable_ledger.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtable_ledgerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtable_ledger);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/finsys/icons/Search_16x16.png"))); // NOI18N
        jLabel1.setText("Search : ");

        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });

        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/finsys/icons/Delete_16x16.png"))); // NOI18N
        btndelete.setText("delete");
        btndelete.setOpaque(false);
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(153, 0, 153));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("** SELECT A ROW TO EDIT OR DELETE **");
        jLabel27.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))))
            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // update
        Comboitem g = (Comboitem) jComboBox_soemain.getSelectedItem();
        int catid = g.getKey();

        Comboitem g1 = (Comboitem) jComboBox_soe.getSelectedItem();
        int catid1 = g1.getKey();

        if (ID == null) {
            dialogmessage = "Please Select Record To Update";
            JOptionPane.showMessageDialog(null, dialogmessage,
                    "WARNING!!", JOptionPane.WARNING_MESSAGE);
        } else if (catid == 0) {
            dialogmessage = "PLEASE SELECT SOE MAIN GROUP !!!";
            JOptionPane.showMessageDialog(null, dialogmessage,
                    "ERROR!!", JOptionPane.ERROR_MESSAGE);
        } else if (catid1 == 0) {
            dialogmessage = "PLEASE SELECT SOE GROUP!!!";
            JOptionPane.showMessageDialog(null, dialogmessage,
                    "ERROR!!", JOptionPane.ERROR_MESSAGE);
        } else if (null==ledgername) {
            //"".equals(ledgername)
            dialogmessage = "PLEASE ENTER LEDGER NAME!!!";
            JOptionPane.showMessageDialog(null, dialogmessage,
                    "ERROR!!", JOptionPane.ERROR_MESSAGE);
        } else if (ID == null) {
            dialogmessage = "Please Select Record To Update";
            JOptionPane.showMessageDialog(null, dialogmessage,
                    "WARNING!!", JOptionPane.WARNING_MESSAGE);
        } else if (catid == 0 && catid1 == 0 && ledgername.equals("")) {
            dialogmessage = "Empty Record!!!";
            JOptionPane.showMessageDialog(null, dialogmessage,
                    "WARNING!!", JOptionPane.WARNING_MESSAGE);
        } else {
            ledgername = txtledger.getText().trim().toUpperCase();
            String query = "update finsys.m_ledger set soemaingroupid='" + catid + "',soegroupid='" + catid1 + "',ledgername='" + ledgername + "' where ledgerid='" + ID + "'";
            executeSqlQuery(query, "updated");
            m = new Logdetails();
            int l = m.Initialisem(0, "m_ledger", Integer.valueOf(ID), "U", ucode, "");
            ResetRecord();

        }
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        ResetRecord();
    }//GEN-LAST:event_btnclearActionPerformed

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed

        Comboitem g = (Comboitem) jComboBox_soemain.getSelectedItem();
        int catid = g.getKey();
        Comboitem g1 = (Comboitem) jComboBox_soe.getSelectedItem();
        int catid1 = g1.getKey();
        ledgername = txtledger.getText().trim().toUpperCase();
        if (catid == 0) {
            dialogmessage = "PLEASE SELECT SOE MAIN GROUP !!!";
            JOptionPane.showMessageDialog(null, dialogmessage,
                    "ERROR!!", JOptionPane.ERROR_MESSAGE);
        } else if (catid1 == 0) {
            dialogmessage = "PLEASE SELECT SOE GROUP!!!";
            JOptionPane.showMessageDialog(null, dialogmessage,
                    "ERROR!!", JOptionPane.ERROR_MESSAGE);
        } else if ("".equals(ledgername)) {
            dialogmessage = "PLEASE ENTER LEDGER NAME!!!";
            JOptionPane.showMessageDialog(null, dialogmessage,
                    "ERROR!!", JOptionPane.ERROR_MESSAGE);
        } else {
            db = new database();
            try {

                if (!(catid == 0) && !(catid1 == 0) && !ledgername.equals("")) {
                    i.setSoemaingroupid(catid);
                    i.setSoegroupid(catid1);
                    i.setLedgername(ledgername);
                    //System.out.println("values"+i);
                    int maxid = db.getmax("SELECT MAX(ledgerid) as max FROM finsys.m_ledger");
                    int result = db.insertLedger(i);
                    System.out.println(result);
                    if (result == 1) {
                        dialogmessage = "LEDGER ADDED SUCCESSFULLY";
                        JOptionPane.showMessageDialog(null, dialogmessage,
                                "SUCCESSFULL!!", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("Record Added");
                        m = new Logdetails();
                        int l = m.Initialisem(0, "m_ledger", maxid, "A", ucode, "");
                        ResetRecord();
                        ReloadTable();

                    } else {
                        dialogmessage = "Failed To Insert";
                        JOptionPane.showMessageDialog(null, "Failed To Insert in DataBase",
                                "WARNING!!", JOptionPane.WARNING_MESSAGE);

                    }

                } else {
                    dialogmessage = "Empty Record !!!";
                    dialogtype = JOptionPane.WARNING_MESSAGE;
                    JOptionPane.showMessageDialog(null, dialogmessage, dialogs, dialogtype);

                }

            } catch (Exception ex) {
                System.out.println("Error while validating :" + ex);
                JOptionPane.showMessageDialog(null, "GENERAL EXCEPTION", "WARNING!!!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnaddActionPerformed

    private void jtable_ledgerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtable_ledgerMouseClicked
        //Display selected row in textbox
        int i = jtable_ledger.getSelectedRow();
        TableModel model = jtable_ledger.getModel();
        String SoeMain = model.getValueAt(i, 0).toString();
        String[] splitSoemain = SoeMain.split("\\s+");
        setSelectedValue(jComboBox_soemain, Integer.valueOf(splitSoemain[0]));
        String Soe = model.getValueAt(i, 1).toString();
        String[] splitSoe = Soe.split("\\s+");
        setSelectedValue(jComboBox_soe, Integer.valueOf(splitSoe[0]));
        txtledger.setText(model.getValueAt(i, 3).toString());
        String ledgerID = model.getValueAt(i, 2).toString();
        String[] splitLedgerid = ledgerID.split("\\s+");
        ID = splitLedgerid[0];
    }//GEN-LAST:event_jtable_ledgerMouseClicked

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        //delete
        String sMSGBOX_TITLE = "FINSYS version 1.0";
        if (ID == null) {
            dialogmessage = "Please Select Record To Delete";
            JOptionPane.showMessageDialog(null, dialogmessage,
                    "WARNING!!", JOptionPane.WARNING_MESSAGE);
        } else {
            int reply = JOptionPane.showConfirmDialog(this, "Are you sure to want to delete this record?", sMSGBOX_TITLE, JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            //System.out.println(reply);
            if (reply == JOptionPane.YES_OPTION) {

                String query = "delete from finsys.m_ledger where ledgerid='" + ID + "'";
                executeSqlQuery(query, "deleted");
                m = new Logdetails();
                int l = m.Initialisem(0, "m_ledger", Integer.valueOf(ID), "D", ucode, "");
                ResetRecord();

            } else {
                remove(reply);
            }
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        // TODO add your handling code here:
        String query = search.getText().toUpperCase();
        filter(query);
    }//GEN-LAST:event_searchKeyReleased

    public void filter(String query) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        jtable_ledger.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    public void setSelectedValue(JComboBox combobox, int value) {
        Comboitem item;
        for (int i = 0; i < combobox.getItemCount(); i++) {
            item = (Comboitem) combobox.getItemAt(i);
            if (item.getKey() == value) {
                combobox.setSelectedIndex(i);
                break;
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnadd;
    private javax.swing.JButton btnclear;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnupdate;
    private javax.swing.JComboBox<Comboitem> jComboBox_soe;
    private javax.swing.JComboBox<Comboitem> jComboBox_soemain;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable_ledger;
    private javax.swing.JTextField search;
    private javax.swing.JTextField txtledger;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
