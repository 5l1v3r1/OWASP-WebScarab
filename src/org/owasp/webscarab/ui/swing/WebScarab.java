/*
 * WebScarab.java
 *
 * Created on July 13, 2003, 7:11 PM
 */

package org.owasp.webscarab.ui.swing;

import org.owasp.webscarab.backend.FileSystemStore;

import org.owasp.webscarab.model.StoreException;

import org.owasp.webscarab.plugin.Preferences;

import org.owasp.webscarab.plugin.proxy.Proxy;
import org.owasp.webscarab.plugin.proxy.module.ManualEdit;
import org.owasp.webscarab.plugin.proxy.module.CookieTracker;
import org.owasp.webscarab.plugin.proxy.module.RevealHidden;
import org.owasp.webscarab.plugin.proxy.module.BrowserCache;

import org.owasp.webscarab.plugin.spider.Spider;
import org.owasp.webscarab.plugin.manualrequest.ManualRequest;

import org.owasp.webscarab.ui.Framework;
import org.owasp.webscarab.ui.swing.SwingPlugin;

import org.owasp.webscarab.ui.swing.proxy.ProxyPanel;
import org.owasp.webscarab.ui.swing.proxy.ManualEditPanel;
import org.owasp.webscarab.ui.swing.proxy.MiscPanel;

import org.owasp.webscarab.ui.swing.spider.SpiderPanel;
import org.owasp.webscarab.ui.swing.manualrequest.ManualRequestPanel;

import java.util.Properties;
import java.util.ArrayList;

import java.io.File;
import java.io.PrintStream;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author  rdawes
 */
public class WebScarab extends javax.swing.JFrame {
    
    private Framework _framework;
    private ArrayList _plugins;
    
    private File _defaultDir = null;
    private Properties _prop = null;
    
