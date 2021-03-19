package View;

import javax.swing.*;

public class ErrorView extends JFrame {
    String data = "Data.csv";

    public ErrorView(String error) {
        JOptionPane.showMessageDialog(null, error + " \n폴더에" + data + " 파일이 존재하지 않습니다.", " File Not Exist", JOptionPane.WARNING_MESSAGE);
    }
}
