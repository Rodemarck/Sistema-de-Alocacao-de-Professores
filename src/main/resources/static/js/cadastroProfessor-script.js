function serialize (e){for(var n=[],t=0;t<e.elements.length;t++){var o=e.elements[t];if(o.name&&!o.disabled&&"file"!==o.type&&"reset"!==o.type&&"submit"!==o.type&&"button"!==o.type)if("select-multiple"===o.type)for(var p=0;p<o.options.length;p++)o.options[p].selected&&n.push(encodeURIComponent(o.name)+"="+encodeURIComponent(o.options[p].value));else("checkbox"!==o.type&&"radio"!==o.type||o.checked)&&n.push(encodeURIComponent(o.name)+"="+encodeURIComponent(o.value))}return n.join("&")};
function cadastra(form){
    console.log('enviadno')
    let request = new XMLHttpRequest();
    request.open('POST','/variavel/professores');
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    request.onerror = () => console.log(request);
    request.onload = () =>{
        if(request.status >= 200 && request.status <=  400)
            window.location.href = '/';
        else
            alert('deu erro nessa merda');
    }
    request.send(serialize(form));
    return false
}