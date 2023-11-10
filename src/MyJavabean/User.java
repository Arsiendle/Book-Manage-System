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


    //账号存在判断
    public boolean isExist(String name) {
        return true;//该方法由子类重写，写这行只是防止报错
    }

    //密码验证
    public boolean isPass(String password) {
        return true;//该方法由子类重写，写这行只是防止报错
    }

    //从数据库读取密码并赋值
    public void readPassword() {
    }

    //向数据库增加账户
    public void addAccount(String name, String password) {
    }

    public boolean getIslogin() {
        return islogin;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }
}