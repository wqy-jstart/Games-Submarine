package cn.tedu.submarine;
import javax.swing.ImageIcon;//类也是一个数据类型
/**
 * 创建图片类
 * */
public class Images {
//  公开的  静态的  图片类型  变量名
    public static ImageIcon sea;//调用时需要类名打点访问
    public static ImageIcon gameover;
    public static ImageIcon battleship;
    public static ImageIcon obsersubm;
    public static ImageIcon torpesubm;
    public static ImageIcon minesubm;
    public static ImageIcon mine;
    public static ImageIcon bomb;
    static {//静态块：初始化静态图片
        sea = new ImageIcon("img/sea.png");//读取img下的sea.png到变量sea中
        gameover = new ImageIcon("img/gameover.png");
        battleship = new ImageIcon("img/battleship.png");
        obsersubm = new ImageIcon("img/obsersubm.png");
        torpesubm = new ImageIcon("img/torpesubm.png");
        minesubm = new ImageIcon("img/minesubm.png");
        mine = new ImageIcon("img/mine.png");
        bomb = new ImageIcon("img/bomb.png");
    }
    /**
     * main方法，检验图片传输正确与否
     * @param args
     */
    public static void main(String[] args) {
        //返回8表示图片读取成功，返回其余数字表示图片读取失败
        System.out.println(sea.getImageLoadStatus());
        System.out.println(gameover.getImageLoadStatus());
        System.out.println(battleship.getImageLoadStatus());
        System.out.println(obsersubm.getImageLoadStatus());
        System.out.println(torpesubm.getImageLoadStatus());
        System.out.println(minesubm.getImageLoadStatus());
        System.out.println(mine.getImageLoadStatus());
        System.out.println(bomb.getImageLoadStatus());//8
    }
}
