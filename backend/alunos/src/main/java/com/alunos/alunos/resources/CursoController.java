package com.alunos.alunos.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alunos.alunos.models.Curso;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin
public class CursoController {


    private List<Curso> cursos = Arrays.asList( new Curso(1, "Java"),
                                                new Curso(2, "JavaScript"),
                                                new Curso(3, ".NET"),
                                                new Curso(4, "Python"));

    @PostMapping("cursos")
    @CrossOrigin
    public ResponseEntity<Curso> save(@RequestBody Curso curso) {
        curso.setId(cursos.size()+1);
        cursos.add(curso);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(curso.getId()).toUri();
        
        return ResponseEntity.created(location).body(curso);
    }

    @GetMapping("cursos/{id}")
    public ResponseEntity<Curso> getCurso(@PathVariable int id) {
        Curso curs = cursos.stream().filter(curso -> curso.getId() == id).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        return ResponseEntity.ok(curs);
    }
    

    @GetMapping("cursos")
    public List<Curso> getCurso() {
        return cursos;
    }
    
}
