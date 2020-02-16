const divs = [
    document.getElementById('tempPref'),
    document.getElementById('numPref'),
    document.getElementById('horMaxMin'),
    document.getElementById('qtdCad'),
    document.getElementById('extras')
];
const limpa = () => divs.forEach(el=>el.style.display = 'none');
const caso = {
    '1':()=>divs[0].style.display = 'block',
    '2':()=>divs[1].style.display = 'block',
    '3':()=>divs[2].style.display = 'block',
    '4':()=>divs[3].style.display = 'block',
    '5':()=>divs[4].style.display = 'block'
}
function mostrarForm (valor) {
    limpa();
    caso[valor]();
}