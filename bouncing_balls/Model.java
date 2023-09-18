package bouncing_balls;

/**
 * The physics model.
 * 
 * This class is where you should implement your bouncing balls model.
 * 
 * The code has intentionally been kept as simple as possible, but if you wish, you can improve the design.
 * 
 * @author Simon Robillard
 *
 */
class Model {

	double areaWidth, areaHeight;
	double g = -9.8;
	
	Ball [] balls;

	Model(double width, double height) {
		areaWidth = width;
		areaHeight = height;
		
		// Initialize the model with a few balls
		balls = new Ball[2];
		balls[0] = new Ball(width / 3, height * 0.9, 1.2, 1.6, 0, g, 0.2);
		balls[1] = new Ball(2 * width / 3, height * 0.7, -0.6, 0.6, 0, g, 0.3);
	}

	void step(double deltaT) {
		// TODO this method implements one step of simulation with a step deltaT
		for (Ball b : balls) {
			// detect collision with the border
			if (b.x < b.radius || b.x > areaWidth - b.radius) {
				b.vx *= -1; // change direction of ball
			}
			if (b.y < b.radius || b.y > areaHeight - b.radius) {
				b.vy *= -1;
			}
			
			// compute new velocity and position 
			b.vx += deltaT * b.ax;
			b.vy += deltaT * b.gy;

			// compute new position according to the speed of the ball
			b.x += deltaT * b.vx;
			b.y += deltaT * b.vy;


		}
	}

		
	double rectToPolar(double x, double y){
		// r = sqrt(x^2 + y^2)
		// p = atan2(y,x)

		double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double p;
		
		if (y >= 0 && r != 0){
			p = Math.acos(x/r);
			return p;

		}
		else if (y < 0){
			p = - Math.acos(x/r);
			return p;
		}

		else if ( r == 0 ){
			//throw new Exception("undefined");
			System.out.println("undefined");
			throw new IllegalArgumentException("undefined when r=0");
		}
		return 0;
		
	}

	void polarToRect(double x, double y){
		// x = r*cos(p) and y = r*sin(p)
		double p = rectToPolar(x, y);
		double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		x = r * Math.cos(p);
		y = r * Math.sin(p);
	
	}
	
	/**
	 * Simple inner class describing balls.
	 */
	class Ball {
		
		Ball(double x, double y, double vx, double vy, double ax, double gy, double r) {
			this.x = x; // x(t) (old) position
			this.y = y; // y(t) (old) position 
			this.vx = vx; // x'(t) velocity in x
			this.vy = vy; // y'(t) velocity in y
			this.ax = ax; // x''(t) acceleration in x
			this.gy = gy; // y''(t) acceleration i
			this.radius = r;
		}

		/**
		 * Position, speed, and radius of the ball. You may wish to add other attributes.
		 */
		double x, y, vx, vy, ax, gy, radius;
	}
}
