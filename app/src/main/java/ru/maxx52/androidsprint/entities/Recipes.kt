package ru.maxx52.androidsprint.entities

object STUB {
    private val categories: List<Category> = listOf(
        Category(1, "Бургеры", "Рецепты всех популярных видов бургеров","burger.png"),
        Category(2,"Пицца","Пицца на любой вкус и цвет. Лучшая подборка для тебя", "pizza.png"),
        Category(3,"Десерты","Самые вкусные рецепты десертов специально для вас","dessert.png"),
        Category(4,"Салаты","Хрустящий калейдоскоп под соусом вдохновения","salad.png"),
        Category(5, "Рыба", "Печеная, жареная, сушеная, любая рыба на твой вкус", "fish.png"),
        Category(6, "Супы", "От классики до экзотики: мир в одной тарелке", "soup.png"),
    )

    fun getCategories(): List<Category> {
        return categories
    }
}