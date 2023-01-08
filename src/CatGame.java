import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import javax.sound.sampled.*;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;


public class CatGame extends JFrame implements GLEventListener, KeyListener {
	JFrame frame;
	GLCanvas canvas;
	Animator an;
	Clip clip1, clip2, clip3;
	
	public CatGame() {
		frame = new JFrame("Mohammed");

		frame.setLocation(200, 50);

		canvas = new GLCanvas();
		canvas.setSize(600, 600);
		an = new Animator(canvas);
		an.start();

		canvas.addGLEventListener(this);
		canvas.addKeyListener(this);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		glu.gluOrtho2D(-300, 300, -300, 300);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	float catx = 190, caty = -150, speed = 0.3f, carx = -350, car2x = 350, cary = 650, car2y = -650;
	String ch = "";
	int score;

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

		// road
		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor3f(0.2f, 0.2f, 0.2f);
		gl.glVertex2i(300, 40);
		gl.glVertex2i(-300, 40);
		gl.glVertex2i(-300, -80);
		gl.glVertex2i(300, -80);
		gl.glEnd();

		gl.glEnable(GL2.GL_LINE_STIPPLE);
		gl.glLineStipple(1, (short) 0xFF00);
		gl.glLineWidth(3);
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f(1, 1, 1);
		gl.glVertex2i(300, -20);
		gl.glVertex2i(170, -20);
		gl.glEnd();
		gl.glDisable(GL2.GL_LINE_STIPPLE);

		gl.glEnable(GL2.GL_LINE_STIPPLE);
		gl.glLineStipple(1, (short) 0xFF00);
		gl.glLineWidth(3);
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f(1, 1, 1);
		gl.glVertex2i(50, -20);
		gl.glVertex2i(-300, -20);
		gl.glEnd();
		gl.glDisable(GL2.GL_LINE_STIPPLE);

		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor3f(0.2f, 0.2f, 0.2f);
		gl.glVertex2i(20, -300);
		gl.glVertex2i(140, -300);
		gl.glVertex2i(140, 300);
		gl.glVertex2i(20, 300);
		gl.glEnd();

		gl.glEnable(GL2.GL_LINE_STIPPLE);
		gl.glLineStipple(1, (short) 0xFF00);
		gl.glLineWidth(3);
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f(1, 1, 1);
		gl.glVertex2i(80, 40);
		gl.glVertex2i(80, 300);
		gl.glEnd();
		gl.glDisable(GL2.GL_LINE_STIPPLE);

		gl.glEnable(GL2.GL_LINE_STIPPLE);
		gl.glLineStipple(1, (short) 0xFF00);
		gl.glLineWidth(3);
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f(1, 1, 1);
		gl.glVertex2i(80, -80);
		gl.glVertex2i(80, -300);
		gl.glEnd();
		gl.glDisable(GL2.GL_LINE_STIPPLE);

		//////////////////////
		// top right grass
		// green
		gl.glColor3d(0.1, 0.8, 0);
		gl.glRectd(150, 50, 300, 300);

		// dark green
		gl.glColor3d(0.1, 0.6, 0.2);
		gl.glRectd(180, 80, 300, 300);

		// green
		gl.glColor3d(0.1, 0.8, 0);
		gl.glRectd(210, 110, 300, 300);

		// dark green
		gl.glColor3d(0.1, 0.6, 0.2);
		gl.glRectd(240, 140, 300, 300);

		// green
		gl.glColor3d(0.1, 0.8, 0);
		gl.glRectd(270, 170, 300, 300);

		// top right side walk
		gl.glColor3d(0.5, 0.5, 0.5);
		gl.glRectd(140, 40, 150, 300);
		gl.glRectd(140, 40, 300, 50);

		// bottom right grass
		// green
		gl.glColor3d(0.1, 0.8, 0);
		gl.glRectd(150, -90, 300, -300);

		// dark green
		gl.glColor3d(0.1, 0.6, 0.2);
		gl.glRectd(180, -120, 300, -300);

		// green
		gl.glColor3d(0.1, 0.8, 0);
		gl.glRectd(210, -150, 300, -300);

		// dark green
		gl.glColor3d(0.1, 0.6, 0.2);
		gl.glRectd(240, -180, 300, -300);

		// green
		gl.glColor3d(0.1, 0.8, 0);
		gl.glRectd(270, -210, 300, -300);

		// bottom right side walk
		gl.glColor3d(0.5, 0.5, 0.5);
		gl.glRectd(140, -80, 150, -300);
		gl.glRectd(140, -80, 300, -90);
		// bottom left grass
		// green
		gl.glColor3d(0.1, 0.8, 0);
		gl.glRectd(10, -90, -300, -300);

		// dark green
		gl.glColor3d(0.1, 0.6, 0.2);
		gl.glRectd(-20, -120, -300, -300);

		// green
		gl.glColor3d(0.1, 0.8, 0);
		gl.glRectd(-50, -150, -300, -300);

		// dark green
		gl.glColor3d(0.1, 0.6, 0.2);
		gl.glRectd(-80, -180, -300, -300);

		// green
		gl.glColor3d(0.1, 0.8, 0);
		gl.glRectd(-110, -210, -300, -300);

		// dark green
		gl.glColor3d(0.1, 0.6, 0.2);
		gl.glRectd(-140, -240, -300, -300);

		// green
		gl.glColor3d(0.1, 0.8, 0);
		gl.glRectd(-170, -270, -300, -300);

		// bottom left side walk
		gl.glColor3d(0.5, 0.5, 0.5);
		gl.glRectd(20, -80, 10, -300);
		gl.glRectd(20, -80, -300, -90);

		// ground
		gl.glColor3d(0.5, 0.5, 0.5);
		gl.glRectd(-300, 40, 20, 300);

		// building 1
		gl.glColor3d(0.4, 0.4, 0.5);
		gl.glRectd(-300, 50, -150, 180);

		// outline of glass
		gl.glColor3d(0, 0, 0);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(-280, 70);
		gl.glVertex2d(-170, 70);
		gl.glVertex2d(-170, 70);
		gl.glVertex2d(-170, 160);
		gl.glVertex2d(-170, 160);
		gl.glVertex2d(-280, 160);
		gl.glVertex2d(-280, 160);
		gl.glVertex2d(-280, 70);
		gl.glEnd();

		// glass
		gl.glColor3d(0.3, 1, 1);
		gl.glRectd(-280, 70, -170, 160);

		// pyramid outline for glass
		gl.glLineWidth(1);
		gl.glColor3d(0, 0, 0);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(-280, 70);
		gl.glVertex2d(-225, 115);
		gl.glVertex2d(-170, 70);
		gl.glVertex2d(-225, 115);
		gl.glVertex2d(-280, 160);
		gl.glVertex2d(-225, 115);
		gl.glVertex2d(-170, 160);
		gl.glVertex2d(-225, 115);
		gl.glEnd();

		// b1 roof details
		gl.glColor3d(0.2, 0.4, 0.5);
		gl.glRectd(-250, 165, -220, 175);
		gl.glRectd(-200, 165, -170, 175);

		gl.glLineWidth(4);
		gl.glColor3d(0.5, 0.5, 0.5);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(-290, 80);
		gl.glVertex2d(-290, 100);
		gl.glEnd();

		// building 2
		gl.glColor3d(0.4, 0.2, 0.3);
		gl.glRectd(-130, 50, 10, 100);
		gl.glRectd(-50, 100, 10, 180);

		// b2 roof details
		gl.glColor3d(1, 1, 1);
		gl.glRectd(-120, 60, -70, 80);
		gl.glRectd(-60, 60, -10, 80);

		gl.glColor3d(0, 0, 0);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(-100, 65);
		gl.glVertex2d(-90, 75);
		gl.glVertex2d(-90, 65);
		gl.glVertex2d(-100, 75);

		gl.glVertex2d(-40, 65);
		gl.glVertex2d(-30, 75);
		gl.glVertex2d(-30, 65);
		gl.glVertex2d(-40, 75);
		gl.glEnd();

		gl.glColor3d(0, 0, 0);
		gl.glLineWidth(4);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(-40, 170);
		gl.glVertex2d(0, 170);
		gl.glVertex2d(-40, 160);
		gl.glVertex2d(0, 160);
		gl.glVertex2d(-40, 150);
		gl.glVertex2d(0, 150);
		gl.glVertex2d(-40, 130);
		gl.glVertex2d(0, 130);
		gl.glVertex2d(-40, 120);
		gl.glVertex2d(0, 120);
		gl.glVertex2d(-40, 110);
		gl.glVertex2d(0, 110);
		gl.glEnd();

		// b2 park
		gl.glColor3d(0.5, 0.9, 0.3);
		gl.glRectd(-130, 100, -50, 180);

		// b2 park outline
		gl.glColor3d(1, 1, 1);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(-50, 178);
		gl.glVertex2d(-130, 178);
		gl.glVertex2d(-128, 178);
		gl.glVertex2d(-128, 100);
		gl.glEnd();

		// b2park details
		gl.glLineWidth(4);
		gl.glColor3d(0.9, 0.5, 0);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(-120, 110);
		gl.glVertex2d(-120, 130);
		gl.glVertex2d(-120, 140);
		gl.glVertex2d(-120, 160);
		gl.glVertex2d(-110, 170);
		gl.glVertex2d(-90, 170);
		gl.glVertex2d(-80, 170);
		gl.glVertex2d(-60, 170);
		gl.glEnd();
		gl.glColor3d(0.3, 0.3, 1);
		gl.glRectd(-110, 110, -60, 160);

		// building 3
		gl.glColor3d(0.7, 0.6, 0.6);
		gl.glRectd(-200, 200, 10, 290);
		gl.glColor3d(0, 0, 0);
		gl.glLineWidth(1);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(-200, 200);
		gl.glVertex2d(10, 200);
		gl.glVertex2d(10, 200);
		gl.glVertex2d(10, 290);
		gl.glVertex2d(10, 290);
		gl.glVertex2d(-200, 290);
		gl.glVertex2d(-200, 290);
		gl.glVertex2d(-200, 200);
		gl.glEnd();

		// b3 roof details
		// glass
		gl.glColor3d(0.3, 1, 1);
		gl.glRectd(-180, 210, -140, 240);
		gl.glRectd(-50, 210, -10, 240);
		gl.glRectd(-50, 250, -10, 280);
		gl.glRectd(-180, 250, -140, 280);
		gl.glRectd(-120, 210, -70, 280);

		// glass outlines
		gl.glColor3d(0, 0, 0);
		gl.glLineWidth(1);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(-120, 210);
		gl.glVertex2d(-70, 210);
		gl.glVertex2d(-70, 210);
		gl.glVertex2d(-70, 280);
		gl.glVertex2d(-70, 280);
		gl.glVertex2d(-120, 280);
		gl.glVertex2d(-120, 280);
		gl.glVertex2d(-120, 210);

		gl.glVertex2d(-180, 250);
		gl.glVertex2d(-140, 250);
		gl.glVertex2d(-140, 250);
		gl.glVertex2d(-140, 280);
		gl.glVertex2d(-140, 280);
		gl.glVertex2d(-180, 280);
		gl.glVertex2d(-180, 280);
		gl.glVertex2d(-180, 250);

		gl.glVertex2d(-50, 250);
		gl.glVertex2d(-10, 250);
		gl.glVertex2d(-10, 250);
		gl.glVertex2d(-10, 280);
		gl.glVertex2d(-10, 280);
		gl.glVertex2d(-50, 280);
		gl.glVertex2d(-50, 280);
		gl.glVertex2d(-50, 250);

		gl.glVertex2d(-180, 210);
		gl.glVertex2d(-140, 210);
		gl.glVertex2d(-140, 210);
		gl.glVertex2d(-140, 240);
		gl.glVertex2d(-140, 240);
		gl.glVertex2d(-180, 240);
		gl.glVertex2d(-180, 240);
		gl.glVertex2d(-180, 210);

		gl.glVertex2d(-50, 210);
		gl.glVertex2d(-10, 210);
		gl.glVertex2d(-10, 210);
		gl.glVertex2d(-10, 240);
		gl.glVertex2d(-10, 240);
		gl.glVertex2d(-50, 240);
		gl.glVertex2d(-50, 240);
		gl.glVertex2d(-50, 210);
		gl.glEnd();

		// garage
		gl.glColor3d(1, 1, 1);
		gl.glLineWidth(4);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(-260, 300);
		gl.glVertex2d(-260, 280);
		gl.glVertex2d(-240, 300);
		gl.glVertex2d(-240, 280);
		gl.glVertex2d(-220, 300);
		gl.glVertex2d(-220, 280);

		gl.glVertex2d(-300, 270);
		gl.glVertex2d(-270, 270);
		gl.glVertex2d(-300, 250);
		gl.glVertex2d(-270, 250);
		gl.glVertex2d(-300, 230);
		gl.glVertex2d(-270, 230);
		gl.glVertex2d(-300, 210);
		gl.glVertex2d(-270, 210);
		gl.glVertex2d(-300, 190);
		gl.glVertex2d(-270, 190);
		gl.glEnd();

		// food
		if ((int) catx <= fx + 10 && (int) catx >= fx - 10 && (int) caty <= fy + 10 && (int) caty >= fy - 10) {
			food();
			score++;
			eatSound();
		}
		gl.glPushMatrix();
		gl.glTranslated(fx, fy, 0);
		gl.glColor3f(1f, 215 / 255f, 0);
		circle(gl, 0, 0, 10, 1f, 200 / 255f, 0);
		circle(gl, 0, 0, 5, 1f, 0.2f, 0.2f);
		gl.glPopMatrix();

		/////////////// cat/////////////
		gl.glPushMatrix();
		gl.glTranslated(catx, caty, 0);
		if (ch == "w" || ch == "") {
			circle(gl, 0, 18, 10, 1, 1, 1);
			circle(gl, -5, 21, 2, 0, 0, 0);
			circle(gl, 5, 21, 2, 0, 0, 0);
			gl.glColor3f(1, 1, 1);
			gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex2i(-5, -19);
			gl.glVertex2i(-4, -26);
			gl.glVertex2i(-2, -28);
			gl.glVertex2i(0, -40);
			gl.glVertex2i(2, -28);
			gl.glVertex2i(4, -26);
			gl.glVertex2i(5, -19);
			gl.glEnd();
			if (ch == "w" && (caty <= 40 || catx >= 20))
				caty += 0.3;
		} else if (ch == "s") {
			circle(gl, 0, -18, 10, 1, 1, 1);
			circle(gl, -5, -21, 2, 0, 0, 0);
			circle(gl, 5, -21, 2, 0, 0, 0);
			gl.glColor3f(1, 1, 1);
			gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex2i(-5, 19);
			gl.glVertex2i(-4, 26);
			gl.glVertex2i(-2, 28);
			gl.glVertex2i(0, 40);
			gl.glVertex2i(2, 28);
			gl.glVertex2i(4, 26);
			gl.glVertex2i(5, 19);
			gl.glEnd();
			caty -= 0.3;
		} else if (ch == "d") {
			circle(gl, 18, 0, 10, 1, 1, 1);
			circle(gl, 21, -5, 2, 0, 0, 0);
			circle(gl, 21, 5, 2, 0, 0, 0);
			gl.glColor3f(1, 1, 1);
			gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex2i(-19, -5);
			gl.glVertex2i(-26, -4);
			gl.glVertex2i(-28, -2);
			gl.glVertex2i(-40, 0);
			gl.glVertex2i(-28, 2);
			gl.glVertex2i(-26, 4);
			gl.glVertex2i(-19, 5);
			gl.glEnd();
			catx += 0.3;
		} else if (ch == "a") {
			circle(gl, -18, 0, 10, 1, 1, 1);
			circle(gl, -21, -5, 2, 0, 0, 0);
			circle(gl, -21, 5, 2, 0, 0, 0);
			gl.glColor3f(1, 1, 1);
			gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex2i(19, -5);
			gl.glVertex2i(26, -4);
			gl.glVertex2i(28, -2);
			gl.glVertex2i(40, 0);
			gl.glVertex2i(28, 2);
			gl.glVertex2i(26, 4);
			gl.glVertex2i(19, 5);
			gl.glEnd();
			if (caty <= 40 || catx >= 20)
				catx -= 0.3;
		}
		circle(gl, 0, 0, 20, 1, 1, 1);
		gl.glPopMatrix();
		

		//////////////////// score //////////////////////

		GLUT glut = new GLUT();
		gl.glColor3d(1, 1, 1);
		gl.glRasterPos2i(200, 270);
		glut.glutBitmapString(glut.BITMAP_TIMES_ROMAN_24, "score = " + score);

		///////////////////////////////////////////

		// car : brown
		gl.glPushMatrix();
		gl.glTranslated(carx += speed, 0, 0);
		gl.glColor3d(0.8, 0.5, 0);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(-200, 30);
		gl.glVertex2d(-205, 20);
		gl.glVertex2d(-205, 0);
		gl.glVertex2d(-200, -10);
		gl.glVertex2d(-100, -10);
		gl.glVertex2d(-95, 0);
		gl.glVertex2d(-95, 10);
		gl.glVertex2d(-95, 20);
		gl.glVertex2d(-100, 30);
		gl.glVertex2d(-200, 30);
		gl.glEnd();

// head lights
		gl.glPointSize(6);
		gl.glColor3d(0.9, 0.7, 0);
		gl.glBegin(GL2.GL_POINTS);
		gl.glVertex2d(-105, 0);
		gl.glVertex2d(-105, 20);
		gl.glEnd();

// mirrors
		gl.glLineWidth(3);
		gl.glColor3d(0.8, 0.5, 0);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(-130, -10);
		gl.glVertex2d(-140, -15);
		gl.glVertex2d(-130, 30);
		gl.glVertex2d(-140, 35);
		gl.glEnd();

// car roof
		gl.glColor3d(0.8, 0.4, 0.3);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(-185, 20);
		gl.glVertex2d(-130, 20);
		gl.glVertex2d(-130, 0);
		gl.glVertex2d(-185, 0);
		gl.glVertex2d(-185, 20);
		gl.glEnd();

// car glass
// front glass
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(-125, 20);
		gl.glVertex2d(-125, 0);
		gl.glVertex2d(-115, 0);
		gl.glVertex2d(-115, 20);
		gl.glVertex2d(-125, 20);
		gl.glEnd();

// back glass
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(-195, 20);
		gl.glVertex2d(-195, 0);
		gl.glVertex2d(-190, 0);
		gl.glVertex2d(-190, 20);
		gl.glVertex2d(-195, 20);
		gl.glEnd();

// right window
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(-185, 22);
		gl.glVertex2d(-130, 22);
		gl.glVertex2d(-130, 25);
		gl.glVertex2d(-185, 25);
		gl.glVertex2d(-185, 22);
		gl.glEnd();

// left window
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(-185, -2);
		gl.glVertex2d(-130, -2);
		gl.glVertex2d(-130, -5);
		gl.glVertex2d(-185, -5);
		gl.glVertex2d(-185, -2);
		gl.glEnd();
		gl.glPopMatrix();

		///////////////////////////////////////////

		// car : blue
		gl.glPushMatrix();
		gl.glTranslated(car2x -= speed, 0, 0);
		gl.glColor3d(0, 0, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(270, -30);
		gl.glVertex2d(275, -40);
		gl.glVertex2d(275, -60);
		gl.glVertex2d(270, -70);
		gl.glVertex2d(170, -70);
		gl.glVertex2d(165, -60);
		gl.glVertex2d(165, -50);
		gl.glVertex2d(165, -40);
		gl.glVertex2d(170, -30);
		gl.glVertex2d(270, -30);
		gl.glEnd();

// head lights
		gl.glPointSize(6);
		gl.glColor3d(0.9, 0.7, 0);
		gl.glBegin(GL2.GL_POINTS);
		gl.glVertex2d(175, -60);
		gl.glVertex2d(175, -40);
		gl.glEnd();

// mirrors
		gl.glLineWidth(4);
		gl.glColor3d(0, 0, 1);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(200, -70);
		gl.glVertex2d(210, -75);
		gl.glVertex2d(200, -30);
		gl.glVertex2d(210, -25);
		gl.glEnd();

// car roof
		gl.glColor3d(0.8, 0.4, 0.3);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(255, -40);
		gl.glVertex2d(200, -40);
		gl.glVertex2d(200, -60);
		gl.glVertex2d(255, -60);
		gl.glVertex2d(255, -40);
		gl.glEnd();

// car glass
// front glass
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(195, -40);
		gl.glVertex2d(195, -60);
		gl.glVertex2d(185, -60);
		gl.glVertex2d(185, -40);
		gl.glVertex2d(195, -40);
		gl.glEnd();

// back glass
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(265, -40);
		gl.glVertex2d(265, -60);
		gl.glVertex2d(260, -60);
		gl.glVertex2d(260, -40);
		gl.glVertex2d(265, -40);
		gl.glEnd();

// right window
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(255, -38);
		gl.glVertex2d(200, -38);
		gl.glVertex2d(200, -35);
		gl.glVertex2d(255, -35);
		gl.glVertex2d(255, -38);
		gl.glEnd();

// left window
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(255, -62);
		gl.glVertex2d(200, -62);
		gl.glVertex2d(200, -65);
		gl.glVertex2d(255, -65);
		gl.glVertex2d(255, -62);
		gl.glEnd();
		gl.glPopMatrix();

		///////////////////////////////////////////

		// car : red
		gl.glPushMatrix();
		gl.glTranslated(0, cary -= speed, 0);
		gl.glColor3d(1, 0, 0);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(25, 180);
		gl.glVertex2d(40, 190);
		gl.glVertex2d(60, 190);
		gl.glVertex2d(75, 180);
		gl.glVertex2d(75, 80);
		gl.glVertex2d(60, 70);
		gl.glVertex2d(40, 70);
		gl.glVertex2d(25, 80);
		gl.glVertex2d(25, 180);

		gl.glEnd();

// head lights
		gl.glPointSize(6);
		gl.glColor3d(0.9, 0.7, 0);
		gl.glBegin(GL2.GL_POINTS);
		gl.glVertex2d(65, 85);
		gl.glVertex2d(35, 85);
		gl.glEnd();

// mirrors
		gl.glLineWidth(4);
		gl.glColor3d(1, 0, 0);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(75, 100);
		gl.glVertex2d(80, 105);
		gl.glVertex2d(25, 100);
		gl.glVertex2d(20, 105);
		gl.glEnd();

// car roof
		gl.glColor3d(0.8, 0.4, 0.3);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(35, 170);
		gl.glVertex2d(65, 170);
		gl.glVertex2d(65, 105);
		gl.glVertex2d(35, 105);
		gl.glVertex2d(35, 170);
		gl.glEnd();

// car glass
// front glass
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(35, 105);
		gl.glVertex2d(65, 105);
		gl.glVertex2d(65, 95);
		gl.glVertex2d(35, 95);
		gl.glVertex2d(35, 105);
		gl.glEnd();

// back glass
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(35, 175);
		gl.glVertex2d(65, 175);
		gl.glVertex2d(65, 180);
		gl.glVertex2d(35, 180);
		gl.glVertex2d(35, 175);
		gl.glEnd();

// right window
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(65, 170);
		gl.glVertex2d(68, 170);
		gl.glVertex2d(68, 105);
		gl.glVertex2d(65, 105);
		gl.glVertex2d(65, 170);
		gl.glEnd();

// left window
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(35, 170);
		gl.glVertex2d(32, 170);
		gl.glVertex2d(32, 105);
		gl.glVertex2d(35, 105);
		gl.glVertex2d(35, 170);
		gl.glEnd();
		gl.glPopMatrix();

		///////////////////////////////////////////

		// car : cyan
		gl.glPushMatrix();
		gl.glTranslated(0, car2y += speed, 0);
		gl.glColor3d(0, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(85, -230);
		gl.glVertex2d(100, -240);
		gl.glVertex2d(120, -240);
		gl.glVertex2d(135, -230);
		gl.glVertex2d(135, -130);
		gl.glVertex2d(120, -120);
		gl.glVertex2d(100, -120);
		gl.glVertex2d(85, -130);
		gl.glVertex2d(85, -230);
		gl.glEnd();

// head lights
		gl.glPointSize(6);
		gl.glColor3d(0.9, 0.7, 0);
		gl.glBegin(GL2.GL_POINTS);
		gl.glVertex2d(125, -135);
		gl.glVertex2d(95, -135);
		gl.glEnd();

// mirrors
		gl.glLineWidth(6);
		gl.glColor3d(0, 1, 1);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(135, -150);
		gl.glVertex2d(140, -155);
		gl.glVertex2d(85, -150);
		gl.glVertex2d(80, -155);
		gl.glEnd();

// car roof
		gl.glColor3d(0.8, 0.4, 0.3);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(95, -220);
		gl.glVertex2d(125, -220);
		gl.glVertex2d(125, -155);
		gl.glVertex2d(95, -155);
		gl.glVertex2d(95, -220);
		gl.glEnd();

// car glass
// front glass
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(95, -155);
		gl.glVertex2d(125, -155);
		gl.glVertex2d(125, -145);
		gl.glVertex2d(95, -145);
		gl.glVertex2d(95, -155);
		gl.glEnd();

// back glass
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(95, -225);
		gl.glVertex2d(125, -225);
		gl.glVertex2d(125, -230);
		gl.glVertex2d(95, -230);
		gl.glVertex2d(125, -225);
		gl.glEnd();

// right window
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(125, -220);
		gl.glVertex2d(128, -220);
		gl.glVertex2d(128, -155);
		gl.glVertex2d(125, -155);
		gl.glVertex2d(125, -220);
		gl.glEnd();

// left window
		gl.glColor3d(0.3, 1, 1);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(95, -220);
		gl.glVertex2d(92, -220);
		gl.glVertex2d(92, -155);
		gl.glVertex2d(95, -155);
		gl.glVertex2d(95, -220);
		gl.glEnd();
		gl.glPopMatrix();

		if (carx >= 850f && carx <= 851f) {
			carx = -350f;
			car2x = 350f;
			cary = 650f;
			car2y = -650f;
		}
		if (catx <= carx - 80f && catx >= carx - 81f && caty <= 50 && caty >= -30) {
			gl.glColor3d(1, 1, 1);
			gl.glRectd(-120, 40, 170, -40);
			gl.glColor3d(0, 0, 0);
			gl.glRasterPos2i(-100, 0);
			glut.glutBitmapString(glut.BITMAP_TIMES_ROMAN_24, "Game Over " +"your score: "+score);
			an.stop();
			failSound();
		}
		if (catx >= car2x + 150f && catx <= car2x + 151f && caty <= -10 && caty >= -90) {
			gl.glColor3d(1, 1, 1);
			gl.glRectd(-120, 40, 170, -40);
			gl.glColor3d(0, 0, 0);
			gl.glRasterPos2i(-100, 0);
			glut.glutBitmapString(glut.BITMAP_TIMES_ROMAN_24, "Game Over " +"your score: "+score);
			an.stop();
			failSound();
		}
		if (caty >= cary + 50f && caty <= cary + 51f && catx <= 90 && catx >= 10) {
			gl.glColor3d(1, 1, 1);
			gl.glRectd(-120, 40, 170, -40);
			gl.glColor3d(0, 0, 0);
			gl.glRasterPos2i(-100, 0);
			glut.glutBitmapString(glut.BITMAP_TIMES_ROMAN_24, "Game Over " +"your score: "+score);
			an.stop();
			failSound();
		}
		if (caty <= car2y - 105f && caty >= car2y - 106f && catx <= 150 && catx >= 70) {
			gl.glColor3d(1, 1, 1);
			gl.glRectd(-120, 40, 170, -40);
			gl.glColor3d(0, 0, 0);
			gl.glRasterPos2i(-100, 0);
			glut.glutBitmapString(glut.BITMAP_TIMES_ROMAN_24, "Game Over " +"your score: "+score);
			an.stop();
			failSound();
		}
		if(score!=0&&score%5==0&&x==0) {
			speed+=0.2f;
			x++;
			levelSound();
		}
		if(x==1&&score%5!=0) {
			x=0;
		}

	}
	int x;
	public void circle(GL2 gl, int cx, int cy, int radio, float r, float g, float b) {
		double theta;
		final double PI = Math.PI;

		gl.glColor3f(r, g, b);
		gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
		gl.glBegin(GL2.GL_POLYGON);

		for (int i = 0; i < 360; i++) {
			theta = i * PI / 180;
			gl.glVertex2d(cx + radio * Math.cos(theta), cy + radio * Math.sin(theta));
		}
		gl.glEnd();
		gl.glFlush();
	}

	int fx, fy;
	Random random = new Random();

	void food() {
		while (true) {
			fx = random.nextInt(290 + 290) - 290;
			fy = random.nextInt(290 + 290) - 290;
			if (!(fx < 30 && fy > 30))
				break;
		}

	}
	
	public void eatSound(){
		try { 
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sounds//win.wav"));
			clip1 = AudioSystem.getClip();
			clip1.open(audio);
			clip1.start();
		}
		catch(UnsupportedAudioFileException e1) 
		{System.out.println(e1.getStackTrace());
		
		}catch(IOException e2)
		{System.out.println(e2.getStackTrace());}
		
		catch(LineUnavailableException e3) {
			System.out.println(e3.getStackTrace());
			
		}
	}
	
	public void failSound(){
		try { 
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sounds//game-over.wav"));
			clip2 = AudioSystem.getClip();
			clip2.open(audio);
			clip2.start();
		}
		catch(UnsupportedAudioFileException e1) 
		{System.out.println(e1.getStackTrace());
		
		}catch(IOException e2)
		{System.out.println(e2.getStackTrace());}
		
		catch(LineUnavailableException e3) {
			System.out.println(e3.getStackTrace());
			
		}
	}
	
	public void levelSound(){
		try { 
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sounds//winning.wav"));
			clip3 = AudioSystem.getClip();
			clip3.open(audio);
			clip3.start();
		}
		catch(UnsupportedAudioFileException e1) 
		{System.out.println(e1.getStackTrace());
		
		}catch(IOException e2)
		{System.out.println(e2.getStackTrace());}
		
		catch(LineUnavailableException e3) {
			System.out.println(e3.getStackTrace());
			
		}
	}
	
	public void restart() {
		an.start();
		catx = 190; caty = -150; carx = -350; car2x = 350; cary = 650; car2y = -650;
		score=0;
		speed=0.3f;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'w') {
			ch = "w";
		} else if (e.getKeyChar() == 's') {
			ch = "s";
		} else if (e.getKeyChar() == 'd') {
			ch = "d";
		} else if (e.getKeyChar() == 'a') {
			ch = "a";
		} else if (e.getKeyChar() == 'r') {
			restart();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		ch = "";

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {

		new CatGame();

	}
}
