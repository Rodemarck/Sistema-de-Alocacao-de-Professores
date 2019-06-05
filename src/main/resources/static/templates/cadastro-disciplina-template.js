const CadastroDisciplinaTemplate = `
    <div class="container">
        <div class="row">
            <div class="col-12">
                <h1 class="mt-4 text-center color-dark font-weigh-700">Cadastro Disciplinas</h1>
                <hr class="title-row">
            </div>
        </div>
        <form action="">
            <div class="form-cadastro bg-dark col-lg-10 col-xl-9 m-auto pt-4 pb-4 px-5">
                <p class="text-center">Insira as informações da disciplina que deseja cadastrar</p>
                <hr style="background: #ebe9e9">
                <div class="form-row">
                    <label class="col-sm-2 col-form-label" for="iDisciplina">Disciplina</label>
                    <div class="col-sm-10 my-1 mb-4">
                        <input class="form-control tamanho-input p-1" type="text" name="disciplina" id="iDisciplina" placeholder="Nome da Disciplina" required>
                    </div>
                </div>
                <div class="form-row">
                    <label class="col-sm-2 col-form-label" for="iCpf">Código</label>
                    <div class="col-sm-4 my-1 mb-4">
                        <input class="form-control tamanho-input p-1" type="text" name="codigo" id="iCodigo" placeholder="Código da Disciplina" required>
                    </div>
                    <div class="col-sm-1"><!--apenas para espaçamento--></div>
                    <label class="col-sm-1 col-form-label" for="iArea">Área</label>
                    <div class="col-sm-4 my-1 mb-4">
                        <select class="form-control tamanho-input p-1" id="iArea">
                            <option>Arq</option>
                            <option>Ensiso</option>
                            <option>FC</option>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <label class="col-sm-2 col-form-label" for="iCurso">Curso</label>
                    <div class="col-sm-10 my-1 mb-4">
                        <select class="form-control tamanho-input p-1" id="iCurso">
                            <option value="bcc">Bacharelado em Ciência da Computação</option>
                            <option value="lc">Licenciatura em Computação</option>
                            <option value="ambas">Ambas</option>
                            <option value="ext">Externa</option>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <label class="col-sm-2 col-form-label" for="iTipo">Carga Horária</label>
                    <div class="col-sm-4 my-1 mb-4">
                        <select class="form-control tamanho-input p-1" id="iTipo">
                            <option value="">30</option>
                            <option value="">60</option>
                            <option value="">120</option>
                        </select>
                    </div>
                    <div class="col-sm-1"><!--apenas para espaçamento--></div>
                    <label class="col-sm-1 col-form-label" for="iArea">Título</label>
                    <div class="col-sm-4 my-1 mb-4">
                        <select class="form-control tamanho-input p-1" id="iArea">
                            <option>Graduação</option>
                            <option>Pós</option>
                        </select>
                    </div>
                </div>
                <div class="form-row mb-4">
                        <label class="col-sm-2 col-form-label" for="tipo" id="iTipo">Tipo</label>
                        <div class="col-sm-3 form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="tipo" id="iObr" value="obr" checked>
                            <label class="form-check-label" for="iObr">Obrigatória</label>
                        </div>
                        <div class="col-sm-3 form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="tipo" id="iOpt" value="opt">
                            <label class="form-check-label" for="iOpt">Optativa</label>
                        </div>
                        <div class="col-sm-3 form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="tipo" id="iExt" value="ext">
                            <label class="form-check-label" for="iExt">Externa</label>
                        </div>
                    </div>
                <hr style="background: #ebe9e9">
                <div v-if="width >= 576" class="row">
                    <div class="col-sm-6 text-right">
                        <button type="submit" id="btn-logar" class="btn btn-outline-light" value="Entrar">Cadastrar</button>
                    </div>
                    <div class="col-sm-6">
                        <button type="button" id="btn-voltar" class="btn btn-outline-light">Voltar</button>
                    </div>
                </div>
                <div v-else class="row">
                    <div class="col-sm-6 text-center mt-2 mb-3">
                        <button type="submit" id="btn-logar" class="btn btn-outline-light" value="Entrar">Cadastrar</button>
                    </div>
                    <div class="col-sm-6 text-center" >
                        <button type="button" id="btn-voltar" class="btn btn-outline-light">Voltar</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    `

export {CadastroDisciplinaTemplate}