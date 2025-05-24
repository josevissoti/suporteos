package com.curso.resources;

import com.curso.domains.Technician;
import com.curso.domains.dtos.TechnicianDTO;
import com.curso.services.TechnicianService;
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
@RequestMapping(value = "/technician")
@Tag(name = "Technician", description = "API para o Gerenciamento de Technician")
public class TechnicianResource {

    @Autowired
    private TechnicianService techService;

    @GetMapping
    @Operation(summary = "Lista todas as Technician", description = "Retorna uma lista com todas as  Technician")
    public ResponseEntity<List<TechnicianDTO>> findAll() {
        return ResponseEntity.ok().body(techService.findAll());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca uma Technician por id", description = "Realiza uma busca de Technician cadastrada por id")
    public ResponseEntity<TechnicianDTO> findbyId(@PathVariable Long id) {
        Technician obj = this.techService.findbyId(id);
        return ResponseEntity.ok().body(new TechnicianDTO(obj));
    }

    @GetMapping(value = "/cpf/{cpf}")
    @Operation(summary = "Busca uma Technician por cpf", description = "Realiza uma busca de Technician cadastrada por cpf")
    public ResponseEntity<TechnicianDTO> findByCpf(@PathVariable String cpf) {
        Technician obj = this.techService.findbyCpf(cpf);
        return ResponseEntity.ok().body(new TechnicianDTO(obj));
    }

    @GetMapping(value = "email/{email}")
    @Operation(summary = "Busca uma Technician por email", description = "Realiza uma busca de Technician cadastrada por email")
    public ResponseEntity<TechnicianDTO> findByEmail(@PathVariable String email) {
        Technician obj = this.techService.findbyEmail(email);
        return ResponseEntity.ok().body(new TechnicianDTO(obj));
    }

    @PostMapping
    @Operation(summary = "Cria uma nova Technician ", description = "Cria uma nova Technician de acordo com a informações inseridas")
    public ResponseEntity<TechnicianDTO> create(@Valid @RequestBody TechnicianDTO objDto) {
        Technician newObj = techService.create(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getIdPerson()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Altera uma Technician", description = "Realiza a alteração de uma  Technician existente")
    public ResponseEntity<TechnicianDTO> update(@PathVariable Long id, @Valid @RequestBody TechnicianDTO objDto) {
        Technician Obj = techService.update(id, objDto);
        return ResponseEntity.ok().body(new TechnicianDTO(Obj));
    }

    @Operation(summary = "Deleta uma Technician ", description = "Remove uma  Technician a partir do seu  id")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TechnicianDTO> delete(@PathVariable Long id) {
        techService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

