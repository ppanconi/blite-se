/*
 * DesktopView.java
 */

package it.unifi.dsi.blitese.localenv.gui;

import it.unifi.dsi.blitese.localenv.gui.env.EnvComponent;
import it.unifi.dsi.blitese.localenv.gui.log.SetUpLogging;
import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.Task;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import jsyntaxpane.DefaultSyntaxKit;


/**
 * The application's main frame.
 */
public class DesktopView extends FrameView {

    public DesktopView(SingleFrameApplication app) {
        super(app);

        initComponents();
        treeManager = new TreeViewManager(envTree);
        envComponent = new EnvComponent();
        
        treeTabbedPane.add("Engines", envComponent);
        
        //file chooser Init
        fc = new JFileChooser();
        
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clearing status...");
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        
        
        
        SetUpLogging.initializeLogging(reportArea);
        DefaultSyntaxKit.initKit();
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = DesktopApplication.getApplication().getMainFrame();
            aboutBox = new DesktopAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        DesktopApplication.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        newButton = new javax.swing.JButton();
        openButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        buttonsSeparator1 = new javax.swing.JToolBar.Separator();
        buildButton = new javax.swing.JButton();
        runButton = new javax.swing.JButton();
        mainSplitPane = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        filesTabbedPane = new javax.swing.JTabbedPane();
        treeTabbedPane = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        envTree = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        reportArea = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(it.unifi.dsi.blitese.localenv.gui.DesktopApplication.class).getContext().getResourceMap(DesktopView.class);
        jSeparator1.setBackground(resourceMap.getColor("jSeparator1.background")); // NOI18N
        jSeparator1.setName("jSeparator1"); // NOI18N
        jToolBar1.add(jSeparator1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(it.unifi.dsi.blitese.localenv.gui.DesktopApplication.class).getContext().getActionMap(DesktopView.class, this);
        newButton.setAction(actionMap.get("newDef")); // NOI18N
        newButton.setFocusable(false);
        newButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newButton.setName("newButton"); // NOI18N
        newButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(newButton);

        openButton.setAction(actionMap.get("open")); // NOI18N
        openButton.setFocusable(false);
        openButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openButton.setName("openButton"); // NOI18N
        openButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(openButton);

        saveButton.setAction(actionMap.get("saveFile")); // NOI18N
        saveButton.setFocusable(false);
        saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton.setName("saveButton"); // NOI18N
        saveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(saveButton);

        buttonsSeparator1.setBackground(resourceMap.getColor("buttonsSeparator1.background")); // NOI18N
        buttonsSeparator1.setMaximumSize(new java.awt.Dimension(2, 0));
        buttonsSeparator1.setMinimumSize(new java.awt.Dimension(2, 0));
        buttonsSeparator1.setName("buttonsSeparator1"); // NOI18N
        jToolBar1.add(buttonsSeparator1);

        buildButton.setAction(actionMap.get("buildFile")); // NOI18N
        buildButton.setFocusable(false);
        buildButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buildButton.setName("buildButton"); // NOI18N
        buildButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(buildButton);

        runButton.setAction(actionMap.get("runEnv")); // NOI18N
        runButton.setFocusable(false);
        runButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        runButton.setName("runButton"); // NOI18N
        runButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(runButton);

        mainSplitPane.setDividerLocation(320);
        mainSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        mainSplitPane.setName("mainSplitPane"); // NOI18N

        jSplitPane1.setDividerLocation(150);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        filesTabbedPane.setName("filesTabbedPane"); // NOI18N
        jSplitPane1.setRightComponent(filesTabbedPane);

        treeTabbedPane.setName("treeTabbedPane"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        envTree.setName("envTree"); // NOI18N
        jScrollPane1.setViewportView(envTree);

        treeTabbedPane.addTab(resourceMap.getString("jScrollPane1.TabConstraints.tabTitle"), jScrollPane1); // NOI18N

        jSplitPane1.setLeftComponent(treeTabbedPane);

        mainSplitPane.setLeftComponent(jSplitPane1);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        reportArea.setColumns(20);
        reportArea.setRows(5);
        reportArea.setName("reportArea"); // NOI18N
        jScrollPane2.setViewportView(reportArea);

        mainSplitPane.setBottomComponent(jScrollPane2);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
            .addComponent(mainSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 557, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void newDef() {
        
        JScrollPane jScrollPane = new javax.swing.JScrollPane();
        JEditorPane jEdtTest = new javax.swing.JEditorPane();
         jEdtTest.setContentType("text/java");
        jEdtTest.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jEdtTest.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                System.out.println("Oo!");
            }
        });
        jScrollPane.setViewportView(jEdtTest);
        
        
        filesTabbedPane.addTab("new definition...", jScrollPane);
    }

    @Action
    public void open() {

//        FileOpenService fos = null;
//        FileContents fileContents = null;
//
//        try {
//            fos = (FileOpenService)ServiceManager.
//                      lookup("javax.jnlp.FileOpenService"); 
//        } catch (UnavailableServiceException exc) { }
//
//        if (fos != null) {
//            try {
//                fileContents = fos.openFileDialog(null, null); 
//            } catch (Exception exc) {
//                log.append("Open command failed: "
//                           + exc.getLocalizedMessage()
//                           + newline);
//                log.setCaretPosition(log.getDocument().getLength());
//            }
//        }
//
//        if (fileContents != null) {
//            try {
//                //This is where a real application would do something
//                //with the file.
//                log.append("Opened file: " + fileContents.getName()
//                           + "." + newline);
//            } catch (IOException exc) {
//                log.append("Problem opening file: "
//                           + exc.getLocalizedMessage()
//                           + newline);
//            }
//        } else {
//            log.append("User canceled open request." + newline);
//        }
//        log.setCaretPosition(log.getDocument().getLength());
        
        if (fc == null) {
            fc = new JFileChooser();
        }
        
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = fc.showOpenDialog( DesktopApplication.getApplication().getMainFrame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            try {
                filesTabbedPane.addTab(file.getName(), sourcesManager.addSource(file));
            } catch (IOException ex) {
                Logger.getLogger(DesktopView.class.getName()).log(Level.SEVERE, null, ex);
//                reportExection(ex);
            }
            
        }
        
    }

    @Action
    public void saveFile() {
        JComponent targetComponent = (JComponent) filesTabbedPane.getSelectedComponent();
        try {
            
            File file = sourcesManager.saveSource(targetComponent);
            statusMessageLabel.setText("File " + file + " saved");
            
        } catch (IOException ex) {
            Logger.getLogger(DesktopView.class.getName()).log(Level.SEVERE, null, ex);
//            reportExection(ex);
        }
    }

    @Action
    public Task buildFile() {
        return new BuildFileTask(getApplication());
    }

    private class BuildFileTask extends org.jdesktop.application.Task<Object, Void> {
        
        JComponent targetComponent;
        
        BuildFileTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to BuildFileTask fields, here.
            super(app);
            
            targetComponent = (JComponent) filesTabbedPane.getSelectedComponent();
        }
        @Override protected Object doInBackground()  {
            try {
                // Your Task's code here.  This method runs
                // on a background thread, so don't reference
                // the Swing GUI from here.
                return sourcesManager.buildSource(targetComponent);
            } catch (Exception ex) {
                Logger.getLogger(DesktopView.class.getName()).log(Level.SEVERE, null, ex);
//                reportExection(ex);
            }
            return null;
            
        }
        @Override protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
            if (result != null) {
                setMessage("Build Successful");
                BLTDEFCompilationUnit compilationUnit = (BLTDEFCompilationUnit) result;
                treeManager.synchCompilationUnit(compilationUnit);
                
            }
        }
        
    }

    @Action
    public Task runEnv() {
        return new RunEnvTask(getApplication());
    }

    private class RunEnvTask extends org.jdesktop.application.Task<Object, Void> {
        RunEnvTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to RunEnvTask fields, here.
            super(app);
        }
        @Override protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
