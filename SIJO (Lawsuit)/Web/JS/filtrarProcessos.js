/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$("#botaoFiltrarProcessos").click(function () {
    // Loopar por todos os TRs da tabela, deixando todos visiveis inicialmente (reset)
   $('#tabelaFiltro').children('tr').each(function () {
        $(this).show();
        //alert($(this).text());
        
        let aberto = $('#seletorAberto').find(":selected").val();
        let promovente = $('#seletorPromovido').find(":selected").val();
        let vencedor = $($('#seletorGanhou')).find(":selected").val();
        
        let procAberto = $(this).attr('aberta');
        let procPromovente = $(this).attr('promovente');
        let procVencedor =$(this).attr('vencedor');
        
        // Aberto ou Fechado
        if (aberto != "todos") {
            // Se seletor de aberto estiver em todos, não há nenhum cenário em que este será escondido.
            
            if (aberto != procAberto) {
                $(this).hide();
            }
        }
        
        // Promovente ou Promovido
        if (promovente != "todos") {
            // Se seletor de promovente estiver em todos, não há nenhum cenário em que este será escondido.
        
            if (promovente != procPromovente) {
                $(this).hide();
            }
       }
        
        // Vencedor ou Perdedor
        if (vencedor != "todos") {
            // Se seletor de vencedor estiver em todos, não há nenhum cenário em que este será escondido.
        
            if (vencedor != procVencedor) {
                $(this).hide();
            }
       }
        
    });
});