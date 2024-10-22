package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FestivalRepository extends CrudRepository<Festival, Long> {
    // No es necesario redefinir findAll, pero si quieres un Set puedes hacerlo así:
    Set<Festival> findAll(); // Definido para usarlo si realmente necesitas un Set

    // También puedes agregar métodos de búsqueda específicos si los necesitas
}
