package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    
    private JTextField displayField;
    private JButton[] numberButtons;
    private JButton[] functionButtons;
    private JButton addButton, subButton, mulButton, divButton;
    private JButton decButton, equButton, delButton, clrButton;
    private JPanel panel;
    
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;
    
    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 550);
        setLayout(null);
        setResizable(false);
        
        // Display field
        displayField = new JTextField();
        displayField.setBounds(50, 25, 300, 50);
        displayField.setFont(new Font("Arial", Font.PLAIN, 32));
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        add(displayField);
        
        // Initialize buttons
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 24));
            numberButtons[i].setFocusable(false);
            numberButtons[i].addActionListener(this);
        }
        
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Del");
        clrButton = new JButton("C");
        
        functionButtons = new JButton[]{addButton, subButton, mulButton, divButton, decButton, equButton, delButton, clrButton};
        
        for (JButton button : functionButtons) {
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.setFocusable(false);
            button.addActionListener(this);
        }
        
        // Panel for buttons
        panel = new JPanel();
        panel.setBounds(50, 100, 300, 400);
        panel.setLayout(new GridLayout(5, 4, 10, 10));
        
        // Add buttons to panel
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(addButton);
        
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(mulButton);
        
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);
        
        panel.add(delButton);
        panel.add(clrButton);
        
        add(panel);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Number buttons
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                displayField.setText(displayField.getText().concat(String.valueOf(i)));
            }
        }
        
        // Decimal button
        if (e.getSource() == decButton) {
            if (!displayField.getText().contains(".")) {
                displayField.setText(displayField.getText().concat("."));
            }
        }
        
        // Clear button
        if (e.getSource() == clrButton) {
            displayField.setText("");
            num1 = 0;
            num2 = 0;
            result = 0;
        }
        
        // Delete button
        if (e.getSource() == delButton) {
            String currentText = displayField.getText();
            if (currentText.length() > 0) {
                displayField.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
        
        // Operation buttons
        if (e.getSource() == addButton) {
            num1 = Double.parseDouble(displayField.getText());
            operator = '+';
            displayField.setText("");
        }
        
        if (e.getSource() == subButton) {
            num1 = Double.parseDouble(displayField.getText());
            operator = '-';
            displayField.setText("");
        }
        
        if (e.getSource() == mulButton) {
            num1 = Double.parseDouble(displayField.getText());
            operator = '*';
            displayField.setText("");
        }
        
        if (e.getSource() == divButton) {
            num1 = Double.parseDouble(displayField.getText());
            operator = '/';
            displayField.setText("");
        }
        
        // Equals button
        if (e.getSource() == equButton) {
            num2 = Double.parseDouble(displayField.getText());
            
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        displayField.setText("Error");
                        return;
                    }
                    break;
            }
            
            displayField.setText(String.valueOf(result));
            num1 = result;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}
