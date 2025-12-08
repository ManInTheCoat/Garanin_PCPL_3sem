from behave import given, when, then
from lab_python_oop.rectangle import Rectangle
from lab_python_oop.circle import Circle
from lab_python_oop.square import Square
import math

FIGURE_CLASSES = {
    "прямоугольник": Rectangle,
    "круг": Circle,
    "квадрат": Square
}

@given('я создаю "{color}" "{figure_type}"')
def step_given_create_figure_base(context, color, figure_type):
    """Шаг: Сохраняет тип фигуры и цвет в контексте."""
    context.figure_type = FIGURE_CLASSES.get(figure_type)
    context.color = color
    assert context.figure_type is not None, f"Неизвестный тип фигуры: {figure_type}"

@when('его ширина равна {width:d} и высота равна {height:d}')
def step_when_set_rectangle_dims(context, width, height):
    """Шаг: Инициализирует Прямоугольник."""
    assert context.figure_type == Rectangle
    context.figure = context.figure_type(context.color, width, height)

@when('его радиус равен {radius:g}')
def step_when_set_circle_dims(context, radius):
    """Шаг: Инициализирует Круг."""
    assert context.figure_type == Circle
    context.figure = context.figure_type(context.color, radius)

@when('его сторона равна {side:d}')
def step_when_set_square_dims(context, side):
    """Шаг: Инициализирует Квадрат."""
    assert context.figure_type == Square
    context.figure = context.figure_type(context.color, side)

@then('его площадь должна быть {area:g}')
def step_then_check_area(context, area):
    """Шаг: Проверяет точное значение площади."""
    assert context.figure.square() == area, f"Ожидалось {area}, получено {context.figure.square()}"

@then('его площадь должна быть приблизительно {area:g}')
def step_then_check_area_approx(context, area):
    """Шаг: Проверяет приблизительное значение площади (для float)."""
    assert math.isclose(context.figure.square(), area, rel_tol=1e-5), f"Ожидалось {area}, получено {context.figure.square()}"

@then('он также должен быть "{parent_class_name}"')
def step_then_check_inheritance(context, parent_class_name):
    """Шаг: Проверяет наследование (isinstance)."""
    parent_class = None
    if parent_class_name == "прямоугольником":
        parent_class = Rectangle

    assert parent_class is not None, f"Неизвестный родительский класс: {parent_class_name}"
    assert isinstance(context.figure, parent_class), f"Объект не является экземпляром {parent_class_name}"