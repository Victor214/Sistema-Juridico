/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


 function fMasc(objeto,mascara) {
    obj=objeto;
    masc=mascara;
    setTimeout("fMascEx()",1);
}

 function fMascEx() {
    obj.value=masc(obj.value);
}

function mCPF(cpf){
    cpf=cpf.replace(/\D/g,"");
    cpf=cpf.replace(/(\d{3})(\d)/,"$1.$2");
    cpf=cpf.replace(/(\d{3})(\d)/,"$1.$2");
    cpf=cpf.replace(/(\d{3})(\d{1,2})$/,"$1-$2");
    return cpf;
}