import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder expression;
    private boolean lastWasOperator;

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        expression = new StringBuilder();
        lastWasOperator = false;

        // Display panel
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBackground(new Color(50, 50, 50));
        
        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setBackground(new Color(50, 50, 50));
        display.setForeground(Color.WHITE);
        display.setPreferredSize(new Dimension(300, 60));
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        displayPanel.add(display);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
        buttonPanel.setBackground(new Color(60, 60, 60));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String btn : buttons) {
            JButton button = new JButton(btn);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setFocusPainted(false);
            button.setBackground(new Color(100, 100, 100));
            button.setForeground(Color.WHITE);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        // Clear button
        JButton clearBtn = new JButton("C");
        clearBtn.setFont(new Font("Arial", Font.BOLD, 18));
        clearBtn.setFocusPainted(false);
        clearBtn.setBackground(new Color(200, 100, 100));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.addActionListener(e -> clear());

        // Backspace button
        JButton backspaceBtn = new JButton("←");
        backspaceBtn.setFont(new Font("Arial", Font.BOLD, 18));
        backspaceBtn.setFocusPainted(false);
        backspaceBtn.setBackground(new Color(200, 100, 100));
        backspaceBtn.setForeground(Color.WHITE);
        backspaceBtn.addActionListener(e -> backspace());

        // Bottom panel with clear and backspace
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2, 5, 5));
        bottomPanel.setBackground(new Color(60, 60, 60));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        bottomPanel.add(clearBtn);
        bottomPanel.add(backspaceBtn);

        // Main container
        add(displayPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]")) {
            expression.append(command);
            lastWasOperator = false;
        } else if (command.matches("[+\\-*/.]")) {
            if (expression.length() > 0 && !lastWasOperator) {
                expression.append(command);
                lastWasOperator = true;
            }
        } else if (command.equals("=")) {
            calculate();
        }

        display.setText(expression.toString());
    }

    private void calculate() {
        try {
            double result = eval(expression.toString());
            expression.setLength(0);
            expression.append(result);
            lastWasOperator = true;
            display.setText(expression.toString());
        } catch (Exception ex) {
            display.setText("Error");
            expression.setLength(0);
        }
    }

    private void clear() {
        expression.setLength(0);
        lastWasOperator = false;
        display.setText("");
    }

    private void backspace() {
        if (expression.length() > 0) {
            expression.deleteCharAt(expression.length() - 1);
            lastWasOperator = false;
        }
        display.setText(expression.toString());
    }

    private double eval(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                while (true) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                while (true) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();
                double x;
                int startPos = this.pos;
                if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }
        }.parse();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}