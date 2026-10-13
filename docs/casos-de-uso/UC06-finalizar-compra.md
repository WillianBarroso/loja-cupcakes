# UC06 - Finalizar Compra (Checkout)

- Ator Principal: Cliente.

- Descrição: Processa a conclusão do pedido com os itens armazenados no carrinho mantido na sessão HTTP do usuário.

- Pré-condição: Existir pelo menos 1 (um) cupcake adicionado no carrinho (Carrinho.getItens().size() > 0).

- Pós-condição: O carrinho da sessão é esvaziado e a tela de confirmação do pedido é apresentada.

## Fluxo Principal

1. O cliente acessa a tela do Carrinho de Compras (/carrinho) com itens adicionados.

2. O cliente clica no botão "Finalizar Compra".

3. O sistema intercepta a requisição na rota /checkout e verifica a existência do atributo usuarioLogado na HttpSession.

4. O sistema confirma que o usuário está autenticado.

5. O sistema limpa a lista de itens do carrinho de sessão (carrinho.limpar()).

6. O sistema exibe a tela de confirmação (sucesso.html) com a mensagem: "Compra realizada com sucesso! Obrigado, [Nome do Usuário].".

## Fluxos Alternativos e Exceções

### FA01 - Usuário Não Autenticado:

- 4a. O sistema identifica que usuarioLogado é nulo na sessão.

- 4b. O sistema redireciona o usuário para a rota de login (/login?redirect=checkout).

- 4c. O usuário efetua o login com sucesso (UC05).

- d. O sistema retorna o usuário automaticamente para o passo 5 do fluxo principal do checkout.

### FE01 - Carrinho Vazio:

- 1a. O cliente tenta acessar /checkout sem nenhum item no carrinho.

- 1b. O sistema identifica o carrinho vazio e redireciona o cliente para a Home (/) com a mensagem: "Seu carrinho está vazio.".