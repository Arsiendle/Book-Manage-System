package MyUIFrame;

import MyUtil.EnterUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RegitUI extends JFrame {
    private static RegitUI instance;

    public static RegitUI getInstance() {
        if (instance == null) {
            instance = new RegitUI();
        }
        return instance;
    }

    public void closeInterface() {
        this.setVisible(false); // ���ص�ǰ����
    }

    public void showInterface() {
        // ����˺ź������ı���
        accountField.setText("");
        newPasswordField.setText("");
        repeatPasswordField.setText("");
        // ���ý�ɫ������Ĭ��ѡ��
        roleComboBox.setSelectedIndex(0);

        this.setVisible(true); // ��ʾ��ǰ����
    }

    private JTextField accountField;
    private JPasswordField newPasswordField;
    private JPasswordField repeatPasswordField;
    private JComboBox<String> roleComboBox;

    private RegitUI() {
        initView();
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // �˺ű�ǩ
        JLabel accountLabel = new JLabel("�˺�:");
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(accountLabel, constraints);

        // �˺������
        accountField = new JTextField(20);
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(accountField, constraints);

        // �������ǩ
        JLabel newPasswordLabel = new JLabel("������:");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(newPasswordLabel, constraints);

        // �����������
        newPasswordField = new JPasswordField(20);
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

        // ��ɫ��ǩ
        JLabel roleLabel = new JLabel("��ɫ:");
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        panel.add(roleLabel, constraints);

        // ��ɫ������
        String[] roles = {"��ͨ�û�", "����Ա"};
        roleComboBox = new JComboBox<>(roles);
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        panel.add(roleComboBox, constraints);

        // ע�ᰴť
        JButton registerButton = new JButton("ȷ��");
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        panel.add(registerButton, constraints);

        // ע�ᰴť����¼�
        registerButton.addActionListener(e -> {
            String username = accountField.getText();
            String newPassword = new String(newPasswordField.getPassword());
            String repeatPassword = new String(repeatPasswordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            System.out.println("�˺�: " + username);
            System.out.println("������: " + newPassword);
            System.out.println("�ظ�����: " + repeatPassword);
            System.out.println("��ɫ: " + role);

            if (!newPassword.equals(repeatPassword)) {
                showDialog("��������ظ����벻ƥ��");
                return;
            }

            // ִ��ע���߼�
            EnterUtil.EnterStart(username, newPassword, Objects.equals(role, "����Ա") ? 1 : 0,1);
        });

        // �������ӵ�������
        add(panel);

        setTitle("ע��");  // ���ô��ڱ���
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