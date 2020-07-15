/* 	Author: Stergina
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class mainApp extends JFrame implements ActionListener {

	JFrame frame;
	JMenu menu1; JMenu menu2;
	JMenuItem j1; JMenuItem j2; JMenuItem j3; JMenuItem j4;
	JMenuBar menubar;
	JPanel products_panel, orders_panel, sales_panel, tabPanel;
	JTabbedPane tabs;
	JList list,list2;
	JLabel label;
	ProductsList myproducts; OrdersList myorders; SalesList mysales;
	JButton but1,but2,but3,but4,but5,but6,but7,but8,but9,but10,but11,but12,but13,but14,but15,but16,but17,but18,but19,but20;
	JButton but21,but22,but23,but24,but25,but26,but27,but28,but29,but30, but31, but32, but33, but34, but35, but36, but37, but38, but39, but40, but41, but42;
	ImageIcon image;
	TitledBorder border;
	ArrayList<JButton> products_buttons = new ArrayList<JButton>();
	ArrayList<JButton> products_buttons2 = new ArrayList<JButton>();
	ArrayList<JButton> products_buttons3 = new ArrayList<JButton>();
	ArrayList<JButton> products_buttons4 = new ArrayList<JButton>();
	ArrayList<JButton> orders_buttons = new ArrayList<JButton>();
	ArrayList<JButton> sales_buttons = new ArrayList<JButton>();
	ArrayList<Integer> orders_code = new ArrayList<Integer>();
	ArrayList<Integer> sales_code = new ArrayList<Integer>();
				
	public static void main(String[] args) 
	{
		new mainApp();
	}

	//We define a constructor to setup the GUI.
	public mainApp ()
	{

		//Initialization of the Products list.
		myproducts = new ProductsList();	
		
		//Initialization of the Orders list.
		myorders = new OrdersList();	
		
		//Initialization of the Sales list.
		mysales = new SalesList();	
		
		//Loading the text file that contains the available products.	
		myproducts.loadFile("ProductsList.txt");  
		
		//Loading the text file that contains the orders.
		myorders.loadFile("OrdersList.txt",myproducts);  
		
		//Loading the text file that contains the sales.
		mysales.loadFile("SalesList.txt",myproducts);
		
		//We create the Main Frame.
   		frame = new JFrame("Application");
    	frame.setSize(900,600);
    	frame.setLayout(new BorderLayout());
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new JPanel());
		
    	//We create menubar with 2 menus.
		JMenu menu1 = new JMenu("File");
		JMenu menu2 = new JMenu("Search");

		//Create two items for menu1.
		j1 = new JMenuItem("Load");
		j1.addActionListener(this);
		menu1.add(j1);

		j2 = new JMenuItem("Save");
		j2.addActionListener(this);
		menu1.add(j2);
		
		//Create two items for menu2.
		j3 = new JMenuItem("Orders");
		j3.addActionListener(this);
		menu2.add(j3);

		j4 = new JMenuItem("Sales");
		j4.addActionListener(this);
		menu2.add(j4);		
		
		//We add the menus to the menubar.
		menubar = new JMenuBar();
		menubar.add(menu1);
		menubar.add(menu2);
		
		//We add the menubar to the frame.
		frame.add(menubar,BorderLayout.PAGE_START);
		
		//We create a subPanel that contains the three tabs: products_panel, orders_panel, sales_panel
		products_panel = new JPanel(new CardLayout());
		orders_panel = new JPanel();
		sales_panel = new JPanel();
		tabs = new JTabbedPane();
		tabs.addTab("Products", products_panel);
		tabs.addTab("Orders", orders_panel);
		tabs.addTab("Sales", sales_panel);
		tabPanel = new JPanel();
    	tabPanel.setLayout(new GridLayout(2,1));
    	tabPanel.setPreferredSize(new Dimension(400,400));
    	tabPanel.add(tabs);

		//We add the subPanel to the frame.
		frame.add(tabPanel,BorderLayout.LINE_START);

		//We create WindowListener for Main Frame on windowClosing.
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//myorders.updateFile("OrdersList.txt"); 
				//mysales.updateFile("SalesList.txt"); 
			}
		});
     	frame.setVisible(true);

   	}//constructor mainApp

	class MyDialogPopup extends JDialog {
		public String sName;
			public MyDialogPopup() {
				setBounds(100, 100, 296, 175);
				setTitle("");	
				setLocationRelativeTo(null);
				getContentPane().setLayout(null);
				final JTextField name = new JTextField();
				name.setBounds(57, 36, 175, 20);
				getContentPane().add(name);
				JButton btnOK = new JButton("OK");
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						sName = name.getText();
						dispose();
					}
				});
				btnOK.setBounds(70, 93, 78, 23);
				getContentPane().add(btnOK);
		}
	}

	public void actionPerformed(ActionEvent e)  
	{
		File selectedFile;
		JFileChooser fc = new JFileChooser();
		int returnValue;

		//We extract the current date.
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
		String today = formatter.format(date);
			
		//We get the day after the current date. 
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);
		Date currentDatePlusOne = c.getTime();
			
		//We get the date after a week of the current date.
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 7);
		Date currentDatePlusSeven = c.getTime();
	
		//Event if the user clicks on the menu Item "Load".			
		if (e.getSource() == j1) 
		{
			returnValue = fc.showOpenDialog(null); //We open the File Chooser.		 		
			if (returnValue == JFileChooser.APPROVE_OPTION) 
			{
				selectedFile = fc.getSelectedFile(); //User chooses the file.
				
				//Tab Products.
				if (selectedFile.getName().equals("ProductsList.txt")) 
				{
					border = new TitledBorder("Types of Products");
					border.setTitleJustification(TitledBorder.CENTER);
					myproducts.loadFile(selectedFile.getName()); //We load the lines from file "ProductsList.txt" to an ArrayList Products.
					JPanel paneButton = new JPanel();
					paneButton.setLayout(new GridLayout(2, 1, 2, 2));
					but1 = new JButton("HardWare");
					paneButton.add(but1);
					but2 = new JButton("Peripherals");
					paneButton.add(but2);
					products_panel.setBorder(border);
					products_panel.add(paneButton, "panebutton1");
					CardLayout cardLayout = (CardLayout) products_panel.getLayout();
					cardLayout.show(products_panel, "panebutton1");
					
					//HardWare Products.
					but1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) 
						{			
							border = new TitledBorder("Available Hardware Products");
							border.setTitleJustification(TitledBorder.CENTER);
							JPanel paneButton2 = new JPanel();
							paneButton2.setLayout(new GridLayout(5, 1, 2, 2));
							but3 = new JButton("Motherboard");
							paneButton2.add(but3);
							but4 = new JButton("CPU");
							paneButton2.add(but4);
							but5 = new JButton("RAM");
							paneButton2.add(but5);
							but6 = new JButton("Graphics Card");
							paneButton2.add(but6);
							but7 = new JButton("Hard Disk");
							paneButton2.add(but7);
							products_panel.setBorder(border);
							products_panel.add(paneButton2, "panebutton2");	
							cardLayout.next(products_panel);
							
							but3.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									border = new TitledBorder("Motherboards");
									border.setTitleJustification(TitledBorder.CENTER);								
									JPanel paneButton3 = new JPanel();
									paneButton3.setLayout(new GridLayout(2, 1, 2, 2));
									but8 = new JButton("B250M-DS3H");
									paneButton3.add(but8);						
									but9 = new JButton("AMD X370");
									paneButton3.add(but9);
									products_panel.setBorder(border);
									products_panel.add(paneButton3, "panebutton3");	
									cardLayout.next(products_panel);
									
									but8.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{	
											JFrame frame2 = new JFrame("Description");
											frame2.setSize(700,500);
											frame2.setResizable(true);
											JPanel pane = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane.setLayout(bl);
											pane.setBackground(Color.WHITE); 
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u = 0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("B250M-DS3H")){
													u = i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/1.png");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											label.setIcon(image);
											pane.add(txt, BorderLayout.LINE_START); 
											pane.add(but42, BorderLayout.PAGE_END); 
											pane.add(label,BorderLayout.LINE_END);
											frame2.add(pane);
											frame2.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
									
									but9.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{				
											JFrame frame3 = new JFrame("Description");
											frame3.setSize(700,600);
											frame3.setResizable(true);
											JPanel pane2 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane2.setLayout(bl);
											pane2.setBackground(Color.WHITE);
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));											
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u=0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("AMD X370")){
													u = i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/2.jpg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											
											label.setIcon(image);
											pane2.add(txt, BorderLayout.LINE_START); 
											pane2.add(but42, BorderLayout.PAGE_END); 
											pane2.add(label,BorderLayout.LINE_END);
											frame3.add(pane2);
											frame3.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
								}          
							});		
							
							but4.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									border = new TitledBorder("CPUs");
									border.setTitleJustification(TitledBorder.CENTER);										
									JPanel paneButton4 = new JPanel();
									paneButton4.setLayout(new GridLayout(2, 1, 2, 2));
									but10 = new JButton("i7-6700K");
									paneButton4.add(but10);
									but11 = new JButton("i5-7400");
									paneButton4.add(but11);
									products_panel.setBorder(border);
									products_panel.add(paneButton4, "panebutton4");	
									cardLayout.next(products_panel);
									
									but10.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{	
											JFrame frame4 = new JFrame("Description");
											frame4.setSize(780,550);
											frame4.setResizable(true);
											JPanel pane3 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane3.setLayout(bl);
											pane3.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u = 0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("i7-6700K")){
													u = i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/3.jpg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											
											label.setIcon(image);
											pane3.add(txt, BorderLayout.LINE_START); 
											pane3.add(but42, BorderLayout.PAGE_END); 
											pane3.add(label,BorderLayout.LINE_END);
											frame4.add(pane3);
											frame4.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
									
									but11.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{				
											JFrame frame5 = new JFrame("Description");
											frame5.setSize(780,600);
											frame5.setResizable(true);
											JPanel pane4 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane4.setLayout(bl);
											pane4.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u = 0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("i5-7400")){
													u = i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/4.png");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											
											label.setIcon(image);
											pane4.add(txt, BorderLayout.LINE_START); 
											pane4.add(but42, BorderLayout.PAGE_END); 
											pane4.add(label,BorderLayout.LINE_END);
											frame5.add(pane4);
											frame5.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
								}          
							});	

							but5.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									border = new TitledBorder("RAMs");
									border.setTitleJustification(TitledBorder.CENTER);										
									JPanel paneButton5 = new JPanel();
									paneButton5.setLayout(new GridLayout(2, 1, 2, 2));
									but12 = new JButton("GR800S264L6/2G");
									paneButton5.add(but12);
									but13 = new JButton("Ballistix Sport LT Red");
									paneButton5.add(but13);
									products_panel.setBorder(border);
									products_panel.add(paneButton5, "panebutton5");	
									cardLayout.next(products_panel);
									
									but12.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{	
											JFrame frame6 = new JFrame("Description");
											frame6.setSize(780,400);
											frame6.setResizable(true);
											JPanel pane5 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane5.setLayout(bl);
											pane5.setBackground(Color.WHITE);
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));											
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u =0 ;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("GR800S264L6/2G")){
													u = i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/5.jpg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											
											label.setIcon(image);
											pane5.add(txt, BorderLayout.LINE_START); 
											pane5.add(but42, BorderLayout.PAGE_END); 
											pane5.add(label,BorderLayout.LINE_END);
											frame6.add(pane5);
											frame6.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
									
									but13.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{				
											JFrame frame7 = new JFrame("Description");
											frame7.setSize(700,300);
											frame7.setResizable(true);
											JPanel pane6 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane6.setLayout(bl);
											pane6.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u=0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("Ballistix Sport LT Red")){
													u=i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/6.jpg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											label.setIcon(image);
											pane6.add(txt, BorderLayout.LINE_START); 
											pane6.add(but42, BorderLayout.PAGE_END); 
											pane6.add(label,BorderLayout.LINE_END);
											frame7.add(pane6);
											frame7.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
								}          
							});	  

							but6.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									border = new TitledBorder("Graphics Cards");
									border.setTitleJustification(TitledBorder.CENTER);										
									JPanel paneButton6 = new JPanel();
									paneButton6.setLayout(new GridLayout(2, 1, 2, 2));
									but14 = new JButton("GT 730");
									paneButton6.add(but14);
									but15 = new JButton("R7240-2GD3-L");
									paneButton6.add(but15);
									products_panel.setBorder(border);
									products_panel.add(paneButton6, "panebutton6");	
									cardLayout.next(products_panel);
									
									but14.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{	
											JFrame frame8 = new JFrame("Description");
											frame8.setSize(860,500);
											frame8.setResizable(true);
											JPanel pane7 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane7.setLayout(bl);
											pane7.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u=0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("GT 730")){
													u=i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/7.png");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											label.setIcon(image);
											pane7.add(txt, BorderLayout.LINE_START); 
											pane7.add(but42, BorderLayout.PAGE_END); 
											pane7.add(label,BorderLayout.LINE_END);
											frame8.add(pane7);
											frame8.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
									
									but15.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{				
											JFrame frame9 = new JFrame("Description");
											frame9.setSize(840,500);
											frame9.setResizable(true);
											JPanel pane8 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane8.setLayout(bl);
											pane8.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u=0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("R7240-2GD3-L")){
													u=i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/8.jpg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											label.setIcon(image);
											pane8.add(txt, BorderLayout.LINE_START); 
											pane8.add(but42, BorderLayout.PAGE_END); 
											pane8.add(label,BorderLayout.LINE_END);
											frame9.add(pane8);
											frame9.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
								}          
							});	 

							but7.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									border = new TitledBorder("Hard Disks");
									border.setTitleJustification(TitledBorder.CENTER);										
									JPanel paneButton7 = new JPanel();
									paneButton7.setLayout(new GridLayout(2, 1, 2, 2));
									but16 = new JButton("WD10EZEX");
									paneButton7.add(but16);
									but17 = new JButton("MZ-75E250B");
									paneButton7.add(but17);
									products_panel.setBorder(border);
									products_panel.add(paneButton7, "panebutton7");	
									cardLayout.next(products_panel);
									
									but16.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{	
											JFrame frame10 = new JFrame("Description");
											frame10.setSize(700,500);
											frame10.setResizable(true);
											JPanel pane9 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane9.setLayout(bl);
											pane9.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u=0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("WD10EZEX")){
													u=i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/9.jpg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											label.setIcon(image);
											pane9.add(txt, BorderLayout.LINE_START); 
											pane9.add(but42, BorderLayout.PAGE_END); 
											pane9.add(label,BorderLayout.LINE_END);
											frame10.add(pane9);
											frame10.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
									
									but17.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{				
											JFrame frame11 = new JFrame("Description");
											frame11.setSize(840,500);
											frame11.setResizable(true);
											JPanel pane10 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane10.setLayout(bl);
											pane10.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u=0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("MZ-75E250B")){
													u=i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/10.jpg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											label.setIcon(image);
											pane10.add(txt, BorderLayout.LINE_START); 
											pane10.add(but42, BorderLayout.PAGE_END); 
											pane10.add(label,BorderLayout.LINE_END);
											frame11.add(pane10);
											frame11.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
								}          
							});
						}
					});	
					
					//Peripherals Products.
					but2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) 
						{			
							border = new TitledBorder("Available Peripherals Products");
							border.setTitleJustification(TitledBorder.CENTER);
							JPanel paneButton8 = new JPanel();
							paneButton8.setLayout(new GridLayout(4, 1, 2, 2));
							but18 = new JButton("Monitor");
							paneButton8.add(but18);
							but19 = new JButton("Keyboard");
							paneButton8.add(but19);
							but20 = new JButton("Mouse");
							paneButton8.add(but20);
							but21 = new JButton("Printer");
							paneButton8.add(but21);
							products_panel.setBorder(border);
							products_panel.add(paneButton8, "panebutton8");	
							cardLayout.next(products_panel);
							
							but18.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									border = new TitledBorder("Monitors");
									border.setTitleJustification(TitledBorder.CENTER);								
									JPanel paneButton9 = new JPanel();
									paneButton9.setLayout(new GridLayout(2, 1, 2, 2));
									but22 = new JButton("223V5LHSB");
									paneButton9.add(but22);						
									but23 = new JButton("24GM79G-B");
									paneButton9.add(but23);
									products_panel.setBorder(border);
									products_panel.add(paneButton9, "panebutton9");	
									cardLayout.next(products_panel);
									
									but22.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{	
											JFrame frame12 = new JFrame("Description");
											frame12.setSize(800,500);
											frame12.setResizable(true);
											JPanel pane11 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane11.setLayout(bl);
											pane11.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u=0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("223V5LHSB")){
													u=i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/11.jpg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											label.setIcon(image);
											pane11.add(txt, BorderLayout.LINE_START); 
											pane11.add(but42, BorderLayout.PAGE_END); 
											pane11.add(label,BorderLayout.LINE_END);
											frame12.add(pane11);
											frame12.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
									
									but23.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{				
											JFrame frame13 = new JFrame("Description");
											frame13.setSize(700,400);
											frame13.setResizable(true);
											JPanel pane12 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane12.setLayout(bl);
											pane12.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u=0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("24GM79G-B")){
													u=i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/12.jpg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											label.setIcon(image);
											pane12.add(txt,BorderLayout.LINE_START); 
											pane12.add(but42, BorderLayout.PAGE_END); 
											pane12.add(label,BorderLayout.LINE_END);
											frame13.add(pane12);
											frame13.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
								}          
							});	
							
							but19.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									border = new TitledBorder("Keyboards");
									border.setTitleJustification(TitledBorder.CENTER);								
									JPanel paneButton10 = new JPanel();
									paneButton10.setLayout(new GridLayout(2, 1, 2, 2));
									but24 = new JButton("MK120");
									paneButton10.add(but24);						
									but25 = new JButton("7N9-00016");
									paneButton10.add(but25);
									products_panel.setBorder(border);
									products_panel.add(paneButton10, "panebutton9");	
									cardLayout.next(products_panel);
									
									but24.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{	
											JFrame frame14 = new JFrame("Description");
											frame14.setSize(800,300);
											frame14.setResizable(true);
											JPanel pane13 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane13.setLayout(bl);
											pane13.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u=0;
											for (int i=0;i < myproducts.Products.size();i++){
												u=i;
												if (myproducts.Products.get(i).getProductName().equals("MK120")){
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/13.jpg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											label.setIcon(image);
											pane13.add(txt,BorderLayout.LINE_START);
											pane13.add(but42, BorderLayout.PAGE_END); 											
											pane13.add(label,BorderLayout.LINE_END);
											frame14.add(pane13);
											frame14.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
									
									but25.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{				
											JFrame frame15 = new JFrame("Description");
											frame15.setSize(700,300);
											frame15.setResizable(true);
											JPanel pane14 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane14.setLayout(bl);
											pane14.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u =0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("7N9-00016")){
													u = i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/14.png");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											label.setIcon(image);
											pane14.add(txt,BorderLayout.LINE_START); 
											pane14.add(but42, BorderLayout.PAGE_END); 
											pane14.add(label,BorderLayout.LINE_END);
											frame15.add(pane14);
											frame15.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
								}          
							});	
							
							but20.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									border = new TitledBorder("Mice");
									border.setTitleJustification(TitledBorder.CENTER);								
									JPanel paneButton11 = new JPanel();
									paneButton11.setLayout(new GridLayout(2, 1, 2, 2));
									but26 = new JButton("Kiro");
									paneButton11.add(but26);						
									but27 = new JButton("2000 Twilight");
									paneButton11.add(but27);
									products_panel.setBorder(border);
									products_panel.add(paneButton11, "panebutton9");	
									cardLayout.next(products_panel);
									
									but26.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{	
											JFrame frame16 = new JFrame("Description");
											frame16.setSize(700,550);
											frame16.setResizable(true);
											JPanel pane15 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane15.setLayout(bl);
											pane15.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u=0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("Kiro")){
													u = i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/15.jpg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											label.setIcon(image);
											pane15.add(txt,BorderLayout.LINE_START);
											pane15.add(but42, BorderLayout.PAGE_END); 											
											pane15.add(label,BorderLayout.LINE_END);
											frame16.add(pane15);
											frame16.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
									
									but27.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{				
											JFrame frame17 = new JFrame("Description");
											frame17.setSize(700,500);
											frame17.setResizable(true);
											JPanel pane16 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane16.setLayout(bl);
											pane16.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u=0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("2000 Twilight")){
													u = i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/16.png");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											label.setIcon(image);
											pane16.add(txt,BorderLayout.LINE_START); 
											pane16.add(but42, BorderLayout.PAGE_END); 
											pane16.add(label,BorderLayout.LINE_END);
											frame17.add(pane16);
											frame17.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
								}          
							});	
							
							but21.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									border = new TitledBorder("Mice");
									border.setTitleJustification(TitledBorder.CENTER);								
									JPanel paneButton12 = new JPanel();
									paneButton12.setLayout(new GridLayout(2, 1, 2, 2));
									but28 = new JButton("SL-M2026");
									paneButton12.add(but28);						
									but29 = new JButton("L310");
									paneButton12.add(but29);
									products_panel.setBorder(border);
									products_panel.add(paneButton12, "panebutton9");	
									cardLayout.next(products_panel);
									
									but28.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{	
											JFrame frame18 = new JFrame("Description");
											frame18.setSize(800,500);
											frame18.setResizable(true);
											JPanel pane17 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane17.setLayout(bl);
											pane17.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u =0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("SL-M2026")){
													u = i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/17.jpeg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											
											label.setIcon(image);
											pane17.add(txt,BorderLayout.LINE_START); 
											pane17.add(but42, BorderLayout.PAGE_END); 
											pane17.add(label,BorderLayout.LINE_END);
											frame18.add(pane17);
											frame18.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
									
									but29.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{				
											JFrame frame19 = new JFrame("Description");
											frame19.setSize(900,500);
											frame19.setResizable(true);
											JPanel pane18 = new JPanel();
											BorderLayout bl = new BorderLayout();
											pane18.setLayout(bl);
											pane18.setBackground(Color.WHITE); 
											but42 = new JButton("Buy it now!");
											but42.setPreferredSize(new Dimension(100, 100));
											label = new JLabel();									
											JTextArea txt = new JTextArea();
											int u = 0;
											for (int i=0;i < myproducts.Products.size();i++){
												if (myproducts.Products.get(i).getProductName().equals("L310")){
													u = i;
													txt.setText(myproducts.Products.get(i).toString()); 
													txt.setFont(new Font("Serif",Font.PLAIN,18));
													myproducts.Products.get(i).setImagePath("Images/18.jpg");
													image = new ImageIcon(myproducts.Products.get(i).getImagePath());
												}
											}
											String s = myproducts.Products.get(u).getProductName();
											
											if (myproducts.getAvailability(u)!=0) {
												but42 = new JButton("Buy it now!");
												but42.setPreferredSize(new Dimension(100, 100));
											}
											
											label.setIcon(image);
											pane18.add(txt,BorderLayout.LINE_START); 
											pane18.add(but42, BorderLayout.PAGE_END); 
											pane18.add(label,BorderLayout.LINE_END);
											frame19.add(pane18);
											frame19.setVisible(true);
											pack();	
											
											but42.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) 
													{
														MyDialogPopup dialog = new MyDialogPopup();
														dialog.setTitle("Write your full name");
														dialog.setModal(true);
														dialog.setVisible(true);
														Sale s1 = new Sale();
														s1.setCustomerName(dialog.sName);
														dialog = new MyDialogPopup();
														dialog.setTitle("Write your phone number");
														dialog.setModal(true);
														dialog.setVisible(true);
														s1.setCustomerPhone(dialog.sName);
														s1.setSaleDate(today); 
														s1.setSoldPro(s);
													}
											});	
										}          
									});		
								}          
							});	
						}          
					});	
				
				//Tab Orders.
				} else if (selectedFile.getName().equals("OrdersList.txt")) {
					String s;
					border = new TitledBorder("Orders");
					border.setTitleJustification(TitledBorder.CENTER);
					myproducts.loadFile(selectedFile.getName()); 
					myorders.loadFile(selectedFile.getName(),myproducts); 
					JPanel paneButton = new JPanel();
					paneButton.setLayout(new GridLayout(6, 2, 2, 2));
					for (int i = 0; i < myorders.Orders.size()/2; i++){
						orders_code.add(myorders.Orders.get(i).getOrderCode());
						s = Integer.toString(orders_code.get(i));
						but30 = new JButton("Order " + s);
						orders_buttons.add(but30);						
						paneButton.add(but30);
					}	
					orders_panel.setBorder(border);
					orders_panel.add(paneButton);
					
					for (int i = 0; i < orders_buttons.size(); i++){
						int num = i;
						int co = orders_code.get(i);
						orders_buttons.get(i).addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									JFrame frame = new JFrame("Order " + Integer.toString(co));
									frame.setSize(400,400);
									frame.setResizable(true);
									JPanel pane = new JPanel();
									BorderLayout bl = new BorderLayout();
									pane.setLayout(bl);
									pane.setBackground(Color.WHITE); 								
									JTextArea txt = new JTextArea();
									txt.setText(myorders.lookUpOrders(co)); 
									txt.setFont(new Font("Serif",Font.PLAIN,18));
									but31 = new JButton("Complete the order!");
									but31.setPreferredSize(new Dimension(100, 100));
									pane.add(txt,BorderLayout.LINE_START);
									pane.add(but31,BorderLayout.PAGE_END);									
									frame.add(pane);
									frame.setVisible(true);
									pack();	
									
									but31.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{		
											myorders.changeOrderStatus(co,2); //We change the status of the order to "Executed".
											Sale s = new Sale();
											s.setCustomerName(myorders.getOrder(co).getCustomerName());
											s.setCustomerPhone(myorders.getOrder(co).getCustomerPhone());
											s.setSaleDate(myorders.getOrder(co).getArrivalDate());
											s.setSoldPro(myorders.getOrder(co).getOrderedPro());
											s.setFinalPrice(myorders.getOrder(co).getFinalPrice());
											mysales.addSale(s); //We add the old order as a new sale since it is executed.	
											myorders.removeOrder(co); //We remove the old order since it is updated.
											but31.setVisible(false);
											orders_buttons.get(num).setVisible(false);    
										}											
									});	
								}          
						});	
					}		
					
				//Tab Sales.						
				} else if (selectedFile.getName().equals("SalesList.txt")) {
					String s;
					border = new TitledBorder("Sales");
					border.setTitleJustification(TitledBorder.CENTER);
					myproducts.loadFile(selectedFile.getName()); 
					mysales.loadFile(selectedFile.getName(),myproducts);  
					JPanel paneButton = new JPanel();
					paneButton.setLayout(new GridLayout(5, 4, 2, 2));
					for (int i = 0; i < mysales.Sales.size()/2; i++){
						sales_code.add(mysales.Sales.get(i).getSaleCode());
						s = Integer.toString(sales_code.get(i));
						but33 = new JButton("Sale " + s);
						sales_buttons.add(but33);						
						paneButton.add(but33);
					}	
					sales_panel.setBorder(border);
					sales_panel.add(paneButton);		

					for (int i = 0; i < sales_buttons.size(); i++){
						int co = sales_code.get(i);
						sales_buttons.get(i).addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									JFrame frame = new JFrame("Sale " + Integer.toString(co));
									frame.setSize(400,400);
									frame.setResizable(true);
									JPanel pane = new JPanel();
									BorderLayout bl = new BorderLayout();
									pane.setLayout(bl);
									pane.setBackground(Color.WHITE); 								
									JTextArea txt = new JTextArea();
									txt.setText(mysales.lookUpSales(co)); 
									txt.setFont(new Font("Serif",Font.PLAIN,18));
									pane.add(txt,BorderLayout.LINE_START); 
									frame.add(pane);
									frame.setVisible(true);
									pack();	
								}          
						});	
					}						
				}
			}
		} else if (e.getSource() == j3) {
			JFrame frame20 = new JFrame("Options");
			frame20.setSize(600,600);
			frame20.setResizable(true);
			JPanel paneButton = new JPanel();
			paneButton.setLayout(new GridLayout(2, 1, 1, 1));
			but34 = new JButton("Orders by Product Type");
			paneButton.add(but34);
			but35 = new JButton("Orders by Product Name");
			paneButton.add(but35);
			frame20.add(paneButton);
			frame20.setVisible(true);
			pack();		

				but34.addActionListener(new ActionListener() {	
					public void actionPerformed(ActionEvent e) 
					{		
						ArrayList <String> s = new ArrayList<>();
						frame20.getContentPane().removeAll();
						JPanel paneButton3 = new JPanel();
						paneButton3.setLayout(new GridLayout(9, 1, 1, 1));
						int h = 0;
						for (int k = 0; k < myproducts.Products.size();k++){
							s.add(myproducts.getType(k));
							but38 = new JButton(s.get(k-h));
							products_buttons.add(but38);
							paneButton3.add(but38);
							k++; h++;
						}					
						frame20.setTitle("Orders by Product Type");
						frame20.add(paneButton3);
						frame20.setVisible(true);
						pack();		
						
						for (int i = 0; i < products_buttons.size(); i++){
						String type = s.get(i);
						products_buttons.get(i).addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									JFrame frame22 = new JFrame(type);
									frame22.setSize(1000,270);
									frame22.setResizable(true);
									JPanel pane = new JPanel();
									pane.setLayout(new GridLayout(1, 3, 0, 0));							
									ArrayList <String> text = new ArrayList<String>();
									for (int j=0; j < myorders.Orders.size();j++){
										if (myorders.getTypeClean(j).equals(type)){
											text.add(myorders.lookUpOrders(myorders.Orders.get(j).getOrderCode())); 
										}
									}
									JTextArea txt;
									for (int f=0; f < text.size();f++){
										txt = new JTextArea();
										txt.setText(text.get(f)); 
										txt.setFont(new Font("Serif",Font.PLAIN,18));
										pane.add(txt); 
									}
									frame22.add(pane);
									frame22.setVisible(true);
									pack();	
								}          
						});	
					}	
					}          
				});		

				but35.addActionListener(new ActionListener() {	
					public void actionPerformed(ActionEvent e) 
					{		
						ArrayList<String> name = new ArrayList<>();
						frame20.getContentPane().removeAll();
						JPanel paneButton4 = new JPanel();
						paneButton4.setLayout(new GridLayout(9, 2, 1, 1));
						for (int t = 0; t < myproducts.Products.size(); t++){
							name.add(myproducts.Products.get(t).getProductName());
							but39 = new JButton(name.get(t));
							products_buttons2.add(but39);
							paneButton4.add(but39);
						}					
						frame20.setTitle("Orders by Product Name");
						frame20.add(paneButton4);
						frame20.setVisible(true);
						pack();		
						
						for (int o = 0; o < products_buttons2.size(); o++){
						String names = name.get(o);
						products_buttons2.get(o).addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									JFrame frame29 = new JFrame(names);
									frame29.setSize(1000,1000);
									frame29.setResizable(true);
									JPanel pane25 = new JPanel();
									pane25.setLayout(new GridLayout(1,3,0,0));	
									ArrayList <String> text = new ArrayList<String>();
									for (int p=0; p < myorders.Orders.size(); p++){
										if (myorders.Orders.get(p).getOrderedPro().equals(names)){
											text.add(myorders.lookUpOrders(myorders.Orders.get(p).getOrderCode())); 
										}
									}
									JTextArea txt;	
									for (int l=0; l < text.size(); l++){		
											txt = new JTextArea();
											txt.setText(text.get(l));
											txt.setFont(new Font("Serif",Font.PLAIN,18));
											pane25.add(txt);
									}
									frame29.add(pane25);
									frame29.setVisible(true);
									pack();	
								}          
						});	
					}   
				}					
			});	
			
		} else if (e.getSource() == j4) {
			JFrame frame30 = new JFrame("Options");
			frame30.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame30.setSize(600,600);
			frame30.setResizable(true);
			JPanel paneButton62 = new JPanel();
			paneButton62.setLayout(new GridLayout(2, 1, 1, 1));
			but36 = new JButton("Sales by Product Type");
			paneButton62.add(but36);
			but37 = new JButton("Sales by Product Name");
			paneButton62.add(but37);
			frame30.add(paneButton62);
			frame30.setVisible(true);
			pack();		
	
				but36.addActionListener(new ActionListener() {	
					public void actionPerformed(ActionEvent e) 
					{		
						ArrayList <String> s = new ArrayList<>();
						frame30.getContentPane().removeAll();
						frame30.getContentPane().removeAll();
						JPanel paneButton73 = new JPanel();
						paneButton73.setLayout(new GridLayout(9, 1, 1, 1));
						int h=0;
						for (int k=0; k < myproducts.Products.size(); k++){
							s.add(myproducts.getType(k));
							but40 = new JButton(s.get(k-h));
							products_buttons3.add(but40);
							paneButton73.add(but40);
							k++; h++;
						}					
						frame30.setTitle("Sales by Product Type");
						frame30.add(paneButton73);
						frame30.setVisible(true);
						pack();	
						
						for (int i = 0; i < products_buttons3.size(); i++){
						String type = s.get(i);
						products_buttons3.get(i).addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									JFrame frame82 = new JFrame(type);
									frame82.setSize(1000,270);
									frame82.setResizable(true);
									JPanel pane = new JPanel();
									pane.setLayout(new GridLayout(1, 3, 0, 0));							
									ArrayList <String> text = new ArrayList<String>();
									for (int j=0; j < mysales.Sales.size();j++){
										if (mysales.getTypeClean(j).equals(type)){
											text.add(mysales.lookUpSales(mysales.Sales.get(j).getSaleCode())); 
										}
									}
									JTextArea txt;
									for (int f=0; f < text.size();f++){
										txt = new JTextArea();
										txt.setText(text.get(f)); 
										txt.setFont(new Font("Serif",Font.PLAIN,18));
										pane.add(txt); 
									}
									frame82.add(pane);
									frame82.setVisible(true);
									pack();	
								}          
							});	
						}							
					}          
				});		

				but37.addActionListener(new ActionListener() {	
					public void actionPerformed(ActionEvent e) 
					{		
						ArrayList<String> name = new ArrayList<>();
						frame30.getContentPane().removeAll();
						frame30.getContentPane().removeAll();
						JPanel paneButton43 = new JPanel();
						paneButton43.setLayout(new GridLayout(9, 2, 1, 1));
						for (int t = 0; t < myproducts.Products.size(); t++){
							name.add(myproducts.Products.get(t).getProductName());
							but41 = new JButton(name.get(t));
							products_buttons4.add(but41);
							paneButton43.add(but41);
						}					
						frame30.setTitle("Sales by Product Name");
						frame30.add(paneButton43);
						frame30.setVisible(true);
						pack();		
						
						for (int o = 0; o < products_buttons4.size(); o++){
						String names = name.get(o);
						products_buttons4.get(o).addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{		
									JFrame frame63 = new JFrame(names);
									frame63.setSize(1000,1000);
									frame63.setResizable(true);
									JPanel pane251 = new JPanel();
									pane251.setLayout(new GridLayout(1,3,0,0));	
									ArrayList <String> text = new ArrayList<String>();
									for (int p=0; p < mysales.Sales.size(); p++){
										if (mysales.Sales.get(p).getSoldPro().equals(names)){
											text.add(mysales.lookUpSales(mysales.Sales.get(p).getSaleCode())); 
										}
									}
									JTextArea txt;	
									for (int l=0; l < text.size(); l++){		
											txt = new JTextArea();
											txt.setText(text.get(l));
											txt.setFont(new Font("Serif",Font.PLAIN,18));
											pane251.add(txt);
									}
									frame63.add(pane251);
									frame63.setVisible(true);
									pack();	
								}          
							});	
						}  
					}						
				});					
			}		
	}//actionPerformed

	public void mouseExited(MouseEvent event){}
	public void mouseEntered(MouseEvent event){}
	public void mouseReleased(MouseEvent event){}
	public void mousePressed(MouseEvent event){}
	
} //mainApp