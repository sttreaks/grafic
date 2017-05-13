package sample;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Array;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel implements MouseMotionListener {

    private  int SquareWidth = 30;
    private  int Max = 100;
    private  int MaxLine = 10000;
    private  Rectangle[] squares = new Rectangle[Max];
    private  Line2D[] lineLOL = new Line2D[MaxLine];
    private  int[][] smej = new int[Max][Max];


    private  int squareCount = 0;
    private  int squareCountLine = 0;
    private  int currentSquareIndex = -1;
    private  int currentSquareIndexLine = -1;
    private  double XX = 0;
    private  double YY = 0;
    private  double XX2 = 0;
    private  double YY2 = 0;
    private  double XXX = 0;
    private  double YYY = 0;
    private  int bb88 = 0;
    private  double XXZ1 = 0;
    private  double YYZ2 = 0;

    public Main() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                currentSquareIndex = 0;
                int x = evt.getX();
                int y = evt.getY();
                if(ihaveaversh(x,y) == 1) {
                    if (bb88 > 0) {
                        int k = inversh(x, y);
                        if (k != bb88)
                            if (smej[bb88][k] == 1) {
                                smej[bb88][k] = 0;
                                smej[k][bb88] = 0;
                            } else {
                                smej[bb88][k] = 1;
                                smej[k][bb88] = 1;
                            }
                        bb88 = 0;
                        addLine();
                    } else {
                        bb88 = inversh(x, y);

                    }
                }

                if (ihaveaversh(x,y) == 0) {
                    add(x - SquareWidth / 2, y - SquareWidth / 2);
                    int q = inversh(x,y);
                    smej[q][q] =2;
                    bb88 = 0;

                }

            }

            public void mouseClicked(MouseEvent evt) {
                int x = evt.getX();
                int y = evt.getY();

                if (evt.getClickCount() >= 2) {
                    int q = inversh(x,y);
                    remove(q);
                        for (int j = 0;j < Max-1; j++ )
                        {
                            smej[q][j] = 0;
                            smej[j][q] = 0;
                        }
                    addLine();

                }

        });
        addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i<squareCountLine; i++)
            ((Graphics2D)g).draw(lineLOL[i]);
        for (Integer i = 0; i < squareCount; i++) {
            ((Graphics2D) g).fill(squares[i]);
            ((Graphics2D) g).setColor(Color.red);
            ((Graphics2D) g).drawString( i.toString(),  (float)((Rectangle) squares[i]).getCenterX()-3 , (float)((Rectangle) squares[i]).getCenterY()+3 );
            ((Graphics2D) g).setColor(Color.black);
        }
    }

     public int ihaveaversh(int x, int y) {
        for (int i = 0; i < squareCount; i++) {
            XXZ1 = ((Rectangle) squares[i]).getCenterX();
            YYZ2 = ((Rectangle) squares[i]).getCenterY();
            for (int xx = 0; xx < 2 * SquareWidth; xx++) {
                for (int yy = 0; yy < 2 * SquareWidth; yy++) {
                    if (x == (XXZ1 - SquareWidth + xx) && y == (YYZ2 - SquareWidth + yy))
                        return 1;
                }
            }
        }
         return 0;
     }

    public int inversh(int x, int y){
        for (int i = 0; i < squareCount; i++) {
            XXX = ((Rectangle) squares[i]).getCenterX();
            YYY = ((Rectangle) squares[i]).getCenterY();
                for (int xx = 0; xx<SquareWidth; xx++)
                    for(int yy = 0; yy<SquareWidth; yy++){
                        if(XXX == (x-SquareWidth/2+xx) && YYY == (y-SquareWidth/2+yy))
                            return i;
                    }

        }
        return -1;
    }

    public int getSquare(int x, int y) {
        for (int i = 0; i < squareCount; i++)
            if(squares[i].contains(x,y))
                return i;
        return -1;
    }

    public void add(int x, int y) {
        if (squareCount < Max) {
            squares[squareCount] = new Rectangle(x, y,SquareWidth,SquareWidth);
            currentSquareIndex = squareCount;
            // number now -
            squareCount++;
            repaint();
        }
    }

    public void addLine() {
        Arrays.fill(lineLOL, null);
        currentSquareIndexLine =0;
        squareCountLine = 0;
        for(int w = 0; w < Max; w++)
            for(int h = 0; h < Max; h++)
                if (smej[w][h] == 1) {
                    XX = ((Rectangle) squares[w]).getCenterX();
                    YY = ((Rectangle) squares[w]).getCenterY();
                    XX2 = ((Rectangle) squares[h]).getCenterX();
                    YY2 = ((Rectangle) squares[h]).getCenterY();
                    lineLOL[squareCountLine] = new Line2D.Double(XX2, YY2, XX, YY);
                    currentSquareIndexLine = squareCountLine;
                    squareCountLine++;
                }
            repaint();
    }


    public void remove(int n) {
        if (n < 0 || n >= squareCount)
            return;
        squareCount--;
        squares[n] = squares[squareCount];
        if (currentSquareIndex == n)
            currentSquareIndex = -1;
        repaint();
    }


    public void mouseMoved(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        if (getSquare(x, y) >= 0)
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        else
            setCursor(Cursor.getDefaultCursor());
    }

    public void mouseDragged(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();

        int q = inversh(x,y);

        if (q >= 0) {
            Graphics g = getGraphics();
            g.setXORMode(getBackground());

            ((Graphics2D)g).draw(squares[q]);

            squares[q].x = x - SquareWidth/2;
            squares[q].y = y - SquareWidth/2;

            ((Graphics2D)g).draw(squares[q]);
            g.dispose();
            bb88 = 0;
            addLine();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
        frame.setSize(1200, 900);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Container contentPane = frame.getContentPane();
        contentPane.add(new Main());

        frame.show();
    }
}