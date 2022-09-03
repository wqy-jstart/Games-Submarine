package cn.tedu.submarine;
//导入Java工具包
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.util.Random;

    /**
 * 海洋对象------------超类/父类/基类
 */
public abstract class SeaObject {//抽象类不完整，Seaobject不能被实例化(不能new对象)
    public static final int LIVE = 0; //活着的
    public static final int DEAD = 1; //死了的
    protected int state = LIVE; //当前状态(默认为活着的)
    //成员变量
    //实际项目中成员变量都应该是private的因暂时没说get/set方法，所以先设计成protected
    private int width;  //宽
    private int height; //高
    private int x;      //x坐标
    private int y;      //y坐标
    private int speed;  //速度

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }
        //因三种潜艇的宽/高是不一样的，所以数据不能写死，需传参写活
    //因为三种潜艇的x/y/speed代码都是一样的，所以数据可以写死，不需要传参
    /**
     * 构造方法，为侦查潜艇、鱼雷潜艇、水雷潜艇准备
     * @param width
     * @param height
     */
    public SeaObject(int width,int height){//此构造专门给侦查潜艇、鱼雷潜艇、水雷潜艇准备的
        this.width=width;
        this.height=height;
        //随着宽和高的变换，x/y坐标都会改变
        x=-width;
        Random rand=new Random();
        //World.HEIGHT被设为常量，用类名打点访问，不能改变
        y=rand.nextInt(World.HEIGHT-height-150+1)+150;
        speed =rand.nextInt(3)+1;
    }
    /**
         * 重载上面的构造，专门为战舰/炸弹/水雷准备的
         * @param width
         * @param height
         * @param x
         * @param y
         * @param speed
         */
    public SeaObject(int width,int height,int x,int y,int speed){
       this.width=width;
       this.height=height;
       this.x=x;
       this.y=y;
       this.speed=speed;
    }
    /**
         * 抽象方法，只定义，不去具体实现(没有大括号),包含抽象方法的类一定要是抽象类
         */
    public abstract void move();
    /**
         * 获取图片的抽象方法，强制派生类去重写
         * @return
         */
    public abstract ImageIcon getImage();
    /**
         * 判断状态
         * @return
         */
    public boolean islive(){
        return state==LIVE; //若state为LIVE，表示活着的，返回true，否则返回false
    }
    //3）判断对象是否是死了的
    public boolean isDead(){
        return state==DEAD; //若state为DEAD，表示死了的，返回true，否则返回false
    }
    /**
         * 专门画对象的方法   g:画笔
         * @param g
         */
    public void paintImage(Graphics g){
        if(this.islive()){  //若活着的
            this.getImage().paintIcon(null,g,this.x,this.y);//-----不要求掌握，将图画到对应战舰的位置
        }
    }
        /**
         * 检测潜艇是否越界
         */
        public boolean isOutOfBounds(){
            return this.x>=World.WIDTH; //潜艇的x>=窗口的宽，即为出界了
        }

        /**
         * 判断是否碰撞的方法 this表示一个对象
         * @param other 表示另一个对象
         * @return 若撞上则返回true，否则返回false
         */
        public boolean isHit(SeaObject other){
            //假设：this为潜艇，other为炸弹
            int x1 = this.x-other.width;  //x1:潜艇的x-炸弹的宽
            int x2 = this.x+this.width;   //x2:潜艇的x+潜艇的宽
            int y1 = this.y-other.height; //y1:潜艇的y-炸弹的高
            int y2 = this.y+this.height;  //y2:潜艇的y+潜艇的高
            int x =other.x; //x:炸弹的x
            int y =other.y; //y:炸弹的y
            return x>=x1 && x<=x2 && y>=y1 && y<=y2;
        }
        public void goDead(){
            state = DEAD; //将当前状态修改为死了的
        }
    }

