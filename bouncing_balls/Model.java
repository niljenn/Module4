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
		// separate wall collision handling 
		for (int i = 0; i < balls.length; i++) {
			Ball b = balls[i];
			
			// detect collision with the border
			if (b.x < b.radius || b.x > areaWidth - b.radius) {
				b.vx *= -1; // change direction of ball
			}
			if (b.y < b.radius || b.y > areaHeight - b.radius) {
				b.vy *= -1;
			}
		}

		// separate ball collision handling
		for (int i = 0; i < balls.length; i++) {
			Ball b1 = balls[i];
			for (int j = i+1; j < balls.length; j++) {
				Ball b2 = balls[j];

				double dx = b2.x - b1.x;	// distance in x axis
				double dy = b2.y - b1.y;	// distance in y axis
				double distance = Math.sqrt(dx * dx + dy * dy); 	// pythagoras theorem
	
				if (distance < b1.radius + b2.radius) {		// if they have collided:
					// convert velocities of b1 and b2 from rect to polar
					double[] polarV1 = rectToPolar(b1.vx, b1.vy);
					double[] polarV2 = rectToPolar(b2.vx, b2.vy);
	
					// swap velocities in polar coordinates, directions of velocities are exchanged while magnitude remains the same
					polarV1[0] = polarV2[0];
	
					// convert velocities of b1 and b2 back to rect coordinates
					double[] rectV1 = polarToRect(polarV1[0], polarV1[1]);
					double[] rectV2 = polarToRect(polarV2[0], polarV2[1]);
	
					// update velocities of b1 and b2
					b1.vx = rectV1[0];
					b1.vy = rectV1[1];
					b2.vx = rectV2[0];
					b2.vy = rectV2[1];
				}
			}
		
	
			// compute new velocity
			b1.vx += deltaT * b1.ax;
			b1.vy += deltaT * b1.gy;

			// compute new position
			b1.x += deltaT * b1.vx;
			b1.y += deltaT * b1.vy;
	}
}

		
	double[] rectToPolar(double x, double y){
		// r = sqrt(x^2 + y^2)
		// p = atan2(y,x)

		double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); //magnitude
		double p; //angle

		if (y >= 0 && r != 0){
			p = Math.acos(x/r);
			return new double[]{p,r};

		}
		else if (y < 0){
			p = - Math.acos(x/r);
			return new double[]{p, r};
		}

		else if ( r == 0 ){
			System.out.println("undefined");
			throw new IllegalArgumentException("undefined when r=0");
		}
		
		else{
			throw new IllegalArgumentException("Null");
		}
		
	}


	double[] polarToRect(double p, double r){
		// x = r*cos(p) and y = r*sin(p)
		double x = r * Math.cos(p);
		double y = r * Math.sin(p);
	
		return new double[]{x, y};
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
			this.gy = gy; // y''(t) acceleration in y
			this.radius = r;
		}

		/**
		 * Position, speed, and radius of the ball. You may wish to add other attributes.
		 */
		double x, y, vx, vy, ax, gy, radius;
	}
}
