import pytest
import math
from lab_python_oop.rectangle import Rectangle
from lab_python_oop.circle import Circle
from lab_python_oop.square import Square

def test_rectangle_square():
    """Тест: Вычисление площади прямоугольника."""
    r = Rectangle("синий", 10, 5)
    assert r.square() == 50

def test_circle_square():
    """Тест: Вычисление площади круга (сравнение float)."""
    c = Circle("зеленый", 7)
    expected_area = math.pi * (7**2)
    assert c.square() == pytest.approx(expected_area)

def test_square_square_and_type():
    """Тест: Площадь квадрата и проверка наследования."""
    s = Square("красный", 6)
    assert s.square() == 36
    assert isinstance(s, Rectangle)

def test_square_repr():
    """Тест: Строковое представление (__repr__) Квадрата."""
    s = Square("красный", 6)
    expected_repr = "Квадрат красный цвета со стороной 6 площадью 36."
    assert repr(s) == expected_repr
