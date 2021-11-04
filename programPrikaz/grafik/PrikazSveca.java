package programPrikaz.grafik;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import parser.Sveca;
import parser.indikatori.Ema;
import parser.indikatori.Indikator;
import parser.indikatori.Ma;

public class PrikazSveca extends JPanel {
	final static int SIRINA = 14;
	
	int origW = 200;
	int origH = 200;
	
	double zoomX = 1;
	double zoomY = 1;
	
	ArrayList<Sveca> svece;
	ArrayList<Sveca> original;
	double skala = 0;
	double maks = 0;
	double min = 0;
	
	boolean ema = false;
	boolean ma = false;
	int emaN = 1;
	int maN = 1;
	Indikator.Vrsta emaVrsta = Indikator.Vrsta.CLOSE;
	Indikator.Vrsta maVrsta = Indikator.Vrsta.CLOSE;
	private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zoomer;
    private boolean dragger;
    private boolean released;
    private double xOffset;
    private double yOffset = 0;
    private int xDiff;
    private int yDiff;
    private double currMousePositionX = 0;
    private double currMousePositionY = 0;
    private Point startPoint;
    private boolean firstTime = true;
    
    private int selected = -1;
	PrikazSveca(ArrayList<Sveca> svece,int h,int w){
		super(null);
		this.setBorder(BorderFactory.createBevelBorder(0));
		this.setPreferredSize(new Dimension((SIRINA+1)*svece.size(),h));
		origH = h;
		origW = w;
		this.svece = svece; 
		original = svece;
		srediSvece();
		this.setBackground(Color.WHITE);
		this.addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
		        released = false;
		        startPoint = MouseInfo.getPointerInfo().getLocation();
		    }

