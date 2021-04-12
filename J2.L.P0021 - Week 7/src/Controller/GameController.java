//package Controller;
//
//import GUI.Game;
//import java.awt.Dimension;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import static java.lang.Thread.sleep;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.Timer;
//
///**
// *
// * @author Manh Hung
// */
//public class GameController {
//
//    final int BUTTON_SIZE = 50;
//    final int GRID_GAP = 20;
//
//    private final Game game = new Game();
//    private final JButton btnNewGame = game.getBtnNewGame();
//    private final JLabel labelElapsed = game.getLabelElapsed();
//    private final JLabel labelMoveCount = game.getLabelMoveCount();
//    private final JComboBox cbbSize = game.getCbbSize();
//    private final JPanel panelGameArea = game.getGameArea();
//
//    private int size = 3; //default size is 3x3
//    private final List<JButton> buttonMap = new ArrayList<>();
//    private List<Integer> gameVector;
//    private int emptyButtonIndex, timer, moveCount;
//    private boolean isPlaying = false;
//    private int secong = 0;
//
//    public GameController() {
//        game.setVisible(true);
//        setupGame();
//        TimeThread().start();
//
//        cbbSize.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                updateSize();
//            }
//        });
//        btnNewGame.addActionListener(new java.awt.event.ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                newGame();
//            }
//        });
//    }
//
//    public Thread TimeThread() {
//        Thread elapsed = new Thread() {
//            @Override
//            public void run() {
//                while (true) {
//                    if (isPlaying) {
//                        labelElapsed.setText(timer++ + " sec");
//                    }
//                    try {
//                        sleep(1000);
//                    } catch (InterruptedException ex) {
//                        System.err.println("Thread elapse error");
//                    }
//                }
//            }
//        };
//        return elapsed;
//    } 
//
//    public void newGame() {
//        if (isPlaying) {
//            isPlaying = false;
//            if (JOptionPane.showConfirmDialog(game, "Do you really want to start new game?",
//                    "Confirm Dialog", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//                setupGame();
//            } else {
//                isPlaying = true;
//            }
//        } else {
//            setupGame();
//        }
//    }
//
//    public void updateSize() {
//        String s = cbbSize.getSelectedItem().toString();
//        String[] sizeOfGame = s.split("x");
//        this.size = Integer.parseInt(sizeOfGame[0]);
//    }
//
//    List<Integer> generateGameVector() {
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < size * size; i++) {
//            list.add(i + 1);
//        }
//        do {
//            //Using Shuffle Collection to shuffle.
//            Collections.shuffle(list);
//        } while (!isSolvable(list));
//        return list;
//    }
//
//    public boolean isSolvable(List<Integer> list) {
//        int N = 0;
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i) == size * size) {
//                continue;
//            }
//            for (int j = i; j < list.size(); j++) {
//                if (list.get(i) > list.get(j)) {
//                    N++;
//                }
//            }
//        }
//        //If size is odd -> N : Odd is unsolvable, Even is solvable 
//        if (size % 2 == 1) {
//            return N % 2 == 0;
//        } else {
//            // If size is even: Blank button in odd row(counting from the top)
//            if (isBlankInOddRow(list)) {
//                return N % 2 == 1;
//            } else {
//                return N % 2 == 0;
//            }
//        }
//    }
//
//    public boolean isBlankInOddRow(List<Integer> list) {
//        for (int i = 0; i < size * size; i++) {
//            if (list.get(i) == size * size) {
//                return true;
//            }
////            incresing i by size.
//            if (i % size == size - 1) {
//                i += size;
//            }
//        }
//        return false;
//    }
//
//    public void setupGame() {
////        updateSize();
//        timer = 0;
//        labelElapsed.setText(timer + " sec");
//        moveCount = 0;
//        labelMoveCount.setText(moveCount + "");
//
//        panelGameArea.removeAll();
//        panelGameArea.setLayout(new GridLayout(size, size, GRID_GAP, GRID_GAP));
//        gameVector = generateGameVector();
//
//        for (int i = 0; i < size * size; i++) {
//            int number = gameVector.get(i);
//            JButton button = new JButton();
//            button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
//            addActionButton(button);
//            // set empty label to last key of buttonMap
//            if (number == size * size) {
//                button.setText("");
//                emptyButtonIndex = i;
//                // set default Empty slot
//            } else {
//                button.setText(number + "");
//            }
//            panelGameArea.add(button);
//            buttonMap.add(i, button);
//        }
//        game.pack();
//    }
//
//    public void addActionButton(JButton button) {
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (button.getText().isEmpty()) {
//                    return;
//                }
//                isPlaying = true;
//                int currentBtn = Integer.parseInt(button.getText());
//                int currentIndex = gameVector.indexOf(currentBtn);
//                moving(currentIndex);
//                winningNotification();
//            }
//        });
//    }
//
//    public void moving(int currentIndex) {
//        //If clicked button near by empty button -> swap
//        if (PosisionNearEmpty(currentIndex)) {
//            swapButton(currentIndex, emptyButtonIndex);
//            emptyButtonIndex = currentIndex;
//            labelMoveCount.setText(++moveCount + "");
//        }
//    }
//
//    public boolean PosisionNearEmpty(int current) {
//        if ((current - size) == emptyButtonIndex) { //top
//            return true;
//        } else if (current + size == emptyButtonIndex) {//bottom
//            return true;
//        } else if (current - 1 == emptyButtonIndex) {//left
//            return true;
//        } else if (current + 1 == emptyButtonIndex) {//right
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public void swapButton(int currentIndex, int emptyButtonIndex) {
//        String tempS = buttonMap.get(currentIndex).getText();
//        buttonMap.get(currentIndex).setText(buttonMap.get(emptyButtonIndex).getText());
//        buttonMap.get(emptyButtonIndex).setText(tempS);
//
//        int temp = gameVector.get(currentIndex);
//        gameVector.set(currentIndex, gameVector.get(emptyButtonIndex));
//        gameVector.set(emptyButtonIndex, temp);
//
//    }
//
//    public void winningNotification() {
//        for (int i = 0; i < gameVector.size() - 1; i++) {
//            if (gameVector.get(i) > gameVector.get(i + 1)) {
//                return;
//            }
//        }
//        isPlaying = false;
//        JOptionPane.showMessageDialog(null, "Congra, you win !!!");
//        for (int j = 0; j < gameVector.size(); j++) {
//            buttonMap.get(j).setEnabled(false);
//        }
//    }
//}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GUI.Game;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Manh Hung
 */
public class GameController {

    final int BUTTON_SIZE = 50;
    final int GRID_GAP = 20;

    private final Game game = new Game();
    private final JButton btnNewGame = game.getBtnNewGame();
    private final JLabel labelElapsed = game.getLabelElapsed();
    private final JLabel labelMoveCount = game.getLabelMoveCount();
    private final JComboBox cbbSize = game.getCbbSize();
    private final JPanel panelGameArea = game.getGameArea();

    private int size = 3; //default size is 3x3
    private HashMap<Integer, JButton> buttonList = new HashMap<>();
    private int emptyButtonIndex, timer, moveCount, currentIndex, N;
    private Timer t;
    private int count = 0;

    public GameController() {
        game.setVisible(true);
        setupGame();
        cbbSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSize();
            }
        });

        btnNewGame.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGame1();
            }
        });
    }

    public void countTime() {
        moveCount = 0;
        timer = 0;
        labelElapsed.setText("0 sec");
        labelMoveCount.setText(moveCount + "");
        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                labelElapsed.setText(timer++ + " sec");
            }
        });
        t.start();
    }

    public void newGame1() {
        if (t != null && !winningNotification()) {
            t.stop();
            if (JOptionPane.showConfirmDialog(game, "Do you really want to start new game?",
                    "Confirm Dialog", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                countTime();
                setupGame();
            } else {
                t.start();
            }
        } else {
            countTime();
            setupGame();
        }
    }

    public void updateSize() {
        String s = cbbSize.getSelectedItem().toString();
        String[] sizeOfGame = s.split("x");
        this.size = Integer.parseInt(sizeOfGame[0]);
    }

    List<Integer> generateGameVector(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            list.add(i + 1);
        }
        do {
            //Using Shuffle Collection to shuffle.
            Collections.shuffle(list);
        } while (!isSolvable(list));
        return list;
    }

    public boolean isSolvable(List<Integer> list) {
        int N = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == size * size) {
                continue;
            }
            for (int j = i; j < list.size(); j++) {
                if (list.get(i) > list.get(j)) {
                    N++;
                }
            }
        }
        //If size is odd -> N : Odd is unsolvable, Even is solvable 
        if (size % 2 == 1) {
            return N % 2 == 0;
        } else {
            // If size is even: Blank button in odd row(counting from the top)
            return ((emptyButtonIndex / size + 1) % 2 == 0 && N % 2 == 0)
                    || ((emptyButtonIndex / size + 1) % 2 == 1 && N % 2 == 1);
        }
    }

    public void setupGame() {
        moveCount = 0;
        timer = 0;
        labelElapsed.setText("0 sec");
        labelMoveCount.setText(moveCount + "");
        panelGameArea.removeAll();
        panelGameArea.setLayout(new GridLayout(size, size, GRID_GAP, GRID_GAP));
        ArrayList<Integer> listNumber = (ArrayList) generateGameVector(size);
        for (int i = 0; i < size * size; i++) {
            int num = listNumber.get(i);
            String txt = num % (size * size) != 0 ? num + "" : "";
            if (txt.equals("")) {
                emptyButtonIndex = i;
            }
            JButton btn = new JButton(txt);
            btn.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
            addActionButton(btn);
            buttonList.put(i, btn);
            panelGameArea.add(btn);
        }
        game.pack();
    }

    public void addActionButton(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCurrentPos(button);
                //check button can swap
                if (PosisionNearEmpty()) {
                    swapButton();
                    labelMoveCount.setText(++moveCount + "");
                    if (++count == 1) {
                        countTime();
                    }
                    if (winningNotification()) {
                        t.stop();
                        JOptionPane.showMessageDialog(null, "You Win !!!", "Congratulate", 1);
                        for (Map.Entry<Integer, JButton> entry : buttonList.entrySet()) {
                            JButton value = entry.getValue();
                            value.setEnabled(false);
                        }
                    }
                }
            }
        });
    }

    public void getCurrentPos(JButton btn) {
        //for loop find pos of button is selected
        for (Map.Entry<Integer, JButton> entry : buttonList.entrySet()) {
            Integer key = entry.getKey();
            JButton value = entry.getValue();
            //save current button pos
            if (btn.getText().equals(value.getText())) {
                currentIndex = key;
                return;
            }
        }
    }

    public boolean PosisionNearEmpty() {
        if ((currentIndex - size) == emptyButtonIndex) { //top
            return true;
        } else if (currentIndex + size == emptyButtonIndex) {//bottom
            return true;
        } else if (currentIndex - 1 == emptyButtonIndex) {//left
            return true;
        } else if (currentIndex + 1 == emptyButtonIndex) {//right
            return true;
        } else {
            return false;
        }
    }

    public void swapButton() {
        String tempS = buttonList.get(currentIndex).getText();
        buttonList.get(emptyButtonIndex).setText(tempS);
        buttonList.get(currentIndex).setText("");
        emptyButtonIndex = currentIndex;
    }

    public boolean winningNotification() {
        // for loop to get key and vakue of HashMap
        for (Map.Entry<Integer, JButton> entry : buttonList.entrySet()) {
            Integer key = entry.getKey();
            JButton value = entry.getValue();
            String txt = value.getText();
            if (!txt.equals("")) {
                int num = 0;
                try {
                    num = Integer.parseInt(txt);
                } catch (NumberFormatException numf) {
                    System.err.println("Convert number checkWin error");
                }
                if (num - 1 != key) {
                    return false;
                }
            }
        }
        return true;
    }

}
