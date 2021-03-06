package com.alvayonara.mealsfood.core.domain.usecase

import com.alvayonara.mealsfood.core.data.source.Resource
import com.alvayonara.mealsfood.core.data.source.remote.network.ApiResponse
import com.alvayonara.mealsfood.core.domain.model.Food
import com.alvayonara.mealsfood.core.domain.model.FoodRecentSearch
import com.alvayonara.mealsfood.core.domain.repository.IFoodRepository
import io.reactivex.Flowable

class FoodInteractor(private val foodRepository: IFoodRepository) : FoodUseCase {

    override fun getPopularFood(): Flowable<Resource<List<Food>>> =
        foodRepository.getPopularFood()

    override fun getListFoodByCategory(category: String): Flowable<Resource<List<Food>>> =
        foodRepository.getListFoodByCategory(category)

    override fun getListFoodByArea(area: String): Flowable<Resource<List<Food>>> =
        foodRepository.getListFoodByArea(area)

    override fun getFoodDetailById(foodId: String): Flowable<Resource<List<Food>>> =
        foodRepository.getFoodDetailById(foodId)

    override fun searchFood(meal: String): Flowable<ApiResponse<List<Food>>> =
        foodRepository.searchFood(meal)

    override fun getFavoriteFood(): Flowable<List<Food>> = foodRepository.getFavoriteFood()

    override fun checkIsFavoriteFood(idMeal: String): Flowable<Int> =
        foodRepository.checkIsFavoriteFood(idMeal)

    override fun insertFavoriteFood(food: Food) =
        foodRepository.insertFavoriteFood(food)

    override fun deleteFavoriteFood(food: Food) =
        foodRepository.deleteFavoriteFood(food)

    override fun getRecentSearchFood(): Flowable<List<FoodRecentSearch>> =
        foodRepository.getRecentSearchFood()

    override fun insertRecentSearchFood(search: FoodRecentSearch) =
        foodRepository.insertRecentSearchFood(search)
}