package com.ieslavereda.ampa.config;

import com.ieslavereda.ampa.model.Libro;
import com.ieslavereda.ampa.model.Prestamo;
import com.ieslavereda.ampa.model.Usuario;
import com.ieslavereda.ampa.repository.LibroRepository;
import com.ieslavereda.ampa.repository.PrestamoRepository;
import com.ieslavereda.ampa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Carga los datos iniciales de la web en la base de datos al arrancar.
 * Solo inserta si la base de datos está vacía.
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;
    private final PrestamoRepository prestamoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (libroRepository.count() > 0) {
            System.out.println(">>> La base de datos ya tiene datos. Omitiendo carga inicial.");
            return;
        }

        System.out.println(">>> Cargando datos iniciales de IES La Vereda...");
        cargarLibrosAmpa();
        cargarLibrosTexto();
        cargarUsuarios();
        cargarPrestamosEjemplo();
        System.out.println(">>> Datos cargados correctamente.");
    }

    // =====================================================
    // LIBROS AMPA (los de la web: libros.html / tests.js)
    // =====================================================
    private void cargarLibrosAmpa() {
        List<Libro> libros = List.of(

            // ── COMEDIA ──────────────────────────────────────────
            Libro.builder()
                .titulo("Concierto para Pan y Pato")
                .autor("Gonzalo Roch")
                .isbn("978-84-123456-1-3")
                .categoria(Libro.CategoriaLibro.COMEDIA)
                .generos("Comedia, Fantasía Humorística, Aventura")
                .sinopsis("Una chef intenta dirigir una orquesta de cocina con un pato de goma como solista mientras todo se vuelve un caos.")
                .paginas(180)
                .edadRecomendada("+8 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            Libro.builder()
                .titulo("El Gran Lío de Gonzalo y sus Puentes Imposibles")
                .autor("Gonzalo Roch")
                .isbn("978-84-987654-2-8")
                .categoria(Libro.CategoriaLibro.COMEDIA)
                .generos("Comedia, Aventura, Fantasía")
                .sinopsis("Un ingeniero con mala suerte intenta construir puentes imposibles con materiales absurdos mientras un dragón se burla de él.")
                .paginas(210)
                .edadRecomendada("+10 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            Libro.builder()
                .titulo("El Pingüino Gourmet Espacial y la Barbacoa Celestial")
                .autor("Gonzalo Roch")
                .isbn("978-84-112233-0-6")
                .categoria(Libro.CategoriaLibro.COMEDIA)
                .generos("Comedia, Ciencia Ficción, Cocina")
                .sinopsis("Un pingüino parrillero viaja por la galaxia para cocinar en una barbacoa celestial y salvar el sistema solar.")
                .paginas(195)
                .edadRecomendada("Todas las edades")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            Libro.builder()
                .titulo("La Santa Pata de Pollo y el Culto de los Cavernícolas Hambrientos")
                .autor("Pau Aguilar")
                .isbn("978-84-556677-4-1")
                .categoria(Libro.CategoriaLibro.COMEDIA)
                .generos("Comedia, Sátira, Prehistoria")
                .sinopsis("Una tribu de cavernícolas empieza a adorar una pata de pollo gigante caída del cielo mientras otros intentan comérsela.")
                .paginas(165)
                .edadRecomendada("+12 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            // ── TERROR ───────────────────────────────────────────
            Libro.builder()
                .titulo("El Ojo de Cristal de la Muerte y el Sótano de Guerrero")
                .autor("Guillermo García")
                .isbn("978-84-334455-0-9")
                .categoria(Libro.CategoriaLibro.TERROR)
                .generos("Terror, Suspenso, Fantasía Oscura")
                .sinopsis("Un ojo gigante custodia un trono subterráneo mientras espera el sacrificio de aquellos que osan entrar en su sótano.")
                .paginas(220)
                .edadRecomendada("+16 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            Libro.builder()
                .titulo("El Sótano de las Almas Olvidadas")
                .autor("Gonzalo Roch")
                .isbn("978-84-445566-0-3")
                .categoria(Libro.CategoriaLibro.TERROR)
                .generos("Terror, Sobrenatural, Misterio")
                .sinopsis("Una mano monstruosa surge de un suelo sembrado de calaveras en un invernadero abandonado donde las almas no descansan.")
                .paginas(190)
                .edadRecomendada("+16 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            Libro.builder()
                .titulo("La Cocina de los Dientes Quebrados y el Sueño de la Carne Muerta")
                .autor("Gonzalo Roch")
                .isbn("978-84-556677-1-0")
                .categoria(Libro.CategoriaLibro.TERROR)
                .generos("Terror Gore, Humor Negro, Distopía")
                .sinopsis("En una cocina de pesadilla, chefs siniestros sirven un corazón palpitante a una plaga de ratas hambrientas.")
                .paginas(205)
                .edadRecomendada("+18 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            Libro.builder()
                .titulo("La Cripta de los Huesos Quebrados y el Alma Muerta del Emperador")
                .autor("Pau Aguilar")
                .isbn("978-84-667788-0-1")
                .categoria(Libro.CategoriaLibro.TERROR)
                .generos("Terror Clásico, Gótico, Fantasía Oscura")
                .sinopsis("El esqueleto de un antiguo emperador aguarda en su trono de huesos a que alguien despierte su alma corrupta.")
                .paginas(240)
                .edadRecomendada("+14 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            // ── FICCIÓN ──────────────────────────────────────────
            Libro.builder()
                .titulo("Cosmic Chronicles")
                .autor("Pau Aguilar")
                .isbn("978-84-778899-2-5")
                .categoria(Libro.CategoriaLibro.FICCION)
                .generos("Ciencia Ficción, Aventura, Exploración")
                .sinopsis("Un astronauta solitario descubre un antiguo portal alienígena oculto en un exuberante bosque lleno de cristales energéticos.")
                .paginas(235)
                .edadRecomendada("+12 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            Libro.builder()
                .titulo("Cosmic Odyssey")
                .autor("Pau Aguilar")
                .isbn("978-84-889900-3-6")
                .categoria(Libro.CategoriaLibro.FICCION)
                .generos("Ciencia Ficción, Space Opera, Acción")
                .sinopsis("Un mercenario espacial cruza un peligroso puente colgante mientras una gigantesca nave nodriza abandonada flota sobre la jungla.")
                .paginas(250)
                .edadRecomendada("+14 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            Libro.builder()
                .titulo("Echoes of a Lost World")
                .autor("Pau Aguilar")
                .isbn("978-84-990011-0-9")
                .categoria(Libro.CategoriaLibro.FICCION)
                .generos("Ficción, Misterio, Aventura")
                .sinopsis("Un explorador llega a una mansión victoriana oculta en un bosque prehistórico bajo una luna de sangre.")
                .paginas(215)
                .edadRecomendada("+12 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            Libro.builder()
                .titulo("Neo-Shadows Chronicles")
                .autor("Pau Aguilar")
                .isbn("978-84-001122-0-3")
                .categoria(Libro.CategoriaLibro.FICCION)
                .generos("Ciencia Ficción, Cyberpunk, Noir")
                .sinopsis("En una megaciudad lluviosa y llena de neón, un detective privado usa tecnología ilegal para resolver crímenes corporativos.")
                .paginas(260)
                .edadRecomendada("+16 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            // ── HISTORIA ─────────────────────────────────────────
            Libro.builder()
                .titulo("El Misterio del Templo de la Serpiente y la Ciudad de Oro")
                .autor("Gonzalo Roch")
                .isbn("978-84-123123-4-9")
                .categoria(Libro.CategoriaLibro.HISTORIA)
                .generos("Historia, Aventura, Exploración")
                .sinopsis("Un explorador sigue pistas ancestrales en la selva para localizar una legendaria ciudad dorada y su templo sagrado.")
                .paginas(225)
                .edadRecomendada("+10 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            Libro.builder()
                .titulo("El Secreto de las Galeras y sus Mares Perdidos")
                .autor("Gonzalo Roch")
                .isbn("978-84-234234-5-0")
                .categoria(Libro.CategoriaLibro.HISTORIA)
                .generos("Historia, Arqueología, Aventura")
                .sinopsis("Una arqueóloga submarina descubre los restos de una antigua flota y los tesoros que guardaban los mares hace siglos.")
                .paginas(210)
                .edadRecomendada("+10 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            Libro.builder()
                .titulo("El Secreto de los Faraones y sus Pirámides Perdidas")
                .autor("Guillermo García")
                .isbn("978-84-345345-1-6")
                .categoria(Libro.CategoriaLibro.HISTORIA)
                .generos("Historia, Egiptología, Misterio")
                .sinopsis("Un arqueólogo desentierra una tumba real olvidada bajo las arenas del desierto mientras revela el pasado de los faraones.")
                .paginas(245)
                .edadRecomendada("+12 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build(),

            Libro.builder()
                .titulo("La Gran Hernia de Marco y sus Acueductos Imposibles")
                .autor("Gonzalo Roch")
                .isbn("978-84-456456-0-3")
                .categoria(Libro.CategoriaLibro.HISTORIA)
                .generos("Historia, Comedia, Aventura")
                .sinopsis("Un centurión romano intenta construir grandes infraestructuras imperiales mientras lidia con problemas físicos y desafíos absurdos.")
                .paginas(195)
                .edadRecomendada("+12 años")
                .cantidadTotal(3).cantidadDisponible(3)
                .build()
        );

        libroRepository.saveAll(libros);
        System.out.println(">>> " + libros.size() + " libros AMPA cargados.");
    }

    // =====================================================
    // LIBROS DE TEXTO (los del inventario: index.html)
    // =====================================================
    private void cargarLibrosTexto() {
        List<Libro> librosTexto = List.of(

            // ── MATEMÁTICAS ──────────────────────────────────────
            Libro.builder().titulo("Matemáticas 1 ESO").asignatura("Matemáticas").nivelEducativo("1 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),
            Libro.builder().titulo("Matemáticas 2 ESO").asignatura("Matemáticas").nivelEducativo("2 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),
            Libro.builder().titulo("Matemáticas 3 ESO").asignatura("Matemáticas").nivelEducativo("3 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),
            Libro.builder().titulo("Matemáticas 4 ESO").asignatura("Matemáticas").nivelEducativo("4 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),

            // ── HISTORIA ─────────────────────────────────────────
            Libro.builder().titulo("Historia 1 ESO").asignatura("Historia").nivelEducativo("1 ESO")
                .isbn("978-84-456456-0-3-H1").categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),
            Libro.builder().titulo("Historia 2 ESO").asignatura("Historia").nivelEducativo("2 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),
            Libro.builder().titulo("Historia 3 ESO").asignatura("Historia").nivelEducativo("3 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),
            Libro.builder().titulo("Historia 4 ESO").asignatura("Historia").nivelEducativo("4 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),

            // ── BIOLOGÍA ─────────────────────────────────────────
            Libro.builder().titulo("Biología 1 ESO").asignatura("Biología").nivelEducativo("1 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),
            Libro.builder().titulo("Biología 2 ESO").asignatura("Biología").nivelEducativo("2 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),
            Libro.builder().titulo("Biología 3 ESO").asignatura("Biología").nivelEducativo("3 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),
            Libro.builder().titulo("Biología 4 ESO").asignatura("Biología").nivelEducativo("4 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),

            // ── INGLÉS ────────────────────────────────────────────
            Libro.builder().titulo("Inglés 1 ESO").asignatura("Inglés").nivelEducativo("1 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),
            Libro.builder().titulo("Inglés 2 ESO").asignatura("Inglés").nivelEducativo("2 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),
            Libro.builder().titulo("Inglés 3 ESO").asignatura("Inglés").nivelEducativo("3 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build(),
            Libro.builder().titulo("Inglés 4 ESO").asignatura("Inglés").nivelEducativo("4 ESO")
                .categoria(Libro.CategoriaLibro.LIBRO_TEXTO).cantidadTotal(30).cantidadDisponible(30).build()
        );

        libroRepository.saveAll(librosTexto);
        System.out.println(">>> " + librosTexto.size() + " libros de texto cargados.");
    }

    // =====================================================
    // USUARIOS (los de alumnado.js)
    // =====================================================
    private void cargarUsuarios() {
        List<Usuario> usuarios = List.of(
            Usuario.builder()
                .nia("202601").nombre("Pau").apellidos("Aguilar")
                .tipo(Usuario.TipoUsuario.ALUMNO).cursoOCargo("2º DAW")
                .build(),
            Usuario.builder()
                .nia("202602").nombre("Gonzalo").apellidos("Roch")
                .tipo(Usuario.TipoUsuario.ALUMNO).cursoOCargo("2º DAW")
                .build(),
            Usuario.builder()
                .nia("202603").nombre("Guillermo").apellidos("García")
                .tipo(Usuario.TipoUsuario.PROFESOR).cursoOCargo("Informática")
                .build(),
            // Usuarios extra de ejemplo
            Usuario.builder()
                .nia("202604").nombre("María").apellidos("López")
                .tipo(Usuario.TipoUsuario.ALUMNO).cursoOCargo("1º ESO")
                .build(),
            Usuario.builder()
                .nia("202605").nombre("Carlos").apellidos("Martínez")
                .tipo(Usuario.TipoUsuario.ALUMNO).cursoOCargo("3º ESO")
                .build(),
            Usuario.builder()
                .nia("202606").nombre("Ana").apellidos("Sánchez")
                .tipo(Usuario.TipoUsuario.PROFESOR).cursoOCargo("Matemáticas")
                .build()
        );

        usuarioRepository.saveAll(usuarios);
        System.out.println(">>> " + usuarios.size() + " usuarios cargados.");
    }

    // =====================================================
    // PRÉSTAMOS DE EJEMPLO (refleja el historial de la web)
    // =====================================================
    private void cargarPrestamosEjemplo() {
        // Pau (202601) tiene "La Gran Hernia de Marco" pendiente
        Usuario pau   = usuarioRepository.findByNia("202601").orElseThrow();
        Usuario gonza = usuarioRepository.findByNia("202602").orElseThrow();
        Usuario guille = usuarioRepository.findByNia("202603").orElseThrow();

        Libro granHernia   = libroRepository.findByTituloContainingIgnoreCase("Gran Hernia").stream().findFirst().orElse(null);
        Libro pingüino     = libroRepository.findByTituloContainingIgnoreCase("Pingüino Gourmet").stream().findFirst().orElse(null);
        Libro redesLocales = null; // No existe en la AMPA, es ficticio del JS

        if (granHernia != null) {
            // Pau: "La Gran Hernia" - PENDIENTE
            prestamoRepository.save(Prestamo.builder()
                .usuario(pau).libro(granHernia)
                .fechaPrestamo(LocalDate.now().minusDays(10))
                .fechaDevolucionPrevista(LocalDate.now().plusDays(20))
                .estado(Prestamo.EstadoPrestamo.PENDIENTE)
                .build());
            granHernia.setCantidadDisponible(granHernia.getCantidadDisponible() - 1);
            libroRepository.save(granHernia);
        }

        if (pingüino != null) {
            // Pau: "El Pingüino Gourmet" - DEVUELTO
            prestamoRepository.save(Prestamo.builder()
                .usuario(pau).libro(pingüino)
                .fechaPrestamo(LocalDate.now().minusDays(40))
                .fechaDevolucionPrevista(LocalDate.now().minusDays(10))
                .fechaDevolucionReal(LocalDate.now().minusDays(12))
                .estado(Prestamo.EstadoPrestamo.DEVUELTO)
                .build());
        }

        System.out.println(">>> Préstamos de ejemplo cargados.");
    }
}
