package cn.tedu.submarine;
//导入Java工具包
import javax.swing.ImageIcon;

//方法通常公开，因为需要被调用
/**
 * 侦查潜艇类方法  继承了海洋对象类并且实现了得分的接口
 */
public class ObserveSubmarine extends SeaObject implements EnemyScore {
//--------侦查潜艇类构造方法，在main中创建此类对象后会调用此无参构造
    /**
     * 公开构造侦查潜艇的方法
     */
    public ObserveSubmarine() {
//侦查潜艇类继承了海洋对象类
//系统自动调用超类(海洋对象类)的构造方法，因超类构造方法中含有参数，因此需要在第一句调用并且传参
        /**
         * 利用super调用超类的构造方法及传参
         */
        super(63, 19);
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
        //图片为静态变量---需类名打点调用
        return Images.obsersubm;//返回侦查潜艇图片
    }
    public Mine shootMine1(){
        //int x =this.x+this.width;
        //int y =this.y-11;
        //return new Mine(x,y);
        return new Mine(this.getX()+this.getWidth(),this.getY()-11);
    }

    /**
     * 重写得分接口中的方法
     * @return分数
     */
    public int getScore(){
        return 10; //打掉侦查潜艇，得10分
    }
}
