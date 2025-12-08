# Пример:
# goods = [
#    {'title': 'Ковер', 'price': 2000, 'color': 'green'},
#    {'title': 'Диван для отдыха', 'price': 5300, 'color': 'black'}
# ]
# field(goods, 'title') должен выдавать 'Ковер', 'Диван для отдыха'
# field(goods, 'title', 'price') должен выдавать {'title': 'Ковер', 'price': 2000}, {'title': 'Диван для отдыха', 'price': 5300}

def field(items, *args):
    assert len(args) > 0

    for item in items:
        if len(args) == 1:
            key = args[0]
            value = item.get(key)
            if value is not None:
                yield value
        else:
            result_dict = {}
            for key in args:
                value = item.get(key)
                if value is not None:
                    result_dict[key] = value
            if result_dict:
                yield result_dict

if __name__ == "__main__":
    goods = [
        {'title': 'Ковер', 'price': 2000, 'color': 'green'},
        {'title': 'Диван для отдыха', 'color': 'black'},
        {'title': 'Лампа', 'price': None, 'color': 'white'},
        {'title': None, 'price': None}
    ]

    print(list(field(goods, 'title')))
    print(list(field(goods, 'title', 'price')))
    print(list(field(goods, 'color', 'price')))