    /** Creates new form WebScarab */
    public WebScarab(String[] args) {
        initComponents();
        
        // capture STDOUT and STDERR to a TextArea
        System.setOut(redirectOutput(stdoutTextArea, System.out));
        System.setErr(redirectOutput(stderrTextArea, System.err));
        
        // create the framework
        _framework = new Framework();
        
        // load the properties
        _prop = Preferences.getPreferences();
        
        // load the conversation log GUI plugin
        addPlugin(new ConversationLog(_framework));
        // load the TreeView GUI plugin
        addPlugin(new URLTreePanel(_framework));
        
        // create the plugins, and their GUI's
        
        // Proxy plugin
        Proxy proxy = new Proxy(_framework);
        _framework.addPlugin(proxy);
        
        // load the proxy modules
        ManualEdit me = new ManualEdit();
        proxy.addPlugin(me);
        
        RevealHidden rh = new RevealHidden();
        proxy.addPlugin(rh);
        
        BrowserCache bc = new BrowserCache();
        proxy.addPlugin(bc);
        
        CookieTracker ct = new CookieTracker(proxy.getCookieJar());
        proxy.addPlugin(ct);
        
        // create the proxy GUI panels
        ProxyPanel proxyPanel = new ProxyPanel(proxy);
        proxyPanel.addPlugin(new ManualEditPanel(me));
        proxyPanel.addPlugin(new MiscPanel(rh, bc, ct));
        addPlugin(proxyPanel);
        
        // Spider plugin
        Spider spider = new Spider(_framework);
        _framework.addPlugin(spider);
        addPlugin(new SpiderPanel(spider));
        
        // ManualRequest Plugin
        ManualRequest manualrequest = new ManualRequest(_framework);
        _framework.addPlugin(manualrequest);
        addPlugin(new ManualRequestPanel(manualrequest));
        
        if (args != null && args.length ==1) {
            try {
                if (FileSystemStore.isExistingSession(args[0])) {
                    FileSystemStore store = new FileSystemStore(args[0]);
                    _framework.setSessionStore(store);
                } else {
                    System.err.println("No session found in " + args[0]);
                }
            } catch (StoreException se) {
                // pop up an alert dialog box or something
                System.err.println("Error loading session : " + se);
            }
        } else {
            // This could/should be done as a pop-up alert, or a File/Open or File/New dialog?
            System.out.println("Data will not be saved unless you create or open a session");
        }
    }
    
    
    public void addPlugin(SwingPlugin plugin) {
        if (_plugins == null) {
            _plugins = new ArrayList();
        }
        _plugins.add(plugin);
        mainTabbedPane.add(plugin.getPanel(), plugin.getPluginName());
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        mainSplitPane = new javax.swing.JSplitPane();
        mainTabbedPane = new javax.swing.JTabbedPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        stdoutTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        stderrTextArea = new javax.swing.JTextArea();
        mainMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        proxyMenuItem = new javax.swing.JMenuItem();
        optionsMenuItem = new javax.swing.JMenuItem();
        saveConfigMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setTitle("WebScarab");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        mainSplitPane.setBorder(null);
        mainSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        mainSplitPane.setResizeWeight(0.5);
        mainSplitPane.setContinuousLayout(true);
        mainSplitPane.setAutoscrolls(true);
        mainTabbedPane.setMinimumSize(new java.awt.Dimension(300, 300));
        mainTabbedPane.setPreferredSize(new java.awt.Dimension(400, 400));
        mainSplitPane.setLeftComponent(mainTabbedPane);

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(146, 40));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(127, 60));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(22, 40));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(3, 40));
        stdoutTextArea.setBackground(new java.awt.Color(204, 204, 204));
        stdoutTextArea.setEditable(false);
        jScrollPane1.setViewportView(stdoutTextArea);

        jTabbedPane1.addTab("stdout", jScrollPane1);

        jScrollPane2.setMinimumSize(new java.awt.Dimension(22, 40));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(3, 40));
        stderrTextArea.setBackground(new java.awt.Color(204, 204, 204));
        stderrTextArea.setEditable(false);
        jScrollPane2.setViewportView(stderrTextArea);

        jTabbedPane1.addTab("stderr", jScrollPane2);

        mainSplitPane.setRightComponent(jTabbedPane1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(mainSplitPane, gridBagConstraints);

        fileMenu.setMnemonic('F');
        fileMenu.setText("File");
        newMenuItem.setMnemonic('N');
        newMenuItem.setText("New");
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(newMenuItem);

        openMenuItem.setMnemonic('O');
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(openMenuItem);

        exitMenuItem.setMnemonic('X');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(exitMenuItem);

        mainMenuBar.add(fileMenu);

        toolsMenu.setMnemonic('T');
        toolsMenu.setText("Tools");
        proxyMenuItem.setText("Proxies");
        proxyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proxyMenuItemActionPerformed(evt);
            }
        });

        toolsMenu.add(proxyMenuItem);

        optionsMenuItem.setText("Options");
        toolsMenu.add(optionsMenuItem);

        saveConfigMenuItem.setText("Save Configuration");
        saveConfigMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveConfigMenuItemActionPerformed(evt);
            }
        });

        toolsMenu.add(saveConfigMenuItem);

        mainMenuBar.add(toolsMenu);

        helpMenu.setMnemonic('H');
        helpMenu.setText("Help");
        aboutMenuItem.setMnemonic('A');
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });

        helpMenu.add(aboutMenuItem);

        mainMenuBar.add(helpMenu);

        setJMenuBar(mainMenuBar);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
    }//GEN-END:initComponents
    
    private void saveConfigMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveConfigMenuItemActionPerformed
        try {
            Preferences.savePreferences();
        } catch (Exception e) {
            System.out.println("Error writing preferences : " + e);
        }
    }//GEN-LAST:event_saveConfigMenuItemActionPerformed
    
    private void proxyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proxyMenuItemActionPerformed
        new ProxyConfig(this, true, _prop).show();
        System.out.println("ProxyConfig has returned");
        _framework.setProxies(_prop);
    }//GEN-LAST:event_proxyMenuItemActionPerformed
    
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        saveSessionData();
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed
    
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        saveSessionData();
        JFileChooser jfc = new JFileChooser(_defaultDir);
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setDialogTitle("Choose a directory that contains a previous session");
        int returnVal = jfc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            String dir = file.toString() + System.getProperty("file.separator");
            try {
                if (FileSystemStore.isExistingSession(dir)) {
                    FileSystemStore store = new FileSystemStore(dir);
                    _framework.setSessionStore(store);
                } else {
                    System.err.println("No session found in " + dir);
                }
            } catch (StoreException se) {
                // pop up an alert dialog box or something
                System.err.println("Error loading session : " + se);
            }
        }
        _defaultDir = jfc.getCurrentDirectory();
    }//GEN-LAST:event_openMenuItemActionPerformed
    
    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        saveSessionData();
        JFileChooser jfc = new JFileChooser(_defaultDir);
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setDialogTitle("Select a directory to write the session into");
        int returnVal = jfc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            String dir = file.toString() + System.getProperty("file.separator");
            try {
                if (FileSystemStore.isExistingSession(dir)) {
                    System.err.println(dir + " is an existing session!");
                } else {
                    FileSystemStore store = new FileSystemStore(dir);
                    store.init();
                    _framework.setSessionStore(store);
                }
            } catch (StoreException se) {
                // pop up an alert dialog box or something
                System.err.println("Error loading session : " + se);
            }
        }
        _defaultDir = jfc.getCurrentDirectory();
    }//GEN-LAST:event_newMenuItemActionPerformed
    
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        // FIXME
        System.out.println("Help/About not implemented yet!");
        System.out.println("OWASP WebScarab - part of the Open Web Application Security Project");
        System.out.println("See http://www.owasp.org/");
        System.out.println("Coders : Rogan Dawes (rdawes at telkomsa.net / rdawes at deloitte.co.za)");
        System.out.println("         Ingo Struck (ingo at ingostruck.de)");
    }//GEN-LAST:event_aboutMenuItemActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        saveSessionData();
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    private void saveSessionData() {
        try {
            _framework.saveSessionData();
        } catch (StoreException se) {
            // pop up an alert dialog box or something
            System.err.println("Error saving session : " + se);
        }
    }
    
    private void savePreferences() {
        try {
            Preferences.savePreferences();
        } catch (Exception e) {
            System.err.println("Could not write to prefs file : " + e);
        }
    }
    
    private PrintStream redirectOutput(final JTextArea textarea, final OutputStream old) {
        Document doc = textarea.getDocument();
        // make the text area scroll automatically
        doc.addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                textarea.setCaretPosition(e.getOffset() + e.getLength());
            }
            public void insertUpdate(DocumentEvent e) {
                textarea.setCaretPosition(e.getOffset() + e.getLength());
            }
            public void removeUpdate(DocumentEvent e) {
                textarea.setCaretPosition(e.getOffset() + e.getLength());
            }
        });
        OutputStream[] streams = new OutputStream[] { old, new DocumentOutputStream(doc)};
        return new PrintStream(new TeeOutputStream(streams));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new WebScarab(args).show();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JSplitPane mainSplitPane;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem optionsMenuItem;
    private javax.swing.JMenuItem proxyMenuItem;
    private javax.swing.JMenuItem saveConfigMenuItem;
    private javax.swing.JTextArea stderrTextArea;
    private javax.swing.JTextArea stdoutTextArea;
    private javax.swing.JMenu toolsMenu;
    // End of variables declaration//GEN-END:variables
    
}
