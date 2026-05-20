import javax.swing.*;
import java.awt.*;

public class UserDialog extends JDialog {
    private JTextField txtName;
    private JButton buttonOK;
    private JButton buttonCancel;

    private String inputName;
    private boolean isConfirmed = false;

    public UserDialog(JFrame parent) {
        super(parent, "dodaj uzytkownika", true);
        setLayout(new FlowLayout());

        add(new JLabel("nazwa:"));
        txtName = new JTextField(15);
        add(txtName);

        buttonOK = new JButton("ok");
        buttonCancel = new JButton("anuluj");
        add(buttonOK);
        add(buttonCancel);

        buttonOK.addActionListener(e -> {
            if (txtName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "pole nie moze byc puste", "blad", JOptionPane.ERROR_MESSAGE);
            } else {
                inputName = txtName.getText().trim();
                isConfirmed = true;
                dispose();
            }
        });

        buttonCancel.addActionListener(e -> {
            isConfirmed = false;
            dispose();
        });

        setSize(250, 150);
        setLocationRelativeTo(parent);
    }

    public String getInputName() { return inputName; }
    public boolean isConfirmed() { return isConfirmed; }
}
