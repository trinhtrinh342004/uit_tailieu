/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIandBUS;

import dao.ConnectionUtils;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
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
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
import java.sql.CallableStatement;
import java.util.HashMap;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author fanta
 */
public class QLPHIM extends javax.swing.JFrame {

    /**
     * Creates new form QLPHIM
     */
    ArrayList<Integer>nam_array = new ArrayList<>();
    ArrayList<String> matl = new ArrayList<>();
    ArrayList<String> tentl = new ArrayList<>();
    DefaultTableModel modelPhim = null;
    String ma;
    String MaTL;
    File file = null;
    String TenAnh = null;
    
    private void showAnh (String name){
        ImageIcon MyImage = new ImageIcon(getClass().getResource("/GoiNguon/"+name));
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(HinhAnh.getWidth(), HinhAnh.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        HinhAnh.setIcon(image);
    }
    
    private void showAnh (File file){
        ImageIcon MyImage = new ImageIcon(file.getAbsolutePath());
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(HinhAnh.getWidth(), HinhAnh.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        HinhAnh.setIcon(image);
    }
    
    private void getRowPhim (int index){
        txtDT.setEnabled(true);
        try(Connection con = ConnectionUtils.getMyConnection()){
            ma = (String)(tbPhim.getValueAt(index,0));
            txtTen.setText((String)(tbPhim.getValueAt(index,1)));
            java.util.Date t_TLuong = new SimpleDateFormat("HH:mm").parse((String)(tbPhim.getValueAt(index,2)));
            spin_TLuong.setValue((Object)t_TLuong);
            cbbTL.setSelectedItem(tbPhim.getValueAt(index,3));
            txtDD.setText((String)(tbPhim.getValueAt(index,4)));
            txtDV.setText((String)(tbPhim.getValueAt(index,5)));
            txtNPH.setText((String)(tbPhim.getValueAt(index,6)));
            java.util.Date d_NgPH = new SimpleDateFormat("dd-MM-yyyy").parse((String)(tbPhim.getValueAt(index,7)));
            txtNgPH.setDate(d_NgPH);
            txtDT.setText(String.valueOf(tbPhim.getValueAt(index,8)));
            String SQL = "SELECT HINHANH FROM PHIM WHERE MAPHIM = "+ma;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if(rs.next())
            {
                String ha = rs.getString("HINHANH");
                if(ha != null)
                    showAnh(ha);
                else
                    HinhAnh.setIcon(null);
            }           
        }catch(Exception e){System.out.println(e);}  
    }  
  
    public void setTablePhim() throws InterruptedException{
        try (Connection con = ConnectionUtils.getMyConnection()) {
            int i=-1;
            i = Connection.TRANSACTION_READ_COMMITTED;
            con.setAutoCommit(false);
            con.setTransactionIsolation(i);
            String SQL_PHIM = "SELECT PHIM.MAPHIM,PHIM.TENPHIM,THELOAIPHIM.THELOAI,PHIM.DAODIEN,PHIM.DIENVIEN,PHIM.NHAPH,PHIM.NGAYPH,PHIM.DOANHTHU,PHIM.THOILUONG FROM PHIM,THELOAIPHIM WHERE PHIM.MATL = THELOAIPHIM.MATL ORDER BY PHIM.MAPHIM";
            Statement statement= con.createStatement();
            ResultSet rs=statement.executeQuery(SQL_PHIM);
            SimpleDateFormat TimeFormatter = new SimpleDateFormat("HH:mm");
            SimpleDateFormat DateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            modelPhim = (DefaultTableModel) tbPhim.getModel();
            modelPhim.setRowCount(0);
            while(rs.next()){
                String MaPhim = rs.getString("MAPHIM");
                String TenPhim = rs.getString("TENPHIM");
                String TL = rs.getString("THELOAI");
                String DD = rs.getString("DAODIEN");
                String DV = rs.getString("DIENVIEN");
                String NhaPH = rs.getString("NHAPH");
                String S_NgPH = DateFormatter.format(rs.getDate("NGAYPH"));
                String S_TLuong = TimeFormatter.format(rs.getTime("THOILUONG"));
                String DT = rs.getString("DOANHTHU"); 
                modelPhim.addRow(new Object[]{MaPhim,TenPhim,S_TLuong,TL,DD,DV,NhaPH,S_NgPH,DT});
            }
            System.out.println(1);
            Thread.sleep(3500);
            System.out.println(2);
              String SQL_PHIM2 = "SELECT PHIM.MAPHIM,PHIM.TENPHIM,THELOAIPHIM.THELOAI,PHIM.DAODIEN,PHIM.DIENVIEN,PHIM.NHAPH,PHIM.NGAYPH,PHIM.DOANHTHU,PHIM.THOILUONG FROM PHIM,THELOAIPHIM WHERE PHIM.MATL = THELOAIPHIM.MATL ORDER BY PHIM.MAPHIM";
            Statement statement2= con.createStatement();
            ResultSet rs2=statement2.executeQuery(SQL_PHIM2);
            SimpleDateFormat TimeFormatter2 = new SimpleDateFormat("HH:mm");
            SimpleDateFormat DateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");
            modelPhim = (DefaultTableModel) tbPhim.getModel();
            modelPhim.setRowCount(0);
            while(rs2.next()){
                String MaPhim = rs2.getString("MAPHIM");
                String TenPhim = rs2.getString("TENPHIM");
                String TL = rs2.getString("THELOAI");
                String DD = rs2.getString("DAODIEN");
                String DV = rs2.getString("DIENVIEN");
                String NhaPH = rs2.getString("NHAPH");
                String S_NgPH = DateFormatter2.format(rs2.getDate("NGAYPH"));
                String S_TLuong = TimeFormatter2.format(rs2.getTime("THOILUONG"));
                String DT = rs2.getString("DOANHTHU"); 
                modelPhim.addRow(new Object[]{MaPhim,TenPhim,S_TLuong,TL,DD,DV,NhaPH,S_NgPH,DT});
            }
            con.commit();
           
       
           
        }
        catch (ClassNotFoundException | NumberFormatException | SQLException ex) {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setCbbTL(){
        try (Connection con = ConnectionUtils.getMyConnection()){
            String SQL = "SELECT DISTINCT MATL,THELOAI FROM THELOAIPHIM";
            Statement statement= con.createStatement();
            ResultSet rs=statement.executeQuery(SQL);
            cbbTL.removeAllItems();
            matl.clear();
            tentl.clear();
            while(rs.next()){
                matl.add(rs.getString("MATL"));
                tentl.add(rs.getString("THELOAI"));
            }
            cbbTL.setModel(new DefaultComboBoxModel(tentl.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    private void clearPhim(){
        txtTen.setText("");
        txtDT.setText("0");
        txtDD.setText("");
        txtDV.setText("");
        txtNgPH.setDate(null);
        txtNPH.setText("");
        txtDT.setText("0");
        cbbTL.setSelectedIndex(-1);
        txtDT.setEnabled(false);
        HinhAnh.setIcon(null);
        try {
            Date start_Time = new SimpleDateFormat("HH:mm").parse("00:00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(start_Time);
            spin_TLuong.setValue(cal.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(QLPHIM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void copyFile(File source, File destination) throws IOException {
	// Using try with resources (No need to close files).
	try (InputStream inputStream = new FileInputStream(source); OutputStream outputStream = new FileOutputStream(destination);) {
            // Max length per line = 1024.
            byte[] buffer = new byte[1024];
            int lineLength;
            while ((lineLength = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, lineLength);
            }
	}
    }
    
    public QLPHIM() throws SQLException, ClassNotFoundException, ParseException, InterruptedException {
        initComponents();    
        setCbbTL();
        clearPhim();
        
        //Các bước set giao diện
        /*1. Set kích thước giao diện*/
        setLocation(100,40);
        /*2. Set nút chọn tắt mặc định EXIT_ON_CLOSE*/ 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        /*4. Không cho phóng to*/
       
        setResizable(false);
      
        setTablePhim();
        getNgayChieu();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Trang admin: quản lý phim");
       
    }
   
         public void getNgayChieu() throws SQLException, ClassNotFoundException, ParseException
    {
        try{
                selectNam.removeAllItems();
               Connection con;
               con = ConnectionUtils.getMyConnection();
               String SQL = "SELECT distinct extract(year from ngayban) FROM DATVE";
               Statement s = con.createStatement();
               ResultSet rs = s.executeQuery(SQL);
               while(rs.next())
               {
                   nam_array.add(rs.getInt(1));
               }
              selectNam.setModel(new DefaultComboBoxModel(nam_array.toArray()));
               }catch(HeadlessException | SQLException ex)
                {
                    System.out.println(ex);
                }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDD = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDV = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNPH = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDT = new javax.swing.JTextField();
        cbbTL = new javax.swing.JComboBox<>();
        txtNgPH = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Date TLuong = new Date();
        SpinnerDateModel sm_TLuong =
        new SpinnerDateModel(TLuong, null, null, Calendar.HOUR_OF_DAY);
        spin_TLuong = new javax.swing.JSpinner(sm_TLuong);
        txtLink = new javax.swing.JTextField();
        btOpenFile = new javax.swing.JButton();
        btThem = new javax.swing.JButton();
        btXoa = new javax.swing.JButton();
        btSua = new javax.swing.JButton();
        btLamMoi = new javax.swing.JButton();
        HinhAnh = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPhim = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btBack = new javax.swing.JButton();
        btBack1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        selectNam = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        selectedThang = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        laychuky = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Tên phim:");

        txtTen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Thể loại:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("Đạo diễn:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Diễn viên:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setText("Ngày phát hành:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 255));
        jLabel7.setText("Nhà phát hành:");

        txtNPH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 255));
        jLabel8.setText("Doanh thu:");

        txtDT.setEditable(false);
        txtDT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDTActionPerformed(evt);
            }
        });

        cbbTL.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbbTL.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbTLItemStateChanged(evt);
            }
        });

        txtNgPH.setDateFormatString("dd-MM-yyyy");
        txtNgPH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 255));
        jLabel9.setText("Thời lượng:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setText("Hình ảnh:");

        JSpinner.DateEditor de_TLuong = new JSpinner.DateEditor(spin_TLuong, "HH:mm");
        spin_TLuong.setEditor(de_TLuong);
        spin_TLuong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btOpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GoiNguon/picture (1).png"))); // NOI18N
        btOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOpenFileActionPerformed(evt);
            }
        });

        btThem.setBackground(new java.awt.Color(255, 255, 255));
        btThem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btThem.setForeground(new java.awt.Color(0, 153, 51));
        btThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/checked (1).png"))); // NOI18N
        btThem.setText("Thêm");
        btThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThemActionPerformed(evt);
            }
        });

        btXoa.setBackground(new java.awt.Color(255, 255, 255));
        btXoa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btXoa.setForeground(new java.awt.Color(255, 0, 0));
        btXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/multiply.png"))); // NOI18N
        btXoa.setText("Xóa");
        btXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btXoaActionPerformed(evt);
            }
        });

        btSua.setBackground(new java.awt.Color(255, 255, 255));
        btSua.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btSua.setForeground(new java.awt.Color(255, 153, 0));
        btSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/wrench.png"))); // NOI18N
        btSua.setText("Sửa");
        btSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSuaActionPerformed(evt);
            }
        });

        btLamMoi.setBackground(new java.awt.Color(255, 255, 255));
        btLamMoi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/refresh.png"))); // NOI18N
        btLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLamMoiActionPerformed(evt);
            }
        });

        HinhAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNPH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtLink, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btOpenFile, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtDT, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spin_TLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtDV, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNgPH, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDD, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btThem)
                        .addGap(10, 10, 10)
                        .addComponent(btXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cbbTL, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(HinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(btLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spin_TLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDD, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbTL, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNPH, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgPH, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDT, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLink, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDV, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btXoa)
                            .addComponent(btSua)
                            .addComponent(btThem)))
                    .addComponent(btOpenFile, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btLamMoi)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        try {
            Date start_Time = new SimpleDateFormat("HH:mm").parse("00:00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(start_Time);
            spin_TLuong.setValue(cal.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(QLPHIM.class.getName()).log(Level.SEVERE, null, ex);
        }

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tbPhim.setAutoCreateRowSorter(true);
        tbPhim.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbPhim.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên phim", "Thời lượng", "Thể loại", "Đạo diễn", "Diễn viên", "Nhà phát hành", "Ngày phát hành", "Doanh thu"
            }
        ));
        tbPhim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPhimMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPhim);
        if (tbPhim.getColumnModel().getColumnCount() > 0) {
            tbPhim.getColumnModel().getColumn(0).setMinWidth(40);
            tbPhim.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        txtSearch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 255), new java.awt.Color(255, 51, 0), new java.awt.Color(255, 255, 0), new java.awt.Color(0, 204, 0)));
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 255));
        jLabel11.setText("Tìm kiếm:");

        btBack.setBackground(new java.awt.Color(204, 255, 204));
        btBack.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btBack.setForeground(new java.awt.Color(0, 204, 255));
        btBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/refresh (1).png"))); // NOI18N
        btBack.setText("Refresh");
        btBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBackActionPerformed(evt);
            }
        });

        btBack1.setBackground(new java.awt.Color(255, 255, 204));
        btBack1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btBack1.setForeground(new java.awt.Color(255, 0, 0));
        btBack1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/exit.png"))); // NOI18N
        btBack1.setText("Quay lại");
        btBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBack1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btBack1))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btBack)
                    .addComponent(btBack1))
                .addGap(32, 32, 32))
        );

        jButton1.setText("jButton1");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/diagram.png"))); // NOI18N
        jLabel12.setText("THỐNG KÊ DOANH THU ");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 255));
        jLabel13.setText("Chọn tháng:");

        selectNam.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        selectNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectNamActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 255));
        jLabel14.setText("Chọn năm:");

        selectedThang.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        selectedThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 204, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/money.png"))); // NOI18N
        jButton2.setText("Xuất doanh thu");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 255));
        jLabel15.setText("Người ký");

        jScrollPane2.setViewportView(laychuky);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(187, 187, 187))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(selectedThang, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectNam, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15)
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jButton2)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selectNam, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(selectedThang, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GoiNguon/video-camera.png"))); // NOI18N
        jMenu1.setText(" Phần mềm quản lý rạp chiếu phim. Nhóm JTTeam. ADMINPAGE: QUẢN LÝ PHIM");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbPhimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhimMouseClicked
        // TODO add your handling code here:
        getRowPhim(tbPhim.getSelectedRow());
    }//GEN-LAST:event_tbPhimMouseClicked

    private void btBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBackActionPerformed
        try {
            // TODO add your handling code here:
            setTablePhim();
        } catch (InterruptedException ex) {
            Logger.getLogger(QLPHIM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btBackActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        DefaultTableModel SearchTable = (DefaultTableModel) tbPhim.getModel();
        String search = txtSearch.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(SearchTable);
        tbPhim.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLamMoiActionPerformed
        // TODO add your handling code here:
        clearPhim();
    }//GEN-LAST:event_btLamMoiActionPerformed

    private void btSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSuaActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){
            SimpleDateFormat DateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat TimeFormatter = new SimpleDateFormat("HH:mm");
            java.util.Date d_NgPH = txtNgPH.getDate();
            java.util.Date t_TLuong = (Date)spin_TLuong.getValue();
            String S_NgPH = DateFormatter.format(d_NgPH);
            String S_TLuong = TimeFormatter.format(t_TLuong);
            MaTL = matl.get(cbbTL.getSelectedIndex());
            String CallStore;
            if(file == null)
                CallStore = "{Call update_phim("+ma+",'"+txtTen.getText()+"',"+MaTL+",to_date('"+S_NgPH+"','dd-mm-yyyy'),to_date('"+S_TLuong+"','HH24:MI'),'"+txtDD.getText()+"','"+txtDV.getText()+"','"+txtNPH.getText()+"',null)}";
            else
            {
                String filename = file.getName();
                CallStore = "{Call update_phim("+ma+",'"+txtTen.getText()+"',"+MaTL+",to_date('"+S_NgPH+"','dd-mm-yyyy'),to_date('"+S_TLuong+"','HH24:MI'),'"+txtDD.getText()+"','"+txtDV.getText()+"','"+txtNPH.getText()+"','"+filename+"')}";
                    
                File s = new File(getClass().getResource("/GoiNguon/")+filename);
                //                String localDir = System.getProperty("user.dir");
                String local = System.getProperty("user.dir")+"\\src\\GoiNguon\\";
                File f = new File(local+s.getName());
                copyFile(file,f);
            }
             CallableStatement cs = con.prepareCall(CallStore);
                cs.execute();
                setTablePhim();
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this,"Một trong các thông tin bị sai","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }//GEN-LAST:event_btSuaActionPerformed

    private void btXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btXoaActionPerformed
        // TODO add your handling code here:
        try(Connection con = ConnectionUtils.getMyConnection()){
            String SQL_Phim = "DELETE FROM PHIM WHERE MAPHIM = "+ ma;
            Statement stat_Phim = con.createStatement();
            stat_Phim.executeUpdate(SQL_Phim);
            setTablePhim();
            clearPhim();
            con.close();
            JOptionPane.showMessageDialog(this,"Thành công!");
        } catch (Exception ex){
            Logger.getLogger(QLPHIM.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Xóa không thành công!","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }//GEN-LAST:event_btXoaActionPerformed

    private void btThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThemActionPerformed
        // TODO add your handling code here:
        try (Connection con = ConnectionUtils.getMyConnection()) {
            SimpleDateFormat DateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat TimeFormatter = new SimpleDateFormat("HH:mm");
            java.util.Date d_NgPH = txtNgPH.getDate();
            java.util.Date t_TLuong = (Date)spin_TLuong.getValue();
            String S_NgPH = DateFormatter.format(d_NgPH);
            String S_TLuong = TimeFormatter.format(t_TLuong);
            MaTL = matl.get(cbbTL.getSelectedIndex());
            String SQL_PHIM;
            if(file == null)
            SQL_PHIM="INSERT INTO PHIM(MAPHIM,TENPHIM,MATL,DAODIEN,DIENVIEN,NHAPH,NGAYPH,THOILUONG)"
            +"VALUES(MAPHIM_SEQ4.nextval,'"+txtTen.getText()+"',"+MaTL+",'"+txtDD.getText()+"','"+txtDV.getText()+"','"+txtNPH.getText()+"',to_date('"+S_NgPH+"','dd-mm-yyyy'),to_date('"+S_TLuong+"','hh24:mi'))";
            else
            {
                String filename = file.getName();
                SQL_PHIM="INSERT INTO PHIM(MAPHIM,TENPHIM,MATL,DAODIEN,DIENVIEN,NHAPH,NGAYPH,THOILUONG,HINHANH)"
                +"VALUES(MAPHIM_SEQ4.nextval,'"+txtTen.getText()+"',"+MaTL+",'"+txtDD.getText()+"','"+txtDV.getText()+"','"+txtNPH.getText()+"',to_date('"+S_NgPH+"','dd-mm-yyyy'),to_date('"+S_TLuong+"','hh24:mi'),'"+filename+"')";
                File s = new File(getClass().getResource("/GoiNguon/")+filename);
                //                String localDir = System.getProperty("user.dir");
                String local = System.getProperty("user.dir")+"\\src\\GoiNguon\\";
                File f = new File(local+s.getName());
                copyFile(file,f);
            }
            Statement stat_PHIM = con.createStatement();
            stat_PHIM.executeUpdate(SQL_PHIM);
            setTablePhim();
            getRowPhim(tbPhim.getRowCount()-1);
            JOptionPane.showMessageDialog(this,"Thành công!");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"Một trong các thông tin bị sai","Lỗi",JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }//GEN-LAST:event_btThemActionPerformed

    private void btOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOpenFileActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        //        fc.setCurrentDirectory(new File(System.getProperty("user.home")));
        //        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        try {
            fc.setCurrentDirectory(new File(getClass().getResource("/GoiNguon/").toURI()));
        } catch (URISyntaxException ex) {
            Logger.getLogger(QLPHIM.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images","jpg","gif","png");
        fc.addChoosableFileFilter(filter);
        fc.setDialogTitle("Chọn ảnh");
        int result = fc.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            file = fc.getSelectedFile();
            try {
                if (ImageIO.read(file) != null)
                {
                    showAnh(file);
                }
                else
                {
                    file = null;
                    JOptionPane.showMessageDialog(this,"Không phải ảnh","Lỗi",JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                Logger.getLogger(QLPHIM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btOpenFileActionPerformed

    private void cbbTLItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbTLItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbTLItemStateChanged

    private void txtDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDTActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String month = selectedThang.getSelectedItem().toString();
        String year = selectNam.getSelectedItem().toString();
        String c = laychuky.getText();
        try {
            LayDoanhThu(month,year,c);
        } catch (SQLException | JRException ex) {
            Logger.getLogger(TrangThanhVien.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Doanh thu tháng này chưa có");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void selectNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectNamActionPerformed

    private void btBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBack1ActionPerformed
        // TODO add your handling code here:
         hide();
        AdminPage_new.main(null);
    }//GEN-LAST:event_btBack1ActionPerformed
     public void LayDoanhThu(String a,String b,String c) throws SQLException, JRException {
        // TODO code application logic here
        
        HashMap hs= new HashMap();
        hs.put("thang", a);
        hs.put("YEAR",b);
        hs.put("nguoiky",c);
        String localDir = System.getProperty("user.dir");

      MyreportViewer viewer = new MyreportViewer(localDir+"\\src\\report\\report7.jrxml",hs);
        viewer.setVisible(true);
    }
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
            java.util.logging.Logger.getLogger(QLPHIM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLPHIM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLPHIM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLPHIM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new QLPHIM().setVisible(true);
                    Thread.sleep(5000);
                   

                } catch (SQLException | ClassNotFoundException | ParseException | InterruptedException ex) {
                    Logger.getLogger(QLPHIM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel HinhAnh;
    private javax.swing.JButton btBack;
    private javax.swing.JButton btBack1;
    private javax.swing.JButton btLamMoi;
    private javax.swing.JButton btOpenFile;
    private javax.swing.JButton btSua;
    private javax.swing.JButton btThem;
    private javax.swing.JButton btXoa;
    private javax.swing.JComboBox<String> cbbTL;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane laychuky;
    private javax.swing.JComboBox<String> selectNam;
    private javax.swing.JComboBox<String> selectedThang;
    private javax.swing.JSpinner spin_TLuong;
    private javax.swing.JTable tbPhim;
    private javax.swing.JTextField txtDD;
    private javax.swing.JTextField txtDT;
    private javax.swing.JTextField txtDV;
    private javax.swing.JTextField txtLink;
    private javax.swing.JTextField txtNPH;
    private com.toedter.calendar.JDateChooser txtNgPH;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
