package com.example.vila_tour.controller;

import com.example.vila_tour.domain.*;
import com.example.vila_tour.exception.UserNotFoundException;
import com.example.vila_tour.payload.response.EmailExistResponse;
import com.example.vila_tour.security.services.AuthService;
import com.example.vila_tour.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Controlador para Usuarios
 * @author Team AJO
 * @version curso 2024-2025
 */
@RequestMapping("/users")
@RestController
@Tag(name = "Users", description = "Catálogo de usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    //MÉTODOS GET

    @Operation(summary = "Obtiene el listado de usuarios")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de usuarios",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = User.class))))})
    @GetMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users;
        users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el usuario determinado por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el usuario",
                    content = @Content(schema =  @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable long id, Authentication authentication) {
        // Verificar si el usuario tiene permiso para acceder al recurso
        if (!authService.canAccessUser(authentication, id)) {
            throw new AccessDeniedException("No tienes permiso para acceder a este usuario.");
        }

        // Recuperar y devolver el usuario
        User user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));
        return ResponseEntity.ok(user);
    }


    @Operation(summary = "Obtiene los usuarios por username que contengan el texto de la búsqueda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de usuarios",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = User.class))))})
    @GetMapping(value = "/searchUsername", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Set<User>> getUsersByUsername(@RequestParam("username") String username){
        Set<User> users = userService.findByUsernameContaining(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un usuario por el username exacto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de usuarios",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = User.class))))})
    @GetMapping(value = "/username", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Optional<User>> getUserByUsername(@RequestParam("username") String username){
        Optional<User> users = userService.findByUsername(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Verifica si un usuario existe por email")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(example = "Usuario no encontrado")))
    })
    @GetMapping(value = "/email", produces = "application/json")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        return userService.findByEmail(email)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK)) // Si se encuentra el usuario
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Si no se encuentra
    }

    @Operation(summary = "Verifica si un email existe")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "El estado del correo",
                    content = @Content(schema = @Schema(implementation = EmailExistResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(example = "Usuario no encontrado")))
    })
    @PermitAll
    @GetMapping(value = "/email/exist", produces = "application/json")
    public ResponseEntity<EmailExistResponse> checkIfEmailExists(@RequestParam("email") String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(new EmailExistResponse(exists));
    }

    @Operation(summary = "Obtiene los usuarios por nombre que contengan el texto de la búsqueda")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de usuarios que contengan el texto de la búsqyeda",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = User.class))))})
    @GetMapping(value = "/name", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Set<User>> getUsersByName(@RequestParam("email") String email){
        Set<User> users = userService.findByNameContaining(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene los usuarios por email")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de usuarios que contengan el texto de la búsqyeda",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = User.class))))})
    @GetMapping(value = "/users-email", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Set<User>> getUsersBySurname(@RequestParam("email") String email){
        Set<User> users = userService.findBySurnameContaining(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene los usuarios por rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios encontrado",
                    content = @Content(schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })    @GetMapping(value = "/rol", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Set<User>> getUsersByRole(@RequestParam("role") String role) {
        Role newRole = Role.valueOf(role.toUpperCase());
        Set<User> users = userService.findByRole(newRole);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //MÉTODOS POST, PUT Y DELETE

    @Operation(summary = "Añade un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario añadido exitosamente",
                    content = @Content(schema = @Schema(implementation = Festival.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida, el usuario no pudo añadirse",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> addUser(@RequestBody User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User addedUser = userService.addUser(user);
        return new ResponseEntity<>(addedUser, HttpStatus.OK);
    }


    @Operation(summary = "Modifica un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario modificado exitosamente",
                    content = @Content(schema = @Schema(implementation = Festival.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<User> modifyUser(@PathVariable long id,
                                           @RequestBody User user) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();

        // Verificar si es USER y está intentando modificar su propio perfil
        User authenticatedUser = userService.findByUsername(authenticatedUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (authentication.getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals("USER")) && id != authenticatedUser.getId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Código 403: Prohibido
        }

        // Modificar el usuario
        User newUser = userService.modifyUser(id, user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(UserNotFoundException unfe) {
        Response response = Response.errorResponse(Response.NOT_FOUND,
                unfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
