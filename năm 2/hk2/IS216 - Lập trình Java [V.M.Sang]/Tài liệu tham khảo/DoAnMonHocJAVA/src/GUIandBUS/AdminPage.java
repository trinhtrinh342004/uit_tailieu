/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIandBUS;

import dao.ConnectionUtils;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.RowFilter;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * @author Phuc
 * @author Minh Nhut
 */

public class AdminPage extends javax.swing.JFrame {

    /**
     * Creates new form AdminPage
     */
    
    DefaultTableModel modelNV = null;
    DefaultTableModel modelKH = null;
    DefaultTableModel modelPhim = null;
    DefaultTableModel modelSuat = null;
    DefaultTableModel modelLich = null;
    ArrayList<String> matl = new ArrayList<>();
    ArrayList<String> tentl = new ArrayList<>();
    ArrayList<String> maphong = new ArrayList<>();
    ArrayList<String> maphim = new ArrayList<>();
    ArrayList<String> tenphong = new ArrayList<>();
    ArrayList<String> tenphim = new ArrayList<>();
    ArrayList<String> masc = new ArrayList<>();
    ArrayList<String> suatchieu = new ArrayList<>();
    
    private void cbb_TL(){
        try (Connection con = ConnectionUtils.getMyConnection()){
            String SQL = "SELECT DISTINCT MATL,THELOAI FROM THELOAIPHIM";
            Statement statement= con.createStatement();
            ResultSet rs=statement.executeQuery(SQL);
            cbb_TL.removeAllItems();
            matl.clear();
            tentl.clear();
            while(rs.next()){
                matl.add(rs.getString("MATL"));
                tentl.add(rs.getString("THELOAI"));
            }
        cbb_TL.setModel(new DefaultComboBoxModel(tentl.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  

    private void cbb_Phong(){
        try (Connection con = ConnectionUtils.getMyConnection()){
            String SQL = "SELECT DISTINCT MAPHONG,TENPHONG FROM PHONG ORDER BY MAPHONG";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            cbb_Phong.removeAllItems();
            maphong.clear();
            tenphong.clear();
            while(rs.next()){
                maphong.add(rs.getString("MAPHONG"));
                tenphong.add(rs.getString("TENPHONG"));
            }
        cbb_Phong.setModel(new DefaultComboBoxModel(tenphong.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void cbb_Phim(){
        try (Connection con = ConnectionUtils.getMyConnection()){
            String SQL = "SELECT DISTINCT MAPHIM,TENPHIM FROM PHIM ORDER BY MAPHIM";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            cbb_Phim.removeAllItems();
            maphim.clear();
            tenphim.clear();
            while(rs.next()){
                maphim.add(rs.getString("MAPHIM"));
                tenphim.add(rs.getString("TENPHIM"));
            }
        cbb_Phim.setModel(new DefaultComboBoxModel(tenphim.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cbb_sc(){
        try (Connection con = ConnectionUtils.getMyConnection()){
            SimpleDateFormat DateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat TimeFormatter = new SimpleDateFormat("HH:mm");
            String SQL = "SELECT DISTINCT MASUAT,THOIGIANBD,THOIGIANKT,NGAYCHIEU FROM SUATCHIEU ORDER BY NGAYCHIEU";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            cbb_SC.removeAllItems();
            masc.clear();
            suatchieu.clear();
            while(rs.next()){
                masc.add(rs.getString("MASUAT"));
                String tgbd = TimeFormatter.format(rs.getTime("THOIGIANBD"));
                String tgkt = TimeFormatter.format(rs.getTime("THOIGIANKT"));
                String ngchieu = DateFormatter.format(rs.getDate("NGAYCHIEU"));
                suatchieu.add(tgbd + "-" + tgkt + " ngày " + ngchieu);
            }
        cbb_SC.setModel(new DefaultComboBoxModel(suatchieu.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void JTableNV(){ 
        try (Connection con = ConnectionUtils.getMyConnection()){
            String SQL = "SELECT MANV,HO,TEN,NGAYSINH,NGAYLAM,GIOITINH,CMND,SODT,DIACHI,LUONG FROM NHANVIEN ORDER BY MANV";
            Statement statement = con.createStatement();
            ResultSet rs=statement.executeQuery(SQL);
            modelNV = (DefaultTableModel) jTableNV.getModel();
            modelNV.setRowCount(0);
            while(rs.next())
                {
                   String MaNV = rs.getString(1); 
                   String HoNV = rs.getString(2);
                   String TenNV = rs.getString(3);
                   java.util.Date d_NgSinhNV = rs.getDate(4);
                   java.util.Date d_NgVL = rs.getDate(5);
                   String S_NgSinhNV = String.format("%1$td-%1$tm-%1$tY", d_NgSinhNV);
                   String S_NgVL = String.format("%1$td-%1$tm-%1$tY", d_NgVL);
                   String GTNV = rs.getString(6);
                   String CMND = rs.getString(7);
                   String SDT = rs.getString(8);
                   String DC = rs.getString(9);
                   long Luong = Long.parseLong(rs.getString(10));
                   modelNV.addRow(new Object[]{MaNV,HoNV,TenNV,S_NgSinhNV,S_NgVL,GTNV,CMND,SDT,DC,Luong});
                }
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void JTableKH() {
        try (Connection con = ConnectionUtils.getMyConnection()){
            String SQL = "SELECT MAKH,HO,TEN,NGAYSINH,GIOITINH,LOAIKH,USERNAME,PASSWORD,TICHLUY FROM KHACHHANG ORDER BY MAKH";
            Statement statement= con.createStatement();
            ResultSet rs=statement.executeQuery(SQL);
            modelKH = (DefaultTableModel) jTableKH.getModel();
            modelKH.setRowCount(0);
            while(rs.next())
            {
                String MaKH = rs.getString(1);
                String HoKH = rs.getString(2);
                String TenKH = rs.getString(3);
                java.util.Date d_NgSinhKH = rs.getDate(4);
                String S_NgSinhKH = String.format("%1$td-%1$tm-%1$tY", d_NgSinhKH);
                String GTKH = rs.getString(5);
                String LOAIKH = rs.getString(6);
                String USERNAME = rs.getString(7);
                String PASSWORD = rs.getString(8);
                int TL = Integer.parseInt(rs.getString(9));
                modelKH.addRow(new Object[]{MaKH,USERNAME,PASSWORD,HoKH,TenKH,S_NgSinhKH,GTKH,LOAIKH,TL});
            }
            con.close();
            } 
        catch (ClassNotFoundException | NumberFormatException | SQLException ex) {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void JTablePhim(){
            try (Connection con = ConnectionUtils.getMyConnection()) {
                String SQL_PHIM = "SELECT PHIM.MAPHIM,PHIM.TENPHIM,THELOAIPHIM.THELOAI,PHIM.DAODIEN,PHIM.DIENVIEN,PHIM.NHAPH,PHIM.NGAYPH,PHIM.DOANHTHU FROM PHIM,THELOAIPHIM WHERE PHIM.MATL = THELOAIPHIM.MATL ORDER BY PHIM.MAPHIM";
                Statement statement= con.createStatement();
                ResultSet rs=statement.executeQuery(SQL_PHIM);
                modelPhim = (DefaultTableModel) jTablePhim.getModel();
                modelPhim.setRowCount(0);
                while(rs.next())
                {
                    String MaPhim = rs.getString(1);
                    String TenPhim = rs.getString(2);
                    String TL = rs.getString(3);
                    String DD = rs.getString(4);
                    String DV = rs.getString(5);
                    String NhaPH = rs.getString(6);
                    java.util.Date d_NgPH = rs.getDate(7);
                    String S_NgPH = String.format("%1$td-%1$tm-%1$tY", d_NgPH);
                    long DT = Long.parseLong(rs.getString(8));
                    modelPhim.addRow(new Object[]{MaPhim,TenPhim,TL,DD,DV,NhaPH,S_NgPH,DT});
                }
            }
            catch (ClassNotFoundException | NumberFormatException | SQLException ex) {
                Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    private void JTableSuat(){    
        try (Connection con = ConnectionUtils.getMyConnection()){
            String SQL_SUAT = "SELECT MASUAT,THOIGIANBD,THOIGIANKT,NGAYCHIEU FROM SUATCHIEU ORDER BY MASUAT";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_SUAT);
            SimpleDateFormat TimeFormatter = new SimpleDateFormat("HH:mm");
            SimpleDateFormat DateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            modelSuat = (DefaultTableModel) jTableSC.getModel();
            modelSuat.setRowCount(0);
            while(rs.next()){
                   String MaSuat = rs.getString(1);
                   String t_TGBD = TimeFormatter.format(rs.getTime(2));
                   String t_TGKT = TimeFormatter.format(rs.getTime(3));
                   String d_NgChieu = DateFormatter.format(rs.getDate(4));
                   modelSuat.addRow(new Object[]{MaSuat,t_TGBD,t_TGKT,d_NgChieu});
                }
            con.close();
        } 
        catch (Exception ex){
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void JTableLich(){    
        try (Connection con = ConnectionUtils.getMyConnection()){
            String SQL = "SELECT MALICHCHIEU,TENPHIM,TENPHONG,THOIGIANBD,THOIGIANKT,NGAYCHIEU \n" 
            + "FROM LICHCHIEU,PHIM,PHONG,SUATCHIEU \n" 
            + "WHERE LICHCHIEU.MASUAT=SUATCHIEU.MASUAT AND LICHCHIEU.MASUAT = SUATCHIEU.MASUAT AND LICHCHIEU.MAPHIM = PHIM.MAPHIM AND LICHCHIEU.MAPHONG = PHONG.MAPHONG\n" 
            + "ORDER BY MALICHCHIEU";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            SimpleDateFormat TimeFormatter = new SimpleDateFormat("HH:mm");
            SimpleDateFormat DateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            modelLich = (DefaultTableModel) jTableLC.getModel();
            modelLich.setRowCount(0);
            while(rs.next()){
                   String MaLich = rs.getString("MALICHCHIEU");
                   String Phim = rs.getString("TENPHIM");
                   String Phong = rs.getString("TENPHONG");
                   String t_TGBD = TimeFormatter.format(rs.getTime("THOIGIANBD"));
                   String t_TGKT = TimeFormatter.format(rs.getTime("THOIGIANKT"));
                   String d_NgChieu = DateFormatter.format(rs.getDate("NGAYCHIEU"));
                   modelLich.addRow(new Object[]{MaLich,Phim,Phong,t_TGBD,t_TGKT,d_NgChieu});
                }
            con.close();
        } 
        catch (Exception ex){
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void Row_KH (int index){
        v_MaKH.setEnabled(true);
        v_LoaiKH.setEnabled(true);
        v_DTL.setEnabled(true);
        try{
            v_MaKH.setText((String)(modelKH.getValueAt(index,0)));
            v_TKKH.setText((String)(modelKH.getValueAt(index,1)));
            v_MKKH.setText((String)(modelKH.getValueAt(index,2)));
            v_HoKH.setText((String)(modelKH.getValueAt(index,3)));
            v_TenKH.setText((String)(modelKH.getValueAt(index,4)));
            java.util.Date d_NgSinhKH = new SimpleDateFormat("dd-MM-yyyy").parse((String)(modelKH.getValueAt(index,5)));
            v_NgSinhKH.setDate(d_NgSinhKH);
            v_GTKH.setSelectedItem(modelKH.getValueAt(index,6));
            v_LoaiKH.setSelectedItem(modelKH.getValueAt(index,7));
            v_DTL.setText(String.valueOf(modelKH.getValueAt(index,8)));
        }catch(ParseException e){System.out.println(e);} 
    }
    
    private void Row_NV (int index){
        v_MaNV.setEnabled(true);
        v_LoaiKH.setEditable(false);
        try{
            v_MaNV.setText((String)(modelNV.getValueAt(index,0)));
            v_HoNV.setText((String)(modelNV.getValueAt(index,1)));
            v_TenNV.setText((String)(modelNV.getValueAt(index,2)));
            java.util.Date d_NgSinhNV = new SimpleDateFormat("dd-MM-yyyy").parse((String)(modelNV.getValueAt(index,3)));
            v_NgSinhNV.setDate(d_NgSinhNV);
            java.util.Date d_NgVL = new SimpleDateFormat("dd-MM-yyyy").parse((String)(modelNV.getValueAt(index,4)));
            v_NgVL.setDate(d_NgVL);
            v_GTNV.setSelectedItem(modelNV.getValueAt(index,5));
            v_CMND.setText((String)(modelNV.getValueAt(index,6)));
            v_SDT.setText((String)(modelNV.getValueAt(index,7)));
            v_DC.setText((String)(modelNV.getValueAt(index,8)));
            v_Luong.setText(String.valueOf(modelNV.getValueAt(index,9)));
        }catch(ParseException e){System.out.println(e);}
    }
    
    private void Row_Phim (int index){
        v_MaPhim.setEnabled(true);
        v_DT.setEnabled(true);
        try{
            v_MaPhim.setText((String)(modelPhim.getValueAt(index,0)));
            v_TenPhim.setText((String)(modelPhim.getValueAt(index,1)));
            cbb_TL.setSelectedItem(modelPhim.getValueAt(index,2));
            v_DD.setText((String)(modelPhim.getValueAt(index,3)));
            v_DV.setText((String)(modelPhim.getValueAt(index,4)));
            v_NPH.setText((String)(modelPhim.getValueAt(index,5)));
            java.util.Date d_NgPH = new SimpleDateFormat("dd-MM-yyyy").parse((String)(modelPhim.getValueAt(index,6)));
            v_NgPH.setDate(d_NgPH);
            v_DT.setText(String.valueOf(modelPhim.getValueAt(index,7)));
        }catch(ParseException e){System.out.println(e);}  
    }
    
    private void Row_SC (int index){
        v_MaSC.setEnabled(true);
        try{
            v_MaSC.setText((String)(modelSuat.getValueAt(index,0)));
            java.util.Date t_TGBD = new SimpleDateFormat("HH:mm").parse((String)(modelSuat.getValueAt(index,1)));
            java.util.Date t_TGKT = new SimpleDateFormat("HH:mm").parse((String)(modelSuat.getValueAt(index,2)));
            java.util.Date d_NgChieu = new SimpleDateFormat("dd-MM-yyyy").parse((String)(modelSuat.getValueAt(index,3)));
            spin_TGBD.setValue((Object)t_TGBD);
            spin_TGKT.setValue((Object)t_TGKT);
            v_NgSC.setDate(d_NgChieu);
        } catch(ParseException e){System.out.println(e);}
    }

    private void Row_LC (int index){
        v_MaLC.setEnabled(true);
        try{
            v_MaLC.setText((String)(modelLich.getValueAt(index,0)));
            cbb_Phim.setSelectedItem(modelLich.getValueAt(index,1));
            cbb_Phong.setSelectedItem(modelLich.getValueAt(index,2));
            String date = modelLich.getValueAt(index,3) + "-" + modelLich.getValueAt(index,4) + " ngày " + modelLich.getValueAt(index,5);
            cbb_SC.setSelectedItem(date);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void clearNV(){
        v_MaNV.setText("Tự động điền");
        v_HoNV.setText("");
        v_TenNV.setText("");
        v_CMND.setText("");
        v_Luong.setText("");
        v_SDT.setText("");
        v_DC.setText("");
        v_NgSinhNV.setDate(null);
        v_NgVL.setDate(null);
        v_GTNV.setSelectedIndex(0);
        v_MaNV.setEnabled(false);
    }
    
    private void clearKH(){
        v_MaKH.setText("Tự động điền");
        v_HoKH.setText("");
        v_TenKH.setText("");
        v_TKKH.setText("");
        v_MKKH.setText("");
        v_DTL.setText("0");
        v_NgSinhKH.setDate(null);
        v_GTKH.setSelectedIndex(0);
        v_LoaiKH.setSelectedIndex(0);
        v_MaKH.setEnabled(false);
        v_LoaiKH.setEnabled(false);
        v_DTL.setEnabled(false);
    }
    
    private void clearPhim(){
        v_MaPhim.setText("Tự động điền");
        v_TenPhim.setText("");
        v_DT.setText("0");
        v_DD.setText("");
        v_DV.setText("");
        v_NgPH.setDate(null);
        v_NPH.setText("");
        v_DT.setText("0");
        cbb_TL.setSelectedIndex(0);
        v_MaPhim.setEnabled(false);
        v_DT.setEnabled(false);
    }
    
    private void clearSuat(){
        v_MaSC.setEditable(false);
        v_MaSC.setEnabled(false);
        v_MaSC.setText("Tự động điền");
        v_NgSC.setDate(null);
        try {
            Date start_Time = new SimpleDateFormat("HH:mm").parse("00:00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(start_Time);
            spin_TGBD.setValue(cal.getTime());
            spin_TGKT.setValue(cal.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void clearLich(){
        v_MaLC.setText("Tự động điền");
        v_MaLC.setEnabled(false);
        cbb_SC.setSelectedIndex(0);
        cbb_Phong.setSelectedIndex(0);
        cbb_Phim.setSelectedIndex(0);
    }
    
    public AdminPage() {
        initComponents();                  
        v_MaPhim.setEditable(false);
        v_MaKH.setEditable(false);
        v_MaNV.setEditable(false);
        v_MaLC.setEditable(false);
        v_MaLC.setEnabled(false);
        v_MaLC.setText("Tự động điền");
        v_DTL.setEditable(false);
        v_DT.setEditable(false);
        v_MaKH.setText("Tự động điền");
        v_DTL.setText("0");
        v_NgSinhKH.setDate(null);
        v_GTKH.setSelectedIndex(0);
        v_LoaiKH.setSelectedIndex(0);
        v_MaKH.setEnabled(false);
        v_LoaiKH.setEnabled(false);
        v_DTL.setEnabled(false);
        v_MaNV.setText("Tự động điền");
        v_NgSinhNV.setDate(null);
        v_NgVL.setDate(null);
        v_GTNV.setSelectedIndex(0);
        v_MaNV.setEnabled(false);
        v_MaPhim.setText("Tự động điền");
        v_DT.setText("0");
        cbb_TL.setSelectedIndex(0);
        v_MaPhim.setEnabled(false);
        v_DT.setEnabled(false);
        v_MaSC.setEditable(false);
        v_MaSC.setEnabled(false);
        v_MaSC.setText("Tự động điền");
        v_NgSC.setDate(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanelQLKH = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        v_HoKH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        v_TKKH = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableKH = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        v_GTKH = new javax.swing.JComboBox<>();
        v_NgSinhKH = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        v_TenKH = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        v_MKKH = new javax.swing.JTextField();
        v_MaKH = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        v_DTL = new javax.swing.JTextField();
        v_LoaiKH = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        ClearKH = new javax.swing.JButton();
        SuaKH = new javax.swing.JButton();
        ThemKH = new javax.swing.JButton();
        v_SearchKH = new javax.swing.JTextField();
        XoaKH = new javax.swing.JButton();
        jPanelQLNV = new javax.swing.JPanel();
        jLabelHo = new javax.swing.JLabel();
        v_HoNV = new javax.swing.JTextField();
        jLabelTen = new javax.swing.JLabel();
        v_TenNV = new javax.swing.JTextField();
        jLabelNgSinh = new javax.swing.JLabel();
        jLabelNgLam = new javax.swing.JLabel();
        jLabelCMND = new javax.swing.JLabel();
        v_CMND = new javax.swing.JTextField();
        jLabelSDT = new javax.swing.JLabel();
        v_SDT = new javax.swing.JTextField();
        jLabelDC = new javax.swing.JLabel();
        v_DC = new javax.swing.JTextField();
        v_Luong = new javax.swing.JTextField();
        jLabelLuong = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableNV = new javax.swing.JTable();
        jLabelGT = new javax.swing.JLabel();
        v_GTNV = new javax.swing.JComboBox<>();
        v_NgSinhNV = new com.toedter.calendar.JDateChooser();
        v_NgVL = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        v_MaNV = new javax.swing.JTextField();
        ThemNV = new javax.swing.JButton();
        XoaNV = new javax.swing.JButton();
        SuaNV = new javax.swing.JButton();
        ClearNV = new javax.swing.JButton();
        jPanelQLPhim = new javax.swing.JPanel();
        jLabelTenPhim = new javax.swing.JLabel();
        v_TenPhim = new javax.swing.JTextField();
        jLabelTL = new javax.swing.JLabel();
        jLabelDaoDien = new javax.swing.JLabel();
        v_DD = new javax.swing.JTextField();
        jLabelNPH = new javax.swing.JLabel();
        v_NPH = new javax.swing.JTextField();
        jLabelNgPH = new javax.swing.JLabel();
        jLabelDoanhThu = new javax.swing.JLabel();
        v_DT = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePhim = new javax.swing.JTable();
        v_NgPH = new com.toedter.calendar.JDateChooser();
        cbb_TL = new javax.swing.JComboBox<>();
        v_MaPhim = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        v_DV = new javax.swing.JTextField();
        jLabelDaoDien1 = new javax.swing.JLabel();
        ClearPhim = new javax.swing.JButton();
        SuaPhim = new javax.swing.JButton();
        XoaPhim = new javax.swing.JButton();
        ThemPhim = new javax.swing.JButton();
        jPanelQLSC = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableSC = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        v_MaSC = new javax.swing.JTextField();
        Date TGBD = new Date();
        SpinnerDateModel sm_BD =
        new SpinnerDateModel(TGBD, null, null, Calendar.HOUR_OF_DAY);
        spin_TGBD = new javax.swing.JSpinner(sm_BD);
        Date TGKT = new Date();
        SpinnerDateModel sm_KT =
        new SpinnerDateModel(TGKT, null, null, Calendar.HOUR_OF_DAY);
        spin_TGKT = new javax.swing.JSpinner(sm_KT);
        v_NgSC = new com.toedter.calendar.JDateChooser();
        ThemSuat = new javax.swing.JButton();
        XoaSuat = new javax.swing.JButton();
        SuaSuat = new javax.swing.JButton();
        ClearSC = new javax.swing.JButton();
        jPanelQLLC = new javax.swing.JPanel();
        jLabel = new javax.swing.JLabel();
        v_MaLC = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        cbb_SC = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cbb_Phim = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableLC = new javax.swing.JTable();
        ThemLC = new javax.swing.JButton();
        SuaLC = new javax.swing.JButton();
        ClearLC = new javax.swing.JButton();
        XoaLC = new javax.swing.JButton();
        cbb_Phong = new javax.swing.JComboBox<>();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TRANG QUẢN LÝ ADMIN");

        jTabbedPane2.setFocusable(false);
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Họ:");
        jLabel2.setMaximumSize(new java.awt.Dimension(23, 17));
        jLabel2.setMinimumSize(new java.awt.Dimension(23, 17));
        jLabel2.setPreferredSize(new java.awt.Dimension(23, 17));

        v_HoKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_HoKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_HoKHActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Tài khoản:");
        jLabel3.setMaximumSize(new java.awt.Dimension(23, 17));
        jLabel3.setMinimumSize(new java.awt.Dimension(23, 17));
        jLabel3.setPreferredSize(new java.awt.Dimension(23, 17));

        v_TKKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_TKKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_TKKHActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Loại khách hàng:");

        jTableKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tài khoản", "Mật khẩu", "Họ", "Tên", "Ngày sinh", "Giới tính", "Loại", "Điểm tích lũy"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableKHMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTableKH);
        if (jTableKH.getColumnModel().getColumnCount() > 0) {
            jTableKH.getColumnModel().getColumn(0).setMinWidth(30);
            jTableKH.getColumnModel().getColumn(0).setMaxWidth(30);
        }
        JTableKH();

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setText("Giới tính:");

        v_GTKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_GTKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nu" }));

        v_NgSinhKH.setDateFormatString("dd-MM-yyyy");
        v_NgSinhKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 255));
        jLabel7.setText("Tên:");
        jLabel7.setMaximumSize(new java.awt.Dimension(23, 17));
        jLabel7.setMinimumSize(new java.awt.Dimension(23, 17));
        jLabel7.setPreferredSize(new java.awt.Dimension(23, 17));

        v_TenKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_TenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_TenKHActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 255));
        jLabel8.setText("Mật khẩu:");

        v_MKKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_MKKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_MKKHActionPerformed(evt);
            }
        });

        v_MaKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 255));
        jLabel11.setText("Mã:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 255));
        jLabel12.setText("Điểm tích lũy:");

        v_DTL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        v_LoaiKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_LoaiKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Than Thiet", "VIP", "Super VIP" }));
        v_LoaiKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_LoaiKHActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 255));
        jLabel13.setText("Ngày sinh:");

        ClearKH.setBackground(new java.awt.Color(204, 204, 204));
        ClearKH.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ClearKH.setText("Làm mới");
        ClearKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearKHActionPerformed(evt);
            }
        });

        SuaKH.setBackground(new java.awt.Color(204, 204, 204));
        SuaKH.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        SuaKH.setText("Sửa");
        SuaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuaKHActionPerformed(evt);
            }
        });

        ThemKH.setBackground(new java.awt.Color(204, 204, 204));
        ThemKH.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ThemKH.setText("Thêm");
        ThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemKHActionPerformed(evt);
            }
        });

        v_SearchKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_SearchKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_SearchKHActionPerformed(evt);
            }
        });
        v_SearchKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                v_SearchKHKeyReleased(evt);
            }
        });

        XoaKH.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        XoaKH.setText("Xóa");
        XoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelQLKHLayout = new javax.swing.GroupLayout(jPanelQLKH);
        jPanelQLKH.setLayout(jPanelQLKHLayout);
        jPanelQLKHLayout.setHorizontalGroup(
            jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQLKHLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQLKHLayout.createSequentialGroup()
                        .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelQLKHLayout.createSequentialGroup()
                                .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(v_MaKH, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(v_HoKH, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(v_TenKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(v_MKKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(v_TKKH, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelQLKHLayout.createSequentialGroup()
                                .addComponent(ThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(SuaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelQLKHLayout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel5))
                                .addGap(24, 24, 24)
                                .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(v_DTL)
                                    .addComponent(v_NgSinhKH, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(v_LoaiKH, 0, 200, Short.MAX_VALUE)
                                    .addComponent(v_GTKH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanelQLKHLayout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(XoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(ClearKH, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(v_SearchKH, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanelQLKHLayout.setVerticalGroup(
            jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQLKHLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQLKHLayout.createSequentialGroup()
                        .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelQLKHLayout.createSequentialGroup()
                                .addComponent(v_MaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(v_HoKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(v_TenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(v_TKKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(v_MKKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelQLKHLayout.createSequentialGroup()
                        .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(v_LoaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(v_GTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(v_DTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(v_NgSinhKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(54, 54, 54)
                .addGroup(jPanelQLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearKH, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(v_SearchKH, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Quản lý khách hàng", jPanelQLKH);

        jLabelHo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelHo.setForeground(new java.awt.Color(0, 0, 255));
        jLabelHo.setText("Họ:");

        v_HoNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabelTen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTen.setForeground(new java.awt.Color(0, 0, 255));
        jLabelTen.setText("Tên:");

        v_TenNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_TenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_TenNVActionPerformed(evt);
            }
        });

        jLabelNgSinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNgSinh.setForeground(new java.awt.Color(0, 0, 255));
        jLabelNgSinh.setText("Ngày sinh:");

        jLabelNgLam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNgLam.setForeground(new java.awt.Color(0, 0, 255));
        jLabelNgLam.setText("Ngày vào làm:");

        jLabelCMND.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCMND.setForeground(new java.awt.Color(0, 0, 255));
        jLabelCMND.setText("CMND:");

        v_CMND.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabelSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelSDT.setForeground(new java.awt.Color(0, 0, 255));
        jLabelSDT.setText("Số điện thoại:");

        v_SDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabelDC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDC.setForeground(new java.awt.Color(0, 0, 255));
        jLabelDC.setText("Địa chỉ:");

        v_DC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_DC.setMaximumSize(new java.awt.Dimension(150, 23));
        v_DC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_DCActionPerformed(evt);
            }
        });

        v_Luong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_Luong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_LuongActionPerformed(evt);
            }
        });

        jLabelLuong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelLuong.setForeground(new java.awt.Color(0, 0, 255));
        jLabelLuong.setText("Lương:");

        jTableNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Họ", "Tên", "Ngày sinh", "Ngày vào làm", "Giới tính", "CMND", "Số điện thoại", "Địa chỉ", "Lương"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableNVMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableNV);
        if (jTableNV.getColumnModel().getColumnCount() > 0) {
            jTableNV.getColumnModel().getColumn(0).setMinWidth(30);
            jTableNV.getColumnModel().getColumn(0).setMaxWidth(30);
        }
        JTableNV();

        jLabelGT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelGT.setForeground(new java.awt.Color(0, 0, 255));
        jLabelGT.setText("Giới tính:");

        v_GTNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_GTNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nu" }));
        v_GTNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_GTNVActionPerformed(evt);
            }
        });

        v_NgSinhNV.setDateFormatString("dd-MM-yyyy");
        v_NgSinhNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        v_NgVL.setDateFormatString("dd-MM-yyyy");
        v_NgVL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setText("Mã:");

        v_MaNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_MaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_MaNVActionPerformed(evt);
            }
        });

        ThemNV.setBackground(new java.awt.Color(204, 204, 204));
        ThemNV.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ThemNV.setText("Thêm");
        ThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemNVActionPerformed(evt);
            }
        });

        XoaNV.setBackground(new java.awt.Color(204, 204, 204));
        XoaNV.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        XoaNV.setText("Xóa");
        XoaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaNVActionPerformed(evt);
            }
        });

        SuaNV.setBackground(new java.awt.Color(204, 204, 204));
        SuaNV.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        SuaNV.setText("Sửa");
        SuaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuaNVActionPerformed(evt);
            }
        });

        ClearNV.setBackground(new java.awt.Color(204, 204, 204));
        ClearNV.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ClearNV.setText("Làm mới");
        ClearNV.setToolTipText("");
        ClearNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelQLNVLayout = new javax.swing.GroupLayout(jPanelQLNV);
        jPanelQLNV.setLayout(jPanelQLNVLayout);
        jPanelQLNVLayout.setHorizontalGroup(
            jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQLNVLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQLNVLayout.createSequentialGroup()
                        .addComponent(ThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(SuaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(XoaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(ClearNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelQLNVLayout.createSequentialGroup()
                        .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelQLNVLayout.createSequentialGroup()
                                .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabelTen, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelHo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(v_MaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(v_HoNV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(v_TenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelQLNVLayout.createSequentialGroup()
                                .addComponent(jLabelNgSinh)
                                .addGap(18, 18, 18)
                                .addComponent(v_NgSinhNV, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelQLNVLayout.createSequentialGroup()
                                .addComponent(jLabelGT, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(v_GTNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(100, 100, 100)
                        .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelCMND, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDC, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabelLuong, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelSDT, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelNgLam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(27, 27, 27)
                        .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(v_DC, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(v_Luong, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(v_SDT, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(v_CMND, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(v_NgVL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 948, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanelQLNVLayout.setVerticalGroup(
            jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQLNVLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQLNVLayout.createSequentialGroup()
                        .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCMND, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(v_CMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(v_SDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(v_Luong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(v_NgVL, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                            .addComponent(jLabelNgLam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelQLNVLayout.createSequentialGroup()
                        .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(v_MaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(v_HoNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelHo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(v_TenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNgSinh, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(v_NgSinhNV, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelGT, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(v_GTNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDC, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(v_DC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(jPanelQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearNV, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XoaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        jTabbedPane2.addTab("Quản lý nhân viên", jPanelQLNV);

        jLabelTenPhim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTenPhim.setForeground(new java.awt.Color(0, 0, 255));
        jLabelTenPhim.setText("Tên phim:");

        v_TenPhim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_TenPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_TenPhimActionPerformed(evt);
            }
        });

        jLabelTL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTL.setForeground(new java.awt.Color(0, 0, 255));
        jLabelTL.setText("Thể loại:");

        jLabelDaoDien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDaoDien.setForeground(new java.awt.Color(0, 0, 255));
        jLabelDaoDien.setText("Đạo diễn:");

        v_DD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabelNPH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNPH.setForeground(new java.awt.Color(0, 0, 255));
        jLabelNPH.setText("Nhà phát hành:");

        v_NPH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_NPH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_NPHActionPerformed(evt);
            }
        });

        jLabelNgPH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNgPH.setForeground(new java.awt.Color(0, 0, 255));
        jLabelNgPH.setText("Ngày phát hành:");

        jLabelDoanhThu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDoanhThu.setForeground(new java.awt.Color(0, 0, 255));
        jLabelDoanhThu.setText("Doanh thu:");

        v_DT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_DT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_DTActionPerformed(evt);
            }
        });

        jTablePhim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTablePhim.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên phim", "Thể loại", "Đạo diễn", "Diễn viên", "Nhà phát hành", "Ngày phát hành", "Doanh thu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePhim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePhimMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePhim);
        if (jTablePhim.getColumnModel().getColumnCount() > 0) {
            jTablePhim.getColumnModel().getColumn(0).setMinWidth(30);
            jTablePhim.getColumnModel().getColumn(0).setMaxWidth(30);
        }
        JTablePhim();

        v_NgPH.setDateFormatString("dd-MM-yyyy");
        v_NgPH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cbb_TL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        v_MaPhim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v_MaPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_MaPhimActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 255));
        jLabel9.setText("Mã:");

        v_DV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabelDaoDien1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDaoDien1.setForeground(new java.awt.Color(0, 0, 255));
        jLabelDaoDien1.setText("Diễn viên:");

        ClearPhim.setBackground(new java.awt.Color(204, 204, 204));
        ClearPhim.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ClearPhim.setText("Làm mới");
        ClearPhim.setMaximumSize(new java.awt.Dimension(65, 41));
        ClearPhim.setMinimumSize(new java.awt.Dimension(65, 41));
        ClearPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearPhimActionPerformed(evt);
            }
        });

        SuaPhim.setBackground(new java.awt.Color(204, 204, 204));
        SuaPhim.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        SuaPhim.setText("Sửa");
        SuaPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuaPhimActionPerformed(evt);
            }
        });

        XoaPhim.setBackground(new java.awt.Color(204, 204, 204));
        XoaPhim.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        XoaPhim.setText("Xóa");
        XoaPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaPhimActionPerformed(evt);
            }
        });

        ThemPhim.setBackground(new java.awt.Color(204, 204, 204));
        ThemPhim.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ThemPhim.setText("Thêm");
        ThemPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemPhimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelQLPhimLayout = new javax.swing.GroupLayout(jPanelQLPhim);
        jPanelQLPhim.setLayout(jPanelQLPhimLayout);
        jPanelQLPhimLayout.setHorizontalGroup(
            jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQLPhimLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQLPhimLayout.createSequentialGroup()
                        .addComponent(ThemPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(SuaPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(XoaPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(ClearPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelQLPhimLayout.createSequentialGroup()
                        .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDaoDien, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTenPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDaoDien1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(v_MaPhim, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(v_TenPhim, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(v_DD, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(v_DV))
                        .addGap(100, 100, 100)
                        .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelNgPH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelDoanhThu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelNPH))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(v_DT)
                            .addComponent(v_NPH)
                            .addComponent(v_NgPH, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(cbb_TL, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 928, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanelQLPhimLayout.setVerticalGroup(
            jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQLPhimLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(v_MaPhim)
                        .addComponent(jLabelTL, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbb_TL, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(v_DT))
                    .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(v_TenPhim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelTenPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDaoDien, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(v_DD)
                    .addComponent(jLabelNPH, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(v_NPH, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelDaoDien1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(v_DV))
                    .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabelNgPH, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                        .addComponent(v_NgPH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(55, 55, 55)
                .addGroup(jPanelQLPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SuaPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ThemPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XoaPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(206, 206, 206))
        );

        cbb_TL();

        jTabbedPane2.addTab("Quản lý phim", jPanelQLPhim);

        jTableSC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableSC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Thời gian bắt đầu", "Thời gian kết thúc", "Ngày chiếu"
            }
        ));
        jTableSC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSCMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableSC);
        JTableSuat();

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("Mã:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 255));
        jLabel14.setText("Ngày chiếu:");
        jLabel14.setMaximumSize(new java.awt.Dimension(117, 17));
        jLabel14.setMinimumSize(new java.awt.Dimension(117, 17));
        jLabel14.setPreferredSize(new java.awt.Dimension(117, 17));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 255));
        jLabel15.setText("Thời gian bắt đầu:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 255));
        jLabel16.setText("Thời gian kết thúc:");

        v_MaSC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        JSpinner.DateEditor de_BD = new JSpinner.DateEditor(spin_TGBD, "HH:mm");
        spin_TGBD.setEditor(de_BD);
        spin_TGBD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        JSpinner.DateEditor de_KT = new JSpinner.DateEditor(spin_TGKT, "HH:mm");
        spin_TGKT.setEditor(de_KT);
        spin_TGKT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        v_NgSC.setDateFormatString("dd-MM-yyyy");
        v_NgSC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        ThemSuat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ThemSuat.setText("Thêm");
        ThemSuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemSuatActionPerformed(evt);
            }
        });

        XoaSuat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        XoaSuat.setText("Xóa");
        XoaSuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaSuatActionPerformed(evt);
            }
        });

        SuaSuat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        SuaSuat.setText("Sửa");
        SuaSuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuaSuatActionPerformed(evt);
            }
        });

        ClearSC.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ClearSC.setText("Làm mới");
        ClearSC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearSCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelQLSCLayout = new javax.swing.GroupLayout(jPanelQLSC);
        jPanelQLSC.setLayout(jPanelQLSCLayout);
        jPanelQLSCLayout.setHorizontalGroup(
            jPanelQLSCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQLSCLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanelQLSCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQLSCLayout.createSequentialGroup()
                        .addGroup(jPanelQLSCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLSCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spin_TGKT)
                            .addComponent(spin_TGBD)
                            .addComponent(v_MaSC)
                            .addComponent(v_NgSC, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelQLSCLayout.createSequentialGroup()
                        .addComponent(ThemSuat, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(SuaSuat, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(XoaSuat, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(ClearSC, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanelQLSCLayout.setVerticalGroup(
            jPanelQLSCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelQLSCLayout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(jPanelQLSCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(v_MaSC)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelQLSCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spin_TGBD, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelQLSCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spin_TGKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelQLSCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(v_NgSC, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanelQLSCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ThemSuat, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuaSuat, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearSC, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XoaSuat, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        try {
            Date start_Time = new SimpleDateFormat("HH:mm").parse("00:00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(start_Time);
            spin_TGBD.setValue(cal.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Date start_Time = new SimpleDateFormat("HH:mm").parse("00:00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(start_Time);
            spin_TGKT.setValue(cal.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        jTabbedPane2.addTab("Quản lý suất chiếu ", jPanelQLSC);

        jPanelQLLC.setPreferredSize(new java.awt.Dimension(1001, 400));

        jLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel.setForeground(new java.awt.Color(0, 0, 255));
        jLabel.setText("Mã:");

        v_MaLC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 255));
        jLabel22.setText("Suất chiếu:");

        cbb_SC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 255));
        jLabel20.setText("Phòng:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 255));
        jLabel21.setText("Phim:");

        cbb_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTableLC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableLC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Phim", "Phòng", "Thời gian bắt đầu", "Thời gian kết thúc", "Ngày chiếu"
            }
        ));
        jTableLC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLCMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTableLC);
        JTableLich();

        ThemLC.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ThemLC.setText("Thêm");
        ThemLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemLCActionPerformed(evt);
            }
        });

        SuaLC.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        SuaLC.setText("Sửa");
        SuaLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuaLCActionPerformed(evt);
            }
        });

        ClearLC.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ClearLC.setText("Làm mới");
        ClearLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearLCActionPerformed(evt);
            }
        });

        XoaLC.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        XoaLC.setText("Xóa");
        XoaLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaLCActionPerformed(evt);
            }
        });

        cbb_Phong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanelQLLCLayout = new javax.swing.GroupLayout(jPanelQLLC);
        jPanelQLLC.setLayout(jPanelQLLCLayout);
        jPanelQLLCLayout.setHorizontalGroup(
            jPanelQLLCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQLLCLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanelQLLCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQLLCLayout.createSequentialGroup()
                        .addComponent(ThemLC, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(SuaLC, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(XoaLC, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(ClearLC, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 929, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelQLLCLayout.createSequentialGroup()
                        .addGroup(jPanelQLLCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelQLLCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanelQLLCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(v_MaLC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbb_Phong, 0, 200, Short.MAX_VALUE)
                            .addComponent(cbb_Phim, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbb_SC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanelQLLCLayout.setVerticalGroup(
            jPanelQLLCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQLLCLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanelQLLCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(v_MaLC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelQLLCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_SC, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelQLLCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_Phong, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelQLLCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanelQLLCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ThemLC, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuaLC, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XoaLC, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearLC, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbb_sc();
        cbb_Phim();
        cbb_Phong();

        jTabbedPane2.addTab("Quản lý lịch chiếu", jPanelQLLC);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 817, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ThemPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemPhimActionPerformed
        // TODO add your handling code here:
        try (Connection con = ConnectionUtils.getMyConnection()) {
            java.util.Date d_NgPH = v_NgPH.getDate();
            String S_NgPH = String.format("%1$td-%1$tm-%1$tY", d_NgPH);
            String SQL_TL = "SELECT MATL FROM THELOAIPHIM WHERE THELOAI='"+(String)cbb_TL.getSelectedItem()+"'";
            Statement stat_TL=con.createStatement();
            ResultSet rs_TL = stat_TL.executeQuery(SQL_TL);
            if(rs_TL.next()){
                int MaTL = Integer.parseInt(rs_TL.getString(1));
                String SQL_PHIM="INSERT INTO PHIM(MAPHIM,TENPHIM,MATL,DAODIEN,DIENVIEN,NHAPH,NGAYPH)"
                +"VALUES(MAPHIM_SEQ4.nextval,'"+v_TenPhim.getText()+"',"+MaTL+",'"+v_DD.getText()+"','"+v_DV.getText()+"','"+v_NPH.getText()+"',to_date('"+S_NgPH+"','dd-mm-yyyy'))";
                Statement stat_PHIM = con.createStatement();
                stat_PHIM.executeUpdate(SQL_PHIM);
            }
            JTablePhim();
            cbb_Phim();
            Row_Phim(jTablePhim.getRowCount()-1);
            JOptionPane.showMessageDialog(this,"Thành công!");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"Một trong các thông tin bị sai","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }//GEN-LAST:event_ThemPhimActionPerformed

    private void XoaPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaPhimActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){
            String SQL_Phim = "DELETE FROM PHIM WHERE MAPHIM = "+ v_MaPhim.getText();
            Statement stat_Phim = con.createStatement();
            stat_Phim.executeUpdate(SQL_Phim);
            JTablePhim();
            JTableLich();
            cbb_Phim();
            clearPhim();
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
        } catch (Exception ex){
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Xóa không thành công!","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }//GEN-LAST:event_XoaPhimActionPerformed

    private void SuaPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuaPhimActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){           
            java.util.Date d_NgPH = v_NgPH.getDate();
            String S_NgPH = String.format("%1$td-%1$tm-%1$tY", d_NgPH);
            int MaPhim = Integer.parseInt(v_MaPhim.getText());
            String SQL_TL = "SELECT MATL FROM THELOAIPHIM WHERE THELOAI='"+(String)cbb_TL.getSelectedItem()+"'";
            Statement stat_TL=con.createStatement();
            ResultSet rs_TL = stat_TL.executeQuery(SQL_TL);
            if(rs_TL.next()){
                int MaTL = Integer.parseInt(rs_TL.getString(1));
                String SQL_PHIM="UPDATE PHIM SET MATL = "+MaTL+",TENPHIM = '"+v_TenPhim.getText()+"',DAODIEN = '"+v_DD.getText()+"',DIENVIEN = '"+v_DV.getText()+"',NHAPH = '"+v_NPH.getText()+"',NGAYPH = to_date('"+S_NgPH+"','dd-mm-yyyy') WHERE MAPHIM = "+MaPhim;
                Statement stat_PHIM = con.createStatement();
                stat_PHIM.executeUpdate(SQL_PHIM);
            }
            JTablePhim();
            JTableLich();
            cbb_Phim();
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
        }
        catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(this,"Một trong các thông tin bị sai","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }//GEN-LAST:event_SuaPhimActionPerformed

    private void ClearPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearPhimActionPerformed
        // TODO add your handling code here:
        clearPhim();
    }//GEN-LAST:event_ClearPhimActionPerformed

    private void v_MaPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_MaPhimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_MaPhimActionPerformed

    private void jTablePhimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePhimMouseClicked
        // TODO add your handling code here:
        Row_Phim(jTablePhim.getSelectedRow());
    }//GEN-LAST:event_jTablePhimMouseClicked

    private void v_DTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_DTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_DTActionPerformed

    private void v_NPHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_NPHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_NPHActionPerformed

    private void v_TenPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_TenPhimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_TenPhimActionPerformed

    private void ClearNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearNVActionPerformed
        // TODO add your handling code here:
        clearNV();
    }//GEN-LAST:event_ClearNVActionPerformed

    private void SuaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuaNVActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){
            java.util.Date d_NgSinhNV = v_NgSinhNV.getDate();
            java.util.Date d_NgVL = v_NgVL.getDate();
            String S_NgSinhNV = String.format("%1$td-%1$tm-%1$tY", d_NgSinhNV);
            String S_NgVL = String.format("%1$td-%1$tm-%1$tY", d_NgVL);
            int MaNV = Integer.parseInt(v_MaNV.getText());
            double Luong = Double.parseDouble(v_Luong.getText());
            String SQL_NV = "UPDATE NHANVIEN SET HO = '"+v_HoNV.getText()+"',TEN = '"+v_TenNV.getText()+"',NGAYSINH = to_date('"+S_NgSinhNV+"','dd-mm-yyyy'),NGAYLAM = to_date('"+S_NgVL+"','dd-mm-yyyy'),GIOITINH = '"+v_GTNV.getSelectedItem()+"',CMND = '"+v_CMND.getText()+"',SODT = '"+v_SDT.getText()+"',DIACHI = '"+v_DC.getText()+"',LUONG = "+Luong+" WHERE MANV = "+MaNV;
            Statement stat_NV = con.createStatement();
            stat_NV.executeUpdate(SQL_NV);
            JTableNV();
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this,"Một trong các thông tin bị sai","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }//GEN-LAST:event_SuaNVActionPerformed

    private void ThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemNVActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){
            java.util.Date d_NgSinhNV = v_NgSinhNV.getDate();
            java.util.Date d_NgVL = v_NgVL.getDate();
            String S_NgSinhNV = String.format("%1$td-%1$tm-%1$tY", d_NgSinhNV);
            String S_NgVL = String.format("%1$td-%1$tm-%1$tY", d_NgVL);
            double Luong = Double.parseDouble(v_Luong.getText());
            String SQL_NV="INSERT INTO NHANVIEN(MANV,HO,TEN,NGAYSINH,NGAYLAM,GIOITINH,CMND,SODT,DIACHI,LUONG)"
            +"VALUES(MANV_SEQ2.nextval,'"+v_HoNV.getText()+"','"+v_TenNV.getText()+"',to_date('"+S_NgSinhNV+"','dd-mm-yyyy'),to_date('"+S_NgVL+"','dd-mm-yyyy'),'"+v_GTNV.getSelectedItem()+"','"+v_CMND.getText()+"','"+v_SDT.getText()+"','"+v_DC.getText()+"',"+Luong+")";
            Statement stat_NV = con.createStatement();
            stat_NV.executeUpdate(SQL_NV);
            JTableNV();
            Row_NV(jTableNV.getRowCount()-1);
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this,"Một trong các thông tin bị sai","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }//GEN-LAST:event_ThemNVActionPerformed

    private void v_MaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_MaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_MaNVActionPerformed

    private void v_GTNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_GTNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_GTNVActionPerformed

    private void jTableNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableNVMouseClicked
        // TODO add your handling code here:
        Row_NV(jTableNV.getSelectedRow());
    }//GEN-LAST:event_jTableNVMouseClicked

    private void v_LuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_LuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_LuongActionPerformed

    private void v_DCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_DCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_DCActionPerformed

    private void v_TenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_TenNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_TenNVActionPerformed

    private void ThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemKHActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){
            java.util.Date d_NgSinhKH = v_NgSinhKH.getDate();
            String S_NgSinhKH = String.format("%1$td-%1$tm-%1$tY", d_NgSinhKH);
            String SQL_KH="INSERT INTO KHACHHANG(MAKH,HO,TEN,NGAYSINH,GIOITINH,USERNAME,PASSWORD)"
            +"VALUES(MAKH_SEQ1.nextval,'"+v_HoKH.getText()+"','"+v_TenKH.getText()+"',to_date('"+S_NgSinhKH+"','dd-mm-yyyy'),'"+v_GTKH.getSelectedItem()+"','"+v_TKKH.getText()+"','"+v_MKKH.getText()+"')";
            Statement stat_KH = con.createStatement();
            stat_KH.executeUpdate(SQL_KH);
            JTableKH();
            Row_KH(jTableKH.getRowCount()-1);
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
        }
        catch (Exception ex){
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Một trong các thông tin bị sai","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }//GEN-LAST:event_ThemKHActionPerformed
    
    private void SuaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuaKHActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){
            java.util.Date d_NgSinhKH = v_NgSinhKH.getDate();
            String S_NgSinhKH = String.format("%1$td-%1$tm-%1$tY", d_NgSinhKH);
            int MaKH = Integer.parseInt(v_MaKH.getText());
            String SQL_KH = "UPDATE KHACHHANG SET HO = '"+v_HoKH.getText()+"',TEN = '"+v_TenKH.getText()+"',NGAYSINH = to_date('"+S_NgSinhKH+"','dd-mm-yyyy'),GIOITINH = '"+v_GTKH.getSelectedItem()+"',USERNAME = '"+v_TKKH.getText()+"',PASSWORD = '"+v_MKKH.getText()+"' WHERE MAKH = "+MaKH;
            Statement stat_KH = con.createStatement();
            stat_KH.executeUpdate(SQL_KH);
            JTableKH();
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this,"Một trong các thông tin bị sai","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }//GEN-LAST:event_SuaKHActionPerformed

    private void ClearKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearKHActionPerformed
        // TODO add your handling code here:
        clearKH();
    }//GEN-LAST:event_ClearKHActionPerformed

    private void v_LoaiKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_LoaiKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_LoaiKHActionPerformed

    private void v_MKKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_MKKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_MKKHActionPerformed

    private void v_TenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_TenKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_TenKHActionPerformed

    private void jTableKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableKHMouseClicked
        // TODO add your handling code here:
        Row_KH(jTableKH.getSelectedRow());
    }//GEN-LAST:event_jTableKHMouseClicked

    private void v_TKKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_TKKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_TKKHActionPerformed

    private void v_HoKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_HoKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_HoKHActionPerformed

    private void XoaSuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaSuatActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){
            String SQL_Suat = "DELETE FROM SUATCHIEU WHERE MASUAT = "+ v_MaSC.getText();
            Statement stat_Suat = con.createStatement();
            stat_Suat.executeUpdate(SQL_Suat);
            JTableSuat();
            JTableLich();
            cbb_sc();
            clearSuat();
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
        } catch (Exception ex){
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Xóa không thành công!","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }//GEN-LAST:event_XoaSuatActionPerformed

    private void ThemSuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemSuatActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){
            SimpleDateFormat DateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat TimeFormatter = new SimpleDateFormat("HH:mm");
            java.util.Date d_NgSC = v_NgSC.getDate();
            java.util.Date t_TGBD = (Date)spin_TGBD.getValue();
            java.util.Date t_TGKT = (Date)spin_TGKT.getValue();           
            String S_NgSC = DateFormatter.format(d_NgSC);
            String S_TGBD = TimeFormatter.format(t_TGBD);
            String S_TGKT = TimeFormatter.format(t_TGKT);
            Date TGBD = TimeFormatter.parse(S_TGBD);
            Date TGKT = TimeFormatter.parse(S_TGKT);
            if(TGKT.getTime() - TGBD.getTime() <= 0)
                JOptionPane.showMessageDialog(this,"Thời gian kết thúc phải lớn hơn thời gian bắt đầu","Lỗi",JOptionPane.ERROR_MESSAGE);
            else
            {
                String SQL_SC="INSERT INTO SUATCHIEU(MASUAT,THOIGIANBD,THOIGIANKT,NGAYCHIEU)"
                +"VALUES(MaSuat_seq5.nextval,to_date('"+S_TGBD+"','HH24:MI'),to_date('"+S_TGKT+"','HH24:MI'),to_date('"+S_NgSC+"','dd-mm-yyyy'))";
                Statement stat_Suat = con.createStatement();
                stat_Suat.executeUpdate(SQL_SC);
                JTableSuat();
                cbb_sc();
                con.close();
                JOptionPane.showMessageDialog(this,"Thành công!");
                Row_SC(jTableSC.getRowCount()-1);
            }
        }catch (Exception ex)
        {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Một trong các thông tin bị sai","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }    
    }//GEN-LAST:event_ThemSuatActionPerformed

    private void SuaSuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuaSuatActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){            
            SimpleDateFormat DateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat TimeFormatter = new SimpleDateFormat("HH:mm");
            java.util.Date d_NgSC = v_NgSC.getDate();
            java.util.Date t_TGBD = (Date)spin_TGBD.getValue();
            java.util.Date t_TGKT = (Date)spin_TGKT.getValue();           
            String S_NgSC = DateFormatter.format(d_NgSC);
            String S_TGBD = TimeFormatter.format(t_TGBD);
            String S_TGKT = TimeFormatter.format(t_TGKT);
            Date TGBD = TimeFormatter.parse(S_TGBD);
            Date TGKT = TimeFormatter.parse(S_TGKT);
            System.out.println(S_NgSC +" "+ S_TGBD + "-" + S_TGKT);
            if(TGKT.getTime() - TGBD.getTime() <= 0)
                JOptionPane.showMessageDialog(this,"Thời gian kết thúc phải lớn hơn thời gian bắt đầu","Lỗi",JOptionPane.ERROR_MESSAGE);
            else{
                String SQL_SC="UPDATE SUATCHIEU SET THOIGIANBD = to_date('"+S_TGBD+"','HH24:MI'), THOIGIANKT = to_date('"+S_TGKT+"','HH24:MI'), NGAYCHIEU = to_date('"+S_NgSC+"','dd-mm-yyyy') WHERE MASUAT = "+ v_MaSC.getText();
                Statement stat_Suat = con.createStatement();
                stat_Suat.executeUpdate(SQL_SC);
                JTableSuat();
                JTableLich();
                cbb_sc();
                con.close();
                JOptionPane.showMessageDialog(this,"Thành công!");
            }
        }catch (Exception ex)
        {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Một trong các thông tin bị sai","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }            
    }//GEN-LAST:event_SuaSuatActionPerformed

    private void ClearSCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearSCActionPerformed
        // TODO add your handling code here:
        clearSuat();
    }//GEN-LAST:event_ClearSCActionPerformed

    private void v_SearchKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_v_SearchKHKeyReleased
        // TODO add your handling code here:
        DefaultTableModel SearchTableKH = (DefaultTableModel) jTableKH.getModel();
        String search = v_SearchKH.getText().toLowerCase();
        TableRowSorter<DefaultTableModel> tr;
        tr = new TableRowSorter<>(SearchTableKH);
        jTableKH.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_v_SearchKHKeyReleased

    private void v_SearchKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_SearchKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_v_SearchKHActionPerformed

    private void jTableSCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSCMouseClicked
        // TODO add your handling code here:
        Row_SC (jTableSC.getSelectedRow());
    }//GEN-LAST:event_jTableSCMouseClicked

    private void ThemLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemLCActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){          
            String MaSC = masc.get(cbb_SC.getSelectedIndex());
            String MaPhong = maphong.get(cbb_Phong.getSelectedIndex());
            String MaPhim = maphim.get(cbb_Phim.getSelectedIndex());
            String SQL = "INSERT INTO LICHCHIEU(MALICHCHIEU,MASUAT,MAPHIM,MAPHONG)"
            +"VALUES(MALICHCHIEU_SEQ7.NEXTVAL,"+MaSC+","+MaPhim+","+MaPhong+")";
            Statement stat = con.createStatement();
            stat.executeUpdate(SQL);
            JTableLich();
            Row_LC(jTableLC.getRowCount()-1);
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
        }
        catch (Exception ex){
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Một trong các thông tin bị sai","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }     
    }//GEN-LAST:event_ThemLCActionPerformed

