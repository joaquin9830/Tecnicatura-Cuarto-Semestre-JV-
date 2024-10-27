import pygame




screen = pygame.display.set_mode((800, 600))
clock = pygame.time.Clock()
running = True
dt = 0


player_pos = pygame.Vector2(screen.get_width() / 2, screen.get_height() / 2)


player_speed = 5


while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False


    keys = pygame.key.get_pressed()

    if keys[pygame.K_LEFT]:
        player_pos.x -= player_speed
    if keys[pygame.K_RIGHT]:
        player_pos.x += player_speed
    if keys[pygame.K_DOWN]:
        player_pos.y += player_speed
    if keys[pygame.K_UP]:
        player_pos.y -= player_speed

    #Limpiar pantalla
    screen.fill('green')

    #Dibujar el jugador
    pygame.draw.circle(screen,(255, 0, 0),(int(player_pos.x), int(player_pos.y)), 10)

    #Actializar pantalla
    pygame.display.flip()

    #Control de velocidad del jugador
    clock.tick(60)


pygame.quit() #Termina el programa