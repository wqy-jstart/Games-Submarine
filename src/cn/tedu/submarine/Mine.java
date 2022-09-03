package cn.tedu.submarine;

import javax.swing.*;
/**
 * 创建一个水雷类-------子类/派生类
 */
public class Mine extends SeaObject {
    /**
     * 创建水雷的构造方法及传参
     * @param x
     * @param y
     */
    public Mine(int x,int y){ //因为水雷的初始坐标是根据战舰的坐标计算出来的，所以不能写死
        /**
         * 调用SeaObject超类中第二个构造方法并传参
         */
        super(11,11,x,y,1);
    }
    /**
     * 重写move()移动//(变量中叫修改，方法中叫重写)
     */
    public void move(){
        setY(getY()-getSpeed());//y-(向上)
    }
    /**
     * 在派生类中重写getImage获取图片的方法------签名(方法名，参数列表)要相同
     * @return
     */
    public ImageIcon getImage(){
        return Images.mine;//返回水雷图片
    }
    public boolean isOutOfBounds(){
        return getY()<=150-this.getHeight(); //水雷的y<=150-水雷的高，即为出界了
    }
}
