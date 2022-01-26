/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




let current = 0;

$('#seletorDeFases').change(function () {
    
    if (current > 0) {
        $('[faseBloco=' + current + ']').hide();
    }
    
    let value = $(this).find(":selected").val();
    
    current = value;
    $('[faseBloco=' + value + ']').removeAttr('hidden');
    $('[faseBloco=' + value + ']').show();
    
    $('#respostaSeletorFase').attr("value",  $('[faseBloco=' + value + ']').attr("idFase"));
    
});


$( document ).ready(function() {
    var qtd = 1;
    current = qtd;
    
    $('[faseBloco=' + qtd + ']').removeAttr('hidden');
    $('[faseBloco=' + qtd + ']').show(); 
    
    $('#respostaSeletorFase').attr("value",  $('[faseBloco=' + qtd + ']').attr("idFase"));
});


// Seletor Vencedor e Perdedor
$('#seletorVencedor').change(function () {
    let value = $(this).find(":selected").index();
    $('#perdedor').val($('#seletorVencedor option').eq(1-value).val());
});

$( document ).ready(function() {
    let value = $(this).find(":selected").index();
    $('#perdedor').val($('#seletorVencedor option').eq(1-value).val());
});

// Limit File Size for PDF
$(function(){
    var fileInput = $('.upload-file');
    var maxSize = fileInput.data('max-size');
    $('.upload-form').submit(function(e){
        if(fileInput.get(0).files.length){
            var fileSize = fileInput.get(0).files[0].size; // in bytes
            if(fileSize>maxSize){
                alert('Tamanho do arquivo é maior que o permitido.');
                return false;
            }
        }else{
            alert('Um arquivo deve ser escolhido.');
            return false;
        }
        
    });
});