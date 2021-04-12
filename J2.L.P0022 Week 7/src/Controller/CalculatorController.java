    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GUI.Calculator;
import static java.lang.Math.sqrt;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JFrame;

/**
 *
 * @author Manh Hung
 */
public class CalculatorController {

    Calculator gui = new Calculator();
    BigDecimal result = new BigDecimal("0");
    BigDecimal Dnum = new BigDecimal("0");
    String operator = "";
    Boolean newNumber = true;

    public CalculatorController() {
        gui.setVisible(true);
        gui.getTxtScreen().setText("0");
        gui.getTxtScreen().setEditable(false);

        gui.getBtn0().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(addDigit(getText(), "0"));
            }
        });
        gui.getBtn1().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(addDigit(getText(), "1"));
            }
        });
        gui.getBtn2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(addDigit(getText(), "2"));
            }
        });
        gui.getBtn3().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(addDigit(getText(), "3"));
            }
        });
        gui.getBtn4().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(addDigit(getText(), "4"));

            }
        });
        gui.getBtn5().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(addDigit(getText(), "5"));

            }
        });
        gui.getBtn6().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(addDigit(getText(), "6"));

            }
        });

        gui.getBtn7().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(addDigit(getText(), "7"));

            }
        });

        gui.getBtn8().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(addDigit(getText(), "8"));

            }
        });

        gui.getBtn9().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(addDigit(getText(), "9"));

            }
        });

        gui.getBtnCong().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(Calculate(getText(), "+"));

            }
        });
        gui.getBtnTru().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(Calculate(getText(), "-"));

            }
        });

        gui.getBtnNhan().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(Calculate(getText(), "x"));

            }
        });

        gui.getBtnChia().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(Calculate(getText(), "/"));

            }
        });

        gui.getBtnPhanTram().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(Calculate(getText(), "="));
                setText(ButtonPercent());
            }
        });

        gui.getBtn1TrenX().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(Calculate(getText(), "="));
                setText(ButtonFlip());
            }
        });

        gui.getBtnCham().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(ButtonDot(getText()));
            }
        });

        gui.getBtnCongTru().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(Calculate(getText(), "="));
                setText(ButtonSwap());
            }
        });

        gui.getBtnBang().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(Calculate(getText(), "="));
            }
        });

        gui.getBtnCan().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(Calculate(getText(), "="));
                setText(ButtonSqrt());
            }
        });

        gui.getBtnMCong().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(Calculate(getText(), "="));
                ButtonMAdd(getText());
            }
        });
        gui.getBtnMTru().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(Calculate(getText(), "="));
                ButtonMSub(getText());
            }
        });
        gui.getBtnMR().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setText(ButtonMR());
            }
        });
        gui.getBtnMC().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMC();
            }
        });
        gui.getAllClear().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setText(ButtonClear());
            }
        });
    }

    public void setText(String s) {
        gui.getTxtScreen().setText(s);
    }

    public String getText() {
        return gui.getTxtScreen().getText();
    }

    public CalculatorController(Calculator c) {
        this.gui = c;
    }

    //String text : get Text from screen
    //String number : number user click
    public String addDigit(String textScreen, String number) {
        //input digit will replace current text
        if (newNumber) {
            // after operator = result will be reset so next operation
            // won't take previous operation result
            if (operator.equals("=")) {
                result = new BigDecimal("0");
            }
            //set newNumber = false : to calculator when user Calculate
            newNumber = false;
            return (FixNumber(new BigDecimal(number)));
        } else if ((textScreen).equals("0")) {
            //current text is 0, the first input digit will replace it
            return (FixNumber(new BigDecimal(number)));
        } else {
            //add 1 digit to last of the text
            return (textScreen + number);
        }
    }

    //String text : get Text from Screen
    public String Calculate(String textScreen, String currentOp) {
        String output = textScreen;
        //there was an input number
        if (!newNumber) {
            BigDecimal num = new BigDecimal(textScreen);
            switch (operator) {
                case "+":
                    result = result.add(num);
                    break;
                case "-":
                    result = result.subtract(num);
                    break;
                case "x":
                    result = result.multiply(num);
                    break;
                case "/":
                    //can't divide by0 
                    if (num.equals(new BigDecimal("0"))) {
                        output = "Error";
                    } else {
                        //always round to the next number             
                        result = result.divide(num, 12, RoundingMode.HALF_EVEN);
                    }
                    break;
                default:
                    result = new BigDecimal(textScreen);
                    break;
            }
            if (!output.equals("Error")) {
                output = FixNumber(result);
            }
        }
        //save new operator to calculate next opression
        operator = currentOp;
        newNumber = true;
        return output;
    }

    //return interger format if input number is integer
    public String FixNumber(BigDecimal currentNum) {
        String s = currentNum.toString();  //1.000 
        //go thought s to find if it has dot
        for (int i = 0; i < s.length(); i++) {  
            if (s.charAt(i) == '.') {
                String newS = "";
                int newL = s.length(); //newL = 5,
                //go through s to delete all last 0 digit after dot
                for (int j = s.length() - 1; j > 1; j--) { // 1.
                    //while last digit is still 0
                    if (s.charAt(j) == '0') {
                        //update new length of s
                        newL = j; //2
                    } else { //next digit is real
                        break;
                    }
                }
                //all digit after dot is 0. so dot is useless
                if (s.charAt(newL - 1) == '.') { 
                    newL--;
                }
                //go through s to save new string
                for (int j = 0; j < newL; j++) {
                    newS = newS + s.charAt(j);
                }
                s = newS;
                break;
            }
        }
        return s;
    }

    // Button All Clear
    // Reset everything back to 0
    public String ButtonClear() {
        Dnum = new BigDecimal("0");
        result = new BigDecimal("0");
        operator = "";
        newNumber = true;
        return ("0");
    }

    // Button Memory Clear
    // Clear all numbers in memory
    public void ButtonMC() {
        Dnum = new BigDecimal("0");
        newNumber = true;
    }

    // Button Memory Recall
    // Return number which in memory
    public String ButtonMR() {
        result = Dnum;
        newNumber = true;
        return (FixNumber(Dnum));
    }

    // Button M+
    // Add current number to memory
    // If current display is Error
    // Can't add to memory
    public void ButtonMAdd(String text) {
        if (!text.equals("Error")) {
            Dnum = Dnum.add(new BigDecimal(text));
            newNumber = true;
        }
    }

    // Button M-
    // Substract current number to memory
    // If current display is Error
    // Can't add to memory
    public void ButtonMSub(String text) {
        if (!text.equals("Error")) {
            Dnum = Dnum.subtract(new BigDecimal(text));
            newNumber = true;
        }
    }

    // Button √
    // Square Root the current number
    // If not display Error
    public String ButtonSqrt() {
        // cant square root for number < 0
        if (result.compareTo(new BigDecimal("0")) == -1) {
            return "Error";
        } else {
            Double dnum = sqrt(result.doubleValue());
            result = new BigDecimal(dnum);
            result = result.divide(new BigDecimal("1"), RoundingMode.HALF_EVEN);
            newNumber = true;
            return (FixNumber(result));
        }
    }

    // Button %
    // Divide current number by 100
    public String ButtonPercent() {
        result = result.divide(new BigDecimal("100"));
        newNumber = true;
        return (FixNumber(result));
    }

    // Button 1/x
    // Take 1 then divide by current number
    public String ButtonFlip() {
        // if current number = 0
        // can't divide
        if (result.equals(new BigDecimal("0"))) {
            return "Error";
        } else {
            BigDecimal One = new BigDecimal("1");
            result = One.divide(result, 12, RoundingMode.HALF_EVEN);
            newNumber = true;
            return (FixNumber(result));
        }
    }

    // Button .
    // Add a dot to current number
    // If there was a dot, it won't add another dot 7..5
    public String ButtonDot(String text) {
        // There was a input number
        if (!newNumber) {
//            newNumber = false;
            // go through text to find dot
            for (int i = 0; i < text.length(); i++) {
                //there was a dot in number
                if (text.charAt(i) == '.') {
                    return text;
                }
            }
            //there wasn't any dot in number
            return text + '.';
        } else { //current value of input number is 0
            newNumber = false;
            return "0.";
        }
    }

    // Button +/-
    // Swap current number to be positive or negative
    public String ButtonSwap() {
        result = new BigDecimal("0").subtract(result);
        newNumber = true;
        return (FixNumber(result));
    }

    public void frameSetting(JFrame frame) {
        frame.setLocationRelativeTo(null);
    }
}
