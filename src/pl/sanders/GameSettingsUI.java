package pl.sanders;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSettingsUI extends JFrame{
    private JRadioButton beginnerRadioButton;
    private JRadioButton expertRadioButton;
    private JRadioButton intermediateRadioButton;
    private JButton startGameButton;
    private JButton menuButton;
    private JRadioButton customRadioButton;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField minesField;
    private JPanel difficultyPanel;


    public GameSettingsUI(){
    add(difficultyPanel);
    setTitle("MineSweeper - Settings");
    pack();
    setDefaultCloseOperation(3);
beginnerRadioButton.setSelected(true);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
if(beginnerRadioButton.isSelected()){
    System.out.println("beginner");
    new GameUI(9,9,10).setVisible(true);
    dispose();
    Manager.gameMode=0;
}else if(intermediateRadioButton.isSelected()){
    System.out.println("intermediate");
    new GameUI(16,16,40).setVisible(true);
    dispose();
    Manager.gameMode=1;
}else if(expertRadioButton.isSelected()){
    System.out.println("expert");
    new GameUI(16,30,99).setVisible(true);
    dispose();
    Manager.gameMode=2;
}else if(customRadioButton.isSelected()) {
    try{
        if (Integer.parseInt(widthField.getText()) * Integer.parseInt(heightField.getText()) / 2 < Integer.parseInt(minesField.getText())) {
            JOptionPane.showMessageDialog(difficultyPanel, "Too many bombs");

        } else if (Integer.parseInt(widthField.getText()) >= 8 && Integer.parseInt(widthField.getText()) <= 30
                && Integer.parseInt(heightField.getText()) >= 8 && Integer.parseInt(heightField.getText()) <= 24
                && Integer.parseInt(minesField.getText()) >= 10 && Integer.parseInt(minesField.getText()) <= 668) {
            System.out.println("custom");
            new GameUI(Integer.parseInt(widthField.getText()), Integer.parseInt(heightField.getText()), Integer.parseInt(minesField.getText())).setVisible(true);
            dispose();
            Manager.gameMode=3;
        } else {
            JOptionPane.showMessageDialog(difficultyPanel, "In custom Game " +
                    "\nwidth range 8 - 30 " +
                    "\nheight range 8 - 24" +
                    "\nmines range 10 - 668");

        }
    }
    catch(NumberFormatException ex){
        JOptionPane.showMessageDialog(difficultyPanel, "The fields are empty or contain invalid characters");
    }
}
            }
        });
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuUI menu = new MenuUI();
                menu.setVisible(true);
            }
        });
    }

}
