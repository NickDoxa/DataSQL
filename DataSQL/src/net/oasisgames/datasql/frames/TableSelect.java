package net.oasisgames.datasql.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;

import net.oasisgames.datasql.frames.controllers.MainMenuControl;
import net.oasisgames.datasql.frames.controllers.SelectActionControl;
import net.oasisgames.datasql.frames.controllers.TableSelectControl;

/**
 * @author Nick Doxa
 * @apiNote Windows Form created using NetBeans IDE
 */
public class TableSelect extends javax.swing.JFrame implements FrameControl {

	private static boolean isOpen;
	
	private static boolean exists;
	
	static {
		isOpen = false;
		exists = false;
	}
	
	public static boolean isFrameCreated() {
		return exists;
	}
	
	public static boolean isFrameOpen() {
		return isOpen;
	}
	
	protected static void setOpenStatus(boolean open) {
		isOpen = open;
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
    	controller.setTableNameList(tableList, MainMenuControl.Singleton());
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
	
	private static final long serialVersionUID = -6754780667901006607L;
	
	private final TableSelectControl controller;
    public TableSelect() {
        controller = new TableSelectControl();
    }
    
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableList = new javax.swing.JList<>();
        nextButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DataSQL");
        setMaximumSize(new java.awt.Dimension(500, 340));
        setMinimumSize(new java.awt.Dimension(500, 340));
        setResizable(false);
        setSize(new java.awt.Dimension(500, 340));
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(540, 300));
        jPanel1.setMinimumSize(new java.awt.Dimension(540, 300));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableList.setToolTipText("List names");
        tableList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(tableList);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 80, 200));

        nextButton.setText("Next");
        nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextButton();
			}
        });
        jPanel1.add(nextButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 290, 80, -1));

        backButton.setText("Back");
        backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backButton();
			}
        });
        jPanel1.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 80, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Select Table");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 180, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 340));

        pack();
    }                       

    private void nextButton() {
    	controller.submitTableAndNext(tableList.getSelectedValue(), 
    			SelectActionControl.Singleton());
    }
    
    private void backButton() {
    	controller.backButton();
    }
    
    @Override
    public void run() {
    	if (isFrameOpen()) return;
    	if (isFrameCreated()) {
    		openWindow();
    		return;
    	}
        try {
        	UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TableSelect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TableSelect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TableSelect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TableSelect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	initComponents();
            	openWindow();
            	exists = true;
            	controller.setTableNameList(tableList, MainMenuControl.Singleton());
            }
        });
    }
                     
    private javax.swing.JButton backButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> tableList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton nextButton;
                
}
