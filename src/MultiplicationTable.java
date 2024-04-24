import java.awt.*;
import java.awt.event.*;

public class MultiplicationTable extends Frame {
    private static final int NUM_ROWS = 7; // Number of multiplication rows
    private static final int[] FIXED_NUMBERS = {5, 10, 15, 20, 25, 30, 35}; // Fixed numbers for each row

    private TextField[] inputNumbers = new TextField[NUM_ROWS];
    private Label[] resultLabels = new Label[NUM_ROWS];
    private Label totalSumLabel; // Label to show the total sum

    public MultiplicationTable() {
        super("Multiplication Table");
        setLayout(new GridLayout(NUM_ROWS + 1, 1)); // Modified to NUM_ROWS + 1 for the total sum row

        // Initialize total sum label
        totalSumLabel = new Label("Total Sum: 0", Label.RIGHT);

        // Create components for each row
        for (int i = 0; i < NUM_ROWS; i++) {
            Panel row = new Panel();
            row.setLayout(new FlowLayout());

            inputNumbers[i] = new TextField(10);
            Label multiplyLabel = new Label(" x " + FIXED_NUMBERS[i] + " = ");
            resultLabels[i] = new Label("0"); // Initialize with 0

            final int currentIndex = i;
            inputNumbers[i].addTextListener(new TextListener() {
                public void textValueChanged(TextEvent e) {
                    calculateResult(e);
                    updateTotalSum(); // Update total sum whenever any input changes
                }
            });
            inputNumbers[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (currentIndex < NUM_ROWS - 1) {
                            inputNumbers[currentIndex + 1].requestFocus(); // Move focus to next input
                        }
                    }
                }
            });

            row.add(inputNumbers[i]);
            row.add(multiplyLabel);
            row.add(resultLabels[i]);

            add(row);
        }

        // Adding the total sum label at the bottom of the frame
        Panel totalSumPanel = new Panel();
        totalSumPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        totalSumPanel.add(totalSumLabel);
        add(totalSumPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setSize(500, 350); // Adjust size to accommodate total sum row
        setVisible(true);
    }

    private void calculateResult(TextEvent e) {
        TextField source = (TextField) e.getSource();
        int index = findTextFieldIndex(source);
        if (index >= 0) {
            try {
                int userNumber = source.getText().isEmpty() ? 0 : Integer.parseInt(source.getText());
                int result = userNumber * FIXED_NUMBERS[index];
                resultLabels[index].setText(String.valueOf(result));
            } catch (NumberFormatException ex) {
                resultLabels[index].setText("0"); // Set to 0 if invalid input
            }
        }
    }

    private void updateTotalSum() {
        int sum = 0;
        for (Label label : resultLabels) {
            try {
                sum += Integer.parseInt(label.getText());
            } catch (NumberFormatException ex) {
                // Ignore any non-numeric or reset entries, treating them as 0
            }
        }
        totalSumLabel.setText("Total Sum: " + sum);
    }

    private int findTextFieldIndex(TextField textField) {
        for (int i = 0; i < inputNumbers.length; i++) {
            if (inputNumbers[i] == textField) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        new MultiplicationTable();
    }
}
