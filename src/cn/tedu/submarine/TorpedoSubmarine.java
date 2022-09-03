package cn.tedu.submarine;
//导入Java工具包
import javax.swing.ImageIcon;

/**
 * 创建鱼雷潜艇类  继承了海洋对象类并且实现了得分的接口
 */
public class TorpedoSubmarine extends SeaObject implements EnemyScore{//鱼雷潜艇类
    /**
     * 创建构造方法
     */
    public TorpedoSubmarine(){
        /**
         * 调用父类构造方法及传参
         */
        super(64,20);
    }
    /**
     * 重写move()移动//(变量中叫修改，方法中叫重写)
     */
   public void move(){
       setX(getX()+getSpeed());//x+(向右)
    }
    /**
     * 在派生类中重写getImage获取图片的方法------签名(方法名，参数列表)要相同
     * @return
     */
    public ImageIcon getImage(){
        return Images.torpesubm;//返回鱼雷潜艇图片
    }

    /**
     * 重写得分接口中的方法
     * @return 分数
     */
    public int getScore(){
        return 40; //打掉鱼雷潜艇，得40分
    }
}
