function validarCampos() {

	numero = document.getElementById('numero').value;
	tipo = document.getElementById('tipo').value;
	if (numero === '') {
		alert('Numero deve ser informado')
		return false
	} else if(tipo === ''){
		alert('Tipo deve ser informado')
		return false
	}

	return true;
}