import java.util.*;

public class TestShape{

	public static void main(String[] args)
	{
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		Circle circle = new Circle(13);
		circle.setRadius(20); //changed my mind about the radius

		// polymorphic list
		shapes.add(circle);
		Rectangle rectangle = new Rectangle(12,30);
		shapes.add(rectangle);
		shapes.add(new Rectangle(rectangle.getWidth()+5, rectangle.getHeight() - 12));
		shapes.add(new Circle(65));

		for(Shape shape : shapes){
			System.out.println("This is a " + shape.getName() + 
								" with an area of " + shape.getArea());
		}
	}
}


abstract class Shape{
	protected String name;
	
	public Shape(String name){
		this.name = name;
	}

	public abstract double getArea();
	public String getName() { return name; }
}


class Rectangle extends Shape{	// inheritance, Rectange IS-A Shape
	private double width;
	private double height;

	public Rectangle(double width, double height){
		super("Rectangle");	// call parent class constructor
		this.width = width;
		this.height = height;
	}

    public double getArea() { return (width * height); }

    // setters and getters for ensuring encapsulation (protecting private data)
    public void setWidth(double w) { if(w > 0) width = w; }
	public void setHeight(double h) { if(h > 0) height = h; }
	public double getWidth() { return this.width; }
	public double getHeight() { return this.height; }
	

}


class Circle extends Shape{	  // inheritance, Circle IS-A Shape
	private double radius;

	public Circle(double radius){
		super("Circle");
		this.radius = radius;
	}

	public double getArea() { return (radius * radius * 3.14159); }

	// setters and getters for ensuring encapsulation (protecting private data)
	public void setRadius(double rad){
		if(rad > 0) radius = rad;
	}
	public double getRadius() { return radius; }
}