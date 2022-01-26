/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$("#botaoJuiz").click(function () {
    $('#divJuiz').removeAttr('hidden');
    
    $("#divJuiz").show();
    $("#divAdvogado").hide();
    $("#divParte").hide();
    
    $("#botaoJuiz").css('background-color', 'rgba(9, 162, 255, 0.85)');
    $("#botaoJuiz").css('color', 'rgb(0,0,0)');
    
    $("#botaoAdvogado").css('background-color', 'transparent');
    $("#botaoAdvogado").css('color', 'rgb(70,87,101)');
    
    $("#botaoParte").css('background-color', 'transparent');
    $("#botaoParte").css('color', 'rgb(70,87,101)');
});

$("#botaoAdvogado").click(function () {
    $('#divAdvogado').removeAttr('hidden');
    
    $("#divJuiz").hide();
    $("#divAdvogado").show();
    $("#divParte").hide();
    
    $("#botaoJuiz").css('background-color', 'transparent');
    $("#botaoJuiz").css('color', 'rgb(70,87,101)');
    
    $("#botaoAdvogado").css('background-color', 'rgba(9, 162, 255, 0.85)');
    $("#botaoAdvogado").css('color', 'rgb(0,0,0)');
    
    $("#botaoParte").css('background-color', 'transparent');
    $("#botaoParte").css('color', 'rgb(70,87,101)');
});

$("#botaoParte").click(function () {
    $('#divParte').removeAttr('hidden');
    
    $("#divJuiz").hide();
    $("#divAdvogado").hide();
    $("#divParte").show();
    
    $("#botaoJuiz").css('background-color', 'transparent');
    $("#botaoJuiz").css('color', 'rgb(70,87,101)');
    
    $("#botaoAdvogado").css('background-color', 'transparent');
    $("#botaoAdvogado").css('color', 'rgb(70,87,101)');
    
    $("#botaoParte").css('background-color', 'rgba(9, 162, 255, 0.85)');
    $("#botaoParte").css('color', 'rgb(0,0,0)');
});