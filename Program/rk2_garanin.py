from operator import itemgetter

class Column:
    """Колонка данных"""
    def __init__(self, id, name, data_type, storage_size, table_id):
        self.id = id
        self.name = name
        self.data_type = data_type
        self.storage_size = storage_size
        self.table_id = table_id

class Table:
    """Таблица данных"""
    def __init__(self, id, name):
        self.id = id
        self.name = name

class ColumnTable:
    """
    Колонки таблиц для реализации связи многие-ко-многим
    """
    def __init__(self, table_id, column_id):
        self.table_id = table_id
        self.column_id = column_id

# Данные
def get_data():
    tables = [
        Table(1, 'Users'),
        Table(2, 'Orders'),
        Table(3, 'Products'),
        Table(11, 'System Users'),
        Table(22, 'System Orders'),
        Table(33, 'Archive'),
    ]

    columns = [
        Column(1, 'UserID', 'int', 100, 1),
        Column(2, 'Username', 'varchar', 250, 1),
        Column(3, 'OrderID', 'int', 150, 2),
        Column(4, 'ProductID', 'int', 120, 2),
        Column(5, 'ProductName', 'varchar', 300, 3),
        Column(6, 'Price', 'decimal', 80, 3),
    ]

    cols_tabs = [
        ColumnTable(1, 1), ColumnTable(1, 2),
        ColumnTable(2, 3), ColumnTable(2, 4),
        ColumnTable(3, 5), ColumnTable(3, 6),
        ColumnTable(11, 1), ColumnTable(11, 2),
        ColumnTable(22, 3), ColumnTable(22, 4),
        ColumnTable(33, 5),
    ]
    return tables, columns, cols_tabs

# Функции бизнес-логики

def get_one_to_many(tables, columns):
    """Соединение данных один-ко-многим"""
    return [
        (c.name, c.storage_size, t.name)
        for t in tables
        for c in columns
        if c.table_id == t.id
    ]

def get_many_to_many(tables, columns, cols_tabs):
    """Соединение данных многие-ко-многим"""
    many_to_many_temp = [
        (t.name, ct.table_id, ct.column_id)
        for t in tables
        for ct in cols_tabs
        if t.id == ct.table_id
    ]
    return [
        (c.name, c.storage_size, table_name)
        for table_name, table_id, column_id in many_to_many_temp
        for c in columns
        if c.id == column_id
    ]

def task_a1_solution(one_to_many):
    """
    Задание А1: Вывести список всех связанных элементов,
    отсортированный по названию таблицы.
    """
    return sorted(one_to_many, key=itemgetter(2))

def task_a2_solution(tables, one_to_many):
    """
    Задание А2: Вывести список таблиц с суммарным размером колонок,
    отсортированный по суммарному размеру (по убыванию).
    """
    res_unsorted = []
    for t in tables:
        t_cols = list(filter(lambda i: i[2] == t.name, one_to_many))
        if len(t_cols) > 0:
            t_sizes = [size for _, size, _ in t_cols]
            t_sizes_sum = sum(t_sizes)
            res_unsorted.append((t.name, t_sizes_sum))

    return sorted(res_unsorted, key=itemgetter(1), reverse=True)

def task_a3_solution(tables, many_to_many):
    """
    Задание А3: Вывести словарь, где ключ - имя таблицы (содержащее 'System'),
    а значение - список имен колонок.
    """
    res = {}
    for t in tables:
        if 'System' in t.name:
            t_cols = list(filter(lambda i: i[2] == t.name, many_to_many))
            t_cols_names = [name for name, _, _ in t_cols]
            res[t.name] = t_cols_names
    return res

# Основной запуск
def main():
    tables, columns, cols_tabs = get_data()
    one_to_many = get_one_to_many(tables, columns)
    many_to_many = get_many_to_many(tables, columns, cols_tabs)

    print('Задание А1')
    print(task_a1_solution(one_to_many))

    print('\nЗадание А2')
    print(task_a2_solution(tables, one_to_many))

    print('\nЗадание А3')
    print(task_a3_solution(tables, many_to_many))

if __name__ == '__main__':
    main()