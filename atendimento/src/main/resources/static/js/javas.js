// ===============================================
// VARIÁVEIS GLOBAIS
let isMapsApiLoaded = false;
let mapa = null; 

// NOVAS VARIÁVEIS GLOBAIS PARA OS BOTÕES (ESCOPO GLOBAL)
let btnLocalizarUnidade = null;
let btnConfirmar = null;
let btnLocalizarNovamente = null;

// ... outras variáveis globais

let modalImagem = null;
let fecharModal = null;
let btnFullscreen = null; // O "Botão 2"

// Variável global para armazenar a unidade selecionada
let unidadeSelecionada = null;

// ===============================================

// ===============================================
// FUNÇÃO DE CALLBACK DA API (GLOBAL)
function initMap() {
    console.log("Google Maps API carregada com sucesso.");
    isMapsApiLoaded = true;
    
    // Localização padrão a ser exibida na tela
    inicializarMapaUnidade(-23.558358182719438, -46.74751927424848, 'Oeste');
}
// ===============================================

// ===============================================
// FUNÇÕES DE GERENCIAMENTO DE ESTADO E EVENT LISTENERS (ESCOPO GLOBAL)

// FUNÇÃO PARA INICIALIZAR OS BOTÕES APÓS O CARREGAMENTO DO DOM
document.addEventListener('DOMContentLoaded', () => {
    // Captura as referências dos botões
    btnLocalizarUnidade = document.getElementById('btn-localizar-unidade');
    btnConfirmar = document.getElementById('btn-confirmar');
    btnLocalizarNovamente = document.getElementById('btn-localizar-novamente');

    modalImagem = document.getElementById('modal-imagem');
    fecharModal = document.querySelector('.fechar-modal');
    btnFullscreen = document.getElementById('btn-fullscreen'); // O "Botão 2"

    if (btnFullscreen) {
        btnFullscreen.addEventListener('click', abrirModal);
    }

    if (fecharModal) {
        fecharModal.addEventListener('click', fecharModalFunc);
    }

    window.addEventListener('click', fecharModalFora);

    if (btnLocalizarUnidade) {
        btnLocalizarUnidade.addEventListener('click', calc);
    }

    // Adiciona os event listeners para os novos botões
    if (btnConfirmar) {
        btnConfirmar.addEventListener('click', confirmarBusca);
    }
    if (btnLocalizarNovamente) {
        btnLocalizarNovamente.addEventListener('click', localizarNovamente);
    }

    // NOVO: Função para adicionar o evento de clique nas linhas da tabela
function adicionarEventosTabela() {
    const linhas = document.querySelectorAll('#corpo-tabela-unidades tr');
    
    linhas.forEach(linha => {
        linha.addEventListener('click', function() {
            // 1. Remove o destaque de todas as linhas
            linhas.forEach(l => l.classList.remove('selecionada'));
            
            // 2. Adiciona o destaque à linha clicada
            this.classList.add('selecionada');
            
            // 3. Armazena a unidade selecionada (usando o ID do banco de dados)
            unidadeSelecionada = {
                id: this.getAttribute('data-unidade-id'),
                nome: this.querySelector('td:first-child').textContent // Pega o nome da primeira coluna
            };
            
            // 4. Habilita o botão Confirmar (se a busca já tiver sido feita)
            if (btnLocalizarUnidade.disabled) { // Verifica se estamos no estado de resultado
                btnConfirmar.disabled = false;
            }
        });
    });
}

});

// Estado 1: Antes da busca (Padrão)
function estadoInicial() {
    if (btnLocalizarUnidade) btnLocalizarUnidade.disabled = false;
    if (btnConfirmar) btnConfirmar.disabled = true;
    if (btnLocalizarNovamente) btnLocalizarNovamente.disabled = true;
}

