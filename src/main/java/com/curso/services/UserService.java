package com.curso.services;

import com.curso.domains.User;
import com.curso.domains.dtos.UserDTO;
import com.curso.repositories.UserRepository;
import com.curso.services.exceptions.DataIntegrityViolationException;
import com.curso.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(obj -> new UserDTO(obj)).collect(Collectors.toList());
    }

    public User findbyId(Long id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. ID: " + id));
    }

    public User findbyCpf(String cpf) {
        Optional<User> obj = userRepository.findByCpf(cpf);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. CPF: " + cpf));
    }

    public User findbyEmail(String email) {
        Optional<User> obj = userRepository.findByEmail(email);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Email: " + email));
    }

    public User create(UserDTO objDto) {
        objDto.setIdPerson(null);
        ValidaPorCpfeEmail(objDto);
        User newObj = new User(objDto);
        return userRepository.save(newObj);
    }

    public User update(Long id, UserDTO objDto) {
        objDto.setIdPerson(id);
        User oldObj = findbyId(id);
        ValidaPorCpfeEmail(objDto);
        oldObj = new User(objDto);
        return userRepository.save(oldObj);
    }

    public void delete(Long id) {
        User obj = findbyId(id);
        if (obj.getServiceOrders().size() > 0) {
            throw new DataIntegrityViolationException("Usuario não pode ser deletado pois possui ordens de serviço!");
        }
        userRepository.deleteById(id);
    }

    private void ValidaPorCpfeEmail(UserDTO objDto) {
        Optional<User> obj = userRepository.findByCpf(objDto.getCpf());
        if (obj.isPresent() && obj.get().getIdPerson() != objDto.getIdPerson()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

        obj = userRepository.findByEmail(objDto.getEmail());
        if (obj.isPresent() && obj.get().getIdPerson() != objDto.getIdPerson()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sitema");
        }
    }

}
