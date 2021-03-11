# DocHolder
📜 A service for storing and presenting official documents of an organization.

# Description

# Role Options

</in dev>

# Установка
<pre>
1. Настройка nginx reverse proxy:
   Файл default закинуть в конфигурацию nginx (вероятно в /etc/nginx/sites-avaliable/).
   Там же изменить ip-адресса. Порт 4200 ведёт на vue, порт 8081 - на spring boot.
   Обновить конфигурацию:
   <code>
    nginx -t
    nginx -s reload
    service nginx restart
   </code>

2. Настройка vue.js
   В .\frontend\src\components :13 изменить ip на адресс своего сервера nginx
   В папке frontend выполнить
   <code>
    npm install
    npm run serve
   </code>
   
3. Backend импортировать в IDE как maven проект
</pre>
![image](https://user-images.githubusercontent.com/47406394/110204994-2288af80-7e87-11eb-99db-463bc3974244.png)
