package com.example.vila_tour.controller;

import com.example.vila_tour.domain.Review;
import com.example.vila_tour.domain.ReviewId;
import com.example.vila_tour.exception.FestivalNotFoundException;
import com.example.vila_tour.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Reviews", description = "Gestión de reviews de artículos publicadas por usuarios")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Operation(summary = "Lista todas las reviews")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de reviews obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Review.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron reviews",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Set<Review>> findAllReviews() {
        Set<Review> reviews = reviewService.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @Operation(summary = "Buscar reviews por calificación y artículo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de reviews obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Review.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron reviews",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/byRatingAndArticle", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Review>> findByRatingAndArticle(
            @RequestParam(value = "rating", defaultValue = "-1") long rating,
            @RequestParam(value = "idArticle", defaultValue = "-1") long idArticle) {
        Set<Review> reviews = reviewService.findByRatingAndArticle(rating, idArticle);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @Operation(summary = "Buscar reviews por calificación y usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de reviews obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Review.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron reviews",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/byRatingAndUser", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Review>> findByRatingAndUser(
            @RequestParam(value = "rating", defaultValue = "-1") long rating,
            @RequestParam(value = "idUser", defaultValue = "-1") long idUser) {
        Set<Review> reviews = reviewService.findByRatingAndUser(rating, idUser);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @Operation(summary = "Buscar reviews por artículo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de reviews obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Review.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron reviews",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/byArticle", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Review>> findByArticle(
            @RequestParam(value = "idArticle", defaultValue = "0") long idArticle, long article) {
        Set<Review> reviews = reviewService.findByArticle(idArticle);
        return new ResponseEntity<Set<Review>>(reviews, HttpStatus.OK);
    }

    @Operation(summary = "Buscar reviews por usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de reviews obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Review.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron reviews",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/byUser", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Review>> findByUser(
            @RequestParam(value = "idUser", defaultValue = "0") long idUser) {
        Set<Review> reviews = reviewService.findByUser(idUser);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @Operation(summary = "Añade una nueva review o modifica una existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review añadida exitosamente",
                    content = @Content(schema = @Schema(implementation = Review.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Review> addReview(
            @RequestParam("user_id") Long idUser,
            @RequestParam("article_id") Long idArticle,
            @RequestBody Review review
    ) {
        // Configurar los IDs en la reseña
        review.setId(new ReviewId(idUser, idArticle));

        // Llamada al servicio para añadir la reseña
        Review addedReview = reviewService.addReview(review, idUser, idArticle);

        // Respuesta HTTP
        return new ResponseEntity<>(addedReview, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar una review por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review eliminada exitosamente",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Review no encontrada",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Response> deleteReview(
            @RequestParam(value = "id_user") long idUser,
            @RequestParam(value = "id_article") long idArticle
    ) {
        try {
            ReviewId reviewId = new ReviewId(idUser, idArticle);
            reviewService.deleteReview(reviewId);
            return ResponseEntity.ok(Response.noErrorResponse());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.errorResponse(401, e.getMessage()));
        }
    }



    @ExceptionHandler(FestivalNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(FestivalNotFoundException fnfe) {
        Response response = Response.errorResponse(Response.NOT_FOUND, fnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
