package IS;
import robocode.*;
import java.awt.Color;
import robocode.ScannedRobotEvent;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Robocop - a robot by (your name here)
 */
public class Robocop extends Robot
{
	boolean peek;
	/**
	 * run: Robocop's default behavior
	 */
	boolean peek;
	boolean phaseTwo = false;
	public void run() {
		// Initialization of the robot should be put here
		//comment
		setBodyColor(new Color(172, 204, 234));
		setGunColor(new Color(232, 80, 82));
		setBulletColor(new Color(232, 80, 82));
		setRadarColor(new Color(41, 133, 200));

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar
		double startPosition = 0;
		double moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		// Get to the left wall first
		if (getX()>startPosition){
			turnRight(-(getHeading() - 90));
			back(getX()-startPosition);
		}else {
			turnRight(-(getHeading() - 90));
			ahead(startPosition - getX());
		}
		// Get to the bottom next
		if (getY()>startPosition){
			turnRight(90);
			ahead(getY()-startPosition);
		}else {
			turnRight(90);
			back(startPosition - getY());
		}
		// Once there, get the tour
		turnGunLeft(-(getGunHeading()));
		turnGunLeft(90);
		
		//Mimics the Wall sample bot.
		while(getOthers()>1){
			peek = true;
			ahead(moveAmount);
			peek = false;
			turnRight(90);
		}
		// Switch to phase two where we no longer crawl on the wall but start attacking
		phaseTwo = true;
		while(true){
			turnRight(30);
			turnGunLeft(180);
			ahead(175);
			turnLeft(60);
			turnGunRight(180);
			back(175);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		double range = e.getDistance();
		if (range > 750) {
			//fire(5);
		}
		else if (range > 600 && range <= 750) {
			//fire(2);
		}
		else if (range > 450 && range <= 600) {
			fire(3);
		}
		else if (range > 250 && range <= 450) {
			fire(4);
		}
		else {
			fire(5);
		}
		if (peek) {
			scan();
		}

		if (peek) {
			scan();

		}
	}
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		turnLeft(90);
		ahead(185);
		turnRight(90);
		ahead(185);

		peek = false;


	}

	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		if (phaseTwo){
		turnLeft(-e.getBearing());
		turnLeft(180);
		ahead(400);
		}
		
	}
	public void onHitRobot(HitRobotEvent e){
		if (phaseTwo){
			back(30);
			turnRight(45);
			ahead(60);
			turnLeft(45);
		}else{
			turnGunLeft(45);
			fire(10);
			back(50);
			turnGunRight(45);
		}
	}
	public void onWin(WinEvent e) {
		for (int i = 0; i < 150; i++) {
			ahead(5);
			back(5);
			turnRight(10);
		}
	} 
}
