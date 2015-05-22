// Seed point for fractal lines.
public class Point {
  
  // x and y coordinates.
  float x, y;
  // true iff line centered at this point should be vertical
  // false means horizontal.
  boolean vertical;
  
  public Point(float tx, float ty, boolean t_vert) {
    x = tx;
    y = ty;
    vertical = t_vert;
  }
  
  float getX() {return x;}
  float getY() {return y;}
  boolean isVertical() {return vertical;}
  boolean equals(Point p) {
    return (x == p.getX()) && (y == p.getY());
  }
  String toString() {
    return "(" + this.getX() + ", " + this.getY() + ")"; 
  }
}