//            Logger.getLogger(DesktopView.class.getName()).log(Level.INFO, "Deploing Units");
//            for (BLTDEFCompilationUnit unit : sourcesManager.proviedeCompiledUnits()) {
//                try {
//                    environment.addCompilationUnit(unit);
//                } catch (IncompatibleCompUnitException ex) {
//                    Logger.getLogger(DesktopView.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            environment.startAllReadyToRunDefinitions();
            
            return null;  // return your result
        }
        @Override protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buildButton;
    private javax.swing.JToolBar.Separator buttonsSeparator1;
    private javax.swing.JTree envTree;
    private javax.swing.JTabbedPane filesTabbedPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JSplitPane mainSplitPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton newButton;
    private javax.swing.JButton openButton;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JTextArea reportArea;
    private javax.swing.JButton runButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTabbedPane treeTabbedPane;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    
    private JFileChooser fc;
    
    //my Models manager
    private SourcesFacadeManager sourcesManager = new SourcesFacadeManager();
    private TreeViewManager treeManager;
//    private LocalEnvironment environment = new LocalEnvironment();
    private EnvComponent envComponent;
    
//    private void reportExection(Exception ex) {
//        reportArea.append("\n");
//        reportArea.append(ex.getMessage());
//    }
//    
//    private void reportMessage(String message) {
//        reportArea.append("\n");
//        reportArea.append(message);
//    }
    
    
    
}
