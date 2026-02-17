package faizan.mehdi.notepad;

import javax.swing.*;
import javax.swing.undo.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NotepadFrame extends JFrame {

    private JTextArea textArea;
    private UndoManager undoManager;

    public NotepadFrame() {

        
        setTitle("Faizan Notepad");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

       
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        textArea.setForeground(Color.DARK_GRAY);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

       
        undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(undoManager);

        
        JPanel topPanel = new JPanel();
        
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton btnSave = new JButton("Save");
        JButton btnLoad = new JButton("Load");
        JButton btnUndo = new JButton("Undo");
        JButton btnRedo = new JButton("Redo");
        JButton btnClear = new JButton("Clear");

        topPanel.add(btnSave);
        topPanel.add(btnLoad);
        topPanel.add(btnUndo);
        topPanel.add(btnRedo);
        topPanel.add(btnClear);

        add(topPanel, BorderLayout.NORTH);

        // ----- Button Functions -----
        btnSave.addActionListener(e -> saveFile());
        btnLoad.addActionListener(e -> loadFile());
        btnUndo.addActionListener(e -> {
            if (undoManager.canUndo()) undoManager.undo();
        });
        btnRedo.addActionListener(e -> {
            if (undoManager.canRedo()) undoManager.redo();
        });
        btnClear.addActionListener(e -> textArea.setText(""));

        setVisible(true);
    }

   

  
    private void saveFile() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                fw.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "Saved Successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"Error Saving File!");
            }
        }
    }

    
    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
                textArea.read(br, null);
                JOptionPane.showMessageDialog(this, "Loaded Successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error Loading File!");
            }
        }
    }
}
