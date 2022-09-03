package cn.tedu.submarine;
//导入Java工具包
import javax.swing.ImageIcon;

/**
 * 创建战舰类-------子类/派生类
 */
public class Battleship extends SeaObject {
    private int life;    //命------成员变量(私有化)，项目中数据通常私有
    /**
     * 创建战舰类构造方法
     */
    public Battleship(){//除生命值以外其他的属性都在SeaObject类中第二个构造方法中
        //构造方法的作用是复用给成员变量(属性)初始化
        /**
         * 调用SeaObject(Super)类中第二个构造方法
         */
       super(66,26,270,124,20);//调用并传参
        life=5;
    }
    /**
     * 重写move()移动//(变量中叫修改，方法中叫重写)
     */
    public void move(){
        System.out.println("战舰移动了！");
    }
    /**
     * 在派生类中重写getImage获取图片的方法------签名(方法名，参数列表)要相同
     * @return
     */
    public ImageIcon getImage(){
        return Images.battleship;//返回战舰图片
    }

    /**
     * 发射炸弹-----生成炸弹对象
     * @return 炸弹对象
     */
    public Bomb shootBomb() {
        return new Bomb(this.getX(), this.getY());//炸弹的初始坐标就是战舰的坐标
    }

    /**
     * 战舰左移
     */
    public void moveLeft(){
        if(getX()>=0){
           setX(getX()-getSpeed());
        }
    }

    /**
     * 战舰右移
     */
    public void moveRight() {
        if (getX() <= World.WIDTH - this.getWidth()) {
            setX(getX()+getSpeed());
        }
    }

    /**
     * 所增的命数
     * @param num
     */
    public void addLife(int num){
        life+=num; //命数增一
    }

    /**
     * 获取战舰的命数
     * @return 返回战舰的命数
     */
    public int getLife(){
        return life; //返回命数
    }
    /**
     * 战舰减命
     */
    public void subtractLife(){
        life-=1; //命数减1
    }
}
