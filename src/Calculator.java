import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame implements ActionListener {
    JTextField equationTextField;
    JTextField resultTextField;
    String num1, num2, commandLine;
    char operator;

    Calculator() {
        num1 = num2 = commandLine = "";
        operator = '\0';

        equationTextField = new JTextField();
        equationTextField.setHorizontalAlignment(JTextField.RIGHT);
        equationTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        equationTextField.setEditable(false);
        equationTextField.setBackground(new Color(230, 230, 230));

        resultTextField = new JTextField();
        resultTextField.setHorizontalAlignment(JTextField.RIGHT);
        resultTextField.setFont(new Font("Arial", Font.PLAIN, 24));
        resultTextField.setEditable(false);
        resultTextField.setBackground(new Color(200, 200, 200));

        JButton[] numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setBackground(new Color(190, 190, 190));
        }

        JButton clearButton = createClearButton();
        JButton plusMinusButton = createPlusMinusButton("±");
        JButton percentButton = createPercentButton("%");
        JButton divideButton = createOperatorButton("÷");
        JButton multiplyButton = createOperatorButton("*");
        JButton subtractButton = createOperatorButton("-");
        JButton addButton = createOperatorButton("+");
        JButton commaButton = createCommaButton(".");
        JButton equalsButton = createEqualsButton();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 1, 1)); 
        buttonPanel.setBackground(new Color(150, 150, 150)); 

        buttonPanel.add(clearButton);
        buttonPanel.add(plusMinusButton);
        buttonPanel.add(percentButton);
        buttonPanel.add(divideButton);
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subtractButton);
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(addButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(commaButton);
        buttonPanel.add(equalsButton);

        setLayout(new BorderLayout());
        add(equationTextField, BorderLayout.NORTH);
        add(resultTextField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Calculator");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createCommaButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.addActionListener(this);
        return button;
    }

    private JButton createPercentButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.addActionListener(this);
        return button;
    }

    private JButton createPlusMinusButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.addActionListener(this);
        return button;
    }

    private JButton createOperatorButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.addActionListener(this);
        return button;
    }

    private JButton createEqualsButton() {
        JButton button = new JButton("=");
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.addActionListener(this);
        return button;
    }

    private JButton createClearButton() {
        JButton button = new JButton("C");
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.addActionListener(this);
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (Character.isDigit(command.charAt(0)) || command.equals(".")) {
            if (command.equals(".") && num1.contains(".")) {
                JOptionPane.showMessageDialog(this, "This command is not allowed", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                num1 += command;
                commandLine += command;
                equationTextField.setText(commandLine);
            }
        } else if (command.charAt(0) == 'C') {
            num1 = num2 = "";
            operator = '\0';
            commandLine = "";
            equationTextField.setText("");
            resultTextField.setText("");
        } else if (command.charAt(0) == '=') {
            if (!num1.isEmpty() && !num2.isEmpty() && operator != '\0') {
                commandLine += command;
                equationTextField.setText(commandLine);
                double result = calculateResult();
                updateResult(result);
            }
        } else if (command.equals("%")) {
            if (!num1.isEmpty()) {
                double num = Double.parseDouble(num1);
                num /= 100.0;
                num1 = String.valueOf(num);
                commandLine = num1;
                equationTextField.setText(commandLine);
            }
        } else if (command.equals("+/-")) {
            if (!num1.isEmpty()) {
                double num = Double.parseDouble(num1);
                num = -num;
                num1 = String.valueOf(num);
                commandLine = num1;
                equationTextField.setText(commandLine);
            }
        } else {
            commandLine += command;
            equationTextField.setText(commandLine);
            operator = command.charAt(0);
            num2 = num1;
            num1 = "";
        }
    }

    private double calculateResult() {
        double n1 = Double.parseDouble(num2);
        double n2 = Double.parseDouble(num1);
        switch (operator) {
            case '+':
                return n1 + n2;
            case '-':
                return n1 - n2;
            case '*':
                return n1 * n2;
            case '÷':
                if (n2 != 0) {
                    return n1 / n2;
                } else {
                    JOptionPane.showMessageDialog(this, "Division by zero is not allowed", "Error", JOptionPane.ERROR_MESSAGE);
                    num1 = num2 = "";
                    operator = '\0';
                    resultTextField.setText("");
                    return 0;
                }
            default:
                return 0;
        }
    }

    private void updateResult(double result) {
        if (result % 1 != 0) {
            resultTextField.setText(String.valueOf(result));
            commandLine = String.valueOf(result);
        } else {
            int resultInt = (int) result;
            resultTextField.setText(String.valueOf(resultInt));
            commandLine = String.valueOf(resultInt);
        }
        num1 = String.valueOf(result);
        num2 = "";
        operator = '\0';
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculator();
            }
        });
    }
}
