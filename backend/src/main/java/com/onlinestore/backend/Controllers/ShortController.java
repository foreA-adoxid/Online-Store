package com.onlinestore.backend.Controllers;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.onlinestore.backend.Models.Short;
import com.onlinestore.backend.Services.ShortService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/short/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ShortController {
    private final ShortService shortService;

    @GetMapping
    private Map<String,Object> getShorts(){
        return shortService.findAll();
    }

    @GetMapping("/{id}")
    private Map<String,Object> getSneaker(@PathVariable("id") int id){
        return Map.of("products",shortService.findById(id));
    }

    @PostMapping()
    private Short setShort(@RequestBody Short shorts){
        shortService.setShort(shorts);
        return shorts;
    }

    @DeleteMapping("/{id}")
    private Optional<Short> deleteShort(@PathVariable("id") int id){
         return shortService.deleteShort(id);
    }

}
