import javax.swing.*;
import java.awt.*;

public class RepairDialog extends JDialog {
    private JTextField txtSprzet;
    private JTextField txtKoszt;
    private JButton btnOk;
    private JButton btnCancel;

    private String inputSprzet;
    private double inputKoszt;
    private boolean isConfirmed = false;

    public RepairDialog(JFrame parent) {
        super(parent, "dodaj nowe zgloszenie", true);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("nazwa sprzetu:"));
        txtSprzet = new JTextField();
        add(txtSprzet);

        add(new JLabel("koszt naprawy:"));
        txtKoszt = new JTextField();
        add(txtKoszt);

        btnOk = new JButton("zatwierdz");
        btnCancel = new JButton("anuluj");
        add(btnOk);
        add(btnCancel);

        btnOk.addActionListener(e -> {
            if (txtSprzet.getText().trim().isEmpty() || txtKoszt.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "pola nie moga byc puste", "blad", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                inputSprzet = txtSprzet.getText().trim();
                inputKoszt = Double.parseDouble(txtKoszt.getText().trim());
                isConfirmed = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "koszt musi byc poprawna liczba", "blad", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> {
            isConfirmed = false;
            dispose();
        });

        setSize(300, 150);
        setLocationRelativeTo(parent);
    }

    public String getInputSprzet() { return inputSprzet; }
    public double getInputKoszt() { return inputKoszt; }
    public boolean isConfirmed() { return isConfirmed; }
}
