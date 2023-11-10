package MyUtil;

import MyJavabean.User;

public final class EnterUtil {
    public static User input = new User();

    public static User EnterStart(String username, String password, int type, int flag){
//        登录0 注册1 修改密码2
        ComUtil.flag = flag;

//        input
        input.setName(username);
        input.setPassword(password);
        input.setType(type);

//        将所有输入的信息传入ComUtil.into()方法,进行空值判断
         ComUtil.isNull(input);
         return input;
    }

    public static void modifStart(String Password){

            input.setPassword(Password);
            DBUtil.dbModifMatch(input);
    }

}







