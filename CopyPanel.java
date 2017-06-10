import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by ugyan on 2017.06.08..
 */
public class CopyPanel extends JFrame {

    MultiProcessFrame mpf;

    public CopyPanel() {
        mpf = new MultiProcessFrame();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setSize(600, 200);
        setVisible(true);
        JLabel fromLabel = new JLabel("From");
        JTextField from = new JTextField(30);
        JLabel toLabel = new JLabel("To");
        JTextField to = new JTextField(30);
        JButton okButton = new JButton("Ok");
        JCheckBox checkBox = new JCheckBox("Overwrite");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mpf.setVisible(true);
                if (new File(to.getText()).exists() && !checkBox.isSelected()) { return; }
                mpf.addProcess(new Process(checkBox.isSelected(), from.getText(), to.getText(), new JProgressBar()));

            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(fromLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(from, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(toLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(to, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(checkBox, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(okButton, gbc);
    }

    public static void main(String[] args) {
        new CopyPanel().setVisible(true);
    }
}

