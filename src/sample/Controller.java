package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.HashMap;

public class Controller extends JPanel implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }

    class Pair {
        int x;
        int y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return MessageFormat.format("{0} {1}", String.valueOf(x), String.valueOf(y));
        }
    }

    class Circle{
        int radius = 50;
        private int x;
        private int y;

        Circle(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        void setX(int x) {
            this.x = x;
        }

        void setY(int y) {
            this.y = y;
        }

        int getRadius() {
            return radius;
        }

        boolean pointInCircle(int x, int y) {
            x -= this.x;
            y -= this.y;

            return Math.sqrt(x * x + y * y) <= this.radius;
        }

        @Override
        public String toString() {
            return String.valueOf(x) + " " + String.valueOf(y);
        }
    }

    private static final int rad = 50;
    private int number_of_nodes = 0;
    private boolean new_connection = false;
    private int connection = -1;
    private HashMap<Integer, Circle> circles = new HashMap<>();
    private ArrayList<Pair> lines = new ArrayList<>();

    private Controller() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent){
                int node = this.find(mouseEvent.getX() - rad/2, mouseEvent.getY() - rad/2);

                if (node == -1) {
                    this.create(mouseEvent.getX() - rad/2, mouseEvent.getY() - rad/2);
                    connection = -1;
                    new_connection = false;
                } else if (mouseEvent.getClickCount() == 2) {
                    this.delete(mouseEvent.getX() - rad / 2, mouseEvent.getY() - rad / 2);
                    connection = -1;
                    new_connection = false;
                } else if (mouseEvent.getButton() == MouseEvent.BUTTON3){
                    if (!new_connection) {
                        new_connection = true;
                        connection = node;
                    } else {
                        if (node != connection) {
                            new_connection = false;
                            createLine(node, connection);
                            connection = -1;
                        }
                    }
                }

                repaint();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                int node = this.find(mouseEvent.getX() - rad/2, mouseEvent.getY() - rad/2);
                System.out.println(mouseEvent.getX() - rad/2);
                System.out.println(mouseEvent.getY() - rad/2);
                System.out.println();

                if (node != -1) {
                    circles.get(node).setX(mouseEvent.getX() - rad/2);
                    circles.get(node).setY(mouseEvent.getY() - rad/2);
                }

                repaint();
            }



            private void createLine(int node1, int node2){
                lines.add(new Pair(node1, node2));
            }

            private void deleteLines(int node) {
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).x == node || lines.get(i).y == node) {
                        lines.remove(i);
                        i--;
                    }
                }
            }

            private int find(int x, int y){
                for (int i: circles.keySet()) {
                    if (circles.get(i).pointInCircle(x, y)) {
                        return i;
                    }
                }

                return -1;
            }

            private void delete(int x, int y){
                deleteLines(this.find(x, y));
                circles.remove(this.find(x, y));
            }

            private void create(int x, int y){
                number_of_nodes++;
                circles.put(number_of_nodes, new Circle(x, y));
            }

        });
    }

    @Override
    public void paintComponent(Graphics g) {
        if (lines.size() > 0) {
            for (Pair line : lines) {
                g.setColor(Color.BLACK);
                g.drawLine(circles.get(line.x).getX() + rad / 2, circles.get(line.x).getY() + rad / 2,
                        circles.get(line.y).getX() + rad / 2, circles.get(line.y).getY() + rad / 2);
            }
        }

        for (int key: circles.keySet()) {
            g.setColor(Color.BLACK);
            g.drawOval(circles.get(key).getX(), circles.get(key).getY(),
                    circles.get(key).getRadius(), circles.get(key).getRadius());
            g.setColor(Color.WHITE);
            g.fillOval(circles.get(key).getX(), circles.get(key).getY(),
                    circles.get(key).getRadius(), circles.get(key).getRadius());
            String s = String.valueOf(key);
            g.setColor(Color.pink);
            g.drawString(s, circles.get(key).getX(), circles.get(key).getY());
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Graph");
        frame.setSize(1200, 900);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Container contentPane = frame.getContentPane();
        contentPane.add(new Controller());

        frame.setVisible(true);
    }
}