// Estado 2: Após a busca (Resultado exibido)
function estadoResultadoExibido() {
    if (btnLocalizarUnidade) btnLocalizarUnidade.disabled = true;
    if (btnConfirmar) btnConfirmar.disabled = false;
    if (btnLocalizarNovamente) btnLocalizarNovamente.disabled = false;
}

// Estado 3: Após a confirmação
function estadoConfirmado() {
    if (btnLocalizarUnidade) btnLocalizarUnidade.disabled = true;
    if (btnConfirmar) btnConfirmar.disabled = true;
    if (btnLocalizarNovamente) btnLocalizarNovamente.disabled = false;
}

// NOVAS FUNÇÕES DE AÇÃO
function confirmarBusca(event) {
    if (event) {
        event.preventDefault(); 
    }

    console.log("Busca Confirmada!");
    // Lógica de confirmação (a ser implementada)
    estadoConfirmado();
}

function localizarNovamente(event) {
    if (event) {
        event.preventDefault(); 
    }
    console.log("Localizar Novamente acionado. Retornando ao estado inicial.");
    // Limpar o resultado da busca
    const elementoDestino = document.getElementById('resultado-operacao');
    if (elementoDestino) {
        elementoDestino.innerHTML = '';
    }
    // Sugestão de Melhoria: Retornar o mapa ao estado inicial
    inicializarMapaUnidade(-23.558358182719438, -46.74751927424848, 'Oeste');
    estadoInicial();
}
// ===============================================
// Função para abrir o modal

function abrirModal() {
    if (modalImagem) {
        modalImagem.style.display = "flex"; // Agora ele usa flex SÓ quando é aberto
    }
}


// Função para fechar o modal (clicando no X)
function fecharModalFunc() {
    if (modalImagem) {
        modalImagem.style.display = "none";
    }
}

// Função para fechar o modal (clicando fora)
function fecharModalFora(event) {
    if (event.target === modalImagem) {
        modalImagem.style.display = "none";
    }
}

// ===============================================
// FUNÇÃO DE CLIQUE DO BOTÃO (calc)
function calc(event) {

    if (event) {
        event.preventDefault(); 
    }
    // Validação (Você pode comentar este bloco para testar sem a API Key)
    if (!isMapsApiLoaded) {
        console.error("A API do Google Maps ainda não foi carregada. Tente novamente em um momento.");
        return; 
    }

    // Obtenção das coordenadas e criação do mapa
    const localizacao = buscarCoordenadas();
    inicializarMapaUnidade(localizacao.lat, localizacao.lng, localizacao.nome);

    // Obtenção do campo a ser preenchido e criação do conteúdo resultado
    const elementoDestino = document.getElementById('resultado-operacao');
    insercaoHtml(elementoDestino);

    function calc(event) {
    // ... (código existente)
    
    // Obtenção do campo a ser preenchido e criação do conteúdo resultado
    const elementoDestino = document.getElementById('resultado-operacao');
    insercaoHtml(elementoDestino);
    
    // NOVO: Adiciona os eventos de clique na tabela
    adicionarEventosTabela(); 
    
    // Altera o estado dos botões após a busca
    estadoResultadoExibido();
    
    // NOVO: Desabilita o Confirmar até que uma unidade seja selecionada
    btnConfirmar.disabled = true; 
}

    
    // NOVO: Altera o estado dos botões após a busca
    estadoResultadoExibido();
}
// ===============================================

