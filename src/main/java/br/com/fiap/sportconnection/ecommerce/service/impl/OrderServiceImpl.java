package br.com.fiap.sportconnection.ecommerce.service.impl;

import br.com.fiap.sportconnection.ecommerce.cache.OrderCache;
import br.com.fiap.sportconnection.ecommerce.dto.OrderDTO;
import br.com.fiap.sportconnection.ecommerce.dto.OrderProductDTO;
import br.com.fiap.sportconnection.ecommerce.entity.CustomerEntity;
import br.com.fiap.sportconnection.ecommerce.entity.OrderEntity;
import br.com.fiap.sportconnection.ecommerce.entity.OrderProductEntity;
import br.com.fiap.sportconnection.ecommerce.entity.ProductEntity;
import br.com.fiap.sportconnection.ecommerce.exceptions.NotEnoughResourceException;
import br.com.fiap.sportconnection.ecommerce.exceptions.NotFoundException;
import br.com.fiap.sportconnection.ecommerce.mapper.OrderMapper;
import br.com.fiap.sportconnection.ecommerce.repository.CustomerRepository;
import br.com.fiap.sportconnection.ecommerce.repository.OrderProductRepository;
import br.com.fiap.sportconnection.ecommerce.repository.OrderRepository;
import br.com.fiap.sportconnection.ecommerce.repository.ProductRepository;
import br.com.fiap.sportconnection.ecommerce.service.OrderService;
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
        return OrderMapper.orderEntityToOrderDTO(order);
    }

    @Override
    @Cacheable(value = OrderCache.NAME_ALL, unless = OrderCache.UNLESS_ALL)
    public List<OrderDTO> list() {

        List<OrderEntity> orders = orderRepository.findAll();
        return OrderMapper.orderEntityToOrderDTO(orders);
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

        OrderEntity orderEntity = getOrder(order.getId());
        orderEntity.setDescription(order.getDescription());
        orderEntity.setDiscount(order.getDiscount());

        orderRepository.save(orderEntity);
        return order;
    }

    @Override
    @Caching(
            put = { @CachePut(value = OrderCache.NAME_ONE, key = OrderCache.KEY_ONE_OBJ)},
            evict = {@CacheEvict(value = OrderCache.NAME_ALL, allEntries = true)}
    )
    public OrderDTO add(OrderDTO orderDTO) throws NotFoundException {

        CustomerEntity customer = customerRepository.findById(orderDTO.getCostumerId()).orElseThrow(() -> new NotFoundException("Customer not found"));
        OrderEntity orderEntity = OrderMapper.orderDTOToOrderEntity(orderDTO, customer);
        orderDTO.setId(orderRepository.save(orderEntity).getId());
        return orderDTO;
    }

    @Override
    public OrderDTO addOrderProduct(OrderProductDTO orderProduct) throws NotFoundException, NotEnoughResourceException {

        OrderEntity order = getOrder(orderProduct.getOrderId());
        ProductEntity product = productRepository.findById(orderProduct.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        OrderProductEntity entity = new OrderProductEntity(order, product, orderProduct.getQuantity());

        validateStock(product, entity.getQuantity());
        updateOrderAmount(order, product.getUnityPrice(), entity.getQuantity());

        productRepository.save(product);
        orderProductRepository.save(entity);
        orderRepository.save(order);

        return OrderMapper.orderEntityToOrderDTO(order);
    }

    private void validateStock(ProductEntity product, long quantityProductOrder) throws NotEnoughResourceException {
        long productStock = product.getStockQuantity() - quantityProductOrder;
        if(productStock < 0) {
            throw new NotEnoughResourceException("Not enough on stock");
        }
        product.setStockQuantity((int) productStock);
    }
    private void updateOrderAmount(OrderEntity order, BigDecimal unityPrice, Long quantityProductOrder){
        BigDecimal amount = unityPrice.multiply(BigDecimal.valueOf(quantityProductOrder));
        order.setTotal(order.getTotal().add(amount));
    }
}
