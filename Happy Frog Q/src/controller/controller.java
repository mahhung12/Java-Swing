/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import game.game;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Manh Hung
 */
public final class controller implements Runnable {

    //get Component from JFrameForm
    private final game game = new game();
    private final JPanel GameArea = game.getGameArea();
    private final JButton buttonSave = game.getBtnSave();
    private final JButton buttonPause = game.getBtnPause();
    private final JButton buttonStart = game.getBtnStart();
    private final JButton buttonExit = game.getBtnExit();
    private final JLabel LabelPoint = game.getLblPoint();

    //Create Label Frog, Pipes Button
    private final JLabel lbfrog = new JLabel("Frog");
    private final JButton pipe1Up = new JButton();
    private final JButton pipe2Up = new JButton();
    private final JButton pipe1Down = new JButton();
    private final JButton pipe2Down = new JButton();

    //Set position to Frame, PanelGame, Pipes, Velocity
    int Width_Frame, Height_Frame;
    int Width_Panel, Height_Panel;

    int WidthFrog, HeightFrog;
    int SpaceUpDownPipe, horizontalPipe, Space2Pipe, VerticalPipe;
    int PaceDown, PaceUp, velocity, score;

    //set Position, status when start when.
    int xFrog, yFrog; //Position x, y of Frog
    int x1, x2; //horizontal of pipes (Ngang)
    int h1, h2; //Vertical of pipes (Doc)
    boolean isRunning, isSave = false;

    private final Random rand = new Random();
    Thread thread = null;

