package net.oasisgames.datasql.frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.oasisgames.datasql.database.Connection;
import net.oasisgames.datasql.frames.controllers.MainMenuControl;
import net.oasisgames.datasql.frames.controllers.TableCreateControl;
import net.oasisgames.datasql.frames.controllers.TableSelectControl;

/**
 * @author Nick Doxa
 * @apiNote Windows Form created using NetBeans IDE
 */
public class MainMenu extends javax.swing.JFrame implements FrameControl {
    
	private static boolean isOpen;
	
	private static boolean exists;
	
	public static boolean isFrameCreated() {
		return exists;
	}
	
	public static boolean isFrameOpen() {
		return isOpen;
	}
	
	protected static void setOpenStatus(boolean open) {
		isOpen = open;
	}
	
	private static final long serialVersionUID = 1494329436735426491L;
	private final MainMenuControl controller;
	public MainMenu() {
        controller = new MainMenuControl();
    }
	
	@Override
	public void closeWindow() {
    	this.setVisible(false);
    	this.setEnabled(false);
    	setOpenStatus(false);
    }
    
	@Override
    public void openWindow() {
    	MainMenuControl.setFrameToCenterScreen(this);
    	this.setVisible(true);
    	this.setEnabled(true);
    	setOpenStatus(true);
    	errorTextField.setText("");
    }
	
	private FrameControl lastFrame;
	
	@Override
	public FrameControl getPreviousFrame() {
		return lastFrame;
	}

	@Override
	public void setPreviousFrame(FrameControl frame) {
		lastFrame = frame;
	}

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        nextButton1 = new javax.swing.JButton();
        nextButton2 = new javax.swing.JButton();
        hostTextField = new javax.swing.JTextField();
        portTextField = new javax.swing.JTextField();
        dbnameTextField = new javax.swing.JTextField();
        usernameTextField = new javax.swing.JTextField();
        passwordTextField = new javax.swing.JTextField();
        errorTextField = new javax.swing.JTextArea();
        useDefaultLoginCheck = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DataSQL");
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 100, 200));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(500, 340));
        setPreferredSize(new java.awt.Dimension(500, 340));
        setResizable(false);
        setSize(new java.awt.Dimension(500, 340));
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));
        jPanel1.setForeground(new java.awt.Color(0, 0, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 200));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.setBorder(BorderFactory.createBevelBorder(0));

        useDefaultLoginCheck.setSelected(false);
        useDefaultLoginCheck.setText("Use Default Login?");
        useDefaultLoginCheck.setToolTipText("Checked uses default login, unchecked uses input login");
        useDefaultLoginCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useDefaultLoginActionPerformed(evt);
            }
        });
        jPanel1.add(useDefaultLoginCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 160, -1));
        
        //NEXT BUTTON 1 - CREATE
        nextButton1.setText("Create New Table");
        nextButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nextButton1.setName("createButtonMainMenu"); // NOI18N
        nextButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nextButton1MouseClicked(evt);
            }
        });
        jPanel1.add(nextButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 250, 150, 30));
        
        //NEXT BUTTON 2 - SELECT
        nextButton2.setText("Use Existing Table");
        nextButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nextButton2.setName("selectButtonMainMenu"); // NOI18N
        nextButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nextButton2MouseClicked(evt);
            }
        });
        jPanel1.add(nextButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 250, 150, 30));

        hostTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        hostTextField.setText("Host");
        hostTextField.setToolTipText("Host name of database");

        jPanel1.add(hostTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 120, -1));

        portTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        portTextField.setText("Port");
        portTextField.setToolTipText("Port for the host of the database");

        jPanel1.add(portTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 120, -1));

        dbnameTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dbnameTextField.setText("Database Name");
        dbnameTextField.setToolTipText("Name of the Database you wish to access");

        jPanel1.add(dbnameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 120, -1));

        usernameTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameTextField.setText("Username");
        usernameTextField.setToolTipText("Database username for login");

        jPanel1.add(usernameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 120, -1));

        passwordTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordTextField.setText("Password");
        passwordTextField.setToolTipText("Password for database login");

        jPanel1.add(passwordTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 120, -1));
        errorTextField.setText("");
        errorTextField.setEditable(false);
        errorTextField.setFocusable(false);
        errorTextField.setForeground(Color.RED);
        jPanel1.add(errorTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 180, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleDescription("");
        MainMenuControl.setFrameToCenterScreen(this);
        pack();
    }                        

    private void nextButton1MouseClicked(java.awt.event.MouseEvent evt) {                                         
        controller.submitForm(new JTextField[] {this.hostTextField, this.portTextField,
        		this.dbnameTextField, this.usernameTextField, this.passwordTextField},
        		TableCreateControl.Singleton());
    }
    
    private void nextButton2MouseClicked(java.awt.event.MouseEvent evt) {                                         
        controller.submitForm(new JTextField[] {this.hostTextField, this.portTextField,
        		this.dbnameTextField, this.usernameTextField, this.passwordTextField},
        		TableSelectControl.Singleton());
    }
    
    private void useDefaultLoginActionPerformed(ActionEvent evt) {
    	boolean set = useDefaultLoginCheck.isSelected();
    	hostTextField.setEditable(!set);
    	if (set) hostTextField.setText(Connection.default_host);
    	portTextField.setEditable(!set);
    	if (set) portTextField.setText(Connection.default_port);
    	dbnameTextField.setEditable(!set);
    	if (set) dbnameTextField.setText(Connection.default_database);
    	usernameTextField.setEditable(!set);
    	if (set) usernameTextField.setText(Connection.default_username);
    	passwordTextField.setEditable(!set);
    	if (set) passwordTextField.setText(Connection.default_password);
    }
	
	public JTextArea getErrorText() {
		return this.errorTextField;
	}

    public void run() {
    	if (isFrameOpen()) return;
    	if (isFrameCreated()) {
    		openWindow();
    		return;
    	}
        try {
        	UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                initComponents();
                openWindow();
                exists = true;
                controller.restorePreferences(new JTextField[] 
                		{hostTextField, 
                		portTextField,
                		dbnameTextField,
                		usernameTextField,
                		passwordTextField});
            }
        });
    }
                   
    private javax.swing.JTextField dbnameTextField;
    private javax.swing.JTextArea errorTextField;
    private javax.swing.JTextField hostTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JCheckBox useDefaultLoginCheck;
    private javax.swing.JButton nextButton1;
    private javax.swing.JButton nextButton2;
    private javax.swing.JTextField passwordTextField;
    private javax.swing.JTextField portTextField;
    private javax.swing.JTextField usernameTextField;
              
}
