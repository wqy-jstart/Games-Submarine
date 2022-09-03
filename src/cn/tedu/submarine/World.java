package cn.tedu.submarine;
//导入Java工具包
import javax.swing.JFrame;//框架
import javax.swing.JPanel;//面板
import java.awt.Graphics;//画图片
import java.util.Arrays;//数组扩容
import java.util.Timer;//计时器
import java.util.TimerTask;//计时器
import java.util.Random;//随机数
import java.awt.event.KeyAdapter;//窃听器-----不要求掌握
import java.awt.event.KeyEvent;//触发事件
//整个游戏窗口
/**
 * World类继承面板的属性和方法，设定整个游戏的窗口
 */
public class World extends JPanel {//----------继承面板
//static final修饰，必须声明同时初始化------成员变量
//建议：常量名所有字母都大写，多个单词用_分隔
    /**
     * 设定两个常量，并且赋值
     */
    public static final int WIDTH =641;
    public static final int HEIGHT =479;

    /**
     * 运行状态
     */
    public static final int RUNNING =0;
    /**
     * 暂停状态
     */
    public static final int PAUSE =1;
    /**
     * 游戏结束状态
     */
    public static final int GAME_OVER =2;
    private int state =RUNNING;//当前状态默认为运行状态

    /**
     * 如下一堆对象就是窗口中你所看到的对象
     */
    private Battleship ship = new Battleship();//战舰对象
    private SeaObject[] submarines = {};//潜艇数组，引用类型如果不初始化默认为null
    private Mine[] mines = {};//水雷数组
    private Bomb[] bombs = {};//炸弹数组
    /**
     * 随机生成(侦查、鱼雷、水雷)潜艇对象
     * @return
     */
    private SeaObject nextSubmarine(){
        Random rand = new Random();//new随机数对象
        int type =rand.nextInt(20);//0到19之间
        //按照概率去Retrun潜艇的类型
        if(type<6){
            return new ObserveSubmarine();//返回对象给调用方
        }else if(type<12){
            return new TorpedoSubmarine();
        }else {
            return new MineSubmarine();
        }
    }
    /**
     *创建潜艇入场的方法
     */
    private int subEnterIndex=0;//潜艇入场计数
    private void submarineEnterAction() {//该方法每10毫秒走一次
        subEnterIndex++;//每10毫秒增1
        if (subEnterIndex % 10 == 0) { //语句块每400(40*10)毫秒走一次
            /**
             * 直接让整个潜艇数组入场
             */
            SeaObject obj = nextSubmarine();//获取对象obj
            submarines = Arrays.copyOf(submarines, submarines.length + 1);//扩容
            submarines[submarines.length - 1] = obj; //将获取的对象添加到最后一个元素里
        }
    }
    /**
     * 创建水雷入场的方法
     */
    private int mineEnterIndex =0;//水雷入场计数
    private void mineEnterAction() {//该方法每10毫秒走一次
        mineEnterIndex++;//每10毫秒增1
        if (mineEnterIndex % 100== 0) { //每1000(100*10)毫秒走一次
            /**
             * 水雷是由水雷潜艇释放，所以需遍历以水雷潜艇为引用类型来装水雷
             */
            for (int i = 0; i < submarines.length; i++) { //遍历所有潜艇
                if (submarines[i] instanceof MineSubmarine) { //若潜艇为水雷潜艇
                    MineSubmarine ms = (MineSubmarine) submarines[i]; //将潜艇强转为水雷潜艇类型
                    Mine obj = ms.shootMine(); //该方法会返回一个水雷对象----获取水雷对象
                    mines = Arrays.copyOf(mines,mines.length+1); //扩容
                    mines[mines.length-1] = obj; //将obj添加到mines最后一个元素上
                }
            }
        }
    }
    /**
     * 创建海洋对象移动的方法
     */
    private void moveAction(){ //每10毫秒走一次
        for (int i = 0; i < submarines.length; i++) {
            submarines[i].move();
        }
        for (int i = 0; i < mines.length; i++) {
            mines[i].move();
        }
        for (int i = 0; i < bombs.length; i++) {
            bombs[i].move();
        }
    }

