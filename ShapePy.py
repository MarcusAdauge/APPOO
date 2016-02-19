import abc  #Abstract Base Class model 

class Shape:
	#__metaclass__ = ABCMeta
	def __init__(self, name):
		self._name = name      #protected variable

	def getName(self):
		return self.name

	@abc.abstractmethod
	def getArea(self):
		"""computes the area of the shape"""
		return


class Rectangle(Shape):
	def __init__(self, width, height):
		self.name = "Rectangle"
		self.__width = width     #private variable
		self.__height = height

	def setWidth(self, w): 
		if w > 0:
		 self.__width = w	
	def setHeight(self, h):
		if h > 0:
			self.__height = h
	def getWidth(self):  return self.__width
	def getHeight(self): return self.__height
	
	def getArea(self):
		return self.__width * self.__height


class Circle(Shape):
	def __init__(self, radius):
		self.name = "Circle"
		self.__radius = radius

	def setRadius(self, rad):
		if rad > 0:
			self.__radius = rad
	def getRadius(self): return self.__radius
	def getArea(self):
		return self.__radius * self.__radius * 3.14159


if __name__ == "__main__":
	shapes = []
	circle = Circle(13)
	circle.setRadius(20)

	shapes.append(circle)
	rectangle = Rectangle(12, 30)
	shapes.append(rectangle)
	shapes.append(Rectangle(rectangle.getWidth()+5, rectangle.getHeight()-12))
	shapes.append(Circle(65))

	for x in shapes:
		print("This is a " + x.getName() + 
			   " with an area of " + str(x.getArea()))

	


