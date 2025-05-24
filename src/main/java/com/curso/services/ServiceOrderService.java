package com.curso.services;

import com.curso.domains.ServiceOrder;
import com.curso.domains.Technician;
import com.curso.domains.User;
import com.curso.domains.dtos.ServiceOrderDTO;
import com.curso.domains.enums.OrderPriority;
import com.curso.domains.enums.OrderStatus;
import com.curso.repositories.ServiceOrderRepository;
import com.curso.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceOrderService {

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private TechnicianService technicianService;

    @Autowired
    private UserService userService;

    public ServiceOrder findbyId(UUID id) {
        Optional<ServiceOrder> obj = serviceOrderRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado. ID:" + id));
    }

    public List<ServiceOrderDTO> findAll() {
        return serviceOrderRepository.findAll().stream()
                .map(obj -> new ServiceOrderDTO(obj)).collect(Collectors.toList());
    }

    private ServiceOrder newServiceOrder(ServiceOrderDTO obj) {
        Technician tech = technicianService.findbyId(obj.getTechnician());
        User user = userService.findbyId(obj.getUser());

        ServiceOrder serviceOrder = new ServiceOrder();
        if (obj.getId() != null) {
            serviceOrder.setId(obj.getId());
        }

        if (obj.getOrderStatus().equals(2)) {
            serviceOrder.setEndDate(LocalDate.now());
        }

        serviceOrder.setTechnician(tech);
        serviceOrder.setUser(user);
        serviceOrder.setOrderPriority(OrderPriority.toEnum(obj.getOrderPriority()));
        serviceOrder.setOrderStatus(OrderStatus.toEnum(obj.getOrderStatus()));
        serviceOrder.setTitleOS(obj.getTitleOS());
        serviceOrder.setDescription(obj.getDescription());
        return serviceOrder;
    }

    public ServiceOrder create(ServiceOrderDTO objDto) {
        return serviceOrderRepository.save(newServiceOrder(objDto));
    }

    public ServiceOrder update(UUID id, ServiceOrderDTO objDto) {
        objDto.setId(id);
        ServiceOrder oldObj = findbyId(id);
        oldObj = newServiceOrder(objDto);
        return serviceOrderRepository.save(oldObj);
    }
}
