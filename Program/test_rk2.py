import unittest
from rk2_garanin import Table, Column, ColumnTable, get_one_to_many, get_many_to_many, task_a1_solution, task_a2_solution, task_a3_solution

class TestRk2(unittest.TestCase):

    def setUp(self):
        """Инициализация тестовых данных перед каждым тестом"""
        self.tables = [
            Table(1, 'System Test'),
            Table(2, 'Alpha Table'),
            Table(3, 'Beta Table')
        ]

        self.columns = [
            Column(1, 'Col1', 'int', 10, 1),  # для System Test
            Column(2, 'Col2', 'str', 20, 2),  # для Alpha Table
            Column(3, 'Col3', 'int', 5, 2),  # для Alpha Table
            Column(4, 'Col4', 'str', 100, 3)  # для Beta Table
        ]

        self.cols_tabs = [
            ColumnTable(1, 1),
            ColumnTable(2, 2),
            ColumnTable(2, 3),
            ColumnTable(3, 4)
        ]

        # Подготовка связей
        self.one_to_many = get_one_to_many(self.tables, self.columns)
        self.many_to_many = get_many_to_many(self.tables, self.columns, self.cols_tabs)

    def test_task_a1(self):
        """
        Тест задания А1:
        Проверяем сортировку по названию таблицы.
        """
        result = task_a1_solution(self.one_to_many)

        # Ожидаем, что первым будет 'Alpha Table' (на букву A)
        self.assertEqual(result[0][2], 'Alpha Table')
        # Вторым 'Beta Table'
        self.assertEqual(result[2][2], 'Beta Table')
        # Третьим 'System Test'
        self.assertEqual(result[3][2], 'System Test')

    def test_task_a2(self):
        """
        Тест задания А2:
        Проверяем подсчет суммы (Col2(20) + Col3(5) = 25 для Alpha)
        и сортировку по убыванию размера.
        """
        result = task_a2_solution(self.tables, self.one_to_many)

        # Alpha Table имеет сумму 25, System Test - 10, Beta Table - 100.
        # Ожидаемый порядок: Beta (100), Alpha (25), System (10)

        # Проверка первого элемента (самый большой размер)
        self.assertEqual(result[0][0], 'Beta Table')
        self.assertEqual(result[0][1], 100)

        # Проверка второго элемента
        self.assertEqual(result[1][0], 'Alpha Table')
        self.assertEqual(result[1][1], 25)

    def test_task_a3(self):
        """
        Тест задания А3:
        Проверяем фильтрацию таблиц со словом 'System'
        и вывод списка их колонок.
        """
        result = task_a3_solution(self.tables, self.many_to_many)

        # В тестовых данных только одна таблица содержит 'System'
        self.assertIn('System Test', result)
        self.assertNotIn('Alpha Table', result)

        # Проверяем список колонок для этой таблицы
        self.assertEqual(result['System Test'], ['Col1'])

if __name__ == '__main__':
    unittest.main()