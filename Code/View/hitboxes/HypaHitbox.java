    package View.hitboxes;

    import Model.Shroomer.Hypa;
    import View.drawables.Drawable;
    import View.drawables.DrawableLine;

    import javax.imageio.ImageIO;
    import java.awt.*;
    import java.awt.image.BufferedImage;
    import java.io.File;
    import java.io.IOException;

    public class HypaHitbox extends Hitbox {
        private Hypa hypa;
        private Point end1;
        private Point end2;
        private Color color;
        private int placing;


        public HypaHitbox(Hypa hypa, Color mushroomtype) {
            this.hypa = hypa;
            this.end1 = hypa.getEnd1().getHitbox().getCenterPoint();
            this.end2 = hypa.getEnd2().getHitbox().getCenterPoint();
            this.color = mushroomtype;

            Point normal = getNormalVectorUpDir();
            for (int i=0;i<5;i++){
                boolean isthatplacetaken = false;
                int movingvalue = i%2==0?-i/2:(i+1)/2;
                for(Hypa h: hypa.getEnd1().getHypas()){
                    if ((h.getEnd1()==hypa.getEnd1()&&h.getEnd2()==hypa.getEnd2())||(h.getEnd1()==hypa.getEnd2()&&h.getEnd2()==hypa.getEnd1())){

                        if(h.getHitbox().getPlacing()==i){
                            isthatplacetaken = true;
                            break;
                        }

                  }
                }
                if(!isthatplacetaken){
                    System.out.println("hypa put: " + i);
                    placing = i;
                    end1 = new Point(end1.x+movingvalue*normal.x, end1.y+movingvalue*normal.y);
                    end2 = new Point(end2.x+movingvalue*normal.x, end2.y+movingvalue*normal.y);
                    break;
                }
            }



            hypa.addObserver(this);

            drawable = new DrawableLine(end1, end2, color);
        }

        /*public boolean isHit(Point point) {
            return point.distance(end1) + point.distance(end2) <= end1.distance(end2) + 5;
        }*/

        public boolean isHit(Point point) {
            double threshold = 5.0;
            double dist = pointToSegmentDistance(point, end1, end2);
            return dist <= threshold;
        }

        public int getPlacing(){
            return placing;
        }

        /**
         * Calculates the shortest distance from a point to a line segment.
         */
        private double pointToSegmentDistance(Point p, Point a, Point b) {
            double px = p.x;
            double py = p.y;
            double ax = a.x;
            double ay = a.y;
            double bx = b.x;
            double by = b.y;

            double dx = bx - ax;
            double dy = by - ay;

            if (dx == 0 && dy == 0) {
                return p.distance(a);
            }

            double t = ((px - ax) * dx + (py - ay) * dy) / (dx * dx + dy * dy);

            t = Math.max(0, Math.min(1, t));

            double closestX = ax + t * dx;
            double closestY = ay + t * dy;

            double distX = px - closestX;
            double distY = py - closestY;

            return Math.hypot(distX, distY);
        }


        public void onHypaChanged(int number) {
            if(number==1) ((DrawableLine)drawable).changecolorA(127);
            else ((DrawableLine)drawable).changecolorA(255);
        }

        private Point getEnd1(){
            return end1;
        }

        private Point getEnd2(){
            return end2;
        }

        public void onPositionChanged(){
            this.end1 = hypa.getEnd1().getHitbox().getCenterPoint();
            this.end2 = hypa.getEnd2().getHitbox().getCenterPoint();
            Point normal = getNormalVectorUpDir();


            int movingvalue = placing%2==0?-placing/2:(placing+1)/2;

            end1 = new Point(end1.x+movingvalue*normal.x, end1.y+movingvalue*normal.y);
            end2 = new Point(end2.x+movingvalue*normal.x, end2.y+movingvalue*normal.y);
            ((DrawableLine)drawable).refreshPosition(end1, end2);


        }

        private Point getNormalVectorUpDir(){
            Point rightend = end1.x>end2.x?end1:end2;
            Point leftend = end1.x>end2.x?end2:end1;
            Point normal = new Point(leftend.y-rightend.y, rightend.x-leftend.x);
            double l = Math.sqrt(Math.pow(normal.x,2)+Math.pow(normal.y,2));
            return new Point((int)(normal.x*8/l), (int)(normal.y*8/l));
        }



    }
