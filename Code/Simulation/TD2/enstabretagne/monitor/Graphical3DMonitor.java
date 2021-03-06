/**
* Classe Graphical3DMonitor.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.monitor;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.Map;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.FPSAnimator;

import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;
import enstabretagne.simulation.core.ISimEntity;
import enstabretagne.simulation.core.SimEngine;

public abstract class Graphical3DMonitor implements IMonitor, GLEventListener, MouseListener, 
MouseMotionListener, KeyListener  {
	private SimEngine engine;

	@SuppressWarnings("unused")
	private LogicalDuration updateDelay;
	@SuppressWarnings("unused")
	private int fps;
	
    protected GLU glu;
	
	@SuppressWarnings("rawtypes")
	protected HashMap<Class,IDrawAction> drawActionsMapping;
	protected HashMap<ISimEntity,IDrawAction> objectRepresentation;

	public Graphical3DMonitor(String SimAppClassName,String AppName, int fps) {
		objectRepresentation = new HashMap<>();
		drawActionsMapping = new HashMap<>();
		glu = new GLU();
		
		this.fps=fps;
		this.zoom = 2;
		
		updateDelay=LogicalDuration.ofSeconds(1.0/fps);
		loggersNames = new HashMap<String,HashMap<String,Object>>();

	}
	
	protected HashMap<String,HashMap<String,Object>>  loggersNames;
	protected void initLogger() {		
		Logger.Init(engine,loggersNames,true);		
	}
	
	void terminateLogger() {
		Logger.Terminate();
	}
	
	void initSimEngine(){
		
		//Initialiser ici votre moteur de simulation
		engine =null;

		if(engine == null) return;
		
		initLogger();
	
	}
	
	@SuppressWarnings("rawtypes")
	public void associate3DRepresentationTo(ISimEntity o)
	{
		for(Class i:o.getClass().getInterfaces())
			if (drawActionsMapping.containsKey(i))
				objectRepresentation.put(o, drawActionsMapping.get(i));
	}
	
	
	void terminateSimEngine() {
	}
	
	void terminateMonitor() {
		terminateSimEngine();
		terminateLogger();
	}

	@Override
	public Temporal InitialCalendarDate() {
		return Instant.now();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch(arg0.getKeyCode()) {
		case 522:
			this.zoom *= 1.1;
			break;
		case 61:
			this.zoom /= 1.1;
			break;
		default:
			break;

		}		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int dx = e.getX() - this.lastPosX;
		int dy = e.getY() - this.lastPosY;

		if (e.getButton() == 0) {
			this.xCam += dx;
			this.zCam += dy;
		}
		this.lastPosX = e.getX();
		this.lastPosY = e.getY();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	protected int lastPosX;
	protected int lastPosY;
	@Override
	public void mousePressed(MouseEvent arg0) {
		this.lastPosX = arg0.getX();
		this.lastPosY = arg0.getY();		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	protected float zoom;

	@Override
	public void display(GLAutoDrawable drawable) {

		

		GL2 gl = drawable.getGL().getGL2();
		
	
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL2.GL_PROJECTION);	
		gl.glLoadIdentity();
		setPointOfView(gl);

		gl.glMatrixMode(GL2.GL_MODELVIEW);		
		gl.glLoadIdentity();
		
		
		for( Map.Entry<ISimEntity,IDrawAction> da : objectRepresentation.entrySet()){
			gl.glLoadIdentity();
			gl.glPushMatrix();
			da.getValue().draw(gl, da.getKey());
			gl.glPopMatrix();
		}
		
	}
	
	protected abstract void setPointOfView(GL2 gl);

	@Override
	public void dispose(GLAutoDrawable arg0) {
		terminateMonitor();
		
	}
	
	

	protected int xCam;
	protected int yCam;
	protected int zCam;
	
	
	
	public int getxCam() {
		return xCam;
	}

	public void setxCam(int xCam) {
		this.xCam = xCam;
	}

	public int getyCam() {
		return yCam;
	}

	public void setyCam(int yCam) {
		this.yCam = yCam;
	}

	public int getzCam() {
		return zCam;
	}

	public void setzCam(int zCam) {
		this.zCam = zCam;
	}

	private float lightPos[] = {5.0f, 5.0f, 10.0f, 1.0f} ;

	@Override
	public void init(GLAutoDrawable drawable) {
		drawable.getAnimator().setUpdateFPSFrames(1, null);
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glClearColor(0.9f, 0.9f, 1f, 1f);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, this.lightPos, 0);
		this.setxCam(180);
		this.setyCam(0);
		this.setzCam (45);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL2 gl = drawable.getGL().getGL2();
		int side = Math.min(width, height);
		gl.glViewport((width - side) / 2, (height - side) / 2, side, side);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustum(-1.0, 1.0, -1.0, 1.0, 5.0, 60.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		
	}
	
	@SuppressWarnings("unused")
	private FPSAnimator fpsAnimator;
	public void setFpsAnimator(FPSAnimator fpsAnimator) {
		this.fpsAnimator = fpsAnimator;
	}
	
		
	public void createGui() {
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		GLCanvas canvas = new GLCanvas(caps);

		Frame frame = new Frame("Sailboat Simulation");
		frame.setSize(300, 300);
		frame.add(canvas);
		frame.setVisible(true);

		this.initSimEngine();
		

		canvas.setFocusable(true);
		canvas.requestFocus();
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addKeyListener(this);

		// by default, an AWT Frame doesn't do anything when you click
		// the close button; this bit of code will terminate the program when
		// the window is asked to close
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				terminateLogger();
				System.exit(0);
			}
		});

		FPSAnimator animator = new FPSAnimator(canvas, 50, true);
		this.setFpsAnimator(animator);
		animator.start();
		
		
	}
	

}

