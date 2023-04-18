package com.example.gui;

import com.example.filencrypt.ManageSafe;
import com.example.gui.access.AccessFrame;
import com.example.gui.login.LoginFrame;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AppGui {

    public static void main(String[] args) {

        try {
            // set FlatDarkLaf theme
            UIManager.setLookAndFeel(new FlatDarkLaf());
            // for square corners
            UIManager.put( "Button.arc", 0 );
            UIManager.put( "Component.arc", 0 );
            UIManager.put( "CheckBox.arc", 0 );
            UIManager.put( "ProgressBar.arc", 0 );
            //
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // managing my strongbox
        ManageSafe ms = new ManageSafe();
        // data access window hidden until invalid login
        AccessFrame accessFrame = new AccessFrame(ms);
        // immediately visible login window that self-destructs as soon as login successes
        new LoginFrame(ms, accessFrame);
    }
}
