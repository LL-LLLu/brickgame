package brickBracker;

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;



public  class GamePlay extends JPanel implements KeyListener, ActionListener {




    private boolean play = false;
    private int startScore = 0;
    private int numberOfBrick = 21;
    private int randomx;
    private int randomy;

    private Timer time;
    private int delay = 8;
    //starting position for player.
    private int playerX = 310;
    private int playerY = 550;
    //ball starting position

    //ball moving position
    private int ballxdir = -1;
    private int ballydir = -2;

    private MapGenerator map;


    public GamePlay() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();
    }

    public int getRandomX()
    {
        Random ran = new Random();
       //randomNum = minimum + (int)(Math.random() * maximum);
        //Min + (int)(Math.random() * ((Max - Min) + 1))
        randomx = 100 + (int)(Math.random() * ((600-100) + 1));
        return randomx;
    }

    public int getRandomY()
    {
        Random ran = new Random();
        //randomNum = minimum + (int)(Math.random() * maximum);
        //Min + (int)(Math.random() * ((Max - Min) + 1))
        randomy = 350 + (int)(Math.random() * ((500-350) + 1));
        return randomy;
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D)g);


        //border
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //score
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score" + startScore, 590,30);

        //the paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, playerY, 100, 8);

        //ball
        g.setColor(Color.YELLOW);
        g.fillOval(randomx, randomy, 20, 20);

        if(randomy > 750)
        {
            play = false;
            ballydir = 0;
            ballxdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Score" + startScore, 230, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart! ", 230, 350);

        }



        if(numberOfBrick <= 0)
        {
            play = false;
            ballydir = 0;
            ballxdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Score" + startScore, 230, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart! ", 230, 350);

        }

        //quit the game while the ball fall off the end


//        for(int i = 0; i < 5; i ++)
////        {
////            g.setColor(Color.WHITE);
////            g.fillRect(650*i/5 + 27,40,95,50);
////
////        }
////        for(int i = 0; i < 5; i ++)
////        {
////            g.setColor(Color.WHITE);
////            g.fillRect(650*i/5 + 27,95,95,50);
////        }
////        for(int i = 0; i < 5; i ++)
////        {
////            g.setColor(Color.WHITE);
////            g.fillRect(650*i/5 + 27,150,95,50);
////        }

        g.dispose();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        //make sure that the ball bounces after reaching the border
        if(play)
        {
            if(new Rectangle(randomx,randomy,20,20).intersects(new Rectangle(playerX,playerY,100,8)))
            {
                ballydir = -ballydir;
            }

          A:  for(int i = 0; i < map.map.length; i++)
            {
                for(int j = 0; j < map.map[0].length; j ++)
                {
                    if(map.map[i][j] > 0)
                    {
                        int brickX = j*map.brickWidth + 80;
                        int brickY = i*map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ball = new Rectangle(randomx, randomy, 20,20);
                        Rectangle brickRect = rect;
                        if(ball.intersects(brickRect))
                        {
                            map.setBrickValue(0,i,j);
                            numberOfBrick --;
                            startScore += 5;

                            if(randomx + 19 <= brickRect.x || randomx + 1 >= brickRect.x + brickWidth)
                            {
                                ballxdir = -ballxdir;
                            }
                            else
                            {
                                ballydir = -ballydir;
                            }
                            break A;
                        }

                    }
                }
            }


            randomx += ballxdir;
            randomy += ballydir;
            if(randomx == 0)
            {
                ballxdir = -ballxdir;
            }
            if(randomy < 0)
            {
                ballydir = -ballydir;
            }
            if(randomx > 670)
            {
                ballxdir = -ballxdir;
            }
        }

        //to move the paddle we need to repaint the whole thing for there is only
        //one paddle existing at one point.
        repaint();
    }



    @Override
    public void keyTyped(KeyEvent e) {
        //dont need
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //dont need
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                if (playerX < 10 ) {
                    playerX = 10;
                } else {
                    moveLeft();
                }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {

            if (playerY <= 400 ) {
                playerY = 400;
            } else {
                moveUp();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            if (playerY > 590 ) {
                playerY = 550;
            } else {
                moveDown();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(play != true)
            {
                play = true;
                randomx = getRandomX();
                randomy = getRandomY();


                ballxdir = -1;
                ballydir = -2;
                playerX = 350;
                playerY = 550;
                startScore = 0;
                numberOfBrick = 21;
                map = new MapGenerator(3,7);
                repaint();
            }

        }

    }

    private void moveDown() {
        play = true;
        playerY += 20;
    }

    public void moveUp()
        {
            play = true;
            playerY -= 20;
        }



    public void moveRight () {
            play = true;
            playerX += 20;

        }


        public void moveLeft () {
            play = true;
            playerX -= 20;
        }
    }

