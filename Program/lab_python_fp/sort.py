data = [4, -30, 100, -100, 123, 1, 0, -1, -4]

if __name__ == '__main__':
    result = sorted(data, key=abs, reverse=True)
    print(f"Без лямбда: {result}")

    result_with_lambda = sorted(data, key=lambda x: abs(x), reverse=True)
    print(f"С лямбда: {result_with_lambda}")