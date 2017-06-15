package es.malvarez.microservices.web.controller;

import es.malvarez.microservices.web.domain.Collision;
import es.malvarez.microservices.web.service.CollisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * So this is the controller to populate the UI
 */
@Controller
@RequestMapping("/collision")
public class CollisionController {

    private final CollisionService collisionService;

    @Autowired
    public CollisionController(final CollisionService collisionService) {
        this.collisionService = collisionService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    private Page<Collision> findAll(final Pageable pageable) {
        return collisionService.findAll(pageable);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    private Collision find(@PathVariable  final UUID id) {
        return collisionService.find(id);
    }

}
