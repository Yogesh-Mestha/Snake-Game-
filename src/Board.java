import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class Board extends JPanel implements ActionListener {
    private int dots;
    private Image dot;
    private Image head;

    private final int  alldots = 900;

    private final int  dotsize = 10;
    private final int x[]=new int[alldots];
    private final int y[]=new int[alldots];
    private boolean leftdic=false;
    private boolean rightdic=true;
    private boolean updic=false;
    private boolean downdic=false;
    private final int random_pos=29;
    private Timer timer;

    private int dot_x;
    private int dot_y;
    private boolean inGame=true;


    Board(){

        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        setPreferredSize(new Dimension(300,300));

        initGame();
        loadImages();


    }
    public void loadImages(){
        ImageIcon ic1=new ImageIcon("./icons/dot.png");
        dot= ic1.getImage();
        ImageIcon ic2=new ImageIcon("./icons/head.png");
        head=ic2.getImage();
    }



    public void initGame(){
        dots =3;
        for(int i=0;i<dots;i++){
            y[i]= 50;
            x[i]= 50- i*dotsize;
        }
        locateDot();

        timer=new Timer(140,this);
        timer.start();
    }

    public void locateDot(){
       int r=(int)(Math.random()*random_pos);
       dot_x=r*dotsize;
        r=(int)(Math.random()*random_pos);
       dot_y=r*dotsize;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(inGame) {
            g.drawImage(dot, dot_x, dot_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }else {
            gameOver(g);
        }
    }
    public void gameOver(Graphics g){
        String msg="Game Over!";

        Font font=new Font("SAN SERIF",Font.BOLD,14);
        FontMetrics metrics=getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(300 - metrics.stringWidth(msg))/2,300/2);
    }
    public void move(){
        for(int i=dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftdic){
            x[0]=x[0]-dotsize;
        }
        if(rightdic){
            x[0]=x[0]+dotsize;
        }
        if(updic){
            y[0]=y[0]-dotsize;
        }
        if(downdic){
            y[0]=y[0]+dotsize;
        }

    }
    public  void checkDot(){
        if((x[0]==dot_x)&&(y[0]==dot_y)){
            dots++;

            locateDot();
        }
    }
    public void checkCollision(){
        for(int i=dots;i>0;i--){
            if((i>4)&&(x[0]==x[i])&&(y[0]==y[i])){
                inGame=false;
            }
        }

        if(y[0]>=300){
            inGame=false;
        }
        if(x[0]>=300){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(x[0]<0){
            inGame=false;
        }

        if(!inGame){
            timer.stop();
        }
    }
    public void actionPerformed(ActionEvent e){

        if(inGame) {
            checkDot();
            checkCollision();
            move();
        }
        repaint();
    }

    public class TAdapter extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent e) {
            int key =e.getKeyCode();
            if(key==KeyEvent.VK_LEFT&&(!rightdic)){
                leftdic=true;
                updic=false;
                downdic=false;
            }
            if(key==KeyEvent.VK_RIGHT&&(!leftdic)){
                rightdic=true;
                updic=false;
                downdic=false;
            }

            if(key==KeyEvent.VK_UP&&(!downdic)){
                updic=true;
                leftdic=false;
                rightdic=false;
            }
            if(key==KeyEvent.VK_DOWN&&(!updic)){
                downdic=true;
                leftdic=false;
                rightdic=false;
            }
        }

        public void keyPressed(KeyEvent e){

        }
    }
}
