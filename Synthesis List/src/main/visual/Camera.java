package main.visual;

public class Camera {

        private double x = 140, y = 0;
        private double velX = 0, velY = 0;

        public int getX() {
                return (int)x;
        }

        public void setX(double x) {
                this.x = x;
        }

        public int getY() {
                return (int)y;
        }

        public void setY(double y) {
                this.y = y;
        }

        public double getVelX() {
                return velX;
        }

        public void setVelX(double velX) {
                this.velX = velX;
        }

        public double getVelY() {
                return velY;
        }

        public void setVelY(double velY) {
                this.velY = velY;
        }

        public void tick() {
                x+=velX;
                y+=velY;
        }

        public void reset() {
                x = 140;
                y = 0;
        }

}