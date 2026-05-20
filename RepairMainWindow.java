import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class RepairMainWindow extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd;

    public RepairMainWindow() {
        super("system rejestracji usterki");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"id", "sprzet", "koszt_naprawy"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd = new JButton("dodaj nowe zgloszenie");
        add(btnAdd, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            RepairDialog dialog = new RepairDialog(this);
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                insertData(dialog.getInputSprzet(), dialog.getInputKoszt());
                refreshTable();
            }
        });

        setSize(600, 400);
        setLocationRelativeTo(null);
        refreshTable();
    }

    private void insertData(String sprzet, double koszt) {
        String sql = "INSERT INTO zgloszenia(sprzet, koszt_naprawy) VALUES(?, ?)";
        try (Connection conn = DbRepairConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sprzet);
            pstmt.setDouble(2, koszt);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        String sql = "SELECT id, sprzet, koszt_naprawy FROM zgloszenia";
        try (Connection conn = DbRepairConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("sprzet"),
                        rs.getDouble("koszt_naprawy")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
