package MyUtil;

import MyJavabean.User;
import MyUIFrame.LoginUI;

/*
 * @Description: ͨ�ù��ܵĹ�����
 */
public final class ComUtil {
//    �����ж��ǵ�¼����ע��(Ĭ�ϵ�¼0)
    public static int flag = 0;

//    ��ֵ�ж�
    public static void isNull(User input) {
        if (input.getName().length()==0) {
            System.out.println("�˺Ų���Ϊ��");
            LoginUI.showDialog("�˺Ų���Ϊ��");
        } else if (input.getPassword().length()==0) {
            System.out.println("���벻��Ϊ��");
            LoginUI.showDialog("���벻��Ϊ��");
        } else {
//            ȷ���˺���������������������ݿ�ƥ��
//            ����flag�������ǵ�¼ ע�ỹ���޸�����
            switch (ComUtil.flag) {
                case 0:
                case 2:
                    DBUtil.dbEnterMatch(input);
                    break;
                case 1:
                    DBUtil.dbRegitMatch(input);
                    break;
                default:
                    System.out.println("���ݴ���");
                    break;
            }
        }
    }
}
