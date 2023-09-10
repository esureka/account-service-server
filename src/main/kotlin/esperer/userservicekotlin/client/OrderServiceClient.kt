package esperer.userservicekotlin.client

import esperer.userservicekotlin.vo.ResponseOrder
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@FeignClient(name = "order-service")
interface OrderServiceClient {

    @GetMapping("/order-service/{userId}/orders")
    fun getOrders(@PathVariable userId: UUID): List<ResponseOrder>
}