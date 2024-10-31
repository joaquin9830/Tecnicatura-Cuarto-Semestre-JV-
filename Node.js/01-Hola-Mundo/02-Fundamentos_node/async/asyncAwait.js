async function hello(name) {
    return new Promise(function (resolve, reject) {
        setTimeout(function () {
            console.log('Hello ' + name);
            resolve(name);
        }, 1000);
    });
}

async function talk(name) {
    return new Promise((resolve, reject) => {
        setTimeout(function () {
            console.log('bla bla bla bla');
            resolve(name);
        }, 1000);
    });
}

async function goodbye(name) {
    return new Promise((resolve, reject) => {
        setTimeout(function () {
            console.log('Goodbye ' + name);
            reject('There is an error'); // Intentionally causes an error
        }, 1000);
    });
}

async function main() {
    try {
        let name = await hello('Ariel');
        await talk(name);
        await talk(name);
        await talk(name);
        await goodbye(name);
        console.log('Process ends');
    } catch (error) {
        console.error('An error occurred:', error);
    }
}

// Calling the function
console.log('Starting the process...');
main();
console.log('Second instruction');
