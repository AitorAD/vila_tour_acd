package com.example.vila_tour.controller;

import com.example.vila_tour.domain.Festival;
import com.example.vila_tour.exception.FestivalNotFoundException;
import com.example.vila_tour.service.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/festivals")
@RestController
public class FestivalController {

    @Autowired
    private FestivalService festivalService;

    @GetMapping(value = "")
    public ResponseEntity<Set<Festival>> getFestival() {
        Set<Festival> festivals;
        festivals = festivalService.findAll();
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Festival> getFestival(@PathVariable long id){
        Festival festival = festivalService.findById(id)
                .orElseThrow(() -> new FestivalNotFoundException(id));

        return new ResponseEntity<>(festival, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Festival> addFestival(@RequestBody Festival festival){
        Festival addedFestival = festivalService.addFestival(festival);
        return new ResponseEntity<>(addedFestival, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Festival> modifyFestival(@PathVariable long id,
                                           @RequestBody Festival festival){
        Festival newFestival = festivalService.modifyFestival(id, festival);
        return new ResponseEntity<>(newFestival,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteFestival(@PathVariable long id){
        festivalService.deleteFestival(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(FestivalNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response>
    handleException(FestivalNotFoundException fnfe) {
        Response response = Response.errorResponse(Response.NOT_FOUND,
                fnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
