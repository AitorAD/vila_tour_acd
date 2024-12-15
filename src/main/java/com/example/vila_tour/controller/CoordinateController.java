package com.example.vila_tour.controller;

import com.example.vila_tour.domain.Coordinate;
import com.example.vila_tour.exception.CoordinateNotFoundException;
import com.example.vila_tour.service.CoordinateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.example.vila_tour.controller.Response.NOT_FOUND;

/**
 * Controlador para Coordenadas
 * @version curso 2024-2025
 */
@RequestMapping("/coordinates")
@RestController
public class CoordinateController {

    @Autowired
    private CoordinateService coordinateService;

    @Operation(summary = "Obtiene el listado de coordenadas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de coordenadas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Coordinate.class))))
    })
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<Set<Coordinate>> getCoordinates() {
        Set<Coordinate> coordinates = coordinateService.findAll();
        return new ResponseEntity<>(coordinates, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una coordenada determinada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coordenada encontrada",
                    content = @Content(schema = @Schema(implementation = Coordinate.class))),
            @ApiResponse(responseCode = "404", description = "Coordenada no encontrada",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Coordinate> getCoordinate(@PathVariable("id") Long idCoordinate) {
        Coordinate coordinate = coordinateService.findById(idCoordinate)
                .orElseThrow(() -> new CoordinateNotFoundException(idCoordinate));
        return new ResponseEntity<>(coordinate, HttpStatus.OK);
    }

    @Operation(summary = "Añade una nueva coordenada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Coordenada añadida exitosamente",
                    content = @Content(schema = @Schema(implementation = Coordinate.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida, la coordenada no pudo añadirse",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<Coordinate> addCoordinate(@RequestBody Coordinate coordinate) {
        try {
            Coordinate addedCoordinate = coordinateService.addCoordinate(coordinate);
            return new ResponseEntity<>(addedCoordinate, HttpStatus.CREATED);
        } catch (Exception ex) {
            Response errorResponse = Response.errorResponse(500, "Internal Server Error: " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Modifica una coordenada existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coordenada modificada exitosamente",
                    content = @Content(schema = @Schema(implementation = Coordinate.class))),
            @ApiResponse(responseCode = "404", description = "Coordenada no encontrada",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Coordinate> modifyCoordinate(@PathVariable("id") Long idCoordinate, @RequestBody Coordinate newCoordinate) {
        Coordinate coordinate = coordinateService.modifyCoordinate(idCoordinate, newCoordinate);
        if (coordinate != null) {
            return new ResponseEntity<>(coordinate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Elimina una coordenada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coordenada eliminada exitosamente",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Coordenada no encontrada",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCoordinate(@PathVariable("id") Long idCoordinate) {
        coordinateService.deleteCoordinate(idCoordinate);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(CoordinateNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(CoordinateNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
