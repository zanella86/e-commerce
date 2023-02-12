package br.com.fiap.sportconnection.ecommerce.service.impl;

import br.com.fiap.sportconnection.ecommerce.cache.CustomerCache;
import br.com.fiap.sportconnection.ecommerce.cache.OrderCache;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerDTO;
import br.com.fiap.sportconnection.ecommerce.dto.OrderDTO;
import br.com.fiap.sportconnection.ecommerce.dto.OrderProductDTO;
import br.com.fiap.sportconnection.ecommerce.entity.CustomerEntity;
import br.com.fiap.sportconnection.ecommerce.entity.OrderEntity;
import br.com.fiap.sportconnection.ecommerce.entity.OrderProductEntity;
import br.com.fiap.sportconnection.ecommerce.entity.ProductEntity;
import br.com.fiap.sportconnection.ecommerce.exceptions.EmptyException;
import br.com.fiap.sportconnection.ecommerce.exceptions.NotFoundException;
import br.com.fiap.sportconnection.ecommerce.mapper.OrderEntityMapper;
import br.com.fiap.sportconnection.ecommerce.repository.CustomerRepository;
import br.com.fiap.sportconnection.ecommerce.repository.OrderProductRepository;
import br.com.fiap.sportconnection.ecommerce.repository.OrderRepository;
import br.com.fiap.sportconnection.ecommerce.repository.ProductRepository;
import br.com.fiap.sportconnection.ecommerce.service.CustomerService;
import br.com.fiap.sportconnection.ecommerce.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderProductRepository orderProductRepository;
    private ProductRepository productRepository;

    private CustomerRepository customerRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderProductRepository orderProductRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    private OrderEntity getOrder(Long id) throws NotFoundException {
        Optional<OrderEntity> order = orderRepository.findById(id);
        order.orElseThrow(()  -> new NotFoundException("Order not found"));

        return order.get();
    }


    @Override
    @Cacheable(value = OrderCache.NAME_ONE, key = OrderCache.KEY_ONE)
    public OrderDTO get(Long id) throws NotFoundException {

        OrderEntity order = getOrder(id);
        return OrderEntityMapper.orderEntityToOrderDTO(order);
    }

    @Override
    @Cacheable(value = OrderCache.NAME_ALL, unless = OrderCache.UNLESS_ALL)
    public List<OrderDTO> list() {

        List<OrderEntity> orders = orderRepository.findAll();
        return OrderEntityMapper.orderEntityToOrderDTO(orders);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = OrderCache.NAME_ONE, key = OrderCache.KEY_ONE),
                    @CacheEvict(value = OrderCache.NAME_ALL, allEntries = true)
            }
    )
    public void remove(Long id) throws NotFoundException {
        getOrder(id);
        orderProductRepository.deleteAllByOrderId(id);
        orderRepository.deleteById(id);
    }

    @Override
    @Caching(
            put = { @CachePut(value = OrderCache.NAME_ONE, key = OrderCache.KEY_ONE_OBJ)},
            evict = {@CacheEvict(value = OrderCache.NAME_ALL, allEntries = true)}
    )
    public OrderDTO update(OrderDTO order) throws NotFoundException{

        OrderEntity orderEntity = getOrder(order.id());
        orderEntity.setDescription(order.description());
        orderEntity.setDiscount(order.discount());

        orderRepository.save(orderEntity);
        return order;
    }

    @Override
    @Caching(
            put = { @CachePut(value = OrderCache.NAME_ONE, key = OrderCache.KEY_ONE_OBJ)},
            evict = {@CacheEvict(value = OrderCache.NAME_ALL, allEntries = true)}
    )
    public OrderDTO add(OrderDTO order) throws NotFoundException {

        CustomerEntity customer = customerRepository.findById(order.costumerId()).orElseThrow(() -> new NotFoundException("Customer not found"));
        OrderEntity orderEntity = OrderEntityMapper.orderDTOToOrderEntity(order, customer);
        orderRepository.save(orderEntity);
        return order;
    }

    @Override
    public OrderDTO addOrderProduct(OrderProductDTO orderProduct) throws NotFoundException{
        OrderEntity order = getOrder(orderProduct.orderId());
        ProductEntity product = productRepository.findById(orderProduct.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        OrderProductEntity entity = new OrderProductEntity(order, product, orderProduct.quantity());
        orderProductRepository.save(entity);

        BigDecimal amount = product.getUnityPrice().multiply(BigDecimal.valueOf(entity.getQuantity()));
        order.setTotal(order.getTotal().add(amount));
        orderRepository.save(order);

        return OrderEntityMapper.orderEntityToOrderDTO(order);
    }
}
