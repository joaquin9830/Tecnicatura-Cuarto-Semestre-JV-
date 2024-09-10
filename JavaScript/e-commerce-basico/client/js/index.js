// Obtenemos el array de productos
const shopcontent = document.getElementById('shopcontent');
const cart = []; //Este es nuestro carrtio de compras, un array vacio que se ira llenando con los productos que el usuario seleccione

// Iteramos sobre el array de productos
productos.forEach((product) => {
    const content = document.createElement('div');
    content.innerHTML= document.createElement('div');
    content.innerHTML = `
    <img src="${product.img}">
    <h3>${product.productName}</h3>
    <p>${product.price} $</p>
    `;
    shopcontent.append(content);

    const buyButton = document.createElement('button');
    buyButton.innerText = 'Comprar';

    content.append(buyButton);

    buyButton.addEventListener('click', () => {
        cart.push({
            id: product.id,
            productName: product.productName,
            price: product.price,
            quanty: product.quanty,
        })
    }
});