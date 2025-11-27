// Mascara do telefone
var SPMaskBehavior = function (val) {
    return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
},
    spOptions = {
        onKeyPress: function (val, e, field, options) {
            field.mask(SPMaskBehavior.apply({}, arguments), options);
        }
    };

$('#telefone').mask(SPMaskBehavior, spOptions);

// Array de alunos
var alunos = []
var cursos = []

function loadCursos(){
    $.ajax({
        url: "http://localhost:8080/cursos",
        type: "GET",
        async: false,
        success: (response) => {
            cursos = response;
            for (var cur of cursos){
                document.getElementById("inputCurso").innerHTML += `<option value=${cur.id}>${cur.nome}</option>`;
            }
        }
    })
}

loadCursos();
loadAlunos();

function loadAlunos() {
    $.getJSON("http://localhost:8080/alunos", response => {
        alunos = response;
        for (let aluno of alunos) {
            addNewRow(aluno);
        }
    })
}


function addNewRow(aluno) {
    var table = document.getElementById("tabela");

    var newRow = table.insertRow();

    var idNode = document.createTextNode(aluno.id);
    var nomeNode = document.createTextNode(aluno.nome);
    var emailNode = document.createTextNode(aluno.email);
    var telefoneNode = document.createTextNode(aluno.telefone);
    var cursoNode = document.createTextNode(cursos[aluno.idCurso - 1].nome);
    var turnoNode = document.createTextNode(aluno.periodo == 1 ? "Manhã" : aluno.periodo == 2 ? "Tarde" : "Noite");

    newRow.insertCell().appendChild(idNode);
    // Nome do aluno
    newRow.insertCell().appendChild(nomeNode);

    // Email do aluno responsivo
    var cell1 = newRow.insertCell(); 
    cell1.className="d-none d-md-table-cell";
    cell1.appendChild(emailNode);

    // Telefone do aluno
    var cell2 = newRow.insertCell();
    cell2.className="d-none d-md-table-cell";
    // newRow.insertCell().appendChild(telefoneNode);
    cell2.appendChild(telefoneNode);

    // Curso do aluno
    var cell3 = newRow.insertCell();
    cell3.className="d-none d-md-table-cell";
    //newRow.insertCell().appendChild(cursoNode);
    cell3.appendChild(cursoNode);

    // Turno do aluno
    var cell4 = newRow.insertCell();
    cell4.className="d-none d-md-table-cell";    
    //newRow.insertCell().appendChild(turnoNode);
    cell4.appendChild(turnoNode);
}


function save() {
    var radioPeriodo = document.querySelector('input[name="turno"]:checked');

    var aluno = {
        id: alunos.length + 1,
        nome: document.getElementById("inputName").value,
        telefone: document.getElementById("telefone").value,
        email: document.getElementById("inputEmail").value,
        idCurso: document.getElementById("inputCurso").value,
        periodo: radioPeriodo ? parseInt(radioPeriodo.value) : 0
    }

    $.ajax({
        url: "http://localhost:8080/alunos",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(aluno),
        success: (alunoSalvo) => {
            addNewRow(alunoSalvo);
            alunos.push(alunoSalvo);
            document.getElementById("formulario").reset();
        }
    });
}

