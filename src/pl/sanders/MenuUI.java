package pl.sanders;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuUI extends JFrame{
    private JButton playButton;
    private JButton scoreButton;
    private JButton exitButton;
    private JButton infoButton;
    private JPanel panelMenu;
    private JLabel logo;

    public MenuUI() {
        add(panelMenu);
        setTitle("Minesweeper - Menu");
        pack();
        setDefaultCloseOperation(3);
        try {
            Scoreboard scoreboard = new Scoreboard();
        }catch (Exception e) {

            try {
                Scoreboard.saveData();
                Scoreboard scoreboard = new Scoreboard();
            }catch(Exception ex){}
        }

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new GameSettingsUI().setVisible(true);
               dispose();
            }
        });
        scoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ScoreBoardUI().setVisible(true);
            }
        });
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(panelMenu, "Minesweeper by Bartłomiej Kozieł","INFO",3);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


}
