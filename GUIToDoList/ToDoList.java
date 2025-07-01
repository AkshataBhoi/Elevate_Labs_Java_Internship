
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ToDoList {

    public static void main(String args[]) {
        JFrame f = new JFrame("To Do List");
        f.setSize(400, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel l = new JLabel("Enter Task: ");
        JTextField tf = new JTextField(10);
        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");

        panel.add(l);
        panel.add(tf);
        panel.add(addBtn);
        panel.add(deleteBtn);
        f.add(panel);

        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));

        f.add(taskPanel);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String taskText = tf.getText().trim();
                if (!taskText.isEmpty()) {
                    JCheckBox checkbox = new JCheckBox(taskText);
                    taskPanel.add(checkbox);
                    taskPanel.revalidate();
                    taskPanel.repaint();
                    tf.setText("");
                }
            }
        });
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component[] components = taskPanel.getComponents();
                for (int i = components.length - 1; i >= 0; i--) {
                    if (components[i] instanceof JCheckBox) {
                        JCheckBox cb = (JCheckBox) components[i];
                        if (cb.isSelected()) {
                            taskPanel.remove(cb);
                        }
                    }
                }
                taskPanel.revalidate();// update layout and component arragement
                taskPanel.repaint();// updates design on screen
            }
        });
        f.setVisible(true);

    }

}