		    @Override
		    public void mouseReleased(MouseEvent e) {
		        released = true;
		        dragger = true;
		        PrikazSveca.super.repaint();
		    }
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
		    public void mouseDragged(MouseEvent e) {
		        Point curPoint = e.getLocationOnScreen();
		        xDiff = curPoint.x - startPoint.x;
		        yDiff = curPoint.y - startPoint.y;

		        dragger = true;
		        PrikazSveca.super.repaint();
		    }
			@Override
			public void mouseMoved(MouseEvent e) {

				super.mouseMoved(e);
				if (!dragger) {
					int lstSelected = selected;
					currMousePositionX = (e.getX() - xOffset)/zoomFactor;
					currMousePositionY = (e.getY() - yOffset)/zoomFactor;
					
					selected = (int)(currMousePositionX/(SIRINA+1));
					if (selected < PrikazSveca.this.svece.size() && selected > -1) {
						Sveca s = PrikazSveca.this.svece.get(selected);
						
						if (s.getOpen() < s.getClose()) {
							if (origH -s.getOpen() > currMousePositionY && origH -s.getClose() < currMousePositionY) {
								repaint();
							}
							else {
								selected = -1;
								if (lstSelected != -1)
									repaint();
							}
							return;
						}
						else {
							if (origH -s.getOpen() < currMousePositionY && origH -s.getClose() > currMousePositionY) {
								repaint();
							}
							else {
								selected = -1;
								if (lstSelected != -1)
									repaint();
							}
							return;
						}
					}
					selected = -1;
					if (lstSelected != -1)
						repaint();
				}
				else {
					selected = -1;
				}
			}
		});
		
		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
		    public void mouseWheelMoved(MouseWheelEvent e) {

		        zoomer = true;
		        if (e.getWheelRotation() < 0) {
		        	if (zoomFactor < 5)
		        		zoomFactor *= 1.1;
		        	PrikazSveca.super.repaint();
		        }
		        if (e.getWheelRotation() > 0) {
		        	if (zoomFactor > 0.6)
		        		zoomFactor /= 1.1;
		        	PrikazSveca.super.repaint();
		        }
		    }
			
		});
		xOffset = -(double)(svece.size()*(SIRINA+1) - 1000);
		yOffset = -(origH - this.svece.get(this.svece.size()-1).getHigh() - 100);
		dragger = true;
		super.repaint();
	}
	@Override
	public Dimension getPreferredSize() {
		Dimension velicina = new Dimension(origW,origH);
		velicina.width = (int) Math.round(origW * zoomX);
		velicina.height = (int) Math.round(origH * zoomY);
		return velicina;
	}
	@Override 
	public void paintComponent(Graphics graphics) { 
		super.paintComponent(graphics);
        Graphics2D g = (Graphics2D)graphics.create();
        double granica = -(double)((svece.size() > 120 ? svece.size() : 120)*(SIRINA+1)*zoomFactor - 1000);
        if (zoomer) {
            AffineTransform at = new AffineTransform();

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoomFactor / prevZoomFactor;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;
            
            if (xOffset > 0) 
            	xOffset = 0;
            if (xOffset < granica) 
            	xOffset = granica;
            
            if (yOffset > 0) 
            	yOffset = 0;
            if (yOffset < -(origH * zoomFactor - 500)) 
            	yOffset = -(origH * zoomFactor - 500);
            at.translate(xOffset, yOffset);
            at.scale(zoomFactor, zoomFactor);
            prevZoomFactor = zoomFactor;
            g.transform(at);
           
            zoomer = false;
           
        }

        if (dragger) {
            AffineTransform at = new AffineTransform();
            if (xOffset + xDiff <= 0) {
            	if (xOffset + xDiff < granica) {
            		at.translate(granica,0);
            	}
            	else {
            		at.translate(xOffset + xDiff,0);
            	}
            }
            if (yOffset + yDiff <= 0 ){
            	if (yOffset + yDiff < -(origH*zoomFactor-500)) {
            		at.translate(0, -(origH*zoomFactor-500));
            	}
            	else{
            		at.translate(0, yOffset + yDiff);
            	}
            }
            
            

            if (released) {
                xOffset += xDiff;
                if (xOffset > 0) {
                	xOffset = 0;
                }
                if (xOffset < granica) { 
                	xOffset = granica;

                }
                yOffset += yDiff;
                if (yOffset > 0) { 
                	yOffset = 0;
                }
                if (yOffset < -(origH*zoomFactor-500)) { 
                	yOffset = -(origH*zoomFactor-500);
                }
                dragger = false;
            }
            at.scale(zoomFactor, zoomFactor);
            g.transform(at);

        }
        else {
        	 
        }
        for ( int x = 0; x <= (SIRINA+1)*((svece.size() > 120 ? svece.size() : 120)) + 120; x += 60 )
        	 for ( int y = 0; y <= 1150; y += 60 ) 
        		 g.drawRect( x, y, 60, 60 );

        for (int i=0;i<svece.size();i++) {
        	if (svece.get(i).getClose() > svece.get(i).getOpen()) 
        		g.setColor(Color.GREEN);
        	else
        		g.setColor(Color.RED);
        	g.drawLine(i*(SIRINA+1)+SIRINA/2,(int)(origH - svece.get(i).getLow()),i*(SIRINA+1)+SIRINA/2,(int)(origH-svece.get(i).getHigh()));
        	if (svece.get(i).getOpen() < svece.get(i).getClose()) {
        		g.fillRect(i*(SIRINA+1), (int)(origH - svece.get(i).getClose()), SIRINA, (int)(svece.get(i).getClose() - svece.get(i).getOpen() + 2));
        	}
        	else {
        		g.fillRect(i*(SIRINA+1), (int)(origH - svece.get(i).getOpen()), SIRINA, (int)(svece.get(i).getOpen() - svece.get(i).getClose() + 2));
        	}
        }
        if (ema) {
        	g.setColor(Color.BLACK);
        	drawInd(g,new Ema().izracunaj(svece, emaN, emaVrsta));
        }
        if (ma) {
        	g.setColor(Color.BLUE);
        	drawInd(g,new Ma().izracunaj(svece, maN, maVrsta));
        }
        if (selected > -1)
        	drawBox(g);
        g.dispose();
    }
	public void setEma(boolean ema,int n,Indikator.Vrsta vrsta) {
		this.ema = ema;
		this.emaN = n;
		this.emaVrsta = vrsta;
		dragger = true;
		xDiff = 0;
		yDiff = 0;
		super.repaint();
	}
	public void setMa(boolean ma,int n,Indikator.Vrsta vrsta) {
		this.ma = ma;
		this.maN = n;
		this.maVrsta = vrsta;
		dragger = true;
		xDiff = 0;
		yDiff = 0;
		super.repaint();
	}
	void setSvece(ArrayList<Sveca> svece) {
		this.svece = svece;
		this.original = svece;
		srediSvece();
		xOffset = -(double)(this.svece.size()*(SIRINA+1) - 1000);
		yOffset = -(origH - this.svece.get(this.svece.size()-1).getHigh() - 100);
		dragger = true;
		super.repaint();
	}
	private void srediSvece() {
		maks = Collections.max(svece,Sveca.max).getHigh();
		min = Collections.min(svece,Sveca.min).getLow();
		
		skala = ((double)origH - 60) / (maks - min);
		ArrayList<Sveca> sv = new ArrayList<Sveca>(); 
		for (Sveca s : svece) {
			double low = (s.getLow() - min) * skala;
			double high = (s.getHigh() - min) * skala;
			double open = (s.getOpen() - min) * skala;
			double close = (s.getClose() - min) * skala;
			double timestamp = s.getTimestamp();
			sv.add(new Sveca(timestamp,open,close,high,low));
		}
		svece = sv;
	}
	private void drawInd(Graphics2D g,ArrayList<Double> niz) {
		for (int i=0;i<niz.size()-1;i++) {
			g.drawLine(i * (SIRINA + 1) + SIRINA/2 , (int)(origH - niz.get(i)) ,
						(i + 1) * (SIRINA + 1) + SIRINA/2, (int)(origH - niz.get(i+1)));
		}
	}
	@Override
	public void repaint() {
		xDiff = 0;
		yDiff = 0;
		dragger = true;
		super.repaint();
	}
	private void drawBox(Graphics2D g) {
		int dX = (int)currMousePositionX;
		if (currMousePositionX*zoomFactor+xOffset > 500) {
			dX -= 50;
		}
		int dY = (int)currMousePositionY;
		g.setColor(new Color(252, 248, 172));
		g.fillRect(dX, dY, 65, 42);
		g.setColor(new Color(189, 185, 108));
		g.drawRect(dX+1, dY+1, 64, 41);
		g.setFont(new Font(Font.DIALOG,Font.PLAIN,8));
		g.setColor(Color.BLACK);
		dX += 2;
		dY += 9;
		g.drawString("Open: " + String.format("%.5f",original.get(selected).getOpen()), dX, dY);
		dY += 10;
		g.drawString("Close: " + String.format("%.5f",original.get(selected).getClose()), dX, dY);
		dY += 10;
		g.drawString("High: " + String.format("%.5f",original.get(selected).getHigh()), dX, dY);
		dY += 10;
		g.drawString("Low: " + String.format("%.5f",original.get(selected).getLow()), dX, dY);
	}
}
