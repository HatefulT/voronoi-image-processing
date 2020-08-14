import java.util.ArrayList;

class RectangularContainer {
  int x;
  int y;
  int w;
  int h;

  ArrayList<Integer> objectsIds;

  RectangularContainer(int _x, int _y, int _w, int _h) {
    x = _x;
    y = _y;
    w = _w;
    h = _h;
    objectsIds = new ArrayList<Integer>();
  }

  void add(int id) {
    objectsIds.add(id);
  }

  boolean isRelated(double x1, double y1) {
    return x < x1 && x1 < x+w && y < y1 && y1 < y+h;
  }
}
