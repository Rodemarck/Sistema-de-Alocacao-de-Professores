const RegrasTemplate= `
    <div class="container">
        <div class="row">
            <div class="col">
                <h1 class="mt-3 text-center color-dark font-weigh-700">Regras do Sistema</h1>
                <hr class="title-row">
            </div>
        </div>
        <div class="row">
            <div class="col">
                <hr>
                <ul class="text-center">
                    <li class="d-inline-block my-1 mx-2"><button v-on:click="mostrarForm('1')" id="btn-tPref" class="btn btn-outline-dark">Tempo de preferência</button></li>
                    <li class="d-inline-block my-1 mx-2"><button v-on:click="mostrarForm('2')" id="btn-hPref" class="btn btn-outline-dark">Número de Preferências</button></li>
                    <li class="d-inline-block my-1 mx-2"><button v-on:click="mostrarForm('3')" id="btn-hMaxMin" class="btn btn-outline-dark">Horário Máximo e Mínimo</button></li>
                    <li class="d-inline-block my-1 mx-2"><button v-on:click="mostrarForm('4')" id="btn-qtdCad" class="btn btn-outline-dark">Quantidade de Cadeiras</button></li>
                </ul>
                <hr>
            </div>
        </div>
        <div id="tempPref" class="row d-none">
            <div class="col-sm-9 m-auto">
                <div class="card"> 
                    <div class="card-body text-center  color-dark">
                        <h4 class="card-title">Tempo de Preferência</h4>
                        <h6 style="font-size: 18px" class="card-subtitle my-3 mb-5 text-muted">Defina o tempo que cada professor pode ter com uma cadeira que tem preferência</h6>
                        <form action="" method="">
                            <label for="tempoPreferencia">Tempo: </label>
                            <select class="tamanho-input" name="tempoPreferencia" id="iTempoPreferencia">
                                <option value="1">2 Períodos</option>
                                <option value="2">4 Períodos</option>
                                <option value="3">6 Períodos</option>
                                <option value="4">8 Períodos</option>
                                <option value="5">10 Períodos</option>
                            </select>
                            <button id="btn-mudar" class="ml-5 btn btn-dark">Alterar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="numPref" class="row d-none">
            <div class="col-sm-9 m-auto">
                <div class="card"> 
                    <div class="card-body text-center  color-dark">
                        <h4 class="card-title">Número de Preferências</h4>
                        <h6 style="font-size: 18px" class="card-subtitle my-3 mb-5 text-muted">Defina o número de disciplinas que cada professor pode ter preferência</h6>
                        <form action="" method="">
                            <label for="tempoPreferencia">Disciplinas: </label>
                            <select class="tamanho-input" name="numeroPreferencia" id="iNumeroPreferencia">
                                <option value="1">Uma</option>
                                <option value="2">Duas</option>
                                <option value="3">Três</option>
                                <option value="4">Quatro</option>
                                <option value="5">Cinco</option>
                            </select>
                            <button id="btn-mudar" class="ml-5 btn btn-dark">Alterar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="horMaxMin" class="row d-none">
            <div class="col-sm-9 m-auto">
                <div class="card"> 
                    <div class="card-body text-center  color-dark">
                        <h4 class="card-title">Horário Máximo e Mínimo</h4>
                        <h6 style="font-size: 18px" class="card-subtitle my-3 mb-5 text-muted">Defina o horário máximo e mínimo??</h6>
                        <form action="" method="">
                            <label for="max">Horário Máximo</label>
                            <select name="max" id="iMax">
                                <option value="">Duas horas</option>
                                <option value="">Quatro horas</option>
                                <option value="">Seis horas</option>
                            </select>
                            <button id="btn-mudar" class="ml-5 btn btn-dark">Alterar</button>
                            <br>
                            <label class="mt-2" for="min">Horário Mínimo</label>
                            <select name="min" id="iMin">
                                <option value="">Zero</option>
                                <option value="">Duas horas</option>
                                <option value="">Quatro horas</option>
                            </select>
                            <button id="btn-mudar" class="ml-5 btn btn-dark">Alterar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="qtdCad" class="row d-none">
            <div class="col-sm-9 m-auto">
                <div class="card"> 
                    <div class="card-body text-center  color-dark">
                        <h4 class="card-title">Quantidade de Cadeiras</h4>
                        <h6 style="font-size: 18px" class="card-subtitle my-3 mb-5 text-muted">Defina a quantidade de cadeiras que cada professor pode ter</h6>
                        <div class="col-sm-7 m-auto">
                            <form action="">
                                <table class="tabelinha text-center my-3">
                                    <tr>
                                        <th id="emBranco"></th>
                                        <th>Máximas</th>
                                        <th>Mínimas</th>
                                    </tr>
                                    <tr>
                                        <th>Professor</th>
                                        <td>4</td>
                                        <td>1</td>
                                    </tr>
                                    <tr>
                                        <th>Supervisor</th>
                                        <td>3</td>
                                        <td>1</td>
                                    </tr>
                                    <tr>
                                        <th>Coordenador</th>
                                        <td>2</td>
                                        <td>1</td>
                                    </tr>
                                </table>
                                <button id="btn-mudar" class="btn btn-dark">Alterar</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="text-center my-4">
            <button id="btn-voltar" class="btn btn-outline-dark">Voltar</button>
        </div>
    </div>
    `

export {RegrasTemplate}