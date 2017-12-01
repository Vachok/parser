import javax.swing.*;
import java.awt.*;

/**
 * @since 0.171129.5
 */
public class SimpleGUI extends JFrame {
    private final JTextField input = new JTextField("" , 5);
    private final JRadioButton radio1 = new JRadioButton("Выбери меня");
    private final JCheckBox check = new JCheckBox("Отметь" , false);

    public SimpleGUI() {
        super("Simple Example");
        this.setBounds(200 , 150 , 650 , 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2 , 2 , 3 , 2));
        JLabel label = new JLabel("Input:");
        container.add(label);
        container.add(input);

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        JRadioButton radio2 = new JRadioButton("Выбери меня");
        group.add(radio2);
        container.add(radio1);
        radio1.setSelected(true);
        container.add(radio2);
        container.add(radio2);
        container.add(check);
        JButton button = new JButton("Press");
        //button.addActionListener(new ButtonEventListener());
        container.add(button);


    }

    /**
     *
     */
/*
    public class ButtonEventListener implements ButtonEventListener {


        @Override
        public void actionPerformed( ActionEvent e ) {
            String message = "";
            message += "Нажал на кнопку и...\n";
            message += "Это " + input.getText() + "\n";
            message += (radio1.isSelected() ? "Вачок\n" : "Барсучок" + " выбран\n");
            message += "Ты " + ((check.isSelected()) ? "молодец" : "холодец\n");
            JOptionPane.showMessageDialog(null , message , "Выводы" , JOptionPane.PLAIN_MESSAGE);

        }

    }
*/

}
