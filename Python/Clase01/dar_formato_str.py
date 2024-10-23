#Dar formato a un string
nombre = 'Joaquín'
edad = 25
mensaje_con_formato = 'Mi nombre es %s y tengo %d años' % (nombre, edad)


persona = ('Carla', 'Gomez', 50000.00)
mensaje_con_formato = 'Hola %s %s . Tu sueldo es %.2f'


nombre = 'Juan'
edad = 19
sueldo = 3000

mensaje = 'Nombre {n} Edad {e} Sueldo {s:.2f}'.format(n=nombre, e=edad, s=sueldo)


diccionario = {'nombre': 'Pepe', 'edad': 26, 'sueldo': 8000000.00}
mensaje = 'Nombre {dic[nombre]} Edad {dic[edad]} Sueldo {dic[sueldo]:.2f}'.format(dic=diccionario)
print(mensaje)