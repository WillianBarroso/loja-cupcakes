# UC05 - Efetuar Login
- Ator Principal: Cliente.

- Descrição: Autentica o usuário na aplicação comparando e-mail e senha com os registros do banco de dados e cria a sessão HTTP (usuarioLogado).

- Pré-condição: O usuário deve possuir cadastro prévio no sistema.

- Pós-condição: A sessão do usuário é iniciada (HttpSession) e o cliente ganha acesso ao checkout.

## Fluxo Principal
- O cliente acessa a tela de Login (/login).

- O sistema exibe a interface com os campos de E-mail e Senha.

- O cliente preenche as credenciais e clica em "Entrar".

- O sistema consulta o repositório (UsuarioRepository.findByEmailAndSenha) para verificar a validade dos dados.

- O sistema registra o objeto Usuario na HttpSession com a chave usuarioLogado.

- O sistema redireciona o cliente para a Página Inicial (/).

## Fluxos Alternativos e Exceções
### FA01 - Redirecionamento Pós-Checkout (redirect=checkout):

- 1a. O cliente veio do fluxo de checkout (UC06).

- 5a. Após autenticar com sucesso, o sistema identifica a flag redirect=checkout.

- 6a. O sistema redireciona o cliente diretamente para /checkout em vez da home.

### FE01 - Credenciais Inválidas:

- 4a. O sistema não encontra nenhum usuário com a combinação de e-mail e senha informada.

- 4b. O sistema recarrega a página de login e exibe a mensagem de erro: "E-mail ou senha inválidos.".