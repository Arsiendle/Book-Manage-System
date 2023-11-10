package MyUtil;

import MyJavabean.User;
import MyUIFrame.LoginUI;

/*
 * @Description: 通用功能的工具类
 */
public final class ComUtil {
//    用于判断是登录还是注册(默认登录0)
    public static int flag = 0;

//    空值判断
    public static void isNull(User input) {
        if (input.getName().length()==0) {
            System.out.println("账号不能为空");
            LoginUI.showDialog("账号不能为空");
        } else if (input.getPassword().length()==0) {
            System.out.println("密码不能为空");
            LoginUI.showDialog("密码不能为空");
        } else {
//            确保账号密码输入完整后进行数据库匹配
//            根据flag来决定是登录 注册还是修改密码
            switch (ComUtil.flag) {
                case 0:
                case 2:
                    DBUtil.dbEnterMatch(input);
                    break;
                case 1:
                    DBUtil.dbRegitMatch(input);
                    break;
                default:
                    System.out.println("数据错误");
                    break;
            }
        }
    }
}
