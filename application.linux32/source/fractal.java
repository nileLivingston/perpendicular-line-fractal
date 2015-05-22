import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class fractal extends PApplet {

/*
    Perpendicular Line Fractal
    Idea by a post on http://classymathematics.tumblr.com
    
    Code by Nile Livingston (c) 2015
    
    Use the up and down arrow keys to adjust the fractal size.
*/

int SIZE = 700;

// The length of a line.
float UNIT_LENGTH = 10;
// Half the unit length.
float UNIT_HALF = UNIT_LENGTH/2;
// The number of iterations to draw.
int LIMIT = 1;
int MAX = 128;

// Return true iff p has already been drawn.
public boolean isDrawn(Point p) {
  return get(PApplet.parseInt(p.getX()), PApplet.parseInt(p.getY())) == color(0);
}

// Return true iff p is an intersection of already drawn lines.
public boolean isIntersection(Point p) {
  if (p.isVertical()) {
    return isDrawn(new Point(p.getX() - 1, p.getY(), true)) && isDrawn(new Point(p.getX() + 1, p.getY(), true));
  }
  else {
    return isDrawn(new Point(p.getX(), p.getY() - 1, false) ) && isDrawn(new Point(p.getX(), p.getY() + 1, false));
  }  
}

// Draw the recursive perpendicular line structure.
public void drawLines() {
  // To count iterations of drawing.
  int count = 0;
  // To hold the points to be drawn on a given iteration.
  PointQueue pq = new PointQueue();
  // The initial point.
  Point start = new Point(SIZE/2, SIZE/2, true);
  pq.push(start);
  
  // To hold the points to be added for the next iteration.
  PointQueue next_iter = new PointQueue();
  
  while (count < LIMIT) {
    // While we still have (potential) lines to draw.
    while (!pq.isEmpty()) {
      Point next = pq.pop();
      if (next.isVertical()) {
        // Get the endpoint coordinates.
        float x = next.getX();
        float y1 = next.getY() - UNIT_HALF;
        float y2 = next.getY() + UNIT_HALF;
        // Only draw line and add endpoints if not an intersection.
        if (!isIntersection(next)) {
          line(x, y1, x, y2); 
          // Add the endpoints to the next_iter queue.
          next_iter.push(new Point(x, y1, false));
          next_iter.push(new Point(x, y2, false));
        }
      }
      else {
        // Get the endpoint coordinates.
        float x1 = next.getX() - UNIT_HALF;
        float x2 = next.getX() + UNIT_HALF;
        float y = next.getY();
        // Only draw line and add endpoints if not an intersection.
        if (!isIntersection(next)) {
          line(x1, y, x2, y); 
          // Add the endpoints to the next_iter queue.
          next_iter.push(new Point(x1, y, true));
          next_iter.push(new Point(x2, y, true));  
        }
      }
    }
    // Add all of the next_iter points to the pq queue.
    pq.pushQueue(next_iter);
    count++;  
  }  
}

public void setup() {
  size(SIZE,SIZE);
  frameRate(8);
  smooth();
}

public void draw() {
  background(255);
  stroke(0);
  drawLines();
  
  fill(0);
  textSize(18);
  text("n = " + LIMIT, SIZE/30, SIZE/30);
}

public void keyPressed() {
  if (key == CODED) {
    if (keyCode == UP && LIMIT < MAX) {
      LIMIT++;
    }
    else if (keyCode == DOWN && LIMIT > 1) {
      LIMIT--;
    }
  }
}
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
  
  public float getX() {return x;}
  public float getY() {return y;}
  public boolean isVertical() {return vertical;}
  public boolean equals(Point p) {
    return (x == p.getX()) && (y == p.getY());
  }
  public String toString() {
    return "(" + this.getX() + ", " + this.getY() + ")"; 
  }
}
// An item in the PointQueue.
class QueueItem {
  
  Point p;
  QueueItem next;
  
  QueueItem(Point tp) {
    p = tp;    
    next = null;
  }
  
  public void setNext(QueueItem q) {next = q;}
  
  public QueueItem getNext() {return next;}
  
  public Point getPoint() {return p;}
}

public class PointQueue {
  
  // The head of the queue. null if empty.
  QueueItem head;
  
  public PointQueue() {
    head = null;
  }
  
  public boolean isEmpty() {return head == null;}
  
  public boolean contains(Point p) {
    QueueItem cur = head;
    if (cur == null) return false;
    while (cur.getNext() != null) {
      if (cur.getPoint().equals(p)) return true;
      cur = cur.getNext();  
    }
    return false;
  }
  
  public QueueItem getTail() {
    QueueItem cur = head;
    if (cur == null) return head;
    while (true) {
       if (cur.getNext() == null) return cur;
       cur = cur.getNext();
    }
  }
  
  public void push(Point p) {
    QueueItem tail = getTail();
    if (tail == null) head = new QueueItem(p);
    else tail.setNext(new QueueItem(p));
  }
  
  public void pushQueue(PointQueue pq) {  
    Point p;
    while (!pq.isEmpty()) {
      p = pq.pop(); 
      this.push(p);  
    }
  }
  
  public Point pop() {
    if (head == null) return null;
    Point out = head.getPoint();
    head = head.getNext();
    return out;
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "fractal" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
