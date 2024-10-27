const shopContent = document.getElementById("shopContent");
const cart = []; // Carrito de compras vacío

productos.forEach((product) => {
    const content = document.createElement("div");
    content.classList.add("card");

    content.innerHTML = `
        <div class="card_form">
            <img src="${product.img}" alt="${product.productName}">
            <span>${product.productName}</span>
        </div>
        <div class="card_data">
            <div class="text">
                <label class="text_m">${product.productName}</label>
                <div class="cube text_s">
                    <label class="side front">500gr</label>
                    <label class="side top">$${product.price}</label>
                </div>
            </div>
        </div>
        <div class="card_footer">
            <img src="assets/images/icon-coffe.png" alt="Add to cart" class="cart_icon">
        </div>
    `;

    shopContent.append(content);

    // Lógica del icono del carrito
    const cartIcon = content.querySelector(".cart_icon");
    cartIcon.addEventListener("click", () => {
        const repeat = cart.some((repeatProduct) => repeatProduct.id === product.id);
        if (repeat) {
            cart.map((prod) => {
                if (prod.id === product.id) {
                    prod.quanty++;
                    displayCartCounter();
                }
            });
        } else {
            cart.push({
                id: product.id,
                productName: product.productName,
                price: product.price,
                quanty: product.quanty,
                img: product.img,
            });
            displayCartCounter();
        }
        console.log(cart);
    });
});
