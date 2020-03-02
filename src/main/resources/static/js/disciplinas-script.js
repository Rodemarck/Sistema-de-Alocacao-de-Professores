var origin = undefined;
var tabela;
const cores = [
    '#373a07',
    '#700202',
    '#4b1a39',
    '#9e6105',
    '#140458'
];
function compila() {
    eval(document.querySelector('#codigo').innerText);
    tabela = document.querySelector('#tabela-dinamica');
    popula(periodo);
}
function encode(params) {
    return Object
        .keys(params)
        .map(key => key + "=" + encodeURIComponent(params[key]))
        .join('&');
}
const http = {
    get:(url,data, callback = atualiza, err = log) =>{
        url += ('?' + encode(data));
        let request = new XMLHttpRequest();
        request.open('GET', url, true);
        request.onload = () =>{
            if (request.status >= 200 && request.status < 400)
                callback(request.responseText);
            else
                err(request);
        };
        request.onerror = err;
        request.send();
    },
    post:(url,data, callback = atualiza, err = log) =>{
        let request = new XMLHttpRequest();
        request.open('POST', url, true);
        request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        request.onload = ()=>{
            if(request.status >= 200 && request.status < 400)
                callback(request.responseText);
            else
                err(request);
        };
        request.send(encode(data));
    }
}
function normaliza(el){
    if(el.classList.contains('disciplina'))
        return el;
    if(el.classList.contains('text'))
        return el.parentElement;
    if(el.classList.contains('espaco-disciplina'))
        return el.firstElementChild;
    console.log('deu uma merda muita louca>>>>>>');
    console.log(el.classList);
    return null;
}
function troca(e1,e2){
    console.log('trocando')
    let dados = {
        idPeriodo:periodo.id,
        n1:e1.dataset.numero,
        n2:e2.dataset.numero
    };
    console.log(dados)
    http.post('/trocarDiscplina',dados,
        () => http.get('/atualizarSlots',{idPeriodo:periodo.id}));
}
function atualiza (response) {
    document.querySelector('#div-disciplinas').innerHTML = response;
    compila();
}
const log = err => console.log(err);
window.onload = () =>{
    http.get('/atualizarSlots', {idPeriodo:1},
        fragment =>{
            document.querySelector('#conteudo').innerHTML = fragment;
            compila();
        }
    )
}
function dblclickDisicplina(node ) {
    node = normaliza(node)
    if(node.dataset.nome !== undefined)
        showDisciplinaModal(node);
}
function showDisciplinaModal(node) {
    let modal = document.querySelector('#modal-disciplina');
    let select = modal.querySelector('#modal-professor');
    console.log('removendo')
    for(let i = select.options.length-1 ; i >= 0 ; i--)
        select.remove(i);
    let list = [];
    if(node.dataset.area === 'FC')
        list = profs.fc;
    else if(node.dataset.area === 'ARQ')
        list = profs.arq;
    else if(node.dataset.area == 'ENSISO')
        list = profs.ensiso;
    else
        list = profs.todos;
    console.log('listando')
    list.map(el =>{
        let option = document.createElement('option');
        option.value = el.id;
        option.text = el.nome;
        select.add(option);
    })
    select.selectedIndex = -1;
    modal.querySelector('.modal-title').value = 'informações da disciplina';
    modal.querySelector('#modal-nome').value = node.dataset.nome;
    modal.querySelector('#modal-codigo').value = node.dataset.codigo;
    modal.querySelector('#modal-turma').value = node.dataset.turma
    modal.querySelector('#modal-cor').value = node.dataset.cor;
    modal.querySelector('#modal-tipo').value = node.dataset.tipo;
    modal.querySelector('#recipient-area').value = node.dataset.area;
    modal.querySelector('#modal-curso').value = node.dataset.curso;
    modal.querySelector('#modal-periodo').value = node.dataset.periodo;
    $('#modal-disciplina').modal('show');
}
const mudaPeriodo = id => http.get('/atualizarSlots', {idPeriodo:id});
function mudaCurso(idCurso) {
    var parametros = {
        idCurso: idCurso,
        nPeriodo: periodo.nPeriodo,
        ano:periodo.anoLetivo
    };
    http.get('/mudaCurso', parametros);
}
function salvar() {
    let idProf = document.querySelector('#modal-professor').value;
    let idDisc = document.querySelector(' #modal-nome').value;
    if (idProf!= null && idDisc==null) {
        let param1 = {
            idPeriodo:periodo.id,
            idProfessor:idProf,
            iDisciplina:idDisc
        }
        http.post("/professor/alocar",param1,
            () => http.get('/atualizarSlots', {idPeriodo:periodo.id})
        )
    }
}
function allowDrop() {
    this.event.preventDefault();
}
function drag(node) {
    origin = normaliza(node);
}
function drop(node) {
    console.log('drop')
    let node_ = normaliza(node);
    if (origin != undefined && origin !== node_
        && (origin.dataset.nome !== undefined && node_.dataset.nome !== undefined))
        troca(origin,node_);
}
function offset() {
    let menor = 999;
    periodo.slot.subSlots.forEach(el =>{
        if(el.horario < menor)
            menor = el.horario;
    })
    tabela.scrollTop = tabela.querySelector('#x0y'+menor).offsetTop - 65;
}
function popula(periodo){
    console.log('populando...')
    periodo.slot.subSlots.forEach(el =>{
        let div = tabela.querySelector('#x' + el.dia + 'y' + el.horario);
        let disc = periodo.espacos[el.numero-1].disciplina;
        div.dataset.nome = disc.nome;
        div.dataset.codigo = disc.codigo;
        div.dataset.tipo = disc.tipo;
        div.dataset.area = disc.area;
        div.dataset.id_ = disc.id;
        div.dataset.numero = el.numero
        div.style.backgroundColor = cores[el.numero-1];
        div.querySelector('span').innerText = disc.nome;
     });
    offset();
}