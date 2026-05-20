import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        DbRepairConnector.initDatabase();
        SwingUtilities.invokeLater(() -> new RepairMainWindow().setVisible(true));
    }
}
