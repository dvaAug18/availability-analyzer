Анализатор уровня доступности по access логу.

Пример использования программы:
$ cat access.log | java -jar availability-analyzer-1.0-SNAPSHOT.jar -u 99.9 -t 45
13:32:26 13:33:15 94.5
15:23:02 15:23:08 99.8
