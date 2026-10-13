# UC04 - Efetuar Cadastro
- Ator Principal: Cliente (Usuário Não Autenticado).

- Descrição: Permite que um novo visitante crie uma conta no sistema informando nome, e-mail e senha.

- Pré-condição: Nenhuma.

- Pós-condição: O novo usuário é persistido na base de dados (Usuario) e liberado para realizar login.

## Fluxo Principal
1. O cliente clica no link/botão "Cadastrar-se" ou é redirecionado durante o checkout.

2. O sistema exibe a página do formulário de cadastro (/cadastro).

3. O cliente preenche os campos Nome, E-mail e Senha, e clica no botão "Cadastrar".

4. O sistema valida os campos obrigatórios.

5. O sistema salva o novo usuário no banco de dados.

6. O sistema redireciona o cliente para a página de Login (/login) com mensagem de sucesso.

## Fluxos Alternativos e Exceções
### FA01 - E-mail Já Cadastrado:

- 4a. O sistema verifica que o e-mail informado já existe no banco de dados.

- 4b. O sistema exibe uma mensagem de alerta no formulário: "Este e-mail já está em uso." e mantém os campos preenchidos.

### FE01 - Campos Obrigatórios Não Preenchidos:

- 4a. O sistema identifica que algum campo obrigatório está em branco.

- 4b. A validação do HTML/Browser ou Controller impede o envio e solicita o preenchimento.