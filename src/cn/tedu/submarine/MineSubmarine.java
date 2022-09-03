package cn.tedu.submarine;
//导入Java工具包
import javax.swing.ImageIcon;

/**
 * 水雷潜艇类  继承海洋对象类并且实现得命的接口
 */
public class MineSubmarine extends SeaObject implements EnemyLive{
    /**
     * 构造方法
     */
    public MineSubmarine(){
        super(63,19);
    }
    /**
     * 重写move()移动方法(变量中叫修改，方法中叫重写)
     */
    public void move(){
        setX(getX()+getSpeed());//x+(向右)
    }
    /**
     * 在派生类中重写getImage获取图片的方法------签名(方法名，参数列表)要相同
     * @return
     */
    public ImageIcon getImage(){
        return Images.minesubm;//返回水雷潜艇图片
    }
    /**
     * 发射水雷------生成水雷对象
     * @return
     */
    public Mine shootMine(){
       //int x =this.x+this.width;
       //int y =this.y-11;
       //return new Mine(x,y);
        return new Mine(this.getX()+this.getWidth(),this.getY()-11);
    }

    /**
     * 重写得命接口中的方法
     * @return命数
     */
    public int getLive(){
        return 1; //打掉水雷潜艇，得一条命
    }
}
