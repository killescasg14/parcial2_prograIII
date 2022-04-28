package com.umg.mark01.api.controller;


import com.umg.mark01.core.entities.Persona;
//import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/persona")
public class PersonaController {

    private static List<Persona> listaPersonas = new ArrayList<>();

    private EmitterProcessor<Persona> notificationProcessor;
      //crea una instncia el postconstruct que emite el proceso
    /* -------------------------------------------------------------------------------------------------------------- */
    @PostConstruct
    private void createProcessor() {
        notificationProcessor = EmitterProcessor.<Persona>create();
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    @RequestMapping(
            value = "/test",
            method = RequestMethod.GET,
            produces = "application/json")
    public String getDummy() {
        return "{ \"message\":\"hola mundo\"}";
    }
    /* -------------------------------------------------------------------------------------------------------------- */
    @RequestMapping(
            path = "/add",
            method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Persona entityParam) {
        listaPersonas.add(entityParam);
        return new ResponseEntity<>(entityParam, HttpStatus.OK);
    }
    /* -------------------------------------------------------------------------------------------------------------- */
    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<Persona> getAll()
    {
        return listaPersonas;
    }
    private Flux<ServerSentEvent<Persona>> getPersonaSSE() {

        // notification processor retorna un FLUX en el cual podemos estar "suscritos"
        // cuando este tenga otro valor ...este es el pivote,
        return notificationProcessor
                .log().map(
                        (persona) -> {
                            System.out.println("Sending Persona:" + persona.getId());
                            return ServerSentEvent.<Persona>builder()
                                    .id(UUID.randomUUID().toString())
                                    .event("persona-result")
                                    .data(persona)
                                    .build();
                        }).concatWith(Flux.never());//nunca termina
    }

    private Flux<ServerSentEvent<Persona>> getNotificationHeartbeat() {
        return Flux.interval(Duration.ofSeconds(15))
                .map(i -> {
                    System.out.println(String.format("sending heartbeat [%s] ...", i.toString()));
                    return ServerSentEvent.<Persona>builder()
                            .id(String.valueOf(i))
                            .event("heartbeat-result")
                            .data(null)
                            .build();
                });
    }
    @GetMapping(
            value = "/notification/sse"
    )
    public Flux<ServerSentEvent<Persona>>
        getJobResultNotification(){
        return Flux.merge (
                getNotificationHeartbeat(),
                getPersonaSSE()

        );
    }

}