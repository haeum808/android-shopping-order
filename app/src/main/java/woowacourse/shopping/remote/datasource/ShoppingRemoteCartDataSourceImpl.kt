package woowacourse.shopping.remote.datasource

import retrofit2.Response
import woowacourse.shopping.data.datasource.remote.ShoppingRemoteCartDataSource
import woowacourse.shopping.domain.model.Carts
import woowacourse.shopping.remote.api.CartService
import woowacourse.shopping.remote.mapper.toDomain
import woowacourse.shopping.remote.model.request.PatchCartItemRequest
import woowacourse.shopping.remote.model.request.PostCartItemRequest

class ShoppingRemoteCartDataSourceImpl(private val service: CartService) : ShoppingRemoteCartDataSource {
    override fun insertCartProduct(
        productId: Long,
        quantity: Int,
    ): Result<Int> =
        runCatching {
            val body =
                PostCartItemRequest(
                    productId = productId.toInt(),
                    quantity = quantity,
                )
            service.postCartItem(body).execute().toCartId()
        }

    override fun updateCartProduct(
        cartId: Int,
        quantity: Int,
    ): Result<Unit> =
        runCatching {
            val body = PatchCartItemRequest(quantity = quantity)
            service.patchCartItem(id = cartId, body = body).execute()
        }

    override fun getCartProductsPaged(
        page: Int,
        size: Int,
    ): Result<Carts> =
        runCatching {
            service.getCartItems(page = page, size = size).execute().body()?.toDomain()
                ?: throw IllegalArgumentException()
        }

    override fun getCartProductsQuantity(): Result<Int> =
        runCatching {
            service.getCartItemsCount().execute().body()?.quantity ?: 0
        }

    override fun deleteCartProductById(cartId: Int): Result<Unit> =
        runCatching {
            service.deleteCartItem(id = cartId).execute().body()
        }

    fun <T> Response<T>.toCartId(): Int = this.headers()["location"]?.split("/")?.last()?.toInt() ?: throw IllegalArgumentException()
}
