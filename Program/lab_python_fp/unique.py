import random

# Итератор для удаления дубликатов
class Unique(object):
    def __init__(self, items, **kwargs):
        self._iterator = iter(items)
        self._ignore_case = kwargs.get('ignore_case', False)
        self._seen = set()

    def __next__(self):
        while True:
            current_item = next(self._iterator)

            item_to_check = current_item
            if self._ignore_case and isinstance(current_item, str):
                item_to_check = current_item.lower()

            if item_to_check not in self._seen:
                self._seen.add(item_to_check)
                return current_item

    def __iter__(self):
        return self

def gen_random(num_count, begin, end):
    for _ in range(num_count):
        yield random.randint(begin, end)

if __name__ == "__main__":
    data1 = [1, 1, 1, 2, 2, 1, 3, 3]
    print(f"Исходные данные: {data1}")
    print("Уникальные значения:", list(Unique(data1)))
    print("-" * 20)

    data2 = gen_random(15, 1, 4)
    print("Уникальные значения из генератора (15 чисел от 1 до 4):")
    print(list(Unique(data2)))
    print("-" * 20)

    data3 = ['a', 'A', 'b', 'B', 'a', 'A', 'b', 'B']
    print(f"Исходные данные: {data3}")
    print("Уникальные строки (ignore_case=True):", list(Unique(data3, ignore_case=True)))