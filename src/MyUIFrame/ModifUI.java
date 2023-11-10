package MyUIFrame;

import MyUtil.EnterUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ModifUI extends JFrame {
    private static ModifUI instance;

    public static ModifUI getInstance() {
        if (instance == null) {
            instance = new ModifUI();
        }
        return instance;
    }

    public void closeInterface() {
        this.setVisible(false); // ���ص�ǰ����
    }

    public void showInterface() {
        // ����˺ź������ı���
        newPasswordField.setText("");
        repeatPasswordField.setText("");

        this.setVisible(true); // ��ʾ��ǰ����
    }

    private JTextField newPasswordField;
    private JPasswordField repeatPasswordField;

    private ModifUI() {
        initView();
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);


        // �������ǩ
        JLabel newPasswordLabel = new JLabel("������:");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(newPasswordLabel, constraints);

        // �����������
        newPasswordField = new JTextField(20);
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(newPasswordField, constraints);

        // �ظ������ǩ
        JLabel repeatPasswordLabel = new JLabel("�ظ�����:");
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(repeatPasswordLabel, constraints);

        // �ظ����������
        repeatPasswordField = new JPasswordField(20);
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(repeatPasswordField, constraints);

        // ע�ᰴť
        JButton registerButton = new JButton("ȷ��");
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        panel.add(registerButton, constraints);

        // ע�ᰴť����¼�
        registerButton.addActionListener(e -> {
            String newPassword = new String(newPasswordField.getText());
            String repeatPassword = new String(repeatPasswordField.getPassword());

            System.out.println("������: " + newPassword);
            System.out.println("�ظ�����: " + repeatPassword);

            if (Objects.equals(newPassword, "")) {
                showDialog("�����벻��Ϊ��");
                return;
            } else if (Objects.equals(repeatPassword, "")) {
                showDialog("�ظ����벻��Ϊ��");
                return;
            } else if (!newPassword.equals(repeatPassword)) {
                showDialog("��������ظ����벻ƥ��");
                return;
            }

            // ִ���޸������߼�
            EnterUtil.modifStart(newPassword);
        });

        // �������ӵ�������
        add(panel);

        setTitle("�޸�����");  // ���ô��ڱ���
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // ���ô��ڹرղ���
        pack();  // ����Ӧ��С
        setLocationRelativeTo(null);  // ���ھ�����ʾ
        setVisible(true);  // ��ʾ����
    }

    private void initView() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  // ���������ʽΪϵͳ���
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ����
    public static void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}