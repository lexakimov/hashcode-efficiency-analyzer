> WORK IN PROGRESS
## Исследование влияния реализации метода hashCode() объекта на эффективность использования его в качестве ключа HashMap

HashMap представляет собой структуру данных, которая использует хеш-таблицу для хранения пар ключ-значение.
Для вычисления положения элемента в хеш-таблице у объектов используемых в качестве ключа вызывается метод hashCode().

TODO

### Проблемы:

Из-за коллизий хеш кодов могут возникать следующие проблемы:
- длинные цепочки - эта проблема из-за которой чтение/запись в HashMap может происходить не за O(1) 
  - уменьшить loadFactor
- пустые бакеты - эта проблема не так страшна, однако 
  - увеличить loadFactor

size - фактическое количество элементов в hashMap
bucketCount - количество бакетов (размер table)

loadFactor - указывает максимальное соотношение size / bucketCount, больше которого bucketCount увеличивается.
bucketCount увеличивается кратно степени двойки (1, 2, 4, 8, 16 ...)

### Про loadFactor

- по умолчанию loadFactor = 0.75
- loadFactor < 1 - указывается если возможны коллизии
- при отсутствии коллизий loadFactor = 1 идеален
- loadFactor > 1 - слишком растягивает цепочки в любом случае
- loadFactor >= 1 - table увеличится только, после того, как все бакеты заполнятся на 100%

### Про код репозитория

Я написал небольшую программу, которая позволяет наглядно представить внутреннее состояние экземпляра HashMap после
добавления в него элементов.

- Откройте проект в вашей любимой IDE.
- Откройте класс `com.github.lexakimov.hashcode.Main`.
- Добавьте в него элементы.
- Запустите класс `com.github.lexakimov.hashcode.Main` с параметром виртуальной
  машины `--add-opens java.base/java.util=ALL-UNNAMED`.

### Ссылки

- [Рекомендации по реализации метода hashCode()](https://www.javamex.com/tutorials/collections/hash_function_guidelines.shtml)