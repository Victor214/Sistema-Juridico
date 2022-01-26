$('#selectAcao').change(function () {
    let value = $(this).find(":selected").val();
    
    if (value == 1) {
        $('#divJustificativa').hide();
        $('#divIntimacao').hide();
        $('#divVencedor').hide(); 
    } else if (value == 2) {
        $('#divJustificativa').removeAttr('hidden');
        $('#divJustificativa').show();
        
        $('#divIntimacao').hide();
        $('#divVencedor').hide();
    } else if (value == 3) {
        $('#divIntimacao').removeAttr('hidden');
        $('#divIntimacao').show();
        
        $('#divVencedor').hide();
        $('#divJustificativa').hide();
    } else if (value == 4) {
        $('#divVencedor').removeAttr('hidden');
        $('#divVencedor').show();
        
        $('#divIntimacao').hide(); 
        $('#divJustificativa').hide();
    }
    
});