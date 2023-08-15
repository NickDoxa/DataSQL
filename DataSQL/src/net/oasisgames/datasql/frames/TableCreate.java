package net.oasisgames.datasql.frames;

import javax.swing.UIManager;

import net.oasisgames.datasql.database.Connection;
import net.oasisgames.datasql.frames.controllers.MainMenuControl;
import net.oasisgames.datasql.frames.controllers.TableCreateControl;
import net.oasisgames.datasql.frames.controllers.TableSelectControl;

/**
 * @author Nick Doxa
 * @apiNote Windows Form created using NetBeans IDE
 */
public class TableCreate extends javax.swing.JFrame implements FrameControl {

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
	
	private static final long serialVersionUID = 2733770735325381304L;

	private final TableCreateControl controller;
    public TableCreate() {
    	controller = new TableCreateControl();
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
        useDefaultNameCheck = new javax.swing.JCheckBox();
        tableName = new javax.swing.JTextField();
        valueTypeSelection = new javax.swing.JComboBox<>();
        keyValueTitle = new javax.swing.JLabel();
        keyValueDescription = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        valueTypeRawList = new javax.swing.DefaultListModel<String>();
        valueTypeList = new javax.swing.JList<String>(valueTypeRawList);
        jScrollPane2 = new javax.swing.JScrollPane();
        valueNameRawList = new javax.swing.DefaultListModel<String>();
        valueNameList = new javax.swing.JList<String>(valueNameRawList);
        keyValueNameTitle = new javax.swing.JLabel();
        keyValueNameInput = new javax.swing.JTextField();
        keyValueNameDescription = new javax.swing.JLabel();
        valueNameInput = new javax.swing.JTextField();
        keyValueTypeSelection = new javax.swing.JComboBox<>();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DataSQL");
        setMaximumSize(new java.awt.Dimension(500, 340));
        setMinimumSize(new java.awt.Dimension(500, 340));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));
        jPanel1.setToolTipText("");
        jPanel1.setMaximumSize(new java.awt.Dimension(500, 340));
        jPanel1.setMinimumSize(new java.awt.Dimension(500, 340));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        useDefaultNameCheck.setSelected(true);
        useDefaultNameCheck.setText("Use Default Table Name?");
        useDefaultNameCheck.setToolTipText("Checked uses default name, unchecked uses input name");
        useDefaultNameCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useTableCheckActionPerformed(evt);
            }
        });
        jPanel1.add(useDefaultNameCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 160, -1));

        tableName.setText("datasql");
        tableName.setEditable(!useDefaultNameCheck.isSelected());
        jPanel1.add(tableName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 150, -1));

        valueTypeSelection.setMaximumRowCount(5);
        valueTypeSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "String", "Boolean", "Integer", "Float", "Char" }));
        jPanel1.add(valueTypeSelection, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 90, -1));

        keyValueTitle.setBackground(new java.awt.Color(204, 204, 255));
        keyValueTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        keyValueTitle.setText("Main Data Type");
        jPanel1.add(keyValueTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        keyValueDescription.setLabelFor(valueTypeSelection);
        keyValueDescription.setText("<html><body>NOTE: this is the \"key\" to each row.<br>It is how you will output data and input data into the database.</body></html>");
        keyValueDescription.setFocusable(false);
        jPanel1.add(keyValueDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 220, 70));

        valueTypeList.setBackground(new java.awt.Color(255, 255, 204));
        valueTypeList.setVisibleRowCount(10);
        jScrollPane1.setViewportView(valueTypeList);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 90, 240));

        valueNameList.setBackground(new java.awt.Color(204, 255, 204));
        valueNameList.setVisibleRowCount(10);
        jScrollPane2.setViewportView(valueNameList);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 90, 240));

        keyValueNameTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        keyValueNameTitle.setLabelFor(keyValueNameDescription);
        keyValueNameTitle.setText("Main Data Name");
        jPanel1.add(keyValueNameTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 150, 30));

        keyValueNameInput.setText("KEY");
        jPanel1.add(keyValueNameInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 140, -1));

        keyValueNameDescription.setLabelFor(keyValueNameInput);
        keyValueNameDescription.setText("<html><body>NOTE: This is the name of the data type, for instance, if you were getting someones name the data type would be <String> but the variable would be called \"NAME\"</body><html>");
        jPanel1.add(keyValueNameDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 200, 80));

        valueNameInput.setText("ValueName");
        jPanel1.add(valueNameInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 90, -1));

        keyValueTypeSelection.setMaximumRowCount(5);
        keyValueTypeSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "String", "Boolean", "Integer", "Float", "Char" }));
        jPanel1.add(keyValueTypeSelection, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 90, -1));

        addButton.setBackground(new java.awt.Color(51, 255, 0));
        addButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addButton.setText("+");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        jPanel1.add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 40, 40));

        removeButton.setBackground(new java.awt.Color(255, 51, 51));
        removeButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        removeButton.setText("-");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        jPanel1.add(removeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 40, 40));

        nextButton.setBackground(new java.awt.Color(0, 102, 204));
        nextButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nextButton.setForeground(new java.awt.Color(255, 255, 255));
        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        jPanel1.add(nextButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 300, 100, 30));
        
        backButton.setBackground(new java.awt.Color(204, 0, 40));
        backButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        backButton.setForeground(new java.awt.Color(255, 255, 255));
       	backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        jPanel1.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 300, 100, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        

        pack();
    }                      

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        int index = valueNameRawList.getSize()-1;
        if (index < 0) return;
        valueNameRawList.remove(index);
        valueTypeRawList.remove(index);
    }                                            

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	int index = valueTypeSelection.getSelectedIndex();
        String[] valuePair = controller.addNewValue(
        		valueNameInput.getText(), valueTypeSelection.getItemAt(index));
        if (valuePair == null) {
        	Connection.printToConsole("Insufficient value pairs.");
        	return;
        }
        valueNameRawList.addElement(valuePair[0]);
        valueTypeRawList.addElement(valuePair[1]);
    }                                         

    private void useTableCheckActionPerformed(java.awt.event.ActionEvent evt) {
    	if (useDefaultNameCheck.isSelected()) tableName.setText(Connection.getDefaultTable());
    	tableName.setEditable(!useDefaultNameCheck.isSelected());
    }

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	int typeIndex = keyValueTypeSelection.getSelectedIndex();
        String results = controller.submitResults(keyValueTypeSelection.getItemAt(typeIndex), 
        		keyValueNameInput.getText(), 
        		valueTypeList.getModel(), 
        		valueNameList.getModel(),
        		useDefaultNameCheck.isSelected(),
        		tableName.getText(), TableSelectControl.Singleton()) 
        		? "Results submitted successfully" : "Results failed to submit";
        Connection.printToConsole(results);
    }  
    
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	closeWindow();
    	getPreviousFrame().openWindow();
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
            java.util.logging.Logger.getLogger(TableCreate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TableCreate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TableCreate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TableCreate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	initComponents();
            	openWindow();
            	exists = true;
            }
        });
    }
                   
    private javax.swing.JButton addButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel keyValueDescription;
    private javax.swing.JLabel keyValueNameDescription;
    private javax.swing.JTextField keyValueNameInput;
    private javax.swing.JLabel keyValueNameTitle;
    private javax.swing.JLabel keyValueTitle;
    private javax.swing.JComboBox<String> keyValueTypeSelection;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton backButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JTextField tableName;
    private javax.swing.JCheckBox useDefaultNameCheck;
    private javax.swing.JTextField valueNameInput;
    private javax.swing.JList<String> valueNameList;
    public javax.swing.DefaultListModel<String> valueNameRawList;
    private javax.swing.JList<String> valueTypeList;
    protected javax.swing.DefaultListModel<String> valueTypeRawList;
    private javax.swing.JComboBox<String> valueTypeSelection;

}
