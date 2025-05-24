package com.curso.resources;

import com.curso.domains.User;
import com.curso.domains.dtos.UserDTO;
import com.curso.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value =  "/user")
@Tag(name = "Usuário",  description = "API para o Gerenciamento de Usuários")
public class UserResource {

    @Autowired
    private UserService usersService;

    @GetMapping
    @Operation(summary = "Lista de todos os Usuários", description = "Retorna uma lista como todos os Usuários cadastrados")
    public ResponseEntity<List<UserDTO>> findAll(){
        return ResponseEntity.ok().body(usersService.findAll());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca um Usuário por id", description = "Realiza uma busca de de um Usuário cadastrada por id")
    public ResponseEntity<UserDTO> findByCpf(@PathVariable String cpf){
        User obj = this.usersService.findbyCpf(cpf);
        return ResponseEntity.ok().body(new UserDTO(obj));
    }

    @GetMapping(value = "/email/{email}")
    @Operation(summary = "Busca um Usuário pelo seu email", description = "Realiza uma busca de um Usuário pelo seu e-mail cadastrado")
    public ResponseEntity<UserDTO>  findByEmail(@PathVariable String email){
        User obj = this.usersService.findbyEmail(email);
        return ResponseEntity.ok().body(new UserDTO(obj));
    }

    @PostMapping
    @Operation(summary = "Cria um novo Usuário", description = "Cria um novo Usuário de acordo com as informaçõs fornecidas")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO objDto){
        User newObj = usersService.create(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getIdPerson()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Altera um Usuário", description = "Faz uma ateração de um Usuário existente")
    public  ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO objDto){
        User Obj = usersService.update(id, objDto);
        return  ResponseEntity.ok().body(new UserDTO(Obj));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deleta um Usuário", description = "Deleta um Usuário por meio do seu id")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id){
        usersService.delete(id);
        return  ResponseEntity.noContent().build();
    }

}
