package com.umg.mark01.api.controller;


import com.umg.mark01.core.entities.Persona;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api(value = "/", description = "REST Persona")
@RestController
@RequestMapping("/persona")
@Slf4j
public class PersonaController {


    /* -------------------------------------------------------------------------------------------------------------- */
    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<Persona> getAll() {
        List<Persona> result = new ArrayList<>();

        Persona persona = new Persona();
        persona.setId(1);
        persona.setPrimerNombre("Manuel");
        persona.setSegundoNombre("Mendez");

        result.add(persona);

        persona = new Persona();
        persona.setId(2);
        persona.setPrimerNombre("Manuel");
        persona.setSegundoNombre("Mendez");

        result.add(persona);

        return result;
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST)
    public ResponseEntity<?> create(
            @RequestBody Persona entityParam) {

        entityParam.setSegundoNombre("desde server");

        return new ResponseEntity<>(entityParam, HttpStatus.OK);
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @RequestMapping(
            value = "/byId",
            method = RequestMethod.GET,
            produces = "application/json")
    public Persona get(@RequestParam Integer id) {

        Persona persona = new Persona();
        persona.setId(id);
        persona.setPrimerNombre("Manuel");
        persona.setSegundoNombre("Mendez");

        return persona;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

}