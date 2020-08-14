import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.File;

import java.util.Random;

class Main {
  public static void main(String[] args) {
    double[][][] image = loadImage("input.jpg");
    int W = image[0].length;
    int H = image[0][0].length;

    int w1 = 200; //W/10;
    int h1 = 200; //H/10;

    RectangularContainer[][] grid = new RectangularContainer[W/w1+1][H/h1+1];
    for(int x=0; x<grid.length; x++)
      for(int y=0; y<grid[0].length; y++)
        grid[x][y] = new RectangularContainer(x*w1, y*h1, w1, h1);

    Random r = new Random();
    int N = 3000;
    double[][] points = new double[N][2+3]; // every point is coordinates + color on this coordinates
    for(int i=0; i<N; i++) {
      points[i][0] = r.nextInt(W);
      points[i][1] = r.nextInt(H);

      points[i][2] = image[0][ (int) points[i][0] ][ (int) points[i][1] ];
      points[i][3] = image[1][ (int) points[i][0] ][ (int) points[i][1] ];
      points[i][4] = image[2][ (int) points[i][0] ][ (int) points[i][1] ];

      int X = (int) points[i][0] / w1;
      int Y = (int) points[i][1] / h1;
      grid[ X ][ Y ].add(i);
      if(X-1 >= 0 && ((int) points[i][0] % w1) < w1/2) grid[X-1][Y].add(i);
      else if(X+1 < grid.length) grid[X+1][Y].add(i);
      if(Y-1 >= 0 && ((int) points[i][1] % h1) < h1/2) grid[X][Y-1].add(i);
      else if(Y+1 < grid[0].length) grid[X][Y+1].add(i);
    }

    long t1 = System.currentTimeMillis();

    double[][][] image1 = new double[3][W][H];
    for(int x=0; x<W; x++)
      for(int y=0; y<H; y++) {
        double minD = 100000;
        int i1 = -1;

        for(int i=0; i<grid[ x / w1 ][ y / h1 ].objectsIds.size(); i++) {
          int j = grid[ x / w1 ][ y / h1 ].objectsIds.get(i);
          double d = Math.pow(points[j][0]-x, 2)+Math.pow(points[j][1]-y, 2); // this is squared distance
          if(minD > d) {
            i1 = j;
            minD = d;
          }
        }

        image1[0][x][y] = points[i1][2];
        image1[1][x][y] = points[i1][3];
        image1[2][x][y] = points[i1][4];
      }

    long t2 = System.currentTimeMillis();
    System.out.println(t2-t1);

    saveImage(image1, "output.png");
  }
  public static double[][][] loadImage(String filename) {
    try {
      BufferedImage image = ImageIO.read(new File(filename));
      int w = image.getWidth();
      int h = image.getHeight();
      double[][][] a = new double[3][w][h];
      for(int x=0; x<w; x++)
        for(int y=0; y<h; y++) {
          int c = image.getRGB(x, y);
          a[0][x][y] = ((c & 0xff0000) >> 16) / 255.0;
          a[1][x][y] = ((c & 0xff00) >> 8) / 255.0;
          a[2][x][y] = (c & 0xff) / 255.0;
        }
      return a;
    } catch(IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  public static void saveImage(double[][][] a, String filename) {
    BufferedImage image = new BufferedImage(a[0].length, a[0][0].length, BufferedImage.TYPE_INT_RGB);
    for (int x=0; x<a[0].length; x++) {
      for (int y=0; y<a[0][0].length; y++) {
        int r = (int) (a[0][x][y]*255);
        int g = (int) (a[1][x][y]*255);
        int b = (int) (a[2][x][y]*255);
        image.setRGB(x, y, (r << 16) + (g << 8) + b );
      }
    }

    File f = new File(filename);
    try {
      ImageIO.write(image, "png", f);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
