package Rectangle;

import java.awt.Rectangle;

public class RegionRectangle {
	
	//#region Private Variables -----------------------------------
	private double _x1;
	private double _x2;
	private double _y1;
	private double _y2;
	private double _width;
	private double _height;
	//#endregion Private Variables --------------------------------
	
	//#region Properties ------------------------------------------
	// X1
	public double getX1(){
		return this._x1;
	}
	public void setX1(double x){
		this._x1 = x;
	}
	
	// X2
	public double getX2(){
		return this._x2;
	}
	public void setX2(double x){
		this._x2 = x;
	}
	
	// Y1
	public double getY1(){
		return this._y1;
	}
	public void setY1(double y){
		this._y1 = y;
	}
	
	// Y2
	public double getY2(){
		return this._y2;
	}
	public void setY2(double y){
		this._y2 = y;
	}
	
	// Width
	public double getWidth(){
		return this._width;
	}
	public void setWidth(double width){
		this._width = width;
	}
	
	// Height
	public double getHeight(){
		return this._height;
	}
	public void setHeight(double height){
		this._height = height;
	}
	
	//#endregion Properties ---------------------------------------
	
	//#region Methods ---------------------------------------------
	/**
	 * Constructor for the Rectangle class with no arguments
	 */
	public RegionRectangle(){
		this._x1 = 0;
		this._x2 = 0;
		this._y1 = 0;
		this._y1 = 0;
		this._width = 0;
		this._height = 0;
	}
	
	/**
	 * Constructor for the Rectangle class.
	 * @param x1	The left boundary
	 * @param x2	The right boundary
	 * @param y1	The upper boundary
	 * @param y2	The lower boundary
	 */
	public RegionRectangle(double x1, double x2, double y1, double y2){
		
		// Check if x1 > x2, flip if they are
		if (x1 > x2){
			this._x1 = x2;
			this._x2 = x1;
		}
		else {
			this._x1 = x1;
			this._x2 = x2;
		}
		// Compute width
		this._width = this._x2 - this._x1;
		
		// Check if y1 > y2, flip if they are
		if (y1 > y2){
			this._y1 = y2;
			this._y2 = y1;
		}
		else {
			this._y1 = y1;
			this._y2 = y2;
		}
		// Compute height
		this._height = this._y2 - this._y1;
	}
	
	/***
	 * Tests if a rectangle intersects with this one
	 * @param rect	Another rectangle to check with this one
	 * @return		<code>true</code> if another rectangle intersects with this one
	 */
	public boolean intersects(RegionRectangle rect){
		Rectangle thisRectangle = new Rectangle();
		thisRectangle.setRect(_x1, _y1, _width, _height);
		
		Rectangle inputRectangle = new Rectangle();
		inputRectangle.setRect(rect.getX1(), rect.getY1(), rect.getWidth(), rect.getHeight());
		
		return thisRectangle.intersects(inputRectangle);
	}
	//#endregion Methods ------------------------------------------
}
