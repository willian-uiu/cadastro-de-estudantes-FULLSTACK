package com.alunos.alunos.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alunos.alunos.models.Aluno;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@CrossOrigin
public class AlunoController {
    private List<Aluno> alunos = new ArrayList<>();

    @PostMapping("alunos")
    @CrossOrigin
    public ResponseEntity<Aluno> save(@RequestBody Aluno aluno) {
        aluno.setId(alunos.size() + 1);
        alunos.add(aluno);

        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(aluno.getId())
                        .toUri();

        return ResponseEntity.created(location).body(aluno);
    }

    @GetMapping("alunos/{id}")
    public ResponseEntity<Aluno> getAluno(@PathVariable int id) {
        Aluno aluno = alunos.stream()
                            .filter(a -> a.getId() == id).findFirst()
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        return ResponseEntity.ok(aluno);
    }


    @GetMapping("alunos")
    public List<Aluno> getAlunos() {
        return alunos;
    }
    
    
}
