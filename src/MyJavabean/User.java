package MyJavabean;

public class User {
    private String name;
    private String password;
    private int type;
    private boolean islogin=false;

    public User() {
    }

    public User(String name, String password, int type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }

    //getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    //�˺Ŵ����ж�
    public boolean isExist(String name) {
        return true;//�÷�����������д��д����ֻ�Ƿ�ֹ����
    }

    //������֤
    public boolean isPass(String password) {
        return true;//�÷�����������д��д����ֻ�Ƿ�ֹ����
    }

    //�����ݿ��ȡ���벢��ֵ
    public void readPassword() {
    }

    //�����ݿ������˻�
    public void addAccount(String name, String password) {
    }

    public boolean getIslogin() {
        return islogin;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }
}