    private void ClearLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearLCActionPerformed
        // TODO add your handling code here:
        clearLich();
    }//GEN-LAST:event_ClearLCActionPerformed

    private void XoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaKHActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){
            String SQL_KH = "DELETE FROM KHACHHANG WHERE MAKH = "+ v_MaKH.getText();
            Statement stat_KH = con.createStatement();
            stat_KH.executeUpdate(SQL_KH);
            JTableKH();
            clearKH();
            JOptionPane.showMessageDialog(this,"Thành công!");
            con.close();
        } catch (Exception ex){
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Xóa không thành công!","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }//GEN-LAST:event_XoaKHActionPerformed

    private void XoaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaNVActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){
            String SQL_NV = "DELETE FROM NHANVIEN WHERE MANV = "+ v_MaNV.getText();
            Statement stat_NV = con.createStatement();
            stat_NV.executeUpdate(SQL_NV);
            JTableNV();
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
            clearNV();
        } catch (Exception ex){
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Xóa không thành công!","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }//GEN-LAST:event_XoaNVActionPerformed

    private void jTableLCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLCMouseClicked
        // TODO add your handling code here:
        Row_LC (jTableLC.getSelectedRow());
    }//GEN-LAST:event_jTableLCMouseClicked

    private void SuaLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuaLCActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){          
            String MaLC = v_MaLC.getText();
            String MaSC = masc.get(cbb_SC.getSelectedIndex());
            String MaPhong = maphong.get(cbb_Phong.getSelectedIndex());
            String MaPhim = maphim.get(cbb_Phim.getSelectedIndex());
            String SQL = "UPDATE LICHCHIEU SET MASUAT = " +MaSC+ ", MAPHIM = "+MaPhim+", MAPHONG = "+MaPhong+" WHERE MALICHCHIEU = "+MaLC;
            Statement stat = con.createStatement();
            stat.executeUpdate(SQL);
            JTableLich();
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
        }
        catch (Exception ex){
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Một trong các thông tin bị sai","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        } 
    }//GEN-LAST:event_SuaLCActionPerformed

    private void XoaLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaLCActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){
            String SQL = "DELETE FROM LICHCHIEU WHERE MALICHCHIEU = "+ v_MaLC.getText();
            Statement stat = con.createStatement();
            stat.executeUpdate(SQL);
            JTableLich();
            clearLich();
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
        } catch (Exception ex){
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Xóa không thành công!","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }//GEN-LAST:event_XoaLCActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ClearKH;
    private javax.swing.JButton ClearLC;
    private javax.swing.JButton ClearNV;
    private javax.swing.JButton ClearPhim;
    private javax.swing.JButton ClearSC;
    private javax.swing.JButton SuaKH;
    private javax.swing.JButton SuaLC;
    private javax.swing.JButton SuaNV;
    private javax.swing.JButton SuaPhim;
    private javax.swing.JButton SuaSuat;
    private javax.swing.JButton ThemKH;
    private javax.swing.JButton ThemLC;
    private javax.swing.JButton ThemNV;
    private javax.swing.JButton ThemPhim;
    private javax.swing.JButton ThemSuat;
    private javax.swing.JButton XoaKH;
    private javax.swing.JButton XoaLC;
    private javax.swing.JButton XoaNV;
    private javax.swing.JButton XoaPhim;
    private javax.swing.JButton XoaSuat;
    private javax.swing.JComboBox<String> cbb_Phim;
    private javax.swing.JComboBox<String> cbb_Phong;
    private javax.swing.JComboBox<String> cbb_SC;
    private javax.swing.JComboBox<String> cbb_TL;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCMND;
    private javax.swing.JLabel jLabelDC;
    private javax.swing.JLabel jLabelDaoDien;
    private javax.swing.JLabel jLabelDaoDien1;
    private javax.swing.JLabel jLabelDoanhThu;
    private javax.swing.JLabel jLabelGT;
    private javax.swing.JLabel jLabelHo;
    private javax.swing.JLabel jLabelLuong;
    private javax.swing.JLabel jLabelNPH;
    private javax.swing.JLabel jLabelNgLam;
    private javax.swing.JLabel jLabelNgPH;
    private javax.swing.JLabel jLabelNgSinh;
    private javax.swing.JLabel jLabelSDT;
    private javax.swing.JLabel jLabelTL;
    private javax.swing.JLabel jLabelTen;
    private javax.swing.JLabel jLabelTenPhim;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanelQLKH;
    private javax.swing.JPanel jPanelQLLC;
    private javax.swing.JPanel jPanelQLNV;
    private javax.swing.JPanel jPanelQLPhim;
    private javax.swing.JPanel jPanelQLSC;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableKH;
    private javax.swing.JTable jTableLC;
    private javax.swing.JTable jTableNV;
    private javax.swing.JTable jTablePhim;
    private javax.swing.JTable jTableSC;
    private javax.swing.JSpinner spin_TGBD;
    private javax.swing.JSpinner spin_TGKT;
    private javax.swing.JTextField v_CMND;
    private javax.swing.JTextField v_DC;
    private javax.swing.JTextField v_DD;
    private javax.swing.JTextField v_DT;
    private javax.swing.JTextField v_DTL;
    private javax.swing.JTextField v_DV;
    private javax.swing.JComboBox<String> v_GTKH;
    private javax.swing.JComboBox<String> v_GTNV;
    private javax.swing.JTextField v_HoKH;
    private javax.swing.JTextField v_HoNV;
    private javax.swing.JComboBox<String> v_LoaiKH;
    private javax.swing.JTextField v_Luong;
    private javax.swing.JTextField v_MKKH;
    private javax.swing.JTextField v_MaKH;
    private javax.swing.JTextField v_MaLC;
    private javax.swing.JTextField v_MaNV;
    private javax.swing.JTextField v_MaPhim;
    private javax.swing.JTextField v_MaSC;
    private javax.swing.JTextField v_NPH;
    private com.toedter.calendar.JDateChooser v_NgPH;
    private com.toedter.calendar.JDateChooser v_NgSC;
    private com.toedter.calendar.JDateChooser v_NgSinhKH;
    private com.toedter.calendar.JDateChooser v_NgSinhNV;
    private com.toedter.calendar.JDateChooser v_NgVL;
    private javax.swing.JTextField v_SDT;
    private javax.swing.JTextField v_SearchKH;
    private javax.swing.JTextField v_TKKH;
    private javax.swing.JTextField v_TenKH;
    private javax.swing.JTextField v_TenNV;
    private javax.swing.JTextField v_TenPhim;
    // End of variables declaration//GEN-END:variables
}
