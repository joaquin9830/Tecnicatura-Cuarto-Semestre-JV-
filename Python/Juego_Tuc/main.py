import pygame
import sys
import random
from constantes import SCREEN_WIDTH, SCREEN_HEIGTH, ASSETS_PATH, IMPERIAL_MARCH_PATH, START_IMAGE_PATH, ESTRELLA_PATH, FONDO1_PATH

def mostrar_pantalla_inicio(screen):
    """
    Función para mostrar la pantalla de inicio con imagen y música de fondo.
    """
    # Cargar y mostrar imagen de inicio
    imagen_inicio = pygame.image.load(START_IMAGE_PATH)
    imagen_inicio = pygame.transform.scale(imagen_inicio, (SCREEN_WIDTH, SCREEN_HEIGTH))
    screen.blit(imagen_inicio, (0, 0))
    pygame.display.flip()

    # Reproducir audio
    pygame.mixer.music.load(IMPERIAL_MARCH_PATH)
    pygame.mixer.music.play()

    # Esperar que termine el audio
    while pygame.mixer.music.get_busy():
        for event in pygame.event.get():
            if event.type == pygame.QUIT:  # Corregido "Type" a "type"
                pygame.quit()
                sys.exit()

        # Actualizar pantalla para mantener la imagen visible
        screen.blit(imagen_inicio, (0, 0))
        pygame.display.flip()

def main():
    """
    Función principal que controla el flujo del juego.
    """
    pygame.init()
    screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGTH))
    pygame.display.set_caption('Amenaza Francesa')

    # Cargar icono y fondo del juego
    icon = pygame.image.load(f'{ASSETS_PATH}/images/fondo001.jfif')
    pygame.display.set_icon(icon)

    # Fondo y escalado
    fondo = pygame.image.load(f'{ASSETS_PATH}/images/fondo3.jpg')
    fondo = pygame.transform.scale(fondo, (SCREEN_WIDTH, SCREEN_HEIGTH))

    # Cargar y escalar imágenes de estrella y fondo alternativo
    estrella = pygame.image.load(ESTRELLA_PATH)
    estrella = pygame.transform.scale(estrella, (SCREEN_WIDTH, SCREEN_HEIGTH))

    fondo1 = pygame.image.load(FONDO1_PATH)
    fondo1 = pygame.transform.scale(fondo1, (SCREEN_WIDTH, SCREEN_HEIGTH))

    # Cargar sonido de disparo
    sonido_laser = pygame.mixer.Sound(f'{ASSETS_PATH}/sounds/explosion.mp3')

    # Inicializar el personaje en el centro de la pantalla
    personaje = Personaje(SCREEN_WIDTH // 2, SCREEN_HEIGTH // 2)
    enemigos = []
    explosiones = []
    puntos = 0
    nivel = 1

    clock = pygame.time.Clock()
    running = True

    # Fondo actual, inicializado al fondo principal
    fondo_actual = fondo

    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()

        # Control de movimiento del personaje
        keys = pygame.key.get_pressed()
        dx, dy = 0, 0

        if keys[pygame.K_LEFT]:  # Mover a la izquierda
            dx = -5
        if keys[pygame.K_RIGHT]:  # Mover a la derecha
            dx = 5
        if keys[pygame.K_UP]:  # Mover hacia arriba
            dy = -5
        if keys[pygame.K_DOWN]:  # Mover hacia abajo
            dy = 5

        personaje.mover(dx, dy)

        # Disparo de láser
        if keys[pygame.K_SPACE]:
            personaje.lanzar_laser()
            sonido_laser.play()

        # Movimiento y eliminación de enemigos fuera de la pantalla
        for enemigo in enemigos:
            enemigo.mover()
            if enemigo.rect.top > SCREEN_HEIGTH:
                enemigos.remove(enemigo)

        # Verificar colisiones entre láseres y enemigos
        for laser in personaje.lasers:
            for enemigo in enemigos:
                if enemigo.rect.colliderect(laser.rect):
                    explosiones.append(Explosion(enemigo.rect.centerx, enemigo.rect.centery))
                    enemigos.remove(enemigo)
                    personaje.lasers.remove(laser)
                    puntos += 10
                    break

        # Generar nuevos enemigos aleatoriamente
        if random.random() < 0.02:
            x = random.randint(0, SCREEN_WIDTH - 50)
            enemigo = Enemigo(x, 0)
            enemigos.append(enemigo)

        # Actualizar explosiones y eliminarlas si han terminado
        explosiones = [explosion for explosion in explosiones if explosion.actualizar()]

        # Cambiar fondo cuando se alcanza cierto puntaje
        if puntos >= 250:
            if fondo_actual == fondo:
                fondo_actual = estrella
            else:
                fondo_actual = fondo1
            puntos = 0
            nivel += 1  # Incrementa el nivel

        # Dibujar fondo de pantalla
        screen.blit(fondo_actual, (0, 0))

        # Dibujar personaje, enemigos y explosiones
        personaje.dibujar(screen)
        for enemigo in enemigos:
            enemigo.dibujar(screen)
        for explosion in explosiones:
            explosion.dibujar(screen)

        # Mostrar marcador de puntos y nivel
        font = pygame.font.Font(None, 36)
        texto_puntos = font.render(f"Puntos: {puntos}", True, (255, 255, 255))
        texto_nivel = font.render(f"Nivel: {nivel}", True, (255, 255, 255))
        screen.blit(texto_puntos, (10, 50))
        screen.blit(texto_nivel, (10, 90))

        pygame.display.flip()
        clock.tick(60)

        # Mensaje de Game Over
        if not running:
            screen.fill((0, 0, 0))
            texto_game_over = font.render("GAME OVER", True, (255, 0, 0))
            texto_mensaje_final = font.render("¡QUE LA FUERZA TE ACOMPAÑE!", True, (255, 255, 255))

            # Mostrar mensaje final con efecto de desvanecimiento
            for alpha in range(255, -1, -5):
                screen.fill((0, 0, 0))
                texto_game_over.set_alpha(alpha)
                screen.blit(texto_game_over, (SCREEN_WIDTH // 2 - texto_game_over.get_width() // 2, SCREEN_HEIGTH // 2))
                pygame.display.flip()
                pygame.time.delay(10)

            # Mostrar mensaje final
            screen.fill((0, 0, 0))
            screen.blit(texto_mensaje_final,
                        (SCREEN_WIDTH // 2 - texto_mensaje_final.get_width() // 2, SCREEN_HEIGTH // 2 + 50))
            pygame.display.flip()
            pygame.time.wait(5000)  # Mostrar mensaje final durante 5 segundos

            pygame.quit()
            sys.exit()

if __name__ == '__main__':
    main()
