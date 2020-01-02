package pl.sanders;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

public class GameUI extends Game {

    private JPanel gamePanel;
    private JPanel minefield;
    private JButton resetButton;
    private JPanel infoPanel;
    private JLabel timeLabel;
    private JLabel bombToDetectLabel;
    private JButton menuButton;
    private JButton settingsButton;
    private JPanel buttonsPanel;
    private JButton[][] cells;
    private Timer timer;
    private ActionListener stopwatch;

    public GameUI(int width, int height, int bombs) {
        super(width, height, bombs);
        setTitle("MineSweeper - Settings");
        setDefaultCloseOperation(3);
        gamePanel = new JPanel(new BorderLayout());
        minefield = new JPanel(new GridLayout(width, height, 5, 5));

        cells = new JButton[width][height];

        try {
            gamePanel.add(infoPanel , BorderLayout.PAGE_START);
            gamePanel.add(minefield , BorderLayout.CENTER);
            gamePanel.add(buttonsPanel , BorderLayout.PAGE_END);

            add(gamePanel);
        } catch (NullPointerException e) {
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buildCell(i,j);
            }
        }
        stopwatch = new Clock();
        timer = new Timer(1000,stopwatch);


        bombToDetectLabel.setText("" + manager.bombsToDetect);

        pack();
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
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
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GameSettingsUI settings = new GameSettingsUI();
                settings.setVisible(true);
            }
        });
    }

    private class Clock implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            timeLabel.setText("" + ++manager.seconds);
        }
    }

    public void buildCell(int i,int j){
        try {
            minefield.add(cells[i][j] = new JButton());
            cells[i][j].setPreferredSize(new Dimension(40,40));
            cells[i][j].setFont(new Font("Arial",1,20));
            cells[i][j].setBorder(new EmptyBorder(2,2,2,2));
            colorCell(i,j);
        } catch (NullPointerException e) {}

        cells[i][j].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
              //  System.out.println(i + " " + j);
                if (SwingUtilities.isLeftMouseButton(e) && !manager.board[i][j].isFlagged && manager.gameStatus==Manager.inProgress) {
                    if (manager.board[i][j].value >= 9) {
                        timer.stop();
                        manager.gameStatus = Manager.youLose;
                        manager.board[i][j].isDiscovered = true;
                        resetButton.setIcon(new ImageIcon("src/pl/sanders/loser.png"));
                        cells[i][j].setBackground(Color.WHITE);
                        for (int k = 0; k < manager.width; k++) {
                            for (int l = 0; l < manager.height; l++) {
                                if ( manager.board[k][l].value >= 9  && !manager.board[k][l].isFlagged ) {
                                    cells[k][l].setIcon(new ImageIcon("src/pl/sanders/mine.png"));
                                    cells[k][l].setBackground(Color.WHITE);
                                }
                            }
                        }
                    } else {
                        timer.start();
                        if (manager.board[i][j].value == 0) {
                            manager.board[i][j].isDiscovered = true;

                            analysis(i, j);
                        } else {
                            manager.board[i][j].isDiscovered = true;

                            cells[i][j].setText("" + manager.board[i][j].value);
                            cells[i][j].setBackground(Color.WHITE);
                        }
                        if (manager.checkIfWin()){
                            timer.stop();
                                        for (int k = 0; k < manager.width; k++) {
                                            for (int l = 0; l < manager.height; l++) {
                                                if(!manager.board[k][l].isDiscovered){
                                                    manager.board[k][l].isFlagged = true;
                                                    cells[k][l].setIcon(new ImageIcon("src/pl/sanders/flag.png"));
                                                }
                                            }
                                        }
                            bombToDetectLabel.setText("" + 0);

                            resetButton.setIcon(new ImageIcon("src/pl/sanders/winner.png"));
                           int position=Scoreboard.checkPosition(manager.seconds,Manager.gameMode);
                            if(position<5) {
                               String name = JOptionPane.showInputDialog(gamePanel, "Congratulations! \nYour Time: " + manager.seconds + "\nPlease, enter your nickname :", "You Win!",
                                       JOptionPane.WARNING_MESSAGE);
                               Scoreboard.AddResult(new ScoreboardElement(name,manager.seconds),position,Manager.gameMode);
                                try {
                                    Scoreboard.saveData();
                                }catch (FileNotFoundException exception){}
                                JOptionPane.showMessageDialog(gamePanel,
                                        Scoreboard.getRanking(Manager.gameMode),
                                        "Ranking",
                                        JOptionPane.INFORMATION_MESSAGE,
                                        new ImageIcon("src/pl/sanders/cup.png"));
                           }else{
                               JOptionPane.showMessageDialog(gamePanel, "Congratulations! \nYour Time: " + manager.seconds , "You Win!",
                                       JOptionPane.WARNING_MESSAGE);
                           }

                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e) && manager.gameStatus==Manager.inProgress) {
                    if(!manager.board[i][j].isDiscovered){
                        if (manager.board[i][j].isFlagged) {
                            manager.board[i][j].isFlagged = false;

                            cells[i][j].setIcon(new ImageIcon());
                            bombToDetectLabel.setText("" + ++manager.bombsToDetect);

                        } else {
                            manager.board[i][j].isFlagged = true;
                           cells[i][j].setIcon(new ImageIcon("src/pl/sanders/flag.png"));
                            validate();
                            bombToDetectLabel.setText("" + --manager.bombsToDetect);
                        }
                    }
                }


            }

            @Override
            public void mousePressed(MouseEvent e) {
               if(SwingUtilities.isLeftMouseButton(e) && manager.gameStatus==Manager.inProgress)
                resetButton.setIcon(new ImageIcon("src/pl/sanders/surprised.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e) && manager.gameStatus==Manager.inProgress)
                resetButton.setIcon(new ImageIcon("src/pl/sanders/happy.png"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    public void analysis (int i, int j) {
            for (int k = -1; k <= 1; k++) {
                for (int l = -1; l <= 1; l++) {
                    try {
                        if(!manager.board[i+k][j+l].isFlagged) {
                            if (manager.board[i+k][j+l].value!=0) cells[i + k][j + l].setText("" + manager.board[i + k][j + l].value);
                            cells[i + k][j + l].setBackground(Color.WHITE);

                            if (manager.board[i + k][j + l].isDiscovered == false && manager.board[i + k][j + l].value == 0) {
                                manager.board[i + k][j + l].isDiscovered = true;
                                analysis(i + k, j + l);
                            } else {
                                manager.board[i + k][j + l].isDiscovered = true;
                            }
                        }
                    } catch (Exception e) {}
                }
            }
    }
    private void colorCell(int i, int j){
        switch (manager.board[i][j].value){
            case 1: cells[i][j].setForeground(new Color(0, 0, 250)) ; break;
            case 2: cells[i][j].setForeground(new Color(0, 150, 0)) ; break;
            case 3: cells[i][j].setForeground(new Color(195, 0, 0)) ; break;
            case 4: cells[i][j].setForeground(new Color(0, 0, 150)) ; break;
            case 5: cells[i][j].setForeground(new Color(99, 0, 0)) ; break;
            case 6: cells[i][j].setForeground(new Color(0, 150, 150)) ; break;
            case 7: cells[i][j].setForeground(new Color(1, 1, 1)) ; break;
            case 8: cells[i][j].setForeground(new Color(100, 100, 100)) ; break;
            default: cells[i][j].setForeground(new Color(0, 0, 0)) ;
        }
        cells[i][j].setBackground(Color.GRAY);
        cells[i][j].setText("");
        cells[i][j].setIcon(new ImageIcon());
    }
    private void reset(){
        resetButton.setIcon(new ImageIcon("src/pl/sanders/happy.png"));
        manager = new Manager(manager.width,manager.height,manager.bombs);
        timer.stop();
        stopwatch = new Clock();
        timer = new Timer(1000,stopwatch);
        bombToDetectLabel.setText("" + manager.bombsToDetect);
        timeLabel.setText("" + manager.seconds);
        for (int i = 0; i < manager.width; i++) {
            for (int j = 0; j < manager.height; j++) {
                colorCell(i,j);
            }
        }
    }
}