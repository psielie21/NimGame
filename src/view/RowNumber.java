package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * The RowNumber object serves as a border in a GameContainer where it displays
 * all rows with their corresponding row number.
 */
public class RowNumber extends JPanel {
    private static final long serialVersionUID = 3888504916220987699L;

    /**
     * Creates a new RowNumber object with the specified row number.
     * 
     * @param rowNumber The 1-based index of this row.
     */
    public RowNumber(int rowNumber) {
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);

        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());

        add(new JLabel(rowNumber + ":"), BorderLayout.CENTER);
        setBorder(blackLine);
    }
}
