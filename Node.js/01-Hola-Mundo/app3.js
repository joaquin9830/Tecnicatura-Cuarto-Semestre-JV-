//Probando el comportamiento de los timeouts en NodeJS
//Funciones sincronas 
console.log('Inicio del programa'); 

//Función de tiempo 1
setTimeout(() => {
    console.log('Primer Timeout'); 
}, 3000);

//Función de tiempo 2
setTimeout(() => {
    console.log('Segundo Timeout'); 
}, 0);

//Función de tiempo 3
setTimeout(() => {
    console.log('TercerTimeout'); 
}, 0);

//Finalizamos el programa
console.log('Fin del programa') 