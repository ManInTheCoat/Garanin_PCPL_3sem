from lab_python_oop.rectangle import Rectangle
from lab_python_oop.circle import Circle
from lab_python_oop.square import Square
import numpy

def main():
    N = 6

    r = Rectangle("синего", N, N)
    c = Circle("зеленого", N)
    s = Square("красного", N)

    print(r)
    print(c)
    print(s)

    print(f"\nДемонстрация работы внешнего пакета:")
    print(f"Версия numpy: {numpy.__version__}")
    print(f"Пример использования numpy для вычисления площади круга: {numpy.pi * (N**2)}")

if __name__ == "__main__":
    main()