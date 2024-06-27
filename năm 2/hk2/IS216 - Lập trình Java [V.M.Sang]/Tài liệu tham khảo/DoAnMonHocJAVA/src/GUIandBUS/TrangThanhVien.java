/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIandBUS;
import dao.ConnectionUtils;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
//---------------------------
public class TrangThanhVien extends javax.swing.JFrame implements ActionListener{
    JButton[] button = new JButton[60];
    String makh_var;
    ArrayList<String> ngaychieu = new ArrayList<>();
    ArrayList<Integer> maghe = new ArrayList<>();
    Integer maphim_new;
    ArrayList<String>TenPhim_new = new ArrayList<>();
    Integer masuat=0;
    String chonlich;
    Integer maphim_var=0;
    Integer maphong_var=0;
    int malich_var;
    Integer maghe_tam;
    String URL_hinh;
    Connection con;
    
    /**
     * Creates new form TrangThanhVien
     * @param a
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.text.ParseException
     */
    public TrangThanhVien(String a) throws ClassNotFoundException, SQLException, JRException, ParseException {
        // Thiết lập penel
        initComponents();
        //Các bước set giao diện
        /*1. Set kích thước giao diện*/
        setSize(1270,700);
        /*2. Set nút chọn tắt mặc định EXIT_ON_CLOSE*/ 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        /*3. Tiêu đề*/
        setTitle("Trang thành viên");
        /*4. Không cho phóng to*/
        showGhe();
        setResizable(false);
        /*5. Vị trí trang*/
        setLocation(100,40);
        //Thiết lập lấy thông tin người dùng
        getKH(a);
        //Thiết lập lấy thông tin phim
        getNgayChieu();
        lichChoose.setModel(new DefaultComboBoxModel(ngaychieu.toArray()));
        phimChoose.setSelectedIndex(-1);
        eventButton();
        /*lichChoose.removeAllItems();*/
        
        
       
    }
    public void KhoiTaoJButton()
    {
        for(int i=0;i<50;i++)
        {
            button[i]=new JButton("null");
        }
    }
    public void getPhim(Integer masuat) throws ClassNotFoundException
    {
        try{
            phimChoose.removeAllItems();
            con = ConnectionUtils.getMyConnection();
               
               String SQL3 = "SELECT TENPHIM,phim.MAPHIM from phim,lichchieu where phim.maphim = lichchieu.maphim and masuat="+masuat;
               Statement s3 = con.createStatement();
               ResultSet rs3 = s3.executeQuery(SQL3);
              TenPhim_new.removeAll(TenPhim_new);
              
               while(rs3.next())
               {
                  TenPhim_new.add(rs3.getString(1));
                
               
               }
               phimChoose.setModel(new DefaultComboBoxModel(TenPhim_new.toArray()));
              
                }catch (HeadlessException | SQLException ex)
            {
               System.out.println(ex);
            }
    }
    public void setAnh(String anh)
    {
        ImageIcon MyImage = new ImageIcon(getClass().getResource("/GoiNguon/"+anh));
        Image img=MyImage.getImage();
        Image newImg=img.getScaledInstance(showanh.getWidth(), showanh.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image= new ImageIcon(newImg);
       showanh.setIcon(image);
       
    }
    public void LoadGhe() throws SQLException
    {
        for(int i=0;i<50;i++)
                    {
                        button[i].setBackground(Color.PINK);
                    }
                 setGheVIP();
              
                String SQL6 = "select soghe,hangghe from ghe,datve,lichchieu where datve.maghe = ghe.maghe and lichchieu.maphong ="+maphong_var+"and lichchieu.malichchieu = datve.malichchieu and lichchieu.masuat ="+masuat+"";
                Statement s5 = con.createStatement();
                ResultSet rs7 = s5.executeQuery(SQL6);
                 while(rs7.next())
                 {
                    int j=0;
                    for(int a=1 ;a<=5;a++)    
                    {
                        for(int i=0;i<10;i++)
                        {
                            if(a==rs7.getInt(1) && i ==rs7.getInt(2))
                            {
                                button[j].setBackground(Color.RED);
  
                            }
                            j++;
                        }
                    }
                 }
    }
    public void getKH(String a) throws ClassNotFoundException
    {
        
        try{
                con = ConnectionUtils.getMyConnection();
                String SQL ="SELECT HO,TEN,NGAYSINH,GIOITINH,LOAIKH,USERNAME,MAKH FROM KHACHHANG\n"
                        + "WHERE username=?";
                PreparedStatement ps=con.prepareStatement(SQL);
                ps.setString(1, a);
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                {
                    namekh.setText(rs.getString(1)+" "+rs.getString(2));
                    sex.setText(rs.getString(4));
                    datetime.setDate(rs.getDate(3));
                    loaikh.setText(rs.getString(5));
                    username.setText(rs.getString(6)); 
                    makh_var=rs.getString(7);
                    jMenu1.setText(" Phần mềm quản lý rạp chiếu phim. Nhóm JTTeam. USERPAGE: "+rs.getString(1)+" "+rs.getString(2));
                }
                String SQL2 ="SELECT SUM(GIA) FROM KHACHHANG,DATVE\n"
                        + "WHERE KHACHHANG.username=?"+"AND KHACHHANG.MAKH = DATVE.MAKH";
                PreparedStatement ps2=con.prepareStatement(SQL2);
                ps2.setString(1, a);
                ResultSet rs2 = ps2.executeQuery();
                while(rs2.next())
                {
                  sumcost.setText(rs2.getString(1));
                }
            }catch (HeadlessException | SQLException ex)
            {
               System.out.println(ex);
            }
    }
    public void getNgayChieu() throws SQLException, ClassNotFoundException, ParseException
    {
        try{
               con = ConnectionUtils.getMyConnection();
               String SQL = "SELECT distinct ngaychieu from suatchieu where sysdate-to_date(ngaychieu)<=15";
               Statement s = con.createStatement();
               ResultSet rs = s.executeQuery(SQL);
               while(rs.next())
               {
 
                        String temp = rs.getString(1);
                        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
                        java.util.Date date = dt.parse(temp);
                        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
                        String a = dt1.format(date);
                        ngaychieu.add(a);
                    }
               }catch(HeadlessException | SQLException ex)
                {
                    System.out.println(ex);
                }

    }
   
    public void showGhe(){
        GridLayout gridLayout = new GridLayout(10,5);
        phimpanel2.setLayout(gridLayout);
        int j=0;
        for(int a=1 ;a<=5;a++)    
        {
            for(int i=0;i<=9;i++)
            {
                button[j]=new JButton(""+a+i+"");
                button[j].setBackground(new java.awt.Color(255,255,255));
                phimpanel2.add(button[j]);
                button[j].setFont(new java.awt.Font("Tahoma", 0, 18));
                j++;
            }
        }
    }
    public void eventButton()
    {
        for(int i=0;i<50;i++)
        {
            button[i].addActionListener(this);
        }
    }
    public void LayReport(String a) throws SQLException, JRException {
        // TODO code application logic here
        String makh = a;
        HashMap hs= new HashMap();
        hs.put("makh", makh);
        String localDir = System.getProperty("user.dir");

      MyreportViewer viewer = new MyreportViewer(localDir+"\\src\\report\\report2.jrxml",hs);
        viewer.setVisible(true);
    }
     public void LayReportVe(String a) throws SQLException, JRException {
        // TODO code application logic here
        String mave = a;
        HashMap hs= new HashMap();
        hs.put("MAVE", mave);
        String localDir = System.getProperty("user.dir");

      MyreportViewer viewer = new MyreportViewer(localDir+"\\src\\report\\MauVe.jrxml",hs);
        viewer.setVisible(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane3 = new javax.swing.JTabbedPane();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        namekh = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        sex = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        loaikh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        datetime = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        sumcost = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        panelfail = new javax.swing.JPanel();
        datvefail = new javax.swing.JLabel();
        panelthanhcong = new javax.swing.JPanel();
        datvethanhcong = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lichChoose = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        gioChoose = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        phongchieu = new javax.swing.JLabel();
        ghe = new javax.swing.JLabel();
        phongchieu1 = new javax.swing.JLabel();
        panelPhim = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        phimpanel2 = new javax.swing.JPanel();
        maphong = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        action2 = new javax.swing.JLabel();
        phimChoose = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        action1 = new javax.swing.JLabel();
        showanh = new javax.swing.JLabel();
        btBack = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jRadioButtonMenuItem2.setSelected(true);
        jRadioButtonMenuItem2.setText("jRadioButtonMenuItem2");

        jMenu2.setText("File");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("File");
        jMenuBar3.add(jMenu4);

        jMenu5.setText("Edit");
        jMenuBar3.add(jMenu5);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 204));
        getContentPane().setLayout(null);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/teamwork (1).png"))); // NOI18N
        jLabel4.setText(" THÔNG TIN CÁ NHÂN");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Họ Tên KH");

        namekh.setEditable(false);
        namekh.setBackground(new java.awt.Color(204, 204, 204));
        namekh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        namekh.setForeground(new java.awt.Color(255, 0, 0));
        namekh.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        namekh.setText("Tên khách hàng");
        namekh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namekhActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Giới tính");

        sex.setEditable(false);
        sex.setBackground(new java.awt.Color(204, 204, 204));
        sex.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sex.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sex.setText("Nam/Nu");
        sex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sexActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 255));
        jLabel9.setText("Loại KH");

        loaikh.setEditable(false);
        loaikh.setBackground(new java.awt.Color(204, 204, 204));
        loaikh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        loaikh.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        loaikh.setText("Loại khách hàng");
        loaikh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loaikhActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Ngày sinh");

        datetime.setBackground(new java.awt.Color(255, 255, 255));
        datetime.setDateFormatString("dd-MM-yyyy");
        datetime.setDoubleBuffered(false);
        datetime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setText("Tài khoản");

        username.setEditable(false);
        username.setBackground(new java.awt.Color(204, 204, 204));
        username.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        username.setText("Tài khoản");
        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setText("Tích lũy");

        sumcost.setEditable(false);
        sumcost.setBackground(new java.awt.Color(204, 204, 204));
        sumcost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sumcost.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sumcost.setText("Cost Sum");

        jButton3.setBackground(new java.awt.Color(204, 204, 204));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GoiNguon/phone-call.png"))); // NOI18N
        jButton3.setText("  Hỗ trợ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 204, 204));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GoiNguon/clipboard.png"))); // NOI18N
        jButton4.setText(" Lịch sử đặt vé");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 22, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(datetime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                        .addComponent(username, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(sumcost, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(namekh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                        .addComponent(sex, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(loaikh, javax.swing.GroupLayout.Alignment.LEADING))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)))
                        .addGap(0, 38, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namekh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sex, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loaikh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(datetime, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sumcost, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel5);
        jPanel5.setBounds(10, 10, 400, 450);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        panelfail.setBackground(new java.awt.Color(255, 255, 255));

        datvefail.setBackground(new java.awt.Color(102, 102, 255));
        datvefail.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        datvefail.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        datvefail.setToolTipText("");
        datvefail.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        datvefail.setAlignmentX(1.0F);
        datvefail.setAlignmentY(1.0F);

        javax.swing.GroupLayout panelfailLayout = new javax.swing.GroupLayout(panelfail);
        panelfail.setLayout(panelfailLayout);
        panelfailLayout.setHorizontalGroup(
            panelfailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelfailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(datvefail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelfailLayout.setVerticalGroup(
            panelfailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelfailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(datvefail, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelthanhcong.setBackground(new java.awt.Color(255, 255, 255));

        datvethanhcong.setBackground(new java.awt.Color(102, 102, 255));
        datvethanhcong.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        datvethanhcong.setForeground(new java.awt.Color(0, 153, 51));
        datvethanhcong.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        datvethanhcong.setToolTipText("");
        datvethanhcong.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        datvethanhcong.setAlignmentX(1.0F);
        datvethanhcong.setAlignmentY(1.0F);
        datvethanhcong.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        datvethanhcong.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout panelthanhcongLayout = new javax.swing.GroupLayout(panelthanhcong);
        panelthanhcong.setLayout(panelthanhcongLayout);
        panelthanhcongLayout.setHorizontalGroup(
            panelthanhcongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelthanhcongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(datvethanhcong, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        panelthanhcongLayout.setVerticalGroup(
            panelthanhcongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelthanhcongLayout.createSequentialGroup()
                .addComponent(datvethanhcong, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelfail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelthanhcong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(panelthanhcong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelfail, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 470, 400, 150);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/receipt.png"))); // NOI18N
        jLabel1.setText(" Chọn ngày chiếu");

        lichChoose.setBackground(new java.awt.Color(153, 255, 51));
        lichChoose.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lichChoose.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                lichChooseItemStateChanged(evt);
            }
        });
        lichChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lichChooseActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 51, 204));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/calendar.png"))); // NOI18N
        jLabel11.setText("Giờ chiếu");

        gioChoose.setBackground(new java.awt.Color(153, 255, 51));
        gioChoose.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gioChoose.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "null" }));
        gioChoose.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                gioChooseItemStateChanged(evt);
            }
        });
        gioChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gioChooseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lichChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gioChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lichChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gioChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4);
        jPanel4.setBounds(430, 10, 820, 66);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));

        jLabel14.setBackground(new java.awt.Color(102, 255, 102));
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 51));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("MÀN CHIẾU");
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        phongchieu.setBackground(new java.awt.Color(255, 255, 255));
        phongchieu.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        phongchieu.setForeground(new java.awt.Color(0, 204, 51));
        phongchieu.setText("<Dữ liệu>");

        ghe.setBackground(new java.awt.Color(255, 255, 255));
        ghe.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ghe.setText("Số ghế");

        phongchieu1.setBackground(new java.awt.Color(255, 255, 255));
        phongchieu1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        phongchieu1.setForeground(new java.awt.Color(0, 204, 51));
        phongchieu1.setText("<Dữ liệu>");

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/cinema.png"))); // NOI18N
        jLabel15.setText("Phòng chiếu");

        phimpanel2.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout phimpanel2Layout = new javax.swing.GroupLayout(phimpanel2);
        phimpanel2.setLayout(phimpanel2Layout);
        phimpanel2Layout.setHorizontalGroup(
            phimpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );
        phimpanel2Layout.setVerticalGroup(
            phimpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );

        maphong.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        maphong.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        maphong.setText("Mã phòng");

        javax.swing.GroupLayout panelPhimLayout = new javax.swing.GroupLayout(panelPhim);
        panelPhim.setLayout(panelPhimLayout);
        panelPhimLayout.setHorizontalGroup(
            panelPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPhimLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(panelPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelPhimLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(maphong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(phimpanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        panelPhimLayout.setVerticalGroup(
            panelPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPhimLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(phimpanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelPhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPhimLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPhimLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(maphong, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GoiNguon/ok.png"))); // NOI18N
        jButton5.setBorder(null);
        jButton5.setBorderPainted(false);
        jButton5.setDefaultCapable(false);
        jButton5.setOpaque(false);
        jButton5.setVerifyInputWhenFocusTarget(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(255, 204, 204));
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 51, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Màn chiếu");
        jLabel12.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        jPanel3.setBackground(new java.awt.Color(255, 255, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Ghế VIP");

        jPanel6.setBackground(new java.awt.Color(255, 153, 153));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Ghế Thường");

        jPanel10.setBackground(new java.awt.Color(0, 255, 0));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Ghế VIP chọn");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Ghế đã mua");

        jPanel12.setBackground(new java.awt.Color(255, 153, 0));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Ghế thường chọn");

        jPanel8.setBackground(new java.awt.Color(255, 51, 0));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(685, 685, 685)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(phongchieu, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(ghe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(phongchieu1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(panelPhim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel17))))
                                .addGap(303, 303, 303)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(30, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phongchieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ghe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(phongchieu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30))))
        );

        jButton5.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(jPanel2);
        jPanel2.setBounds(780, 90, 470, 530);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        action2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        action2.setForeground(new java.awt.Color(255, 51, 51));
        action2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        action2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/recruitment.png"))); // NOI18N
        action2.setText(" Diễn viên");
        action2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        action2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        action2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        action2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        phimChoose.setBackground(new java.awt.Color(153, 255, 51));
        phimChoose.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        phimChoose.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                phimChooseItemStateChanged(evt);
            }
        });
        phimChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phimChooseActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 51, 204));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/recording.png"))); // NOI18N
        jLabel8.setText("Chọn phim");

        action1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        action1.setForeground(new java.awt.Color(255, 51, 51));
        action1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        action1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/actor (1).png"))); // NOI18N
        action1.setText(" Đạo diễn");

        showanh.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(action2, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(phimChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(270, 270, 270))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(showanh, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(action1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(phimChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(showanh, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(action1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(action2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        getContentPane().add(jPanel7);
        jPanel7.setBounds(430, 90, 340, 530);

        btBack.setBackground(new java.awt.Color(255, 255, 204));
        btBack.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btBack.setForeground(new java.awt.Color(255, 0, 0));
        btBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIandBUS/exit.png"))); // NOI18N
        btBack.setText("QUAY LẠI");
        btBack.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBackActionPerformed(evt);
            }
        });
        getContentPane().add(btBack);
        btBack.setBounds(510, 310, 140, 50);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GoiNguon/video-camera.png"))); // NOI18N
        jMenu1.setText(" Phần mềm quản lý rạp chiếu phim. Nhóm JTTeam. USERPAGE");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Đăng xuất");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
       String m= username.getText();
       System.out.println(m);
        try {
            LayReport(m);
        } catch (SQLException | JRException ex) {
            Logger.getLogger(TrangThanhVien.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Khách hàng chưa có hóa đơn");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this,"Hệ thống đang bảo trì!\n"+"Mọi chi tiết xin liên hệ: 17520867@gm.uit.edu.vn","Hỗ trợ",JOptionPane.OK_OPTION);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void sexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sexActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sexActionPerformed

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameActionPerformed

    private void loaikhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loaikhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loaikhActionPerformed

    private void namekhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namekhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namekhActionPerformed

    private void lichChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lichChooseActionPerformed
        // TODO add your handling code here:
        for(int i=0;i<50;i++)
        {
            button[i].setBackground(Color.PINK);
        }
       
            try{
                con = ConnectionUtils.getMyConnection();
              /*maphim_var = phimChoose.getSelectedItem().toString();
                 Object ngaygio_var = lichChoose.getSelectedItem();
                phongchieu.setText("NULL");
                String SQL3 ="select MaPhong,thoigianbd from lichchieu,suatchieu,phim\n" +
                "where lichchieu.masuat = suatchieu.masuat and lichchieu.maphim = phim.maphim and tenphim='"+maphim_var+"'and suatchieu.masuat="+ngaygio_var+"";
                 Statement s2 = con.createStatement();
                 ResultSet rs4 = s2.executeQuery(SQL3);
                 while(rs4.next())
                 {
                     phongchieu.setText(rs4.getString(1));
                     ngaygiochieu.setText(rs4.getString(2));
                 }
                 setGheVIP();
                String phongchieu_var = phongchieu.getText();
               
                String SQL4 = "select soghe,hangghe from ghe,datve,lichchieu where datve.maghe = ghe.maghe and lichchieu.maphong ='"+phongchieu_var+"'and lichchieu.malichchieu = datve.malichchieu and lichchieu.masuat ="+ngaygio_var+"";
                Statement s3 = con.createStatement();
                ResultSet rs5 = s3.executeQuery(SQL4);
                 while(rs5.next())
                 {
                    int j=0;
                    for(int a=1 ;a<=5;a++)    
                    {
                        for(int i=0;i<10;i++)
                        {
                            if(a==rs5.getInt(1) && i ==rs5.getInt(2))
                            {
                                button[j].setBackground(Color.RED);
  
                            }
                            j++;
                        }
                    }
                 }*/
                
            }catch (HeadlessException | SQLException ex)
            {
               System.out.println(ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrangThanhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }//GEN-LAST:event_lichChooseActionPerformed

    private void lichChooseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_lichChooseItemStateChanged
        // TODO add your handling code here:
         try{
               con = ConnectionUtils.getMyConnection();
               chonlich = lichChoose.getSelectedItem().toString();
               String SQL2 = "ALTER SESSION SET NLS_DATE_FORMAT = 'DD/MM/YYYY HH24:MI:SS'";
               String SQL = "SELECT THOIGIANBD FROM SUATCHIEU WHERE ngaychieu='"+chonlich+"'";
               Statement s2 = con.createStatement();
               s2.executeUpdate(SQL2);
               Statement s = con.createStatement();
               ResultSet rs= s.executeQuery(SQL);
              
               gioChoose.removeAllItems();
               while(rs.next())
               {
                   String temp = rs.getString(1);
                   SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
                   java.util.Date date = dt.parse(temp);
                   SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm:ss");
                   String a = dt1.format(date);
                   gioChoose.addItem(a);
                   
               }
            }catch (HeadlessException | SQLException ex)
            {
               System.out.println(ex);
            } catch (ClassNotFoundException | ParseException ex) {
            Logger.getLogger(TrangThanhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lichChooseItemStateChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        String ve_thanhcong= " ";
        for(int i=0;i<50;i++)
        {
                if(button[i].getBackground().equals(Color.ORANGE))
                {
                        maghe.add(Integer.parseInt(button[i].getText()));   
                }
               else if(button[i].getBackground().equals(Color.GREEN))
                {
                        maghe.add(Integer.parseInt(button[i].getText())); 
                }
         }
          System.out.println(makh_var);
       String SQL = "SELECT MALICHCHIEU FROM LICHCHIEU WHERE MAPHONG="+maphong_var+"and MAPHIM="+maphim_var+"and MASUAT="+masuat;
        try {
            Statement s= con.createStatement();
            ResultSet rs = s.executeQuery(SQL);
            while(rs.next())
            {
                malich_var=rs.getInt(1);
            }
            System.out.println(malich_var);
          
        } catch (SQLException ex) {
            Logger.getLogger(TrangThanhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        int a[]=new int[50];
        for(int i=0;i<maghe.size();i++)
        {
            
            int temp = maghe.get(i);
            a[i]=temp;
             maghe_tam = 0;
            String SQL2 = "select maghe from ghe where maphong ="+maphong.getText()+" and concat(to_char(soghe),to_char(hangghe))="+temp+"";
            Statement s2;
            try {
                s2 = con.createStatement();
                ResultSet rs2 = s2.executeQuery(SQL2);
                while(rs2.next())
                {
                    maghe_tam=rs2.getInt(1);
                    
                }
                System.out.println(maghe_tam);
                String CallStore = "{Call sp_datve("+makh_var+",'1',"+malich_var+","+maghe_tam+",'ONLINE','The')}";
                CallableStatement cs = con.prepareCall(CallStore);
                cs.executeUpdate();
               String SQLtemp = "SELECT mave from datve where malichchieu="+malich_var+"and maghe="+maghe_tam+"";
                 Statement s3= con.createStatement();
                ResultSet rs3 = s3.executeQuery(SQLtemp);
                while(rs3.next())
                {
                    LayReportVe(rs3.getString(1));
                    System.out.println(rs3.getString(1));
                }
                panelthanhcong.setBackground(Color.green);
                
                ve_thanhcong= ve_thanhcong +a[i] + " ";
                
              
            } catch (SQLException ex) {
//                Logger.getLogger(TrangThanhVien.class.getName()).log(Level.SEVERE, null, ex);
               if(ex.getErrorCode() ==20000)
               {
                   panelfail.setBackground(Color.PINK);
                   datvefail.setText("Vé đã tại ghế "+temp+" một người khác đặt rồi đặt rồi");
                   datvefail.setForeground(Color.red);
               }
               
            } catch (JRException ex) {
                Logger.getLogger(TrangThanhVien.class.getName()).log(Level.SEVERE, null, ex);
            }
            datvethanhcong.setText("Vé tại ghế"+ve_thanhcong+" đặt thành công"); 
            
        }
        maghe.clear();
        try {
            LoadGhe();
        } catch (SQLException ex) {
            Logger.getLogger(TrangThanhVien.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void gioChooseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_gioChooseItemStateChanged

    }//GEN-LAST:event_gioChooseItemStateChanged

    private void gioChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gioChooseActionPerformed
        // TODO add your handling code here:
         try{
               con = ConnectionUtils.getMyConnection();
              
               Object chongiobd =gioChoose.getSelectedItem();
               String SQL2 = "ALTER SESSION SET NLS_DATE_FORMAT = 'DD/MM/YYYY HH24:MI:SS'";
               String SQL = "SELECT MASUAT FROM SUATCHIEU WHERE ngaychieu='"+chonlich+"'and to_char(thoigianbd,'HH24:MI:SS')='"+chongiobd+"'";
               Statement s2 = con.createStatement();
               s2.executeUpdate(SQL2);
               Statement s = con.createStatement();
               ResultSet rs= s.executeQuery(SQL);
            
               while(rs.next())
               {
                masuat = rs.getInt(1);
               }
               getPhim(masuat);
               
               
     
            }catch (HeadlessException | SQLException ex)
            {
               System.out.println(ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrangThanhVien.class.getName()).log(Level.SEVERE, null, ex);
        } 

            
        
    }//GEN-LAST:event_gioChooseActionPerformed

    private void phimChooseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_phimChooseItemStateChanged
        // TODO add your handling code here:
          
    }//GEN-LAST:event_phimChooseItemStateChanged

    private void phimChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phimChooseActionPerformed
        // TODO add your handling code here:
        
            try{
                
                 con = ConnectionUtils.getMyConnection();
                 Object tenphim_selected = phimChoose.getSelectedItem();
                 if(tenphim_selected!=null)
                 {
                     String SQL4 ="SELECT DAODIEN,DIENVIEN,HINHANH,MAPHIM FROM PHIM WHERE tenphim='"+tenphim_selected+"'";
                 Statement s3 = con.createStatement();
                 ResultSet rs5 = s3.executeQuery(SQL4);
                 while(rs5.next())
                 {
                   action1.setText(rs5.getString(1));
                   action2.setText(rs5.getString(2));
                   URL_hinh = rs5.getString(3);
                   setAnh(URL_hinh);
                   maphim_var=rs5.getInt(4);
                 }
                 String SQL5 ="SELECT MAPHONG FROM LICHCHIEU WHERE MASUAT="+masuat+"and MAPHIM ="+maphim_var;
                 Statement s4= con.createStatement();
                 ResultSet rs6 = s4.executeQuery(SQL5);
                 while(rs6.next())
                 {
                     maphong.setText(rs6.getString(1));
                     maphong_var=rs6.getInt(1);
                    
                 }
                 LoadGhe();
                 }
                 
            }catch (HeadlessException | SQLException ex)
            {
               System.out.println(ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrangThanhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_phimChooseActionPerformed

    private void btBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBackActionPerformed
        // TODO add your handling code here:
        hide();
        DangNhap.main(null);
    }//GEN-LAST:event_btBackActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        hide();
        DangNhap.main(null);
    }//GEN-LAST:event_jMenuItem1ActionPerformed
public void setGheVIP()
{
      
            try{
               con = ConnectionUtils.getMyConnection();
                 String phongchieu_var=maphong.getText();
                 String SQL4 ="SELECT soghe,hangghe FROM GHE where loaighe='VIP' and maphong="+phongchieu_var+"";
                 Statement s3 = con.createStatement();
                 ResultSet rs5 = s3.executeQuery(SQL4);
                 while(rs5.next())
                 {
                    int j=0;
                    for(int a=1 ;a<=5;a++)    
                    {
                        for(int i=0;i<10;i++)
                        {
                            if(a==rs5.getInt(1) && i ==rs5.getInt(2))
                            {
                                button[j].setBackground(Color.YELLOW);
                            }
                            j++;
                        }
                    }
                 }
            }catch (HeadlessException | SQLException ex)
            {
               System.out.println(ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrangThanhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void HienThi(String args) throws SQLException {
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
            java.util.logging.Logger.getLogger(TrangThanhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrangThanhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrangThanhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrangThanhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new TrangThanhVien(args).setVisible(true);
            } catch (ClassNotFoundException | SQLException | JRException | ParseException ex) {
                Logger.getLogger(TrangThanhVien.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel action1;
    private javax.swing.JLabel action2;
    private javax.swing.JButton btBack;
    private com.toedter.calendar.JDateChooser datetime;
    private javax.swing.JLabel datvefail;
    private javax.swing.JLabel datvethanhcong;
    private javax.swing.JLabel ghe;
    private javax.swing.JComboBox<String> gioChoose;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JComboBox<String> lichChoose;
    private javax.swing.JTextField loaikh;
    private javax.swing.JLabel maphong;
    private javax.swing.JTextField namekh;
    private javax.swing.JPanel panelPhim;
    private javax.swing.JPanel panelfail;
    private javax.swing.JPanel panelthanhcong;
    private javax.swing.JComboBox<String> phimChoose;
    private javax.swing.JPanel phimpanel2;
    private javax.swing.JLabel phongchieu;
    private javax.swing.JLabel phongchieu1;
    private javax.swing.JTextField sex;
    private javax.swing.JLabel showanh;
    private javax.swing.JTextField sumcost;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0;i<50;i++)
        {
           int x=10;
           if(e.getSource()==button[i] )
           {
               if((button[i].getBackground().equals(Color.YELLOW)))
               {
                  button[i].setBackground(Color.GREEN); 
               }
               else if((button[i].getBackground().equals(Color.PINK)))
                {
                    button[i].setBackground(Color.ORANGE);
                } 
               else if(button[i].getBackground().equals(Color.ORANGE))
                {
                           button[i].setBackground(Color.PINK); 
                 }
               else if(button[i].getBackground().equals(Color.GREEN))
                  {
                          button[i].setBackground(Color.YELLOW); 
                   }
           }
         
         
        }
    }
}
