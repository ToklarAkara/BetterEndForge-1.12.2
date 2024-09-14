import os
import json

# получаем текущую директорию
current_directory = os.path.dirname(__file__)

# перебираем все файлы в директории
for filename in os.listdir(current_directory):
    if filename.endswith('.json'):
        # открытие файла и загрузка содержимого
        with open(filename, 'r') as file:
            data = json.load(file)

        # изменение pустого ключа на 'normal'
        if('variants' in data and '' in data['variants']):
            data['variants']['normal'] = data['variants'].pop('')
        
        # сохранение изменений в тот же файл
        with open(filename, 'w') as file:
            json.dump(data, file, indent=2)