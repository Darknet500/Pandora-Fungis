    package View.Hitbox;

    import Model.Shroomer.Hypa;
    import View.Drawable.DrawableLine;

    import java.awt.*;

    public class HypaHitbox extends Hitbox {
        private Hypa hypa;
        private Point end1;
        private Point end2;
        private Color color;


        public HypaHitbox(Hypa hypa, Color mushroomtype) {
            this.hypa = hypa;
            this.end1 = hypa.getEnd1().getHitbox().getCenterPoint();
            this.end2 = hypa.getEnd2().getHitbox().getCenterPoint();
            this.color = mushroomtype;

            int movingvalue=0;

            int nx = end2.y-end1.y;
            int ny = end1.x-end2.x;

            double l = Math.sqrt(Math.pow(nx,2)+Math.pow(ny,2));
            for (int i=1;i<5;i++){
                boolean isthatplaytaken = false;
                for(Hypa h: hypa.getEnd1().getHypas()){
                    if ((h.getEnd1()==hypa.getEnd1()&&h.getEnd2()==hypa.getEnd2())||(h.getEnd1()==hypa.getEnd2()&&h.getEnd2()==hypa.getEnd1())){
                        Point temppoint = new Point((int)(end1.x+movingvalue*8*(nx/l)), (int)(end1.y+movingvalue*8*(ny/l)));
                        if((h.getHitbox().getEnd1().getX()==temppoint.getX()&&h.getHitbox().getEnd1().getY()==temppoint.getY())||
                                (h.getHitbox().getEnd2().getX()==temppoint.getX()&&h.getHitbox().getEnd2().getY()==temppoint.getY())) {
                            isthatplaytaken = true;
                        }
                    }
                }
                if(!isthatplaytaken){
                    Point movedend1= new Point((int)(end1.x+movingvalue*8*(nx/l)), (int)(end1.y+movingvalue*8*(ny/l)));
                    Point movedend2= new Point((int)(end2.x+movingvalue*8*(nx/l)), (int)(end2.y+movingvalue*8*(ny/l)));
                    end1 = movedend1;
                    end2 = movedend2;
                    break;
                }else{
                    if(i%2==0)
                        movingvalue+=i;
                    else
                        movingvalue-=i;
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


    }
