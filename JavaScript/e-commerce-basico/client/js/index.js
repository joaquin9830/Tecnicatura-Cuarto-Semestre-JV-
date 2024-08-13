// Obtenemos el array de productos
const shopcontent = document.getElementById('shopcontent');;

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
});