    public controller() {
        game.setVisible(true);
//        game.setSize(WidthFrame, HeightFrame);
        game.setLocationRelativeTo(null);
        game.setResizable(false);
        System.out.println(game.getWidth());
        System.out.println(game.getHeight());
        System.out.println(GameArea.getWidth());
        System.out.println(GameArea.getHeight());
        initComponents();
        CreateGame();

        buttonStart.addActionListener((java.awt.event.ActionEvent evt) -> {
            ClickbuttonStart();
        });

        buttonSave.addActionListener((java.awt.event.ActionEvent evt) -> {
            ClickButtonSave();
        });

        buttonExit.addActionListener((java.awt.event.ActionEvent evt) -> {
            System.exit(0);
        });
        GameArea.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyPress(evt);
            }
        });
    }

    public void initComponents() {
        Width_Frame = game.getWidth();
        Height_Frame = game.getHeight();
        Width_Panel = GameArea.getWidth();
        Height_Panel = GameArea.getHeight();

//        Width_Frame = 620;
//        Height_Frame = 600;
//        Width_Panel = 600;
//        Height_Panel = 500;

        WidthFrog = 50;
        HeightFrog = 50;
        SpaceUpDownPipe = 150;
        horizontalPipe = 100;
        Space2Pipe = 350;
        VerticalPipe = 50;
        PaceDown = (int) 1.5;
        PaceUp = 12;

        //set Position, status when start when.
        xFrog = 150;
        yFrog = 225; //Position x, y of Frog
        x1 = Width_Panel;
        x2 = x1 + Space2Pipe; //horizontal of pipes (Ngang)
    }

    public void keyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_SPACE:
                ClickbuttonStart();
                break;
            case KeyEvent.VK_UP:
                FrogMoveUp();
                break;
            case KeyEvent.VK_S:
                ClickButtonSave();
                break;
            default:
                break;
        }
    }

    //function When user click Start
    //if User not play Yes, button will Display "Start"
    //otherwise : display "Pause" or "Continue"
    public void ClickbuttonStart() {
        if (buttonStart.getText().equalsIgnoreCase("Start")) {
            initComponents();
            if (isSave) {
                if (JOptionPane.showConfirmDialog(null, "Do you want play game in Save Point",
                        "Message", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    try {
                        loadFile("location.txt");
                    } catch (IOException ex) {
                        Logger.getLogger(controller.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            CreateGame();
            isRunning = true;
            buttonStart.setText("Pause");
            GameArea.requestFocus();
        } else if (!isRunning) {
            buttonStart.setText("Pause");
            isRunning = true;
            GameArea.requestFocus();
        } else {
            isRunning = false;
            buttonStart.setText("Continue");
        }
    }

    private void loadFile(String fname) throws IOException {
        RandomAccessFile f = new RandomAccessFile(fname, "r");
        String s = null;
        while (true) {
            s = f.readLine();
            if (s == null || s.trim().equals("")) {
                break;
            }
            String[] frog = s.split(",");
            xFrog = Integer.parseInt(frog[0]);
            yFrog = Integer.parseInt(frog[1]);
            x1 = Integer.parseInt(frog[2]);
            x2 = Integer.parseInt(frog[3]);
            h1 = Integer.parseInt(frog[4]);
            h2 = Integer.parseInt(frog[5]);
            score = Integer.parseInt(frog[6]);
            LabelPoint.setText(score + "");
        }
        f.close();
    }

    public void ClickButtonSave() {
        isSave = true;
        if (isRunning) {
            try {
                saveFile("location.txt");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(String fname) throws IOException {
        File f = new File(fname);
        if (f.exists()) {
            f.delete();
        }
        RandomAccessFile g = new RandomAccessFile(fname, "rw");
        g.writeBytes(xFrog + "," + yFrog + "," + x1 + "," + x2
                + "," + h1 + "," + h2 + "," + score + "\r\n");
        g.close();
    }

    public void CreateGame() {
        GameArea.removeAll();
//        initComponents();
        lbfrog.setBounds(xFrog, yFrog, WidthFrog, HeightFrog);
        //Scale imag.
        BufferedImage frogImg = null;
        try {
            frogImg = ImageIO.read(new File("frog.jpg"));
        } catch (IOException e) {
        }
        Image tempImg = frogImg.getScaledInstance(lbfrog.getWidth(), lbfrog.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(tempImg);
        lbfrog.setIcon(imageIcon);
        lbfrog.setSize(WidthFrog, HeightFrog);

        //add image to Panel
        GameArea.add(lbfrog);
        GameArea.setSize(Width_Panel, Height_Panel);

        //random vertical of pipe on top
        h1 = VerticalPipe + rand.nextInt(Height_Panel / 2);
        h2 = VerticalPipe + rand.nextInt(Height_Panel / 2);
        addPipe(pipe1Up, pipe1Down, x1, h1);
        addPipe(pipe2Up, pipe2Down, x2, h2);
        score = 0;
        LabelPoint.setText("Point : " + score);
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    //
    public void addPipe(JButton btn1, JButton btn2, int x, int h) {
        btn1.setBounds(x, 0, horizontalPipe, h);
        btn2.setBounds(x, (h + SpaceUpDownPipe), horizontalPipe, Height_Panel - (h + SpaceUpDownPipe));
        btn1.setVisible(true);
        btn2.setVisible(true);
        GameArea.add(btn1);
        GameArea.add(btn2);
    }

    public void pipeMoveForward() {
        x1 -= 2;
        x2 -= 2;
        if (x1 == -(Width_Frame - Space2Pipe - horizontalPipe)) {
            x1 = Width_Panel;
            h1 = rand.nextInt(Height_Panel / 2);
        } else if (x2 == -(Width_Frame - Space2Pipe - horizontalPipe)) {
            x2 = Width_Panel;
            h2 = rand.nextInt(Height_Panel / 2);
        }
        //set Location x, y and size of pipe after moving
        pipe1Up.setBounds(x1, 0, horizontalPipe, h1);
        pipe1Down.setBounds(x1, (h1 + SpaceUpDownPipe), horizontalPipe, Height_Panel - (h1 + SpaceUpDownPipe));

        pipe2Up.setBounds(x2, 0, horizontalPipe, h2);
        pipe2Down.setBounds(x2, (h2 + SpaceUpDownPipe), horizontalPipe, Height_Panel - (h2 + SpaceUpDownPipe));

    }

    //create a new Rectangle arround Frog and pipes. set location is location of Frog and pipes
    //return true if frog intersects pipes
    public boolean CheckImpact(JLabel frog, JButton pipe) {
        Rectangle r1 = new Rectangle(frog.getX(), frog.getY(), frog.getWidth(), frog.getHeight());
        Rectangle r2 = new Rectangle(pipe.getX() + 1, pipe.getY() + 1, pipe.getWidth(), pipe.getHeight());
        return r1.intersects(r2);
    }

    public boolean isDie() {
        if (lbfrog.getY() > Height_Panel - WidthFrog + 1 || lbfrog.getY() < 0) {
            return true;
        } else if (CheckImpact(lbfrog, pipe1Up)) {
            return true;
        } else if (CheckImpact(lbfrog, pipe2Up)) {
            return true;
        } else if (CheckImpact(lbfrog, pipe1Down)) {
            return true;
        } else if (CheckImpact(lbfrog, pipe2Down)) {
            return true;
        }

        return false;
    }

    public void moveFrogOy() {
        yFrog += velocity;
        lbfrog.setLocation(xFrog, yFrog);
    }

    public void FrogMoveDown() {
        if (velocity < 15) {
            velocity += 1.5;
        }
        moveFrogOy();
    }

    public void FrogMoveUp() {
        if (!isRunning) {
            return;
        }
        isRunning = true;
        if (velocity > 0) {
            velocity = 0;
        }
        velocity = velocity - PaceUp;
        moveFrogOy();
    }

    private void checkScore() {
        if (!isDie() && (lbfrog.getX() == pipe1Up.getX() + horizontalPipe + WidthFrog)
                || (lbfrog.getX() == pipe2Up.getX() + horizontalPipe + WidthFrog)) {
            score++;
            LabelPoint.setText("Point : " + score);
        }
    }

    private void showMessage() {
        buttonStart.setText("Start");
        String medal = null;
        if (score < 10) {
            medal = "No medal";
        } else if (score < 20) {
            medal = "Bronze";
        } else if (score < 30) {
            medal = "Silver";
        } else if (score < 40) {
            medal = "Platium";
        }
        JOptionPane.showMessageDialog(null, "Your Score : " + score + "\nYou archived: " + medal,
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
        buttonStart.requestFocus();
        initComponents();
        CreateGame();
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (isRunning) {
                    FrogMoveDown();
                    pipeMoveForward();
                    checkScore();
                    if (isDie()) {
                        isRunning = false;
                        showMessage();
                    }
                }
                Thread.sleep(35);
            } catch (InterruptedException e) {
            }
        }
    }
}
