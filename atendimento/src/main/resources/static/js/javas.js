// ===============================================
// VARIÁVEIS GLOBAIS
let mapa = null;
let btnLocalizarUnidade = null;
let btnConfirmar = null;
let btnLocalizarNovamente = null;
let modalImagem = null;
let fecharModal = null;
let btnFullscreen = null;
// ===============================================

// ===============================================
// INICIALIZAÇÃO DO DOM
document.addEventListener('DOMContentLoaded', () => {
    btnLocalizarUnidade = document.getElementById('btn-localizar-unidade');
    btnConfirmar = document.getElementById('btn-confirmar');
    btnLocalizarNovamente = document.getElementById('btn-localizar-novamente');
    modalImagem = document.getElementById('modal-imagem');
    fecharModal = document.querySelector('.fechar-modal');
    btnFullscreen = document.getElementById('btn-fullscreen');

    if (btnFullscreen) btnFullscreen.addEventListener('click', abrirModal);
    if (fecharModal) fecharModal.addEventListener('click', fecharModalFunc);
    window.addEventListener('click', fecharModalFora);

	const formBusca = document.querySelector('form[action="/buscarHospital"]');
	if (formBusca) {
	    formBusca.addEventListener('submit', () => {
	        console.log("Formulário de busca enviado, busca iniciada...");
	        const localizacao = buscarCoordenadas();
	        if (localizacao) inicializarMapaUnidade(localizacao.lat, localizacao.lng, localizacao.nome);
	        // Espera um pouquinho para garantir que a página recarregue o Thymeleaf antes do estado mudar
	        setTimeout(estadoResultadoExibido, 200);
	    });
	}

    if (btnConfirmar) btnConfirmar.addEventListener('click', confirmarBusca);
    if (btnLocalizarNovamente) btnLocalizarNovamente.addEventListener('click', localizarNovamente);

});
// ===============================================

// ===============================================
// ESTADOS DOS BOTÕES
function estadoInicial() {
    if (btnLocalizarUnidade) btnLocalizarUnidade.disabled = false;
    if (btnConfirmar) btnConfirmar.disabled = true;
    if (btnLocalizarNovamente) btnLocalizarNovamente.disabled = true;
}

function estadoResultadoExibido() {
    if (btnLocalizarUnidade) btnLocalizarUnidade.disabled = true;
    if (btnConfirmar) btnConfirmar.disabled = false;
    if (btnLocalizarNovamente) btnLocalizarNovamente.disabled = false;
}

function estadoConfirmado() {
    if (btnLocalizarUnidade) btnLocalizarUnidade.disabled = true;
    if (btnConfirmar) btnConfirmar.disabled = true;
    if (btnLocalizarNovamente) btnLocalizarNovamente.disabled = false;
}
// ===============================================

// ===============================================
// AÇÕES DOS BOTÕES
function calc(event) {
    console.log("Busca iniciada...");

    // Atualiza o mapa conforme o bairro escolhido
    const localizacao = buscarCoordenadas();
    if (localizacao) {
        inicializarMapaUnidade(localizacao.lat, localizacao.lng, localizacao.nome);
    }

    // Habilita os botões após a busca
    estadoResultadoExibido();
}

function confirmarBusca(event) {
    console.log("Busca confirmada — leito ocupado registrado.");
    // Aqui, futuramente, pode ser feito um POST para atualizar o banco
    estadoConfirmado();
}

function localizarNovamente(event) {
    console.log("Nova busca iniciada.");
    inicializarMapaUnidade(-23.558358182719438, -46.74751927424848, 'Oeste');
    estadoInicial();
}
// ===============================================

// ===============================================
// MODAL DE IMAGEM
function abrirModal() {
    if (modalImagem) modalImagem.style.display = "flex";
}
function fecharModalFunc() {
    if (modalImagem) modalImagem.style.display = "none";
}
function fecharModalFora(event) {
    if (event.target === modalImagem) modalImagem.style.display = "none";
}
// ===============================================

// ===============================================
// LOCALIZAÇÃO POR BAIRRO
function buscarCoordenadas() {
    const bairroSelecionado = document.getElementById('bairro-select')?.value;
    const coordenadas = {
        'alto-de-pinheiros': { lat: -23.5376, lng: -46.7222, nome: 'UBS Alto de Pinheiros' },
        'barra-funda': { lat: -23.5309, lng: -46.6529, nome: 'PS Municipal Barra Funda' },
        'butanta': { lat: -23.5648, lng: -46.7405, nome: 'Hospital Universitário USP' },
        'pinheiros': { lat: -23.5574, lng: -46.6697, nome: 'Hospital das Clínicas FMUSP' },
        'pompeia': { lat: -23.5339, lng: -46.6882, nome: 'Hospital São Camilo Pompéia' },
        'morumbi': { lat: -23.5995, lng: -46.7151, nome: 'Hospital Albert Einstein' },
        'rio-pequeno': { lat: -23.5756, lng: -46.7644, nome: 'UPA Rio Pequeno' },
        'vila-leopoldina': { lat: -23.5313, lng: -46.7206, nome: 'UBS Parque da Lapa' },
        'vila-madalena': { lat: -23.5535, lng: -46.6911, nome: 'UBS Dr. Manoel Joaquim Pera' },
        'vila-sonia': { lat: -23.5978, lng: -46.7347, nome: 'AMA/UBS Vila Sônia' }
    };
    return coordenadas[bairroSelecionado];
}
// ===============================================

// ===============================================
// MAPA
function inicializarMapaUnidade(lat, lng, titulo) {
    const mapaContainer = document.getElementById('mapa-container');
    if (!mapaContainer) {
        console.error("Container do mapa não encontrado.");
        return;
    }

    const centro = { lat, lng };
    mapa = new google.maps.Map(mapaContainer, {
        zoom: titulo === 'Oeste' ? 13 : 15,
        center: centro
    });

    if (titulo !== 'Oeste') {
        new google.maps.Marker({
            position: centro,
            map: mapa,
            title: titulo
        });
    }
}
// ===============================================
