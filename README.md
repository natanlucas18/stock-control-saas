# Stock Control SaaS
Sistema SaaS moderno para controle de estoque, desenvolvido com foco em escalabilidade, automação e usabilidade para pequenos e médios negócios.
- Sobre o Projeto
O Stock Control SaaS é uma aplicação web completa para gerenciamento de estoque que permite:
 Monitorar níveis de estoque em tempo real
 Gerenciar entradas, tranferências, devoluções e saídas de produtos
 Melhorar auditoria e controle operacional

🧱 Arquitetura
O projeto segue uma arquitetura baseada em separação clara de responsabilidades:
Backend
Java com Spring Boot
Arquitetura baseada em DDD (Domain-Driven Design)
API REST
Autenticação com JWT (Access + Refresh Token)
Cookies httpOnly para segurança
Frontend
Next.js (App Router)
React + Server/Client Components
UI moderna e responsiva
Infraestrutura
Integração com AWS (ex: S3 para arquivos/imagens)
Estrutura preparada para multi-tenant

🛠️ Tecnologias Utilizadas
Backend
Spring Boot
Spring Security
JWT
JPA
PostgreSQL
Docker
Frontend
Next.js
React
TailwindCSS
Zustand
React Query
Docker

📌 Funcionalidades
- Gestão de Estoque
Cadastro de produtos
Controle de entrada, tranferência, devolução e saída
Histórico de movimentações
Notificações Inteligentes
Alerta de estoque baixo
Alerta de estoque acima do limite
- Relatórios
Exportação em PDF
- Autenticação e Autorização
Login seguro
Tokens via cookies httpOnly
Controle de acesso por roles
- Segurança
Tokens armazenados em cookies httpOnly
Separação entre Access Token e Refresh Token
RBAC (Role-Based Access Control)
Boas práticas para evitar exposição de dados sensíveis

⚙️ Como rodar o projeto
 Pré-requisitos
Node.js 20+
Docker 
Banco de dados (PostgreSQL)

📦 Backend
cd backend

# configurar variáveis de ambiente


npm run start:dev

💻 Frontend
cd frontend
npm install
npm run dev

📈 Roadmap
  Multi-tenant completo
  Dashboard com métricas
  Receber notificações automáticas de estoque baixo/excedente
  Gerar relatórios (PDF)
  Imprimir etiquetas com rastreabilidade
 
- Possíveis Casos de Uso
Pequenos comércios
Estoques industriais
E-commerce
Almoxarifado interno

- Licença
Este projeto está sob a licença MIT.

- Considerações finais
Esse projeto vai além de um CRUD simples — ele já tem características reais de um produto SaaS pronto para evoluir, como:
separação de responsabilidades
preocupação com segurança
foco em escalabilidade
funcionalidades com valor de negócio

