# Cocktail Bar

Приложение, которое позволяет пользователям создавать и сохранять свои любимые коктейли в одном месте.

>Актуальный код находиться в ветке `develop`

Стек: Kotlin, Room, Hilt, Kotlin Coroutines

## Что было сделано
- Экран "Мои коктейли"
- Экран "Создание коктейля"
- Диалоговое окно "Добавление ингредиентов"

## Описание работы приложения
При запуске приложения пользователя встречает приветственная картинка и возможность добавить свои первый коктейль.

<p align="center">
<img src="https://github.com/antbessonov/CocktailBar/blob/master/screenshot/screenshot_1.jpg?raw=true" width="400">
</p>

После клика на кнопку добавления, осуществляется переход на следующий экран, где можно указать всю необходимую информацию о коктейли и сохранить его себе в список.

<p align="center">
<img src="https://github.com/antbessonov/CocktailBar/blob/master/screenshot/screenshot_2.gif?raw=true" width="400">
</p>

Обязательными для заполнения является поле "Title", а также необходимо указать хотя бы один ингредиент.

<p align="center">
<img src="https://github.com/antbessonov/CocktailBar/blob/master/screenshot/screenshot_3.gif?raw=true" width="400">
</p>

Если основное поле не заполнено, оно подсвечивается красным цветом.

<p align="center">
<img src="https://github.com/antbessonov/CocktailBar/blob/master/screenshot/screenshot_4.gif?raw=true" width="400">
</p>

В случае если "Title" указан верно, а ингредиенты остались пустыми появиться соответствующие сообщение.

<p align="center">
<img src="https://github.com/antbessonov/CocktailBar/blob/master/screenshot/screenshot_5.gif?raw=true" width="400">
</p>

По нажатию на кнопку "Cancel" - возвращаемся на экран списка без сохранения указанных данных.

<p align="center">
<img src="https://github.com/antbessonov/CocktailBar/blob/master/screenshot/screenshot_6.gif?raw=true" width="400">
</p>

После сохранения данных о коктейле, возвращаясь на экран со списком, пользователь попадает к новому элементу.

<p align="center">
<img src="https://github.com/antbessonov/CocktailBar/blob/master/screenshot/screenshot_7.gif?raw=true" width="400">
</p>

## Используемые библиотеки и технологии

**Room**
Для работы с базой данных используется библиотека Room. Room входит в состав Android Jetpack и является рекомендованной к применению компанией Google.

**Hilt**
Библиотека внедрения зависимостей Hilt построена на основе Dagger и позволяет использовать ее преимущество в упрошенной форме. Hilt интегрирован с библиотеками Android Jetpack. Будучи простой в настройке библиотека Hilt отлично подходит для данного проекта.

**Kotlin Coroutines**
Асинхронная работа реализована с помощью Coroutines. Coroutines являются частью языка Kotlin и рекомендованы для асинхронного программирования на Android.

**Jetpack's Navigation**
Для навигации использовался Android Jetpack's Navigation.
