import pygame
import os

from constantes import ASSETS_PATH

class Explosion:
    def __init__(self, x, y):
        # Cargar imágenes de la animación de la explosión
        self.images = [pygame.image.load(f'{ASSETS_PATH}/images/regularExplosion0{i:02d}.png') for i in range(9)]
        self.index = 0  # Índice de la imagen actual en la animación
        self.image = self.images[self.index]
        self.rect = self.image.get_rect(center=(x, y))  # Posición de la explosión
        self.frame_rate = 0  # Contador de frames para controlar la velocidad de la animación
        self.max_frames = 5  # Número de frames para cambiar a la siguiente imagen de explosión

    def actualizar(self):
        """
        Actualiza el estado de la explosión. Incrementa el frame_rate y cambia la imagen
        cuando se alcanza max_frames. Retorna False cuando la animación termina.
        """
        self.frame_rate += 1
        if self.frame_rate >= self.max_frames:
            self.frame_rate = 0
            self.index += 1
            # Si la animación ha mostrado todas las imágenes, termina
            if self.index >= len(self.images):
                return False
            # Cambia a la siguiente imagen en la secuencia
            self.image = self.images[self.index]
        return True

    def dibujar(self, screen):
        """
        Dibuja la explosión en la pantalla.
        """
        screen.blit(self.image, self.rect.topleft)