// ===============================================
// FUNÇÃO DE BUSCAR COORDENADAS (Mantenha o restante do seu código original a partir daqui)
function buscarCoordenadas(){
    // Obtenção do bairro selecionado pelo usuário
    const bairroSelecionado = document.getElementById('bairro-select').value;

    // Coordenadas das unidades de atendimento
    const coordenadas = {
        'alto-de-pinheiros': { lat: -23.53764575547712, lng: -46.72224011888347, nome: 'UBS Alto de Pinheiros Doutor Suel Abujamra' },
        'barra-funda': {lat: -23.530993665487262, lng: -46.65291270354404, nome: 'Pronto Socorro Municipal Dr. Álvaro de Dino Almeida'},
        'butanta': {lat: -23.564830377904578, lng: -46.74054512841108, nome: 'Hospital Universitário da Universidade de São Paulo'},
        'itaim-bibi': {lat: -23.59038894453855, lng: -46.67350851946824, nome: 'Hospital São Luiz Itaim'},
        'jaguare': {lat: -23.544330289586966, lng: -46.75344593789512, nome: 'UBS Caju'},
        'jardim-paulista': {lat: -23.57308614130675, lng: -46.66804195314559, nome: 'Sírio-Libanês São Paulo | Unidade Jardins'},
        'lapa': {lat: -23.53727966471041, lng: -46.72233764907496, nome: 'UPA III Lapa'},
        'morumbi': {lat: -23.599561960725918, lng: -46.71513019478378, nome: 'Pronto Atendimento Hospital Israelita Albert Einstein'},
        'perdizes': {lat: -23.532749526438113, lng: -46.67553886328496, nome: 'Unidade Einstein Perdizes em São Paulo SP'},
        'pinheiros': {lat: -23.557484067682726, lng: -46.66977921130554, nome: 'Hospital das Clínicas FMUSP'},
        'pompeia': {lat: -23.533991704601476, lng: -46.68822825908507, nome: 'Hospital São Camilo SP - Pronto Socorro | Unidade Pompeia'},
        'rio-pequeno': {lat: -23.5756961495114, lng: -46.764404881278864, nome: 'UPA Rio Pequeno'},
        'vila-leopoldina': {lat: -23.53131568960534, lng: -46.72064649189306, nome: 'UBS Parque da Lapa'},
        'vila-madalena': {lat: -23.553544287880303, lng: -46.69111765766976, nome: 'UBS Dr. Manoel Joaquim Pera'},
        'vila-sonia': {lat: -23.597893393131073, lng: -46.73470171963715, nome: 'AMA/UBS Vila Sonia'}
    };

    return coordenadas[bairroSelecionado];
}
// ===============================================

// ===============================================
// FUNÇÃO PARA INSERIR NOVO HTML
function insercaoHtml(elementoDestino){
    // Define o conteúdo HTML a ser inserido no bloco resultado
    // A SER MODIFICADO POSTERIORMENTE PARA SE ADEQUAR AO BAIRRO ESCOLHIDO
    const novoConteudoHTML = `
        <h3 id="titulo-recomendacao">Recomendação:</h3>
        <div class="unidade-destaque">
            <p class="nome-unidade">UPA Lapa - Lapa</p>
            <p>Leitos livres: <strong>1</strong></p>
            <p>Lotação atual: <span class="lotacao-critica">98%</span></p>
        </div>
    `;
    
    // Insere o novo conteúdo dentro do bloco resultado
    if (elementoDestino) {
        elementoDestino.innerHTML = novoConteudoHTML;
    }
}
// ===============================================

// ===============================================
// FUNÇÃO QUE EXIBE O MAPA
function inicializarMapaUnidade(lat, lng, titulo) {
    const centro = { lat: lat, lng: lng };
    const mapaContainer = document.getElementById('mapa-container');
    
    // Validação do container
    if (!mapaContainer) {
        console.error("Elemento container do mapa não encontrado.");
        return;
    }

    // Define o zoom com base no contexto
    let zoomLevel = 14; // Zoom padrão 
    if (titulo === 'Oeste') {
        zoomLevel = 13; // Zoom para visão mais ampla
    }

    // Cria a instância do mapa a partir das coordenadas
    mapa = new google.maps.Map(mapaContainer, {
        zoom: zoomLevel,
        center: centro,
    });
    
    // Adiciona o marcador no mapa a partir das coordenadas
    if (titulo !== 'Oeste') {
        new google.maps.Marker({
            position: centro,
            map: mapa,
            title: titulo
        });
    }
}
// ===============================================
