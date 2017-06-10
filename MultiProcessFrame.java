import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MultiProcessFrame extends JFrame {

    List<Process> processList = new ArrayList<>();
    List<JButton> stopButtonList = new ArrayList<>();
    GridBagConstraints gbc;
    int xPosition = 0;
    int yPosition = 0;

    public MultiProcessFrame() {

        gbc = new GridBagConstraints();
        setSize(500, 500);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JButton stopAll = new JButton("Stop All");
        gbc.gridx = xPosition;
        gbc.gridy = yPosition;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(stopAll, gbc);
        stopAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stopAllProcess();
            }
        });
    }

    public void addProcess(Process process) {

        JPanel processPanel = new JPanel();
        processPanel.setLayout(new GridBagLayout());
        JTextField processFrom = new JTextField(30);
        processFrom.setText(process.from);
        processFrom.setEditable(false);
        JTextField processTo = new JTextField(30);
        processTo.setText(process.to);
        processTo.setEditable(false);
        JButton stopButton = new JButton("Stop");
        JLabel fromLabel = new JLabel("From");
        JLabel toLabel = new JLabel("To");

        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        processPanel.add(fromLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        processPanel.add(processFrom, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        processPanel.add(toLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        processPanel.add(processTo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        processPanel.add(process.jProgressBar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        processPanel.add(stopButton, gbc);

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                process.cancel(true);
                stopButton.setEnabled(false);
                stopButton.setText("Stopped");
            }
        });

        process.jProgressBar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (process.jProgressBar.getValue() == 100) {
                    stopButton.setEnabled(false);
                    stopButton.setText("Done");
                }
            }
        });

        yPosition++;
        gbc.gridx = xPosition;
        gbc.gridy = yPosition;

        processPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.add(processPanel, gbc);
        setVisible(true);
        processList.add(process);
        stopButtonList.add(stopButton);
        executeProcess(process);
    }

    public void executeProcess(Process process) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                process.execute();
            }
        });

    }

    public void stopAllProcess() {
        for (Process process: processList) {
            process.cancel(true);
        }

        for (JButton stopButton: stopButtonList) {
            stopButton.setText("Stopped");
            stopButton.setEnabled(false);
        }
    }
}