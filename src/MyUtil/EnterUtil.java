package MyUtil;

import MyJavabean.User;

public final class EnterUtil {
    public static User input = new User();

    public static User EnterStart(String username, String password, int type, int flag){
//        ��¼0 ע��1 �޸�����2
        ComUtil.flag = flag;

//        input
        input.setName(username);
        input.setPassword(password);
        input.setType(type);

//        �������������Ϣ����ComUtil.into()����,���п�ֵ�ж�
         ComUtil.isNull(input);
         return input;
    }

    public static void modifStart(String Password){

            input.setPassword(Password);
            DBUtil.dbModifMatch(input);
    }

}







