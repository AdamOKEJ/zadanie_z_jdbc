import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MainWindow extends JFrame {
    private JTable table1;
    private JButton btnOpenDialog;
    private DefaultTableModel tableModel;

    public MainWindow() {
        super("lista uzytkownikow");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"id", "nazwa"}, 0);
        table1 = new JTable(tableModel);
        add(new JScrollPane(table1), BorderLayout.CENTER);

        btnOpenDialog = new JButton("dodaj uzytkownika");
        add(btnOpenDialog, BorderLayout.SOUTH);

        btnOpenDialog.addActionListener(e -> {
            UserDialog dialog = new UserDialog(this);
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                String name = dialog.getInputName();
                insertToDatabase(name);
                refreshTable();
            }
        });

        setSize(400, 300);
        setLocationRelativeTo(null);
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        String query = "SELECT id, nazwa FROM uzytkownicy";

        try (Connection conn = DbConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getInt("id"), rs.getString("nazwa")});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insertToDatabase(String name) {
        String sql = "INSERT INTO uzytkownicy(nazwa) VALUES(?)";
        try (Connection conn = DbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
