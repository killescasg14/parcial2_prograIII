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

    private static List<Persona> listaPersonas = new ArrayList<>();


    /* -------------------------------------------------------------------------------------------------------------- */
    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<Persona> getAll() {
        return listaPersonas;
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST)
    public ResponseEntity<?> create(
            @RequestBody Persona entityParam) {
        listaPersonas.add(entityParam);
        return new ResponseEntity<>(entityParam, HttpStatus.OK);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    @RequestMapping(
            path = "/update",
            method = RequestMethod.PUT)
    public ResponseEntity<?> update(
            @RequestBody Persona entityParam) {

        Persona persona = listaPersonas.get(0);
        persona.setPrimerNombre(entityParam.getSegundoNombre());

        return new ResponseEntity<>(persona, HttpStatus.OK);
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