    /**
     * 删除越界的数组
     */
    private void outOfBoundsAction(){ //每10毫秒走一次
        for (int i = 0; i < submarines.length; i++) { //遍历所有潜艇
            if(submarines[i].isOutOfBounds()||submarines[i].isDead()){ //若出界了，将其从数组里面移除
                submarines[i]=submarines[submarines.length-1]; //将越界的对象替换成最后一个
                submarines=Arrays.copyOf(submarines,submarines.length-1);//缩容
            }
        }
        for (int i = 0; i < mines.length; i++) { //遍历所有水雷
            if(mines[i].isOutOfBounds()||mines[i].isDead()){ //若水雷出界
                mines[i]=mines[mines.length-1]; //将出界的水雷替换成最后一个
                mines=Arrays.copyOf(mines,mines.length-1); //缩容
            }
        }
        for (int i = 0; i < bombs.length; i++) { //遍历所有炸弹
            if(bombs[i].isOutOfBounds()||bombs[i].isDead()){ //若炸弹出界了
                bombs[i]=bombs[bombs.length-1]; //将出界的炸弹替换成最后一个
                bombs=Arrays.copyOf(bombs,bombs.length-1); //缩容
            }
        }
    }
    /**
     * 炸弹与潜艇的碰撞
     */
    private int score=0;
    private void bombBangAction(){ //每10毫秒走一次
        for (int i = 0; i < bombs.length; i++) { //遍历所有炸弹
            Bomb b = bombs[i];  //获取每个炸弹
            for (int j = 0; j < submarines.length; j++) { //遍历所有潜艇
                SeaObject s = submarines[j]; //获取每一个潜艇
                if(b.islive() && s.islive() && s.isHit(b)){ //若都活着还撞上了
                    s.goDead(); //潜艇去死
                    b.goDead(); //炸弹去死
                    if(s instanceof EnemyScore){ //若s指向的对象跟得分接口有关
                        EnemyScore es = (EnemyScore)s;//将s指向的对象强转为得分接口类
                        score+=es.getScore(); //该对象调用得分方法并返回分数
                    }
                    if(s instanceof EnemyLive){ //若s指向的对象跟得命接口有关
                        EnemyLive el = (EnemyLive)s;//将s指向的对象强转为得命接口类
                        int num = el.getLive(); //将方法返回的命数赋值给num
                        ship.addLife(num);//再把num传给战舰
                    }
                }
            }
        }
    }

    /**
     * 水雷与战舰的碰撞
     */
    private void mineBangaction(){ //每10毫秒走一次
        for (int i = 0; i < mines.length; i++) {
            Mine m = mines[i];
            if(ship.islive() && m.islive() && m.isHit(ship)){
                m.goDead();
                ship.subtractLife();
            }
        }
    }

    /**
     * 设置GAME_OVER
     */
    private void checkGameOverAction(){
        if(ship.getLife()<=0){ //若战舰的命数<=0，表示游戏结束了
            state=GAME_OVER; //将当前状态改为游戏结束状态
        }
    }

    /**
     * 启动游戏，控制战舰移动，和发射炸弹
     */
    private void action(){
        KeyAdapter k = new KeyAdapter() {
            @Override
            /**
             * 重写key Released()按键抬起事件
             */
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_P){
                    if(state==RUNNING){
                        state = PAUSE;//运行状态时修改为暂停状态
                    }else if (state==PAUSE){
                        state = RUNNING; //暂停状态时修改为运行状态
                    }
                }

                if(state==RUNNING) { //仅在运行状态时执行
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        Bomb obj = ship.shootBomb(); //深水炸弹入场
                        bombs = Arrays.copyOf(bombs, bombs.length + 1);//扩容
                        bombs[bombs.length - 1] = obj; //将obj添加到最后一个元素上
                    }
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        ship.moveLeft();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        ship.moveRight();
                    }
                }
            }
        };//不要求掌握
        this.addKeyListener(k);//不要求掌握
        /**
         * New一个定时器，控制入场时间
         */
        Timer timer = new Timer();//定时器对象
        int interval =10;//定时间隔(以毫秒为单位)----1000毫秒为1秒
        timer.schedule(new TimerTask() {
            @Override
            public void run() { //定时干的那个事---每10毫秒自动执行一次
                if(state==RUNNING) {
                    /**
                     * 同时执行
                     */
                    submarineEnterAction();  //潜艇入场
                    mineEnterAction();       //水雷入场
                    moveAction();            //海洋对象移动
                    outOfBoundsAction();     //删除越界的海洋对象
                    bombBangAction();        //深水炸弹与潜艇的碰撞
                    mineBangaction();        //水雷与战舰的碰撞
                    checkGameOverAction();   //检测游戏结束
                    repaint(); //重画----系统自动调用paint()方法
                }
            }
        }, interval, interval);//定时计划表
    }

    /**
     * 重写JPanel中的paint()方法 画 g:系统自带的画笔
     * @param g
     */
    public void paint(Graphics g){//-------朝上箭头为重写
        //sea未继承超类的属性，位置自拟
        Images.sea.paintIcon(null,g,0,0);//访问静态变量并且画出
        ship.paintImage(g);
        for (int i = 0; i < submarines.length; i++) {
            submarines[i].paintImage(g);
        }
        for (int i = 0; i < mines.length; i++) {
            mines[i].paintImage(g);
        }
        for (int i = 0; i < bombs.length; i++) {
            bombs[i].paintImage(g);
        }
        g.drawString("SCORE: "+score,200,50);
        g.drawString("LIFE: "+ship.getLife(),400,50);

        if(state==GAME_OVER){ //若当前为游戏结束状态
            Images.gameover.paintIcon(null,g,0,0);
        }
    }
    /**
     * main方法，整个程序的入口
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        World world = new World();//new完对象，成员变量(属性),和new出来的东西就被加载到堆区
        world.setFocusable(true);
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH+6,HEIGHT+29);//设置窗口大小
        frame.setLocationRelativeTo(null);//设置窗口居中
        frame.setResizable(false);
        frame.setVisible(true);//显示窗口,自动调用paint()方法
        world.action();
    }
}