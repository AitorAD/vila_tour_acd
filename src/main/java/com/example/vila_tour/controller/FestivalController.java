package com.example.vila_tour.controller;

import com.example.vila_tour.domain.Festival;
import com.example.vila_tour.exception.FestivalNotFoundException;
import com.example.vila_tour.service.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@RequestMapping("/festivals")
@RestController
public class FestivalController {

    @Autowired
    private FestivalService festivalService;

    @GetMapping(value = "")
    public ResponseEntity<Set<Festival>> getFestivals() {
        Set<Festival> festivals;
        festivals = festivalService.findAllFestivals();
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Festival> getFestival(@PathVariable long id){
        Festival festival = festivalService.findFestivalById(id)
                .orElseThrow(() -> new FestivalNotFoundException(id));

        return new ResponseEntity<>(festival, HttpStatus.OK);
    }

    @GetMapping("/search/description")
    public ResponseEntity<Set<Festival>> findFestivalsByDescription(@RequestParam String description) {
        Set<Festival> festivals = festivalService.findFestivalsByDescription(description);
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @GetMapping("/search/score")
    public ResponseEntity<Set<Festival>> findFestivalsByAverageScore(@RequestParam double averageScore) {
        Set<Festival> festivals = festivalService.findFestivalsByAverageScore(averageScore);
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @GetMapping("/search/startDate")
    public ResponseEntity<Set<Festival>> findFestivalsByStartDate(@RequestParam String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate parsedStartDate = LocalDate.parse(startDate, formatter);
        Set<Festival> festivals = festivalService.findFestivalsByStartDate(parsedStartDate);
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @GetMapping(value = "/sorted")
    public ResponseEntity<Set<Festival>> getFestivalsSortedByName() {
        Set<Festival> festivals;
        festivals = festivalService.findAllByOrderByName();
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @GetMapping(value = "/sortedInverse")
    public ResponseEntity<Set<Festival>> getFestivalsSortedByNameInverse() {
        Set<Festival> festivals;
        festivals = festivalService.findAllByOrderByNameDesc();
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<Set<Festival>> getFestivalsByNameAndAverageScore(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "averageScore", defaultValue = "-1") double averageScore) {

        Set<Festival> festivals;
        festivals = festivalService.findByNameAndAverageScore(name, averageScore);
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<Set<Festival>> getFestivalsByNameContaining(
            @RequestParam(value = "name", defaultValue = "") String name) {

        Set<Festival> festivals;
        festivals = festivalService.findByNameContaining(name);
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Festival> addFestival(@RequestBody Festival festival){
        Festival addedFestival = festivalService.addFestival(festival);
        return new ResponseEntity<>(addedFestival, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Festival> modifyFestival(@PathVariable long id, @RequestBody Festival newFestival){
        Festival festival = festivalService.modifyFestival(id, newFestival);
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
