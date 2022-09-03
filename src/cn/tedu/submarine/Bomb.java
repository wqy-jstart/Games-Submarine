package cn.tedu.submarine;
//导入Java工具包
import javax.swing.*;

/**
 * 炸弹类-------子类/派生类
 */
public class Bomb extends SeaObject {
    /**
     * 构造方法
     * @param x
     * @param y
     */
    public Bomb(int x,int y){ //因为炸弹的初始坐标是根据战舰的坐标计算出来的，所以不能写死
        /**
         * 调用SeaObject类中第二个构造方法
         */
        super(9,12,x,y,3);
    }
    /**
     * 重写move()移动//(变量中叫修改，方法中叫重写)
     */
   public void move(){
        setY(getY()+getSpeed());//y+(向下)
    }
    /**
     * 在派生类中重写getImage获取图片的方法------签名(方法名，参数列表)要相同
     * @return
     */
    public ImageIcon getImage(){
        return Images.bomb;//返回鱼雷图片
    }

    /**
     * 重写isOutOfBounds
     * @return 若出界则返回true,否则返回false
     */
    public boolean isOutOfBounds(){
       return getY()>=World.HEIGHT; //炸弹的y>=窗口的高，即为越界了
    }
}
