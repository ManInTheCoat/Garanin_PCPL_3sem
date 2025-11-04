import sys
import math


def get_coef(index, prompt):
    try:
        coef_str = sys.argv[index]
    except:
        print(prompt)
        coef_str = input()
    coef = float(coef_str)
    return coef


def get_roots(a, b, c):
    if a == 0:
        if b == 0:
            return []

        t = -c / b
        if t > 0:
            root = math.sqrt(t)
            return [-root, root]
        elif t == 0:
            return [0.0]
        else:
            return []

    D = b * b - 4 * a * c

    if D < 0:
        return []

    t_roots = []
    if D == 0:
        t_roots.append(-b / (2 * a))
    else:
        sqD = math.sqrt(D)
        t_roots.append((-b + sqD) / (2 * a))
        t_roots.append((-b - sqD) / (2 * a))

    x_roots = set()
    for t in t_roots:
        if t > 0:
            x_root = math.sqrt(t)
            x_roots.add(x_root)
            x_roots.add(-x_root)
        elif t == 0:
            x_roots.add(0.0)

    return list(x_roots)


def main():
    a = get_coef(1, "Введите коэффициент А:")
    b = get_coef(2, "Введите коэффициент B:")
    c = get_coef(3, "Введите коэффициент C:")

    roots = get_roots(a, b, c)

    len_roots = len(roots)
    if len_roots == 0:
        print("Нет действительных корней")
    else:
        print("Найдено корней: " + str(len_roots))
        print("Корни:", ", ".join(map(str, roots)))


if __name__ == "__main__":
